# 🌱 AI Crop Recommendation System:

AI Crop Recommendation System is a full-stack web application built to help farmers make better farming decisions with the help of Generative AI.

The application allows users to analyze their farm conditions by providing details such as location, soil type, pH level, nutrients, season and live weather information. Based on these inputs, Google Gemini generates crop recommendations along with fertilizer suggestions, irrigation advice, pesticide recommendations, disease risk, harvest time and expected yield.

Apart from crop analysis, the application also provides an AI farming assistant where users can have normal conversations, upload PDF documents and ask questions about them, or upload crop images and interact with the AI to understand diseases, crop conditions or farming-related issues.

The project is built using Spring Boot and Spring AI on the backend with JWT-based authentication, while the frontend is developed using HTML, CSS and JavaScript. All user data including conversations, uploaded documents and farm recommendations are stored securely in MySQL.

---

# Project Features:

1-) Authentication:

- User Registration
- User Login
- JWT Authentication
- Password Encryption using BCrypt
- Role Based Authorization (USER / ADMIN)
- Protected REST APIs using Spring Security

---

2-) AI Farm Analysis:

Users can enter their farm details and receive AI-generated recommendations based on:

- Location
- Soil Type
- Soil pH
- Nitrogen Level
- Phosphorus Level
- Potassium Level
- Season
- Live Weather Data

The AI response includes:

- Recommended Crop
- Reason for Recommendation
- Fertilizer Suggestion
- Pesticide Suggestion
- Irrigation Advice
- Disease Risk
- Harvest Time
- Expected Yield

Every recommendation is automatically stored in the user's history.

---

3-) AI Chat Assistant:

The application includes a farming assistant powered by Google Gemini.

Users can:

- Create multiple conversations
- Continue previous conversations
- Delete individual conversations
- Delete complete chat history

The chat remembers the conversation context, making interactions more natural.

---

4-) PDF Question Answering (RAG):

Users can upload PDF documents inside a conversation.

After uploading a document, they can ask questions in the same chat.

The system retrieves the most relevant content from the uploaded PDF and sends it to Gemini before generating the final answer.

This allows users to interact with farming guides, manuals and agricultural documents naturally.

---

5-) Image Understanding:

Users can upload crop or farm images and ask questions such as:

- Which disease is visible?
- Is this crop healthy?
- What fertilizer should I use?
- What treatment is recommended?

The image is analyzed using Google's multimodal Gemini model.

---

6-) Recommendation History:

Every farm analysis is saved automatically.

Users can:

- View previous recommendations
- Open detailed recommendations
- Delete a single recommendation
- Delete complete history
- Browse history using pagination

---

7-) Dashboard:

The dashboard provides a quick overview of user activity.

It displays:

- Total AI Chats
- Total Farm Analyses
- Uploaded Documents
- Uploaded Images

The dashboard also shows the latest activity for quick access.

---

8-) Secure File Management:

Uploaded documents are stored securely.

Only the owner of a document can access or download it.

Unauthorized users cannot access another user's files.

---

9-) Docker Support:

The complete application can be started using Docker Compose.

Docker automatically creates:

- Spring Boot Container
- MySQL Container

making the project easy to set up on any machine.

---

# Tech Stack:

## Backend:

- Java 21
- Spring Boot
- Spring Security
- Spring AI
- Spring Data JPA
- Hibernate
- JWT Authentication
- Maven
- MySQL
- Google Gemini API
- OpenWeather API

---

## Frontend:

- HTML5
- CSS3
- JavaScript

---

## Database:

- MySQL

---

## Tools:

- IntelliJ IDEA
- Visual Studio Code
- Postman
- Docker
- Git
- GitHub

---

## Main Libraries Used:

- Spring AI
- Spring Security
- Spring Data JPA
- Lombok
- Validation API
- JWT (JJWT)
- MySQL Connector
- Swagger

---

# Why I Built This Project:

The main goal of this project was to learn how Generative AI can be integrated into a Java backend application.

Instead of creating only a simple chatbot, I wanted to build a complete application where AI solves practical farming problems.

While building this project, I learned how to work with Spring AI, Google Gemini, Retrieval-Augmented Generation (RAG), image understanding, JWT authentication, Docker, REST APIs and responsive frontend development.

This project helped me understand how different technologies work together to build a real-world AI application.


# Project Structure:

```
AI-Crop-Recommendation-System
│
├── frontend
│   └── farm-ai-frontend
│       ├── css
│       ├── images
│       ├── js
│       ├── analysis.html
│       ├── chat.html
│       ├── dashboard.html
│       ├── history.html
│       ├── index.html
│       ├── login.html
│       └── register.html
│
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── hardik
│       │           └── farmapp
│       │               ├── Configuration
│       │               ├── Controller
│       │               ├── DTO
│       │               ├── Entity
│       │               ├── Enum
│       │               ├── Repository
│       │               ├── Service
│       │               └── Exception
│       │
│       └── resources
│           ├── application.properties
│           └── application-local.properties
│
├── uploads
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
├── .gitignore
└── README.md
```

---

# How the Application Works:

The application is divided into two parts.

The frontend is built using HTML, CSS and JavaScript. It is responsible for collecting user input, displaying AI responses and communicating with the backend using REST APIs.

The backend is developed using Spring Boot. It handles authentication, communicates with Google Gemini, fetches weather information, stores data in MySQL and returns responses to the frontend.

Every request first passes through Spring Security, where the JWT token is verified before allowing access to protected APIs.

---

# Application Workflow:

## 1. User Authentication:

- User creates an account.
- Password is encrypted using BCrypt before storing it in the database.
- User logs in using email and password.
- A JWT token is generated.
- The frontend stores the token in Local Storage.
- Every protected request includes the token in the Authorization header.

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 2. Farm Analysis Workflow:

```
User

   │

   ▼

Enter Farm Details

   │

   ▼

Weather API

(Current Temperature & Humidity)

   │

   ▼

Spring Boot

   │

   ▼

Google Gemini

   │

   ▼

AI Recommendation

   │

   ▼

Save Recommendation

(MySQL)

   │

   ▼

Display Result
```

The generated recommendation is automatically saved so the user can view it later from the history page.

---

## 3. AI Chat Workflow:

```
Create Conversation

        │

        ▼

Send Message

        │

        ▼

Spring AI

        │

        ▼

Google Gemini

        │

        ▼

Store Conversation

(MySQL)

        │

        ▼

Return AI Response
```

Each conversation has its own unique ID, allowing users to continue previous chats whenever they want.

---

## 4. PDF Chat (RAG) Workflow:

```
Create Conversation

        │

        ▼

Upload PDF

        │

        ▼

Read PDF

        │

        ▼

Generate Embeddings

        │

        ▼

Store Document

        │

        ▼

Ask Question

        │

        ▼

Retrieve Relevant Content

        │

        ▼

Google Gemini

        │

        ▼

Final Answer
```

The user does not need a separate endpoint to ask PDF questions.

Once a PDF has been uploaded into a conversation, the normal AI Chat endpoint automatically answers questions using the uploaded document whenever relevant.

---

## 5. Image Chat Workflow:

```
Create Conversation

        │

        ▼

Upload Image

        │

        ▼

Spring AI

        │

        ▼

Google Gemini Vision

        │

        ▼

AI Analysis

        │

        ▼

Store Conversation

        │

        ▼

Display Response
```

The user can upload crop images and ask questions about diseases, plant health, fertilizers or any other farming-related topic.

---

# Database:

The application uses MySQL to store user information and AI-generated data.

Main tables include:

- Users
- Farm Analysis
- Crop Recommendation
- Chat Conversation
- Chat Messages
- Uploaded Documents

Relationships are managed using Spring Data JPA and Hibernate.

---

# Prerequisites:

Before running the project, make sure the following software is installed.

- Java 17
- Maven
- MySQL 8
- Docker Desktop
- Git

Any modern browser such as Chrome, Edge or Firefox can be used to run the frontend.

---

# Clone the Repository:

```bash
git clone https://github.com/hardik-kaushik07/AI-Crop-Recommendation-System.git
```

Move into the project directory.

```bash
cd AI-Crop-Recommendation-System
```

---

# Environment Variables:

Create a `.env` file in the project root.

Example:

```properties
MYSQL_DATABASE=farmdb

MYSQL_ROOT_PASSWORD=your_password

WEATHER_API_KEY=your_weather_api_key

GEMINI_API_KEY=your_gemini_api_key

GEMINI_MODEL=gemini-2.5-flash
```

Replace the values with your own API keys before running the application.

---

# Running the Backend:

If you want to run the application directly from IntelliJ IDEA, first build the project.

```bash
mvn clean package
```

Start MySQL and then run the Spring Boot application.

The backend will be available at

```
http://localhost:8080
```

---

# Running with Docker:

The project also supports Docker Compose.

After creating the `.env` file, simply run

```bash
docker compose up --build
```

Docker automatically creates

- MySQL Container
- Spring Boot Container

No additional configuration is required.

---

# Running the Frontend:

The frontend is located inside

```
frontend/farm-ai-frontend
```

You can run it using:

- VS Code Live Server
- Any Static Web Server
- GitHub Pages
- Netlify
- Vercel

Before deployment, update the backend URL inside

```
js/config.js
```

to match your backend server.

For local development, it should point to

```
http://localhost:8080
```

# API Endpoints:

## Authentication:

### Register:

```
POST /api/user/register
```

Creates a new user account.

### Login:

```
POST /api/user/login
```

Logs in the user and returns a JWT token.

---

## Dashboard:

### Dashboard Statistics:

```
GET /api/dashboard/stats
```

Returns dashboard statistics such as total chats, farm analyses, uploaded documents, and other user-related information.

---

## Farm Analysis:

### Analyze Farm:

```
POST /api/ai/analyze
```

Generates an AI-based crop recommendation using farm details such as location, soil type, nutrients, season, temperature, humidity, and weather.

---

### Get Analysis History:

```
GET /api/farm/history
```

Returns paginated farm analysis history.

Query Parameters:

```
pageNumber
pageSize
```

Example:

```
GET /api/farm/history?pageNumber=0&pageSize=5
```

---

### Get Analysis by ID:

```
GET /api/farm/history/{id}
```

Returns a specific farm analysis.

---

### Delete Analysis:

```
DELETE /api/farm/delete/{id}
```

Deletes a single farm analysis.

---

### Delete All Analysis History:

```
DELETE /api/farm/delete
```

Deletes all farm analysis records of the logged-in user.

---

## AI Chat:

### Create Conversation:  

```
POST /api/chat/conversation
```

Creates a new chat conversation.

---

### Send Chat Message:

```
POST /api/ai/chat/{conversationId}
```

Sends a text message to the AI.

If a PDF has already been uploaded in the same conversation, the AI answers using both the uploaded document and the user's question.

---

### Image Chat:

```
POST /api/ai/image/chat/{conversationId}
```

Allows users to ask questions about an uploaded image.

---

### Chat History:

```
GET /api/chat/history
```

Returns all conversations of the logged-in user.

---

### Conversation Details:

```
GET /api/chat/{conversationId}
```

Returns complete messages of a conversation.

---

### Delete Conversation:

```
DELETE /api/chat/{conversationId}
```

Deletes a specific conversation.

---

### Delete All Chat History:

```
DELETE /api/chat/history
```

Deletes all conversations of the logged-in user.

---

## Document Upload (RAG):

### Upload PDF:

```
POST /api/rag/upload/{conversationId}
```

Uploads a PDF document for a conversation.

After uploading, users can ask questions related to that document through the normal chat endpoint.

---

## File Access:

### View Uploaded File:

```
GET /api/files/{documentId}
```

Returns the uploaded document if it belongs to the logged-in user.

---

## Authentication:

All endpoints except Register and Login require a valid JWT token.

Include the token in every protected request.

```
Authorization: Bearer YOUR_JWT_TOKEN
```

---

## Swagger:

After starting the backend, Swagger is available at

```
http://localhost:8080/swagger-ui/index.html
```


# Screenshots:

Some screenshots of the application are shown below.

### Home Page

<img width="896" height="6393" alt="127 0 0 1_5500_farm-ai-frontend_index html" src="https://github.com/user-attachments/assets/456d48a1-17aa-42c1-bad5-6e3bfac9a0cd" />


### Register Page

<img width="896" height="1095" alt="127 0 0 1_5500_farm-ai-frontend_register html" src="https://github.com/user-attachments/assets/c9459394-5651-4251-a6d5-893f88a7c48d" />


### Login Page

<img width="1380" height="1013" alt="127 0 0 1_5500_farm-ai-frontend_login html" src="https://github.com/user-attachments/assets/cd52e798-5dca-449b-9bb8-af3d829d21f4" />


### Dashboard

<img width="1368" height="2075" alt="127 0 0 1_5500_farm-ai-frontend_dashboard html" src="https://github.com/user-attachments/assets/e12e2ffc-fe9a-4fe0-83e1-88f5c6eb8afc" />


### Farm Analysis And AI Recommendation

<img width="1356" height="3782" alt="127 0 0 1_5500_farm-ai-frontend_analysis html" src="https://github.com/user-attachments/assets/81b7f142-a9bd-4441-9054-41ba804c5fe0" />


### AI Chat

<img width="1380" height="953" alt="127 0 0 1_5500_farm-ai-frontend_chat html" src="https://github.com/user-attachments/assets/24be66c1-f7c3-4ceb-820b-f290b54550cc" />


### PDF Question Answering

<img width="1380" height="953" alt="127 0 0 1_5500_farm-ai-frontend_chat html (1)" src="https://github.com/user-attachments/assets/895ad88b-0998-4ca1-89b0-eed38a4b500b" />


### Image Question Answering

<img width="1380" height="953" alt="127 0 0 1_5500_farm-ai-frontend_chat html (2)" src="https://github.com/user-attachments/assets/d2d5a0f9-9fd1-4719-b9d2-0ec76372d1c6" />


### Farm Analysis History

<img width="1356" height="1050" alt="127 0 0 1_5500_farm-ai-frontend_history html" src="https://github.com/user-attachments/assets/597f1afa-289d-45bd-b7dd-df0c65f2be84" />


### Swagger UI

<img width="1356" height="4535" alt="localhost_8080_swagger-ui_index html" src="https://github.com/user-attachments/assets/8b2e0992-7d4c-46f6-a9d7-349a905a8ad2" />


---

# Future Improvements

Some features I plan to add in future versions:

- Crop Price Prediction
- Government Scheme Recommendation
- Multi-language Support
- Email Verification
- Forgot Password
- User Profile Management
- Cloud Storage for Uploaded Files
- Voice-based AI Assistant
- Mobile Application

---

# Author:

**Hardik Kaushik**

B.Tech - Information Technology

Java Backend Developer

GitHub:

https://github.com/hardik-kaushik07

LinkedIn:

https://www.linkedin.com/in/hardik-kaushik-55020b3a9

---

# Feedback:

If you find any bugs or have suggestions for improvement, feel free to open an Issue or create a Pull Request.

I'm always open to learning and improving the project.

---

# Support

If you found this project helpful, please consider giving it a ⭐ on GitHub.

It motivates me to keep building more Java and AI-based projects.
