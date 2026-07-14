# 🌱 AI Crop Recommendation System

An AI-powered Crop Recommendation System built using **Spring Boot**, **Spring Security**, **JWT Authentication**, **MySQL**, **OpenRouter AI**, **Weather API**, and a responsive **HTML, CSS, JavaScript** frontend.

The system analyzes farm information such as location, season, soil nutrients, temperature, humidity, and weather conditions to generate intelligent crop recommendations using AI. Every recommendation is securely stored and can be viewed later through the recommendation history.

---

# 🚀 Features

- 🔐 User Registration & Login (JWT Authentication)
- 🌾 AI-Based Crop Recommendation
- 🌦️ Live Weather Data Integration
- 🤖 AI Generated Crop Recommendation
- 🌱 Fertilizer Recommendation
- 🐛 Pesticide Recommendation
- 💧 Irrigation Suggestion
- ⚠️ Disease Risk Prediction
- 🌾 Harvest Time Prediction
- 📈 Expected Yield Estimation
- 📜 Recommendation History
- 🗑 Delete Individual Recommendation
- 🗑 Delete Complete History
- 🔒 Protected REST APIs
- 📖 Swagger Documentation
- 🐳 Docker Support

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- MySQL
- OpenRouter AI
- Weather API
- Docker

---

## Frontend

- HTML5
- CSS3
- JavaScript

---

## Tools

- IntelliJ IDEA
- VS Code
- Postman
- Git
- GitHub
- Docker

---

# 📂 Project Structure

```
AI-Crop-Recommendation-System
│
├── frontend
│   └── farm-ai-frontend
│       ├── css
│       ├── images
│       ├── js
│       ├── index.html
│       ├── register.html
│       ├── login.html
│       ├── dashboard.html
│       ├── analysis.html
│       └── history.html
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── hardik
│   │   │           └── farmapp
│   │   │               ├── Configuration
│   │   │               ├── Controller
│   │   │               ├── Entity
│   │   │               ├── Exception
│   │   │               ├── Repository
│   │   │               ├── Request
│   │   │               ├── Response
│   │   │               └── Service
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
├── .gitignore
└── README.md
```

---

# ⚙ Environment Variables

Create a `.env` file in the project root.

```properties
MYSQL_DATABASE=your_database_name
MYSQL_ROOT_PASSWORD=your_mysql_password

WEATHER_API_KEY=your_weather_api_key

SPRING_AI_OPENAI_API_KEY=your_openrouter_api_key
SPRING_AI_OPENAI_BASE_URL=https://openrouter.ai/api
SPRING_AI_OPENAI_CHAT_OPTIONS_MODEL=tencent/hy3:free
```

---

# 🐳 Run Using Docker

Clone the repository

```bash
git clone https://github.com/hardik-kaushik07/AI-Crop-Recommendation-System.git
```

Move inside project

```bash
cd AI-Crop-Recommendation-System
```

Create your `.env` file.

Then run

```bash
docker compose up --build
```

Backend will run on

```
http://localhost:8080
```

---

# 🌐 Frontend

The frontend is located inside

```
frontend/farm-ai-frontend
```

Open

```
index.html
```

or host it using any static web server such as

- Live Server (VS Code)
- Netlify
- Vercel
- GitHub Pages

Update the backend API URL inside

```
js/config.js
```

before deployment.

---

# 📚 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /api/user/register |
| POST | /api/user/login |

---

## Weather

| Method | Endpoint |
|---------|----------|
| POST | /api/farm/weather |

---

## AI Recommendation

| Method | Endpoint |
|---------|----------|
| POST | /api/ai/recommend |

---

## Recommendation History

| Method | Endpoint |
|---------|----------|
| GET | /api/farm/history |
| GET | /api/farm/history/{id} |
| DELETE | /api/farm/delete/{id} |
| DELETE | /api/farm/delete |

---

# 🔐 Authentication

JWT Authentication is used.

After login, the backend returns a JWT token.

The frontend stores the token in Local Storage.

Protected endpoints require

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📖 Swagger Documentation

After running the backend

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📸 Screenshots

Add screenshots for

- Home Page: <img width="940" height="476" alt="image" src="https://github.com/user-attachments/assets/2702aa5b-ff3f-427f-b05d-36d24a562a41" />


- Register Page: <img width="951" height="481" alt="image" src="https://github.com/user-attachments/assets/36dd8520-f26c-4b8b-8c05-19b2d284f492" />

- Login Page: <img width="953" height="473" alt="image" src="https://github.com/user-attachments/assets/f32859a5-c2fb-47f3-a828-67f764785fa2" />

- Dashboard: <img width="944" height="480" alt="image" src="https://github.com/user-attachments/assets/e5b001ac-e3b1-4f6c-abdf-3eeb7e6c8378" />

- Farm Analysis: <img width="945" height="468" alt="image" src="https://github.com/user-attachments/assets/bf570fbd-e3a8-4151-bc39-057b5559bcc0" />
<img width="952" height="481" alt="image" src="https://github.com/user-attachments/assets/c4303669-98be-4283-b5ff-ddda6c257109" />


- AI Recommendation: <img width="938" height="479" alt="image" src="https://github.com/user-attachments/assets/ebb0527b-9a85-456e-97c9-f9ea4310d5a3" />

- Recommendation History: <img width="941" height="481" alt="image" src="https://github.com/user-attachments/assets/8c6fbd27-5e8a-4bf2-8591-7f02df60269d" />

- Swagger UI: <img width="923" height="478" alt="image" src="https://github.com/user-attachments/assets/15d90869-ee8b-4a6e-a6e7-132ed6cbd49c" />
<img width="910" height="476" alt="image" src="https://github.com/user-attachments/assets/ab95237d-e7b9-480b-9166-97cec3cf9292" />



---

# 🚀 Future Improvements

- Image-based Crop Disease Detection
- AI Chatbot for Farmers
- Multi-language Support
- Email Verification
- Forgot Password
- User Profile
- Crop Price Prediction
- Government Scheme Recommendation
- Weather Forecast Dashboard
- Cloud Image Storage
- Mobile Application

---

# 👨‍💻 Author

**Hardik Kaushik**

B.Tech Information Technology

Java Backend Developer

GitHub

https://github.com/hardik-kaushik07

LinkedIn

https://www.linkedin.com/in/hardik-kaushik-55020b3a9?utm_source=share_via&utm_content=profile&utm_medium=member_android

---

# ⭐ Support

If you found this project helpful, consider giving it a ⭐ on GitHub.
