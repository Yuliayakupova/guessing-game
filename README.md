# Guessing Game Fullstack Project

## Description
This project consists of a Java (Spring Boot) backend and a React frontend.  
The repository contains both source code and the compiled solution inside the `dist` folder.

## How to Run

### 1. Simple run script
Run the following command in the project root to start frontend and backend:
```bash
./run.sh
```````
### 2. Run with Selenium tests
To start frontend and backend, wait for them to be ready, and then run end-to-end Selenium tests, use:
```bash
./run_with_selenium.sh
```````

## Project Structure
- `backend/` — backend source code
- `frontend/` — frontend source code
- `dist/` — compiled frontend and backend, ready to run
- `run.sh` — startup script

## Requirements
- Backend runs on port 8080 (http://localhost:8080)
- Frontend runs on port 3000 (http://localhost:3000)
- H2 database console available at http://localhost:8080/h2-console/

The startup scripts automatically check if these ports are occupied and kill any process using them before starting the app, so you don't need to free them manually.
- Java 17 or higher
- Node.js and npm (only required if you want to rebuild the frontend)
- npx (to serve frontend from the compiled `dist` folder)

## Additional Requirements for Selenium Tests

To run Selenium end-to-end tests (`run_with_selenium.sh`), you need:

- **Google Chrome** browser installed on your machine.
- **ChromeDriver** executable installed and accessible in your system PATH, compatible with your installed Chrome version.

### How to install ChromeDriver

1. Check your Chrome version:
    - Open Chrome → Menu → Help → About Google Chrome
    - Note the version number, e.g., 114.x.x

2. Download ChromeDriver:  
   Go to https://chromedriver.chromium.org/downloads  
   and download the ChromeDriver version that matches your Chrome version.

3. Extract the archive and place the `chromedriver` executable in a directory included in your system PATH, or add its location to your PATH.

4. Verify the installation by running in your terminal:
```bash
   chromedriver --version
```````
