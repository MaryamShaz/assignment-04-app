Simple Offline Android App
Overview

This project is a Simple Offline Android Application developed as part of Assignment #4 (CLO-4) for the Android Application Development course.
The application demonstrates core Android development concepts using Java and XML, including API integration, offline data persistence, RecyclerView, user state management, menu navigation, theme switching, and WebView integration.

The app fetches data from a public REST API, stores it locally using SQLite, and allows the user to access the data even when the device is offline.

Features Implemented
1. User Authentication (Simulated Login)

Simple login screen using EditText and Button

Authentication state stored using SharedPreferences

Persistent login state across app restarts

Logout functionality provided from the main screen

2. REST API Integration

Public API used:

https://jsonplaceholder.typicode.com/posts


Data fetched using HttpURLConnection

JSON parsing handled using org.json

Network failure handling implemented

3. Offline Data Storage (SQLite)

SQLite database implemented using SQLiteOpenHelper

API data stored locally for offline access

Automatic fallback to SQLite when internet is unavailable

User notified whether data is loaded online or offline

4. RecyclerView and Adapter

Data displayed using RecyclerView

Custom RecyclerView.Adapter implemented

Efficient data binding using ViewHolder pattern

Item click navigation supported

5. WebView Integration

In-app WebView used to display related web content

JavaScript enabled

Content loaded without switching to an external browser

6. Theme Management

Light and Dark themes implemented

Theme switching available through Options Menu

Selected theme persisted using SharedPreferences

Theme applied correctly during activity recreation

7. Menu-Based Navigation

Options Menu implemented in MainActivity

Menu actions include:

Theme switching

Logout

Navigation handled using explicit Intents

8. Activity Lifecycle Handling

Proper handling of activity recreation

Persistent user and theme state

No unnecessary API re-fetching when offline

Technologies Used

Language: Java

UI Design: XML

IDE: Android Studio

Database: SQLite

Networking: HttpURLConnection

UI Components: RecyclerView, WebView

State Management: SharedPreferences

Project Structure (Simplified)
app/
├── java/
│   └── com.example.simpleofflineapp/
│       ├── MainActivity.java
│       ├── LoginActivity.java
│       ├── WebViewActivity.java
│       ├── ApiHelper.java
│       ├── DatabaseHelper.java
│       └── RecyclerAdapter.java
├── res/
│   ├── layout/
│   ├── menu/
│   ├── values/
│   └── values-night/
└── AndroidManifest.xml

How to Run the Project

Clone the repository:

git clone <your-repository-url>


Open the project in Android Studio

Allow Gradle to sync completely

Run the app on an emulator or physical device (API 24+)
