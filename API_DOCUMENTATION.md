# EduAI Pro API Documentation

**Version:** 2.0.0  
**Last Updated:** December 2024

## Table of Contents

- [Overview](#overview)
- [Authentication](#authentication)
- [Error Handling](#error-handling)
- [Rate Limiting](#rate-limiting)
- [Pagination](#pagination)
- [API Endpoints](#api-endpoints)

---

## Overview

EduAI Pro provides a RESTful API for managing educational data and AI-powered features. All API endpoints are prefixed with `/api`.

**Base URL:** `http://localhost:8081/api`

**Content Type:** `application/json`

---

## Authentication

Most endpoints require authentication using JWT (JSON Web Tokens).

### Obtaining a Token

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePass123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "TEACHER"
  }
}
```

### Using the Token

Include the token in the Authorization header for all authenticated requests:

```
Authorization: Bearer <your_token_here>
```

### Token Expiration

Tokens expire after 24 hours. When a token expires, you'll receive a 401 Unauthorized response.

---

## Error Handling

All errors follow a consistent format:

```json
{
  "error": "Error message",
  "statusCode": 400,
  "timestamp": "2024-12-12T10:30:00.000Z",
  "path": "/api/students",
  "errors": [
    {
      "field": "email",
      "message": "Valid email is required"
    }
  ]
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request (Validation Error) |
| 401 | Unauthorized |
| 403 | Forbidden (Insufficient Permissions) |
| 404 | Not Found |
| 409 | Conflict (Duplicate Entry) |
| 429 | Too Many Requests (Rate Limited) |
| 500 | Internal Server Error |

---

## Rate Limiting

To prevent abuse, API requests are rate-limited:

- **General Endpoints:** 100 requests per 15 minutes per IP
- **Authentication Endpoints:** 5 requests per 15 minutes per IP

When rate limited, you'll receive a 429 status code with:

```json
{
  "error": "Too many requests from this IP, please try again later.",
  "statusCode": 429
}
```

---

## Pagination

List endpoints support pagination with the following query parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| page | integer | 1 | Page number (1-indexed) |
| limit | integer | 20 | Items per page (max: 100) |
| sortBy | string | - | Field to sort by |
| sortOrder | string | asc | Sort order (asc or desc) |

**Example:**
```
GET /api/students?page=2&limit=10&sortBy=lastName&sortOrder=asc
```

**Response:**
```json
{
  "data": [...],
  "pagination": {
    "page": 2,
    "limit": 10,
    "total": 150,
    "totalPages": 15,
    "hasNextPage": true,
    "hasPrevPage": true
  }
}
```

---

## API Endpoints

### Authentication

#### Register User

**POST** `/api/auth/register`

Creates a new user account.

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "TEACHER"
}
```

**Validation Rules:**
- Email must be valid and unique
- Password minimum 8 characters, must contain uppercase, lowercase, and number
- First name and last name: 2-50 characters, letters only
- Role must be one of: ADMIN, TEACHER, STUDENT, PARENT, COUNSELOR, LIBRARIAN, ACCOUNTANT

**Response:** `201 Created`
```json
{
  "user": {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "TEACHER",
    "isActive": true,
    "createdAt": "2024-12-12T10:30:00.000Z"
  }
}
```

#### Login

**POST** `/api/auth/login`

Authenticates a user and returns a JWT token.

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "TEACHER"
  }
}
```

---

### Students

#### List Students

**GET** `/api/students`

**Authentication:** Required  
**Permissions:** All authenticated users

**Query Parameters:** Supports pagination

**Response:** `200 OK`
```json
{
  "data": [
    {
      "id": 1,
      "userId": 5,
      "studentId": "STU-2024-001",
      "dateOfBirth": "2010-05-15",
      "enrollmentDate": "2024-01-15",
      "gradeLevel": "9",
      "gpa": 3.75,
      "classRank": 12,
      "user": {
        "firstName": "Jane",
        "lastName": "Smith",
        "email": "jane.smith@example.com"
      }
    }
  ],
  "pagination": { ... }
}
```

#### Get Student by ID

**GET** `/api/students/:id`

**Authentication:** Required

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": 5,
  "studentId": "STU-2024-001",
  "dateOfBirth": "2010-05-15",
  "gradeLevel": "9",
  "gpa": 3.75,
  ...
}
```

#### Create Student

**POST** `/api/students`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER

**Request Body:**
```json
{
  "userId": 5,
  "studentId": "STU-2024-001",
  "dateOfBirth": "2010-05-15",
  "enrollmentDate": "2024-01-15",
  "gradeLevel": "9",
  "gpa": 3.75,
  "emergencyContactName": "John Smith",
  "emergencyContactPhone": "555-1234"
}
```

**Response:** `201 Created`

#### Update Student

**PUT** `/api/students/:id`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER

**Request Body:** (partial update supported)
```json
{
  "gpa": 3.85,
  "classRank": 10
}
```

**Response:** `200 OK`

#### Delete Student

**DELETE** `/api/students/:id`

**Authentication:** Required  
**Permissions:** ADMIN only

**Response:** `200 OK`

---

### Assignments

#### List Assignments

**GET** `/api/assignments`

**Authentication:** Required

**Query Parameters:** Supports pagination

**Response:** `200 OK`
```json
{
  "data": [
    {
      "id": 1,
      "title": "Math Homework Chapter 5",
      "description": "Complete exercises 1-20",
      "dueDate": "2024-12-20T23:59:59.000Z",
      "maxPoints": 100,
      "type": "HOMEWORK",
      "createdAt": "2024-12-10T10:00:00.000Z"
    }
  ],
  "pagination": { ... }
}
```

#### Create Assignment

**POST** `/api/assignments`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER

**Request Body:**
```json
{
  "title": "Math Homework Chapter 5",
  "description": "Complete exercises 1-20",
  "dueDate": "2024-12-20T23:59:59.000Z",
  "maxPoints": 100,
  "type": "HOMEWORK"
}
```

**Validation Rules:**
- Title: 3-200 characters
- Description: max 2000 characters (optional)
- Type: HOMEWORK, QUIZ, TEST, PROJECT, or EXAM
- maxPoints: must be positive number

**Response:** `201 Created`

---

### Grades

#### Get Grades by Student

**GET** `/api/grades/student/:studentId`

**Authentication:** Required  
**Permissions:** Student can view own grades, Teachers/Admins can view all

**Response:** `200 OK`
```json
{
  "data": [
    {
      "id": 1,
      "studentId": 1,
      "assignmentId": 1,
      "score": 85,
      "maxPoints": 100,
      "percentage": 85.0,
      "letterGrade": "B",
      "feedback": "Good work!",
      "submittedAt": "2024-12-15T14:30:00.000Z"
    }
  ]
}
```

#### Create Grade

**POST** `/api/grades`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER

**Request Body:**
```json
{
  "studentId": 1,
  "assignmentId": 1,
  "score": 85,
  "feedback": "Good work!"
}
```

**Response:** `201 Created`

---

### Attendance

#### Mark Attendance

**POST** `/api/attendance`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER

**Request Body:**
```json
{
  "studentId": 1,
  "date": "2024-12-12",
  "status": "PRESENT",
  "notes": ""
}
```

**Status Values:** PRESENT, ABSENT, LATE, EXCUSED

**Response:** `201 Created`

#### Get Student Attendance

**GET** `/api/attendance/student/:studentId`

**Authentication:** Required

**Response:** `200 OK`
```json
{
  "data": [
    {
      "id": 1,
      "studentId": 1,
      "date": "2024-12-12",
      "status": "PRESENT",
      "notes": "",
      "createdAt": "2024-12-12T08:00:00.000Z"
    }
  ]
}
```

---

### AI Features

#### Generate Learning Path

**POST** `/api/ai/adaptive-learning/learning-path/:studentId`

**Authentication:** Required

**Request Body:**
```json
{
  "subject": "Mathematics",
  "topic": "Linear Equations",
  "difficulty": "Medium",
  "recentScores": [85, 78, 92],
  "topicsStruggling": ["Fractions"],
  "learningPace": "moderate"
}
```

**Response:** `200 OK`
```json
{
  "path": "Personalized learning path description",
  "estimatedTime": "5 hours",
  "difficulty": "Medium",
  "nodes": [
    {
      "id": 1,
      "title": "Introduction to Linear Equations",
      "type": "lesson",
      "difficulty": "Easy",
      "duration": "30 min",
      "order": 1
    }
  ]
}
```

#### Recommend Content

**POST** `/api/ai/adaptive-learning/recommend-content/:studentId`

**Authentication:** Required

**Request Body:**
```json
{
  "currentTopic": "Linear Equations",
  "masteryLevel": "intermediate"
}
```

**Response:** `200 OK`
```json
{
  "recommendations": [
    {
      "id": 1,
      "title": "Khan Academy: Linear Equations",
      "type": "Video",
      "source": "Khan Academy",
      "difficulty": "Medium",
      "duration": "15 min",
      "relevance": 95
    }
  ]
}
```

---

### Reports

#### Get Summary Statistics

**GET** `/api/reports/summary`

**Authentication:** Required  
**Permissions:** ADMIN, TEACHER, COUNSELOR

**Response:** `200 OK`
```json
{
  "totalStudents": 150,
  "totalAssignments": 45,
  "averageGrade": 82.5,
  "attendanceRate": 0.94,
  "avgGpa": 3.25
}
```

---

### Portal

#### Get Student Portal Data

**GET** `/api/portal/student/:studentId`

**Authentication:** Required  
**Permissions:** Student can view own portal, Parents/Teachers/Admins can view all

**Response:** `200 OK`
```json
{
  "student": { ... },
  "grades": [ ... ],
  "attendance": [ ... ]
}
```

---

## Data Export

### Export Student Data

**GET** `/api/export/students?format=csv`

**Authentication:** Required  
**Permissions:** ADMIN

**Query Parameters:**
- format: json or csv

**Response:** Downloads file

---

## Health Check

**GET** `/api/health`

**Authentication:** Not required

**Response:** `200 OK`
```json
{
  "status": "OK",
  "service": "EduAI Pro Backend",
  "version": "2.0.0",
  "timestamp": "2024-12-12T10:30:00.000Z",
  "uptime": 3600,
  "environment": "development"
}
```

---

## Support

For issues or questions:
- Check server is running on port 8081
- Verify database file exists
- Check API documentation at `/api/docs`
- Review logs in `backend-node/logs/`

---

**Copyright © 2024 EduAI Pro. All rights reserved.**

