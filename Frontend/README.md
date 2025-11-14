#  Frontend (Angular) – DevOps Overview

This document describes the **DevOps setup** for the **Angular Frontend**  
of the **Konecta ERP Graduation Project**.

---

## 🔹 Tech Stack
**Framework:** Angular  
**CI/CD Platform:** GitHub Actions  
**Static Analysis:** SonarQube (via Cloudflare Tunnel)  
**Containerization:** Docker  
**Security Scanning:** Trivy  
**Notifications:** Email (via Gmail SMTP)

---

## 🧱 CI/CD Pipeline – Frontend

### 📁 Workflow File
Located at:  .github/workflows/frontend-ci.yml


---

## ⚙️ Steps Overview

### ✅ 1. Code Checkout
- Uses the latest project source from the `dev` branch.

### ⚡ 2. Node.js Setup
- Installs **Node.js v18** environment.

### 📦 3. Install Dependencies
- Runs `npm install` inside the `Frontend/` directory.

### 🏗️ 4. Build Angular App
- Executes `npm run build` to generate the Angular production build.

---

## 🧠 5. SonarQube Integration
- Uses **SonarQube Scanner CLI** to analyze the source code.
- Automatically downloads and configures:
  - **OpenJDK 17**
  - **SonarScanner CLI v5.0.1**
- Connects securely to a **local SonarQube instance** exposed via **Cloudflare Tunnel**.
- Uses GitHub **Secrets**:
       SONAR_HOST_URL
       SONAR_TOKEN

- Example environment:  SONAR_HOST_URL = https://your-tunnel-url.trycloudflare.com
                        SONAR_TOKEN = sqp_XXXXX


### 🔁 SonarQube Cache
To speed up future analysis runs:
path: /opt/sonar-scanner/.sonar/cache
key: sonar-${{ runner.os }}-${{ hashFiles('**/package-lock.json') }}


---

## 🐳 6. Docker Build
- Builds the Angular image automatically after SonarQube analysis:
docker build -t erp-frontend:latest .


---

## 🔍 7. Trivy Security Scan
- Runs **Trivy** to scan the Docker image for vulnerabilities.
- Outputs a clean table report in the GitHub Actions log.
- Does not break the pipeline (`exit-code: 0`).

---

## 📬 8. Email Notification
- Sends a summary report when the CI finishes successfully.
- Uses Gmail’s **App Password** (not your direct password).
- Secrets used:
EMAIL_USERNAME
EMAIL_PASSWORD

- Recipient example:  
`aliashmawy595@gmail.com`

---

## 🚀 9. Workflow Trigger
Runs automatically on:
- Push to `dev` branch  
- Pull request to `main`

---

## 🎯 Objective
Provide a fully automated **CI/CD pipeline** for the Angular Frontend, including:
- Code Quality (SonarQube)  
- Security Scan (Trivy)  
- Containerization (Docker)  
- Continuous Notifications (Email)  
- Optimized Build Time (Cache)

---

## 👥 DevOps Contributors
- **Beshoy**  
- **Ali**

---

![Frontend CI](https://github.com/ERP-Konecta/erp_konecta/tree/dev/Frontend)
