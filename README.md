# Xampro Automation Testing Project

## Project Overview
This project automates several functionalities of the [Xampro](https://www.xampro.org/) platform, an online IELTS mock exam platform. Users can register, purchase exam packages, take mock exams, update profiles, and view leaderboard data. The project aims to automate and validate key user journeys on this platform, ensuring a seamless experience for users and providing valuable insights through test case compilations and bug reports.

## Technology Stack
- **Language**: Java
- **Build System**: Gradle
- **Automation Tool**: Selenium WebDriver
- **Testing Framework**: JUnit 5
- **Data Manipulation**: JSON Parser for handling user data

## Project Structure
This project automates the following tasks:
1. **Admin Login Validation**: Tests if the admin can successfully log in and view the user data list.
2. **User Registration**: Registers a new user, saving credentials in a JSON file for future automation.
3. **Automated Login**: Logs in the latest registered user by retrieving credentials from the JSON file.
4. **Profile Update**: Automates updating profile fields and verifies successful changes.


## Project Flow
1. **Admin Login Validation**
2. **User Registration and Login**
3. **Profile Data Update**


## Getting Started

### Prerequisites
- **Java JDK** installed
- **Gradle** installed
- **Google Chrome** and **ChromeDriver** for Selenium

### Installation
1. Clone this repository:
   
   ```bash
   git clone https://github.com/Syeda-Somiya-Tasnim/XamproAutomationTests.git
   cd XamproAutomationTests
   ```
2. Install dependencies:

  ```bash
  gradle build
  ```
3. Data File Configuration: Add a data.json file in src/test/resources/ with the following structure to provide user login data:

  ```bash
  {
    "email": "testuser@example.com",
    "password": "password123"
  }
  ```
### Running Tests
To execute all automated tests, use the following command:

  ```bash
  gradle test
  ```

### Features Covered
1. Automated Login: Logs in the user using credentials in the JSON file.
2. Profile Update: Automates profile field updates and validates changes.

### Allure Report Setup
1. Add Allure dependencies to build.gradle:

  ```bash
  plugins {
      id 'io.qameta.allure' version '2.8.1'
  }
  
  dependencies {
      testImplementation 'io.qameta.allure:allure-junit5:2.17.2'
  }
  ```
2. Run Tests with the Allure Plugin:

  ```bash
  gradle clean test
  ```
3. Generate Allure Report:

  ```bash
  allure serve build/allure-results
  ```
The Allure report will be served locally at http://localhost:XXXX.

Allure report image
![image](https://github.com/user-attachments/assets/a6d29c71-483c-41a3-9566-928ea780969f)
![image](https://github.com/user-attachments/assets/1760212b-e939-40b1-8d64-1b4a4c512c7f)
![image](https://github.com/user-attachments/assets/3feb5e67-2da5-4e7a-9563-454af1bc82a0)


