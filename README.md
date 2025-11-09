# ⚙️ DevFlow

DevFlow is a lightweight Java project management tool that helps developers organize tasks, track bugs, and manage small projects directly from the command line. It was built with clean modular design principles, Maven dependencies, and test coverage through JUnit.

---

## 💡 Overview

DevFlow allows users to create and organize projects, assign tasks with priorities and labels, and store them persistently. It supports multiple projects and integrates a simple command interface for task tracking.

The goal of this project was to practice full-stack Java development fundamentals such as object-oriented design, data serialization, and automated testing while maintaining clear separation between logic layers.

---

## 🧩 Features

**Core Capabilities**
- Create, edit, and delete tasks
- Group tasks by project or label
- Assign priority levels (Low, Medium, High)
- Track due dates and completion status
- List or filter tasks using command flags

**Technical Highlights**
- Modular architecture (`core`, `model`, `persistence`, and `test` packages)
- Uses Gson for JSON storage and retrieval
- Tested using JUnit 5
- Built and managed with Maven

---

## 🛠️ Technologies Used

| Technology | Purpose |
|-------------|----------|
| **Java 17** | Core programming language |
| **Maven** | Dependency management and build automation |
| **JUnit 5** | Unit testing framework |
| **Gson** | JSON serialization and parsing |
| **IntelliJ IDEA** | Development and debugging |

---

## 🚀 Demo

Here is DevFlow running in the terminal:

![DevFlow CLI Screenshot](assets/devflow-cli.png)

> The screenshot shows DevFlow’s startup prompt and example commands for adding and listing tasks.
