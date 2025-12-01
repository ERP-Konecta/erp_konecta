# DevOps Workflows Documentation

This directory contains GitHub Actions workflows for the Konecta ERP system's CI/CD pipelines. Each workflow is designed to ensure code quality, security, and automated deployment for different components of the system.

## Overview

The DevOps implementation follows industry best practices with automated testing, security scanning, code quality analysis, and containerized deployments. All workflows are designed to be efficient, maintainable, and provide comprehensive coverage of security and quality checks.

## Workflow Structure

### Service-Specific Workflows

Each major service has its own dedicated workflow file:

- **`backend.yml`** - Authentication service CI/CD pipeline
- **`hr-service.yml`** - HR service CI/CD pipeline
- **`finance-service.yml`** - Finance service CI/CD pipeline
- **`frontend-ci.yml`** - Angular frontend CI/CD pipeline
- **`python-services.yaml`** - Unified workflow for all Python-based services

### Why One Workflow Per Service?

We maintain separate workflows for each service for the following reasons:

1. **Independent Reports and Artifacts**
   - Each service generates its own security reports, quality gate results, and build artifacts
   - Easier to track issues and metrics per service
   - Clear separation of concerns

2. **Simplified Maintenance**
   - Each workflow can be updated independently without affecting others
   - Easier debugging when issues are isolated to a specific service
   - Service-specific configurations and secrets can be managed separately

3. **Team Collaboration**
   - Different team members work on different services
   - Parallel development without workflow conflicts
   - Independent deployment schedules

4. **Service-Specific Tokens and Secrets**
   - SonarQube generates separate tokens for each project being scanned
   - Each service may require different environment variables and secrets
   - Better security isolation

## Key DevOps Optimizations

### 1. Using `uv` Instead of `pip` in Python Dockerfiles

We replaced `pip` with **`uv`** (by Astral) in all Python service Dockerfiles to significantly enhance build speed and efficiency.

**Benefits:**
- **10-100x faster** dependency resolution and installation
- Better dependency conflict resolution
- Lock file support for reproducible builds
- Reduced Docker build time

**Implementation Example:**
```dockerfile
# Install uv for faster dependency installation
COPY --from=ghcr.io/astral-sh/uv:latest /uv /usr/local/bin/uv

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies using uv
RUN uv pip install --system --no-cache -r pyproject.toml
```

This optimization has resulted in dramatically faster Python service builds, reducing CI/CD pipeline execution time.

### 2. Caching SonarQube Packages

Caching SonarQube packages is crucial for reducing workflow execution time. SonarQube scanners download and cache various analysis tools and plugins, which can be time-consuming on every run.

**Cache Configuration:**
```yaml
- name: Cache SonarQube packages
  uses: actions/cache@v4
  with:
    path: ~/.sonar/cache
    key: ${{ runner.os }}-sonar-${{ matrix.service }}
    restore-keys: ${{ runner.os }}-sonar
```

**Performance Impact:**

| Scenario | Execution Time |
|----------|---------------|
| **Before Cache** | ~7 minutes |
| **After Cache** | ~19 seconds |

**Why Caching Matters:**
- SonarQube scanner downloads Java runtime and analysis tools
- Plugin downloads and updates are cached
- Significant reduction in network bandwidth usage
- Faster feedback loop for developers

The cache is keyed by operating system and service name, ensuring each service has its own cache while allowing for cache sharing when appropriate.

### 3. Caching OWASP Dependency-Check Database

OWASP Dependency-Check maintains a local database of vulnerability information that needs to be downloaded and updated regularly. Caching this database saves significant time.

**Cache Configuration:**
```yaml
- name: Cache Dependency-Check database
  uses: actions/cache@v4
  with:
    path: ~/.dependency-check
    key: depcheck-data-${{ runner.os }}-${{ github.run_id }}
    restore-keys: |
      depcheck-data-${{ runner.os }}-
```

**Performance Impact:**

| Scenario | Execution Time |
|----------|---------------|
| **Before Cache** | ~7 minutes |
| **After Cache** | ~4 minutes |

**Why Caching Matters:**
- The NVD (National Vulnerability Database) data is large and updates frequently
- Database updates can take several minutes
- Caching reduces download time while maintaining security by using restore keys to ensure recent data

## Workflow Pipeline Stages

Each workflow typically includes the following stages:

### 1. Security Scanning (GitLeaks)
- Detects hardcoded secrets and credentials
- Scans for API keys, passwords, tokens
- Generates SARIF reports for GitHub Security

### 2. Static Application Security Testing (SAST) - SonarQube
- Code quality analysis
- Security vulnerability detection
- Code smells and technical debt identification
- Quality gate enforcement

### 3. Dependency Vulnerability Scanning (OWASP Dependency-Check)
- Identifies known vulnerabilities in dependencies
- Generates comprehensive reports
- Uploads SARIF to GitHub Security

### 4. Build and Container Scanning
- Docker image building
- Trivy security scanning (container vulnerabilities)
- SBOM (Software Bill of Materials) generation
- Image push to Docker Hub

### 5. Deployment
- Automated deployment to AWS EC2
- Docker Compose updates
- Service health checks

### 6. Dynamic Application Security Testing (DAST) - ZAP
- Runtime security testing
- Web application vulnerability scanning
- Active security assessment

## Problems Solved and Solutions

### Problem 1: `chmod -R 755 /app` Taking Too Long

**Issue:**
When using `chmod -R 755 /app` in Dockerfiles, the operation was extremely slow for large directories, significantly impacting build times.

**Solution:**
We restructured the Dockerfile to create the user first, then switch to that user before setting `WORKDIR /app`. This ensures that files created in `/app` are automatically owned by the non-root user without needing explicit `chmod` operations.

**Before:**
```dockerfile
WORKDIR /app
COPY . .
RUN useradd -r -U app && chmod -R 755 /app
USER app
```

**After:**
```dockerfile
RUN useradd -r -U app
USER app
WORKDIR /app
COPY . .
```

**Benefits:**
- No need for recursive `chmod` operations
- Faster Docker builds
- Cleaner, more maintainable Dockerfiles
- Files automatically have correct ownership

### Problem 2: Repeating Identical Workflows for Python Services

**Issue:**
Initially, each Python service (chatbot, doc_processing, hr_attrition, prophet_forecast, tft_revenue_forecast, cv_parsing) had its own nearly identical workflow file, leading to code duplication and maintenance overhead.

**Solution:**
Created a unified workflow (`python-services.yaml`) that uses a matrix strategy. A change detection job automatically identifies which services have been modified and builds a matrix dynamically.

**Implementation:**
```yaml
jobs:
  detect-changes:
    name: Detect Changed Services
    # Detects changed service directories
    outputs:
      services: ${{ steps.set-services.outputs.services }}
  
  ci-cd-pipeline:
    strategy:
      matrix:
        service: ${{ fromJson(needs.detect-changes.outputs.services) }}
```

**Benefits:**
- Single workflow to maintain
- Automatic change detection
- Parallel execution for multiple services
- Reduced maintenance burden

### Problem 3: GitLeaks Artifact Upload Conflicts

**Issue:**
After consolidating to a single workflow with matrix strategy, GitLeaks was failing because multiple services tried to upload artifacts with the same name, causing conflicts.

**Error:**
```
Error: An artifact with the same name already exists
```

**Solution:**
Disabled automatic artifact upload in GitLeaks configuration and added a separate manual upload step with service-specific artifact names.

**Implementation:**
```yaml
- name: Run GitLeaks Scan
  uses: gitleaks/gitleaks-action@v2
  env:
    GITLEAKS_ENABLE_UPLOAD_ARTIFACT: false  # Disable auto-upload

- name: Upload GitLeaks report
  if: always()
  uses: actions/upload-artifact@v4
  with:
    name: gitleaks-report-${{ matrix.service }}  # Service-specific name
    path: results.sarif
```

**Benefits:**
- No artifact name conflicts
- Service-specific artifact names
- Better organization of security reports
- Maintains parallel execution capability

## GitHub Advanced Security

The repository has GitHub Advanced Security features enabled:

- **Secret Protection:** Automatically detects and prevents secret exposure
- **Push Protection:** Blocks pushes containing secrets before they reach the repository

![GitHub Advanced Security](attachment:72762eeb-2f1b-4ed4-acd8-8744c2eeebe7:image.png)

This provides an additional layer of security beyond GitLeaks scanning, offering real-time protection during development.

## SonarQube Configuration

### Server Configuration

SonarQube is accessed via Cloudflare Tunnel, providing secure access to a local instance. The configuration uses:

- **Host URL:** Set via `SONAR_HOST_URL` secret
- **Authentication Token:** Set via `SONAR_TOKEN` secret

### Optional Improvement: Domain Name Configuration

If you later configure a domain name (e.g., `sonarqube.groophy.in`), you can replace the Cloudflare tunnel URL with:

```properties
sonar.core.serverBaseURL=http://sonarqube.groophy.in
```

This makes it easier for users to access SonarQube without remembering IP addresses or tunnel URLs.

**Benefits:**
- Human-readable URLs
- Easier to remember and share
- More professional appearance
- Simplified access management

## Workflow Execution Metrics

### Performance Improvements Summary

| Optimization | Before | After | Improvement |
|-------------|--------|-------|-------------|
| SonarQube (with cache) | ~7 min | ~19 sec | **95% faster** |
| OWASP Dependency-Check (with cache) | ~7 min | ~4 min | **43% faster** |
| Python builds (with uv) | Variable | **10-100x faster** | Significant |

These optimizations result in:
- Faster developer feedback
- Reduced CI/CD costs
- Better developer experience
- More efficient resource usage

## References

### Cache Action Documentation

- **GitHub Actions Cache:** https://docs.github.com/en/actions/reference/workflows-and-actions/dependency-caching

The cache action is a critical component for optimizing workflow performance. Proper cache key strategies and restore keys are essential for maximizing cache hit rates while ensuring data freshness.

## Best Practices

1. **Always use caching** for tools that download data or dependencies
2. **Use matrix strategies** for parallelizing similar jobs
3. **Service-specific artifacts** prevent naming conflicts
4. **Continue-on-error** for non-blocking security scans
5. **Conditional steps** to skip unnecessary work
6. **Non-root containers** for security best practices
7. **Lock files** for reproducible builds (uv.lock, package-lock.json)

## Troubleshooting

### Cache Not Working

If caches aren't being used:
- Check cache key consistency
- Verify cache paths match
- Review cache restore keys
- Check workflow logs for cache hits/misses

### Workflow Failures

Common issues and solutions:
- **Secret not found:** Ensure all required secrets are configured in repository settings
- **Docker build failures:** Check Dockerfile syntax and base images
- **Deployment failures:** Verify EC2 connection and permissions
- **SonarQube timeouts:** Check SonarQube server availability

