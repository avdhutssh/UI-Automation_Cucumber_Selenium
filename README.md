<!--
    #/**
    # * @author Avdhut Shirgaonkar
    # * Email: avdhut.ssh@gmail.com
    # * LinkedIn: https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/
    # */
    #/***************************************************/
-->

# 💻 UI Automation Using Selenium, Java, and Cucumber (Maven Project)

## 📑 Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Test Execution](#test-execution)
- [Scenario Overview](#scenario-overview)
- [Reporting](#reporting)
- [CICD](#cicd)
- [Contacts](#contacts)

## 📖 Introduction

This project contains a Cucumber-based UI automation framework integrated with Selenium WebDriver and TestNG for parallel test execution. It automates test cases for NBA websites such as [Warriors](https://www.nba.com/warriors), utilizing the Page Object Model (POM) to separate test logic from web page elements.

The framework supports parallel test execution using TestNG and Maven Surefire, allowing seamless scaling for large test suites. It provides detailed reports using Cucumber's HTML plugin and supports CI/CD pipelines using Jenkins or GitHub Actions.

## 🛠️ Prerequisites

Before you start, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or later.
- **Maven**: To manage project dependencies.
- **Git**: To clone the repository.
- **An IDE**: (such as IntelliJ IDEA or Eclipse) with TestNG and Cucumber plugins installed.

For CI/CD:

- **Jenkins**: For automated build and test execution.
- **GitHub Actions**: Integrated for CI pipelines.

## 📁 Project Structure

This project follows the **Page Object Model (POM)** approach and includes components designed for flexibility and scalability. Below is an overview of the structure:

```plaintext
| src/
├── main/
│   ├── java/
│   │   ├── nba/
│   │   │   ├── 1. AutomationFramework/Utilities/               # Common utility classes
│   │   │   │   ├── Base.java  # Parent class for Util classes and contain wait utils
│   │   │   │   ├── BrowserDriverFactory.java  # Browser driver configuration
│   │   │   │   ├── ElementUtils.java          # Methods for element interactions
│   │   │   │   ├── FileUtils.java          # Methods for file handling
│   │   │   │   ├── PropertyReader.java        # Reads properties from config files
│   │   │   ├── 2. Warriors/PageObjects/                
│   │   │   │   ├── WarriorsHomePage.java      # Home page interactions for Warriors website
│   │   │   │   ├── WarriorsShopPage.java      # Shop page interactions
│   │   │   │   ├── WarriorsNewsFeaturesPage.java  # News & Features interactions
│   │   │   ├── 3. Bulls/PageObjects/                
│   │   │   │   ├── BullsHomePage.java      # Home page interactions for Bulls website
│   │   │   ├── 4. Sixers/PageObjects/               
│   │   │   │   ├── SixersHomePage.java      # Home page interactions for Sixers website
│   └── resources/                             # Configuration files (e.g., log4j.xml, config.properties)
├── test/
│   ├── java/
│   │   ├── nba/
│   │   │   ├── 1. StepDefinitions            # Step definitions for feature files
│   │   │   │   ├── warriors/WarriorsSteps.java         # Step definitions for NBA Warriors page scenarios
│   │   │   │   ├── bulls/BullsSteps.java         # Step definitions for NBA Bulls page scenarios
│   │   │   │   ├── sixers/SixersSteps.java         # Step definitions for NBA Sixers page scenarios
│   │   │   ├── 2. Hooks/                      # Cucumber hooks for setup/teardown
│   │   │   ├── 3. Runners/                    # TestNG runner class for Cucumber
│   └── resources/                             # Feature files defining test scenarios
│       └── Features/
│           ├── coreProduct.feature            # Feature file for Core Product scenarios
│           ├── deriveProduct1.feature            # Feature file for Derived Product1 scenarios
│           ├── deriveProduct2.feature            # Feature file for Derived Product2 scenarios
├── target/                                    # Compiled output and cucumber reports
├── pom.xml                                    # Maven project file
└── TestNG.xml                                # TestNG configuration file for managing test suite execution by passing the tags
```

## ▶️ Getting Started

1. Clone the repository:

```bash
git clone https://github.com/avdhutssh/UI-Automation_Cucumber_Selenium.git
```

2. Navigate to the project directory:

```bash
cd UI-Automation_Cucumber_Selenium
```

3. Run Maven clean install to resolve dependencies:

```bash
mvn clean install
```

This will download all required dependencies such as Selenium, Cucumber, TestNG and Log4j.

## 🚀 Test Execution
Run all the test cases using Maven:

### 1. Execute All Tests
```bash
mvn clean test
```

### 2. Execute with Specific Tags
```bash
mvn test -Dcucumber.filter.tags="@Regression"
```

### 3. Execute with Specific browser
```bash
mvn clean test -Dbrowser=firefox
```

## 📜 Scenario Overview

### Core Product Scenarios

1. Find and Store All Men's Jackets: Automates navigating to the Shop page, selecting Men's Jackets, and storing product information in a text file.

2. Count Video Feeds in News & Features Section: Navigates to the News & Features section, counts the total number of video feeds, and counts the feeds present for a specified number of days.

### Derived Product 1 Scenarios

1. Verify and retrive the title of each slide present below tickets menu 

### Derived Product 2 Scenarios

1. Find and store all the footer hyperlinks in a CSV file and report the duplicate footer links.

## 🎯 Reporting

This project uses  **cucumber Reports** for detailed reporting of scenario executions, including logs, screenshots, and status updates.

To view the generated reports:

1. After test execution, navigate to the `target/` directory.
2. Open the `cucumber-html-report.html` file to view a detailed execution report where required files and logs are added to the cucumber report

You can also capture screenshots for failed scenario and view them in the cucumber Report.

![CucumberReport](/Misc/CucumberReport.png)

## 🤖 CI/CD Using Jenkins

### 1. Jenkins Integration

You can integrate the project with Jenkins for Continuous Integration. Follow these steps:

1. Install Maven  plugin
2. Set up a Maven Project Dashboard in Jenkins.
3. Clone the GitHub repository under Source Code Management
4. In the Build section, add the following command to run the tests:
   ```bash
   mvn clean test
   ```

![Jenkins-Execution](/Misc/Jenkins.png)


## 📧 Contacts

- [![Email](https://img.shields.io/badge/Email-avdhut.ssh@gmail.com-green)](mailto:avdhut.ssh@gmail.com)
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue)](https://www.linkedin.com/in/avdhut-shirgaonkar-811243136/)

Feel free to reach out if you have any questions, or suggestions.

Happy Learning!

Avdhut Shirgaonkar