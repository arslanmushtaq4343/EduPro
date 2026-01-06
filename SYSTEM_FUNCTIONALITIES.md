# EduAI Pro - Complete System Functionalities

## 🎯 Overview

EduAI Pro is an AI-Powered School Management System built with Node.js/Express backend and React frontend, featuring comprehensive student management, AI-driven analytics, and educational tools.

---

## 🚀 Quick Start

### Start Servers

**Option 1: Use Batch File (Easiest)**
```bash
# Double-click: RESTART_ALL.bat
```

**Option 2: Manual Start**

**Backend:**
```bash
cd backend-node
$env:PORT="8081"  # Windows PowerShell
npm run dev
```

**Frontend:**
```bash
cd frontend
npm run dev -- --host --port 3001
```

**Access:**
- Frontend: http://localhost:3001
- Backend API: http://localhost:8081/api

---

## 📋 System Features

### 1. Authentication & User Management

#### User Registration & Login
- **Register New User**: Create account with email, password, name, and role
- **User Login**: JWT-based authentication
- **User Roles**: ADMIN, TEACHER, STUDENT, PARENT, COUNSELOR, LIBRARIAN, ACCOUNTANT
- **Password Security**: BCrypt hashing
- **Session Management**: JWT tokens stored in localStorage

**API Endpoints:**
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `GET /api/auth/health` - Health check

**Frontend Pages:**
- `/login` - Login and Registration page

---

### 2. Student Information System (SIS)

#### Student Management
- **View All Students**: List all students with pagination
- **View Student Details**: Get individual student information
- **Create Student**: Add new student records
- **Update Student**: Edit student information
- **Delete Student**: Remove student records
- **Filter by Grade**: Get students by grade level
- **Student Profile**: Complete profile with academic, medical, and contact info

**Student Fields:**
- Basic Info: First Name, Last Name, Student ID, Date of Birth
- Academic: Grade Level, GPA, Class Rank, Enrollment Date
- Medical: Conditions, Allergies, IEP Tracking, Learning Disabilities
- Emergency: Contact Name, Contact Phone

**API Endpoints:**
- `GET /api/students` - Get all students
- `GET /api/students/:id` - Get student by ID
- `GET /api/students/grade/:gradeLevel` - Get students by grade
- `POST /api/students` - Create student (ADMIN/TEACHER only)
- `PUT /api/students/:id` - Update student (ADMIN/TEACHER only)
- `DELETE /api/students/:id` - Delete student (ADMIN/TEACHER only)

**Frontend Pages:**
- `/students` - Student management page with CRUD operations

---

### 3. AI Features

#### 3.1 Adaptive Learning Engine
- **Learning Path Generation**: AI-generated personalized learning paths based on student performance
- **Content Recommendation**: Suggest learning materials based on current topic and mastery level
- **Learning Style Analysis**: Analyze student learning preferences and styles

**API Endpoints:**
- `POST /api/ai/adaptive-learning/learning-path/:studentId` - Generate personalized learning path
- `POST /api/ai/adaptive-learning/recommend-content/:studentId` - Recommend learning content
- `POST /api/ai/adaptive-learning/analyze-learning-style` - Analyze learning style

#### 3.2 Predictive Analytics & Early Warning System
- **Risk Score Calculation**: Predict student at-risk status
- **Grade Prediction**: Predict final grades based on current performance
- **Early Warning System**: Identify students needing intervention
- **Class Performance Analysis**: Analyze overall class performance

**API Endpoints:**
- `POST /api/ai/analytics/risk-score/:studentId` - Calculate risk score
- `POST /api/ai/analytics/predict-grade/:studentId` - Predict final grade
- `POST /api/ai/analytics/early-warning/:studentId` - Generate early warning
- `POST /api/ai/analytics/class-performance` - Analyze class performance

#### 3.3 AI Teaching Assistant
- **Lesson Plan Generator**: Create lesson plans with objectives, activities, and assessments
- **Assessment Generator**: Generate quizzes and tests automatically
- **Auto-Grading**: AI-powered automatic grading system
- **Intervention Plans**: Create personalized intervention strategies for struggling students

**API Endpoints:**
- `POST /api/ai/teaching-assistant/lesson-plan` - Generate lesson plan
- `POST /api/ai/teaching-assistant/assessment` - Generate assessment
- `POST /api/ai/teaching-assistant/auto-grade` - Auto-grade submissions
- `POST /api/ai/teaching-assistant/intervention-plan/:studentId` - Create intervention plan

**Frontend Pages:**
- `/ai` - AI Features page with all AI tools

---

### 4. Assignments Management

#### Assignment Operations
- **View All Assignments**: List all assignments
- **Create Assignment**: Add new assignments with title, description, due date, points
- **Update Assignment**: Edit assignment details
- **Delete Assignment**: Remove assignments
- **Assignment Tracking**: Track assignment completion and submission

**API Endpoints:**
- `GET /api/assignments` - Get all assignments
- `POST /api/assignments` - Create assignment (ADMIN/TEACHER only)
- `PUT /api/assignments/:id` - Update assignment (ADMIN/TEACHER only)
- `DELETE /api/assignments/:id` - Delete assignment (ADMIN/TEACHER only)

**Frontend Pages:**
- `/assignments` - Assignment management page

---

### 5. Gradebook & Grading System

#### Grade Management
- **Submit Grades**: Record grades for assignments
- **Update Grades**: Modify existing grades
- **View Student Grades**: Get all grades for a specific student
- **View Assignment Grades**: Get all grades for a specific assignment
- **Grade Calculations**: Automatic weighted grade calculations
- **Letter Grades**: Automatic letter grade conversion (A-F)

**API Endpoints:**
- `POST /api/grades` - Create grade (ADMIN/TEACHER only)
- `PUT /api/grades/:id` - Update grade (ADMIN/TEACHER only)
- `GET /api/grades/student/:studentId` - Get student grades
- `GET /api/grades/assignment/:assignmentId` - Get assignment grades

**Frontend Pages:**
- Integrated in Assignments and Reports pages

---

### 6. Attendance Tracking

#### Attendance Management
- **Mark Attendance**: Record student attendance (Present/Absent/Late)
- **View Attendance**: Get attendance records for a student
- **Attendance Analytics**: Track attendance patterns and rates
- **Date-based Tracking**: Attendance recorded by date

**API Endpoints:**
- `POST /api/attendance` - Mark attendance (ADMIN/TEACHER only)
- `GET /api/attendance/student/:studentId` - Get student attendance

**Frontend Pages:**
- `/attendance` - Attendance tracking page

---

### 7. Notes & Communication

#### Staff Notes
- **Create Notes**: Add notes about students (ADMIN/TEACHER/COUNSELOR)
- **View Student Notes**: Get all notes for a specific student
- **Note Categories**: Notes can be categorized (academic, behavior, medical, etc.)
- **Staff Communication**: Internal communication system for staff

**API Endpoints:**
- `POST /api/notes` - Create note (ADMIN/TEACHER/COUNSELOR only)
- `GET /api/notes/student/:studentId` - Get student notes (ADMIN/TEACHER/COUNSELOR only)

**Frontend Pages:**
- `/notes` - Notes management page

---

### 8. Reports & Analytics

#### Reporting System
- **Summary Reports**: Overall system statistics
- **Student Reports**: Individual student performance reports
- **Class Reports**: Class-wide analytics
- **Statistics Dashboard**: Real-time statistics for staff

**Statistics Tracked:**
- Total Students
- Total Assignments
- Average Grades
- Attendance Rates

**API Endpoints:**
- `GET /api/reports/summary` - Get summary statistics (ADMIN/TEACHER only)

**Frontend Pages:**
- `/reports` - Reports and analytics dashboard

---

### 9. Student/Parent Portal

#### Portal Features
- **Student View**: Read-only access for students and parents
- **View Grades**: Access to student grades
- **View Attendance**: Access to attendance records
- **Academic Progress**: Track academic progress over time
- **Secure Access**: Role-based access control

**API Endpoints:**
- `GET /api/portal/student/:studentId` - Get portal data for student

**Frontend Pages:**
- `/portal` - Student/Parent portal (read-only)

---

### 10. Dashboard

#### Dashboard Features
- **Role-Based Dashboard**: Different views for different user roles
- **Quick Access Cards**: Easy navigation to all features
- **Statistics Widgets**: Real-time statistics for staff (students, assignments, grades, attendance)
- **Quick Actions**: Fast access to common tasks (Add Student, Create Assignment, Mark Attendance)
- **Welcome Message**: Personalized greeting with user name
- **Modern UI**: Gradient cards, icons, hover effects, responsive design

**Dashboard Features by Role:**

**Staff Roles (ADMIN, TEACHER, COUNSELOR, LIBRARIAN, ACCOUNTANT):**
- Statistics cards showing total students, assignments, average grade, attendance rate
- Access to all features: Students, AI Features, Reports, Assignments, Attendance, Notes, Portal

**Student Role:**
- Access to: AI Features, Reports, Portal

**Frontend Pages:**
- `/dashboard` - Main dashboard (default after login)

---

## 🔐 Role-Based Access Control

### Role Permissions

**ADMIN:**
- Full system access
- All CRUD operations
- User management
- All reports and analytics

**TEACHER:**
- Student management (view, create, update)
- Assignment management (full CRUD)
- Grade submission and updates
- Attendance marking
- Note creation
- Access to AI features
- Reports and analytics

**COUNSELOR:**
- Student viewing
- Note creation and viewing
- Access to AI features for student support
- Reports (limited)

**STUDENT:**
- View own portal (grades, attendance)
- Access to AI learning features
- View reports (own data only)

**PARENT:**
- View child's portal (grades, attendance)
- View reports (child's data only)

**LIBRARIAN / ACCOUNTANT:**
- Basic student access
- Role-specific features (to be expanded)

---

## 🗄️ Database

### Technology
- **Database Type**: JSON file-based (LowDB)
- **Location**: `backend-node/data/eduai_pro.json`
- **Automatic Creation**: Database file created automatically on first run

### Data Models
- **Users**: Authentication and user profiles
- **Students**: Complete student information
- **Assignments**: Assignment details and metadata
- **Grades**: Grade records linked to students and assignments
- **Attendance**: Attendance records by date
- **Notes**: Staff notes about students
- **AI Results**: Cached AI-generated content

---

## 🎨 Frontend Features

### User Interface
- **Modern Design**: Gradient backgrounds, card-based layouts
- **Responsive**: Works on desktop, tablet, and mobile
- **Interactive**: Hover effects, animations, transitions
- **Color-Coded**: Each feature has distinct color scheme
- **Icons**: Visual icons for all features
- **Statistics Widgets**: Real-time data visualization

### Pages Available
1. **Login** (`/login`) - Authentication
2. **Dashboard** (`/dashboard`) - Main dashboard
3. **Students** (`/students`) - Student management
4. **AI Features** (`/ai`) - AI tools and features
5. **Reports** (`/reports`) - Analytics and reports
6. **Assignments** (`/assignments`) - Assignment management
7. **Attendance** (`/attendance`) - Attendance tracking
8. **Notes** (`/notes`) - Staff notes
9. **Portal** (`/portal`) - Student/parent portal

---

## 🔧 Technology Stack

### Backend
- **Runtime**: Node.js
- **Framework**: Express.js
- **Language**: TypeScript
- **Database**: LowDB (JSON file-based)
- **Authentication**: JWT (JSON Web Tokens)
- **Password Hashing**: BCrypt
- **AI Integration**: Groq API

### Frontend
- **Framework**: React 18
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **Routing**: React Router
- **State Management**: React Query
- **HTTP Client**: Axios

---

## 📡 API Structure

### Base URL
```
http://localhost:8081/api
```

### Authentication
All routes (except `/auth/register`, `/auth/login`, `/auth/health`) require JWT authentication via Bearer token in Authorization header.

### Response Format
```json
{
  "data": {...},
  "error": "Error message if any"
}
```

---

## 🚦 Getting Started

### 1. Prerequisites
- Node.js 18+
- npm or yarn

### 2. Install Dependencies

**Backend:**
```bash
cd backend-node
npm install
```

**Frontend:**
```bash
cd frontend
npm install
```

### 3. Configure Environment

**Backend** (`backend-node/.env`):
```env
PORT=8081
GROQ_API_KEY=your_groq_api_key
SECRET_KEY=your_jwt_secret_key
FRONTEND_URL=http://localhost:3001
SSL_VERIFY=false
```

### 4. Start Servers

Use `RESTART_ALL.bat` or start manually as shown in Quick Start section.

### 5. Create Account

1. Open http://localhost:3001
2. Click "Create account"
3. Fill in details (email, password, name, role)
4. Click "Create account"
5. You'll be redirected to login

### 6. Login

1. Use your registered credentials
2. Click "Sign in"
3. You'll be taken to the dashboard

---

## 📝 Notes

- Database is automatically created on first run
- All data is stored in JSON file format
- JWT tokens are stored in browser localStorage
- Password hashing ensures secure password storage
- Role-based access control protects sensitive operations
- AI features require Groq API key (configured by default)

---

## 🔄 System Status

**✅ Fully Implemented:**
- User authentication and registration
- Student management (full CRUD)
- Assignment management
- Grade tracking
- Attendance system
- Notes system
- AI features (all modules)
- Reports and analytics
- Portal access
- Dashboard with statistics

**🔄 In Progress:**
- Enhanced reporting features
- Advanced analytics visualizations

---

## 📞 Support

For issues or questions:
1. Check that both servers are running (backend on 8081, frontend on 3001)
2. Verify database file exists: `backend-node/data/eduai_pro.json`
3. Check browser console for errors
4. Check backend console for API errors

---

**Last Updated**: December 2024
**Version**: 1.0.0

