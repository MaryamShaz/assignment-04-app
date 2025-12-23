# Simple Offline Android App

## Project Description
This project is a **Simple Offline Android Application** developed as part of **Assignment #4 (CLO-4)** for the **Android Application Development** course.  
The application is built using **Java and XML** in Android Studio and demonstrates core Android concepts such as API integration, offline data storage, RecyclerView, menu-based navigation, theme switching, and WebView integration.

---

## Functional Features

### 1. Login and User State Management
- Simple login screen implemented using EditText and Button
- Login state stored using SharedPreferences
- Persistent login session across app restarts
- Logout functionality provided in the main screen

### 2. REST API Integration
- Public REST API used:


https://jsonplaceholder.typicode.com/posts

- Data fetched using HttpURLConnection
- JSON parsed using org.json
- Handles network failure gracefully

### 3. Offline Data Persistence (SQLite)
- SQLite database implemented using SQLiteOpenHelper
- API data stored locally for offline access
- Automatic fallback to SQLite when internet is unavailable
- User notified whether data is loaded online or offline

### 4. RecyclerView and Adapter
- Data displayed using RecyclerView
- Custom RecyclerView.Adapter implemented
- Efficient list rendering using ViewHolder pattern
- Item click navigation supported

### 5. WebView Integration
- In-app WebView used to display related web content
- JavaScript enabled
- External browser redirection avoided

### 6. Theme Management
- Light and Dark themes implemented
- Theme switching available via Options Menu
- Selected theme saved using SharedPreferences
- Theme applied correctly on app restart

### 7. Menu-Based Navigation
- Options Menu implemented in MainActivity
- Menu actions include:
- Theme switching
- Logout
- Navigation handled using Intents

---

## Technologies Used
- Programming Language: Java  
- UI Design: XML  
- IDE: Android Studio  
- Database: SQLite  
- Networking: HttpURLConnection  
- UI Components: RecyclerView, WebView  
- State Management: SharedPreferences  

---

## Project Structure

app/
├── java/
│ └── com.example.simpleofflineapp/
│ ├── MainActivity.java
│ ├── LoginActivity.java
│ ├── WebViewActivity.java
│ ├── ApiHelper.java
│ ├── DatabaseHelper.java
│ └── RecyclerAdapter.java
├── res/
│ ├── layout/
│ ├── menu/
│ ├── values/
│ └── values-night/
└── AndroidManifest.xml


---

## How to Run the Project
1. Clone the repository
2. Open the project in Android Studio
3. Allow Gradle to sync completely
4. Run the application on an emulator or physical device (API 24 or higher)

---



---
