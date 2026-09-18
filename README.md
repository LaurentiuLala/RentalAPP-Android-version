# RentalAPP — Android Car Rental Application

A native Android application for managing and interacting with a car rental system.

Developed as a university project to practice Android development, REST API integration, CRUD operations, authentication, and client-server architecture.

---

## About the Project

**RentalAPP** is a native Android application that acts as a mobile client for a custom REST API developed separately using Spring Boot.

The application allows users to interact with a car rental system through a mobile interface, while the backend handles business logic, authentication, database operations, and data management.

The main purpose of the project was to gain practical experience with Android development and communication between a mobile application and a backend service.

---

## Features

### Authentication

* User registration
* User login
* Authentication handling
* Local secure storage for authentication-related data

### Car Management

* View available cars
* View car information
* Add cars
* Edit cars
* Delete cars
* Manage car availability

### Location Management

* View rental locations
* Add locations
* Edit locations
* Delete locations

### Administration

Administrators can manage:

* Cars
* Locations
* Users
* Rental-related information

### REST API Integration

The Android application communicates with a custom REST API using HTTP requests.

The application uses:

* Retrofit for API communication
* GSON for JSON serialization and deserialization
* OkHttp for HTTP networking and logging
* Glide for image loading

---

## Architecture

The application follows a layered approach that separates the user interface, application logic, API communication, and backend.

```text
+-----------------------------+
|           UI Layer          |
|      Activities / Views     |
+--------------+--------------+
               |
               v
+-----------------------------+
|      Application Logic      |
|     API calls / Adapters    |
+--------------+--------------+
               |
               v
+-----------------------------+
|        API Layer            |
|      Retrofit / OkHttp      |
+--------------+--------------+
               |
               v
+-----------------------------+
|       Spring Boot API       |
|    Backend / Business Logic |
+--------------+--------------+
               |
               v
+-----------------------------+
|          Database           |
+-----------------------------+
```

The Android application does not communicate directly with the database. All data operations are handled through the REST API.

---

## Technologies

| Technology              | Purpose                                |
| ----------------------- | -------------------------------------- |
| Java                    | Main programming language              |
| Android SDK             | Mobile application development         |
| AndroidX                | Android application components         |
| Material Components     | User interface components              |
| Retrofit                | REST API communication                 |
| GSON                    | JSON serialization and deserialization |
| OkHttp                  | HTTP networking and logging            |
| Glide                   | Image loading                          |
| Android Security Crypto | Secure local data handling             |
| Gradle Kotlin DSL       | Build configuration                    |
| Spring Boot             | Backend REST API                       |

The Android module currently uses compile SDK 36, target SDK 36, minimum SDK 24, and Java 11 compatibility.

---

## Backend

The Android application communicates with a custom Spring Boot REST API developed specifically for the project.

The backend is responsible for:

* User management
* Authentication
* Car management
* Location management
* Rental operations
* Business logic
* Database communication

The general architecture is:

```text
Android Application
        |
        | HTTP / REST
        v
Spring Boot REST API
        |
        | JPA / Hibernate
        v
     Database
```

This separation allows the Android application and backend to function as independent components.

---

## Project Structure

```text
RentalAPP-Android-version/
|
+-- app/
|   +-- src/
|       +-- main/
|           +-- java/
|           +-- res/
|
+-- gradle/
|
+-- build.gradle.kts
+-- settings.gradle.kts
+-- gradle.properties
+-- gradlew
+-- gradlew.bat
|
+-- raport_tehnic.txt
+-- README.md
```

---

## Getting Started

### Requirements

Before running the project, make sure you have:

* Android Studio
* Android SDK
* JDK 11 or compatible Java environment
* An Android emulator or physical Android device
* The corresponding Spring Boot REST API running

### Clone the Repository

```bash
git clone https://github.com/LaurentiuLala/RentalAPP-Android-version.git
```

Open the project in Android Studio and allow Gradle to synchronize.

### Run the Application

1. Open the project in Android Studio.
2. Allow Gradle synchronization to complete.
3. Start an Android emulator or connect a physical Android device.
4. Make sure the backend API is running and accessible.
5. Check the API base URL used by the application.
6. Run the `app` configuration.

If the backend is running locally, the API address may need to be adjusted depending on whether the application is running on an emulator or a physical device.

---

## Application Flow

A typical request follows this process:

```text
User
 |
 v
Android UI
 |
 v
Retrofit
 |
 v
HTTP Request
 |
 v
Spring Boot REST API
 |
 v
Business Logic
 |
 v
Database
 |
 v
HTTP Response
 |
 v
Android Application
 |
 v
Updated UI
```

---

## Project Goals

The main goals of the project were:

* Learn native Android development using Java
* Understand Android application architecture
* Learn how to consume REST APIs
* Implement CRUD operations
* Understand client-server communication
* Integrate an Android application with a custom backend
* Implement authentication
* Work with JSON data
* Practice networking in Android
* Gain experience with a multi-layer application

---

## What I Learned

This project provided practical experience with:

* Android Activities
* XML layouts
* Android navigation
* REST APIs
* Retrofit
* GSON
* HTTP requests
* CRUD operations
* Authentication
* Local data storage
* Image loading
* Client-server architecture
* API integration
* Android project organization

---

## Future Improvements

Possible improvements for future versions include:

* MVVM architecture
* Dedicated Repository layer
* Improved separation of application responsibilities
* Material Design 3
* RecyclerView-based interfaces
* Improved error handling
* Loading states and progress indicators
* Vehicle search and filtering
* Improved rental booking workflow
* Automated unit and UI testing
* Dependency injection
* Offline data caching

---

## Technical Documentation

Additional technical information is available in:

[`raport_tehnic.txt`](./raport_tehnic.txt)

The report contains additional information about the project architecture, implementation, functionality, and development process.

---

## Author

**Laurentiu Lala**

University project focused on Android development, REST API integration, and car rental management.

---

## License

This project was developed for educational purposes.
