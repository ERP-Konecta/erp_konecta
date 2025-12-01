# Konecta ERP System

## Overview

The Konecta ERP (Enterprise Resource Planning) System is a comprehensive, microservices-based enterprise management platform designed to streamline business operations across multiple domains. Built with modern technologies and DevOps best practices, this system integrates various business functions into a unified, scalable architecture.

## Architecture

This ERP system follows a **microservices architecture** pattern, where each business domain is implemented as an independent service. Services communicate through well-defined APIs and share data through a combination of relational and document databases.

### Technology Stack

- **Frontend:** Angular with TypeScript
- **Backend Services:** Spring Boot (Java) for core business logic
- **Python Services:** FastAPI/Flask for AI/ML and document processing
- **Databases:** 
  - PostgreSQL for structured relational data
  - MongoDB for document storage and retrieval
- **Containerization:** Docker with multi-stage builds
- **Orchestration:** Docker Compose
- **CI/CD:** GitHub Actions
- **Infrastructure:** AWS EC2

## System Components

### Core Services

1. **Authentication Service** (Port 8080)
   - User authentication and authorization
   - JWT-based security
   - User management and role-based access control

2. **HR Service** (Port 8081)
   - Human resources management
   - Employee information management
   - HR analytics and reporting

3. **Finance Service** (Port 8082)
   - Financial operations management
   - Budget, expense, invoice, and payroll processing
   - Financial reporting and analytics

### Frontend Application

- **Angular Frontend** (Port 3000)
  - Modern single-page application
  - Responsive user interface
  - Real-time data synchronization with backend services

### Python-Based Services

1. **Document Processing Service** (Port 7860)
   - OCR-based document extraction
   - Invoice, purchase order, and approval processing
   - LLM-powered document classification and data extraction
   - Vector embeddings for semantic search

2. **Chatbot Service** (Port 7870)
   - Multi-collection semantic querying
   - Natural language processing across business documents
   - Google Gemini integration for contextual responses

3. **HR Attrition & Training Prediction** (Port 7880)
   - ML-powered employee attrition risk prediction (XGBoost)
   - Training needs analysis (Logistic Regression)
   - Generative AI-powered root cause analysis

4. **TFT Revenue Forecast** (Port 7890)
   - Temporal Fusion Transformer model for revenue forecasting
   - Time-series financial predictions

5. **Prophet Forecast** (Port 7900)
   - Facebook Prophet model for financial forecasting
   - Time-series analysis

6. **CV Parsing Service** (Port 7910)
   - Automated CV/resume parsing and extraction
   - Candidate information processing

## Getting Started

### Prerequisites

- Docker and Docker Compose installed
- Git
- Access to required environment variables and secrets

### Running the System

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd erp_konecta
   ```

2. Set up environment files for services that require them:
   - `doc_processing/.env`
   - `chatbot/.env`
   - `hr_attrition/.env`
   - `cv_parsing/.env`

3. Start all services using Docker Compose:
   ```bash
   docker-compose up -d
   ```

4. Access the frontend at `http://localhost:3000`

### Service Ports

| Service | Port | Description |
|---------|------|-------------|
| Frontend | 3000 | Angular application |
| Authentication | 8080 | Auth service API |
| HR Service | 8081 | HR service API |
| Finance Service | 8082 | Finance service API |
| Document Processing | 7860 | Document processing API |
| Chatbot | 7870 | Chatbot API |
| HR Attrition | 7880 | HR analytics API |
| TFT Forecast | 7890 | Revenue forecast API |
| Prophet Forecast | 7900 | Financial forecast API |
| CV Parsing | 7910 | CV parsing API |
| PostgreSQL | 5432 | Database |
| MongoDB | 27017 | Document database |

## Development

### Project Structure

```
erp_konecta/
├── authentication-service/    # Spring Boot auth service
├── hr-service/                # Spring Boot HR service
├── finance-service/           # Spring Boot finance service
├── frontend/                  # Angular frontend
├── doc_processing/            # Document processing service
├── chatbot/                   # Chatbot service
├── hr_attrition/              # HR analytics service
├── prophet_forecast/          # Prophet forecasting service
├── tft_revenue_forecast/      # TFT forecasting service
├── cv_parsing/                # CV parsing service
├── .github/workflows/         # CI/CD workflows
├── ansible/                   # Infrastructure automation
├── scripts/                   # Deployment scripts
└── docker-compose.yml         # Service orchestration
```

### CI/CD Pipeline

The system uses GitHub Actions for continuous integration and deployment. Each service has dedicated workflows that include:

- Code quality checks (SonarQube)
- Security scanning (GitLeaks, Trivy, OWASP Dependency-Check)
- Automated testing
- Docker image building
- Deployment to production

For detailed DevOps documentation, see [`.github/workflows/README.md`](.github/workflows/README.md).

## Security Features

- JWT-based authentication across services
- Secret management via GitHub Secrets
- Security scanning at multiple layers (code, dependencies, containers)
- Non-root container execution
- Secret protection and push protection enabled in GitHub Advanced Security

## Database Schema

- **PostgreSQL:** Stores relational data for authentication, HR, and finance services
- **MongoDB:** Stores document-based data including invoices, purchase orders, approvals, and vector embeddings

## API Documentation

Each service provides its own API documentation:

- Spring Boot services: Swagger UI available at `/swagger-ui.html`
- Python services: FastAPI/Flask documentation at `/docs` or `/api/docs`

## Contributing

When contributing to this project:

1. Create a feature branch from `dev` or `main`
2. Make your changes
3. Ensure all tests pass
4. Submit a pull request

The CI/CD pipeline will automatically validate your changes before merging.

## Deployment

The system is deployed using:

- **GitHub Actions** for CI/CD automation
- **Docker Hub** for container image registry
- **AWS EC2** for hosting
- **Ansible** for infrastructure automation (optional)

See the [DevOps README](.github/workflows/README.md) for detailed deployment procedures.

