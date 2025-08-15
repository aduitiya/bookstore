# Bookstore API Automation Framework - Java + Rest Assured + TestNG + Allure

This project is a **Java-based API Test Automation Framework** for the Bookstore API, built using **REST-assured**, **TestNG**, and **Allure Reports** to validate the API functionalities.

The framework supports **local execution** and **CI/CD integration via GitHub Actions**, with automatic **Allure report generation**.

## Tech Stack

| Component      | Version | Purpose                         |
| -------------- | ------- | ------------------------------- |
| Java           | 17      | Programming Language            |
| Maven          | 4.0.0   | Build and Dependency Management |
| REST-assured   | 5.3.0   | API Testing Library             |
| TestNG         | 7.8.0   | Test Execution Engine           |
| Allure Reports | 2.29.0  | Test Reporting                  |
| Jacoco         | 0.8.12  | Code Coverage                   |
| GitHub Actions | Latest  | CI/CD Automation                |

---

## **Project Setup & Pre-requisites**

Before running tests, make sure the **Bookstore API** server is running.

### 1️⃣ Start the FastAPI server

```bash
# Navigate to the Python API project
cd server

# Install dependencies
pip install -r requirements.txt

# Start the API
uvicorn main:app --reload
```

The API will be available at:  
`http://127.0.0.1:8000/docs`

---

## **Running Tests Locally**

Clone the repository:

```bash
git clone https://github.com/aduitiya/JKTech.git
cd JKTech/bookstore
```

Run tests with Maven:

```bash
mvn clean test
```

Run tests with **specific suite** (e.g., `testng.xml`):

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

Override default parameters:

```bash
mvn clean test -DbaseUrl=http://127.0.0.1:8000 -DEMAIL="test@example.com" -DPASSWORD="YourPass123"
```

Run `testng.xml` directly from **Eclipse/IntelliJ** to execute the full suite.

---

## **Allure Report Generation**

Generate and serve report:

```bash
mvn allure:serve
```

Generate static HTML report:

```bash
mvn allure:report
```

Reports are generated inside:  
`target/allure-results` and `target/site/allure-maven-plugin`

---

## **Test Coverage**

The framework includes **Positive & Negative** scenarios for:

### ✅ Positive Scenarios

- Health Check
- Create User
- Login and Token Generation
- Add Book
- Get Book
- Update Book
- Delete Book

### ❌ Negative Scenarios

- Create User with missing fields
- Create User with invalid password
- Generate token with invalid credentials
- Create Book without Authorization
- Get Book with invalid ID
- Update Book with invalid ID
- Delete already deleted Book
- Delete Book with invalid ID

---

## **CI/CD with GitHub Actions**

The workflow:

- Runs tests on every push & pull request to `main`
- Uses Java 17 & Maven
- Generates Allure Reports
- Supports secrets for `EMAIL` and `PASSWORD`

Secrets to add in GitHub Repo:

- `EMAIL`
- `PASSWORD`

---

## **Author**

Aditi Sharma
[aduitiya](https://github.com/aduitiya)
