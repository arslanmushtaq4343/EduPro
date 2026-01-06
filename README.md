# EduAI Pro - Professional School Management System

**Version:** 2.0.0  
**Enterprise-Grade AI-Powered Educational Platform**

<div align="center">

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Version](https://img.shields.io/badge/version-2.0.0-green.svg)
![Node](https://img.shields.io/badge/node-%3E%3D18.0.0-brightgreen.svg)
![React](https://img.shields.io/badge/react-18.2.0-blue.svg)

</div>

---

## Overview

EduAI Pro is a comprehensive, enterprise-grade school management system that leverages artificial intelligence to enhance educational outcomes. Built with modern technologies and best practices, it provides a secure, scalable, and user-friendly platform for educational institutions.

### Key Features

- **Student Information System**: Complete student lifecycle management
- **AI-Powered Learning**: Personalized learning paths and content recommendations
- **Predictive Analytics**: Early warning system for at-risk students
- **Grade Management**: Comprehensive gradebook with automatic calculations
- **Attendance Tracking**: Real-time attendance monitoring and reporting
- **Assignment Management**: Create, distribute, and grade assignments
- **Secure Authentication**: JWT-based authentication with role-based access control
- **Data Export**: Export data in multiple formats (JSON, CSV)
- **Automated Backups**: Scheduled database backups
- **API Documentation**: Comprehensive REST API documentation

---

## Architecture

### Technology Stack

#### Backend
- **Runtime**: Node.js 18+
- **Framework**: Express.js
- **Language**: TypeScript
- **Database**: LowDB (JSON-based)
- **Authentication**: JWT + BCrypt
- **AI Integration**: Groq API
- **Logging**: Winston
- **Validation**: Express-validator
- **Security**: Helmet, Rate Limiting, CORS

#### Frontend
- **Framework**: React 18
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **Routing**: React Router v6
- **State Management**: React Query (TanStack Query)
- **HTTP Client**: Axios

### System Architecture

```
┌─────────────────┐
│   React SPA     │
│  (Frontend)     │
└────────┬────────┘
         │
         │ HTTPS/REST
         │
┌────────▼────────┐
│  Express API    │
│   (Backend)     │
├─────────────────┤
│ - Auth          │
│ - Validation    │
│ - Rate Limiting │
│ - Logging       │
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼──┐  ┌──▼────┐
│ LowDB│  │ Groq  │
│  DB  │  │  AI   │
└──────┘  └───────┘
```

---

## Installation

### Prerequisites

- Node.js 18.0.0 or higher
- npm 9.0.0 or higher
- Git

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/eduai-pro.git
   cd eduai-pro
   ```

2. **Backend Setup**
   ```bash
   cd backend-node
   npm install
   
   # Create .env file
   cp env.example .env
   
   # Edit .env with your configuration
   # PORT=8081
   # GROQ_API_KEY=your_groq_api_key
   # SECRET_KEY=your_jwt_secret_key
   # FRONTEND_URL=http://localhost:3001
   ```

3. **Frontend Setup**
   ```bash
   cd ../frontend
   npm install
   ```

4. **Start Services**
   
   **Option A: Use batch scripts (Windows)**
   ```bash
   # From project root
   .\RESTART_ALL.bat
   ```

   **Option B: Manual start**
   ```bash
   # Terminal 1 - Backend
   cd backend-node
   npm run dev
   
   # Terminal 2 - Frontend
   cd frontend
   npm run dev
   ```

5. **Access Application**
   - Frontend: http://localhost:3001
   - Backend API: http://localhost:8081/api
   - API Docs: http://localhost:8081/api/docs
   - Health Check: http://localhost:8081/api/health

---

## Configuration

### Environment Variables

#### Backend (.env)

```env
# Server Configuration
PORT=8081
NODE_ENV=development

# Security
SECRET_KEY=your-super-secret-jwt-key-change-in-production
SSL_VERIFY=false

# AI Service
GROQ_API_KEY=your-groq-api-key

# Frontend URL (for CORS)
FRONTEND_URL=http://localhost:3001

# Logging
LOG_LEVEL=info
```

### Security Configuration

The application implements multiple security layers:

1. **Helmet**: HTTP security headers
2. **CORS**: Configurable cross-origin resource sharing
3. **Rate Limiting**: 
   - General API: 100 requests/15 minutes
   - Authentication: 5 attempts/15 minutes
4. **Input Sanitization**: XSS prevention
5. **Validation**: Express-validator for all inputs
6. **JWT**: Secure token-based authentication

---

## User Roles & Permissions

| Role | Permissions |
|------|-------------|
| **ADMIN** | Full system access, user management, all CRUD operations |
| **TEACHER** | Student management, assignments, grading, attendance, notes |
| **COUNSELOR** | Student viewing, notes, AI features for student support |
| **STUDENT** | View own portal, grades, attendance, AI learning tools |
| **PARENT** | View child's portal, grades, attendance |
| **LIBRARIAN** | Basic student access, library-specific features |
| **ACCOUNTANT** | Basic student access, financial features |

---

## API Documentation

Comprehensive API documentation is available at:
- **Interactive Docs**: `http://localhost:8081/api/docs`
- **Full Documentation**: [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)

### Quick API Examples

**Authentication:**
```bash
# Register
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teacher@school.com",
    "password": "SecurePass123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "TEACHER"
  }'

# Login
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teacher@school.com",
    "password": "SecurePass123"
  }'
```

**Get Students (with pagination):**
```bash
curl -X GET "http://localhost:8081/api/students?page=1&limit=20" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## Data Management

### Backups

Automated backups are scheduled every 24 hours. Manual backups can be triggered via the admin panel or API.

**Backup Location**: `backend-node/backups/`

**Restore from Backup:**
```typescript
import { restoreBackup } from './utils/backup';
await restoreBackup('./backups/backup_2024-12-12.json');
```

### Data Export

Export data in JSON or CSV format:

```bash
# Export students as CSV
curl -X GET "http://localhost:8081/api/export/students?format=csv" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  --output students.csv
```

---

## Logging

Application logs are stored in `backend-node/logs/`:

- **combined.log**: All logs
- **error.log**: Error logs only
- **exceptions.log**: Uncaught exceptions
- **rejections.log**: Unhandled promise rejections

**Log Levels**: error, warn, info, debug

**Configure Log Level** in `.env`:
```env
LOG_LEVEL=info
```

---

## Development

### Project Structure

```
EduPro/
├── backend-node/           # Node.js backend
│   ├── src/
│   │   ├── config/        # Configuration files
│   │   ├── controllers/   # Route controllers
│   │   ├── middleware/    # Custom middleware
│   │   ├── models/        # Data models
│   │   ├── routes/        # API routes
│   │   ├── services/      # Business logic
│   │   └── utils/         # Utility functions
│   ├── logs/             # Application logs
│   ├── backups/          # Database backups
│   └── data/             # Database files
│
├── frontend/              # React frontend
│   ├── src/
│   │   ├── components/   # Reusable components
│   │   ├── pages/        # Page components
│   │   ├── services/     # API services
│   │   └── index.css     # Global styles
│   └── public/           # Static assets
│
└── scripts/              # Utility scripts
```

### Code Style

- **TypeScript**: Strict mode enabled
- **ESLint**: Code linting
- **Prettier**: Code formatting (recommended)
- **Naming Conventions**:
  - camelCase for variables and functions
  - PascalCase for components and classes
  - UPPER_CASE for constants

### Adding New Features

1. **Backend Route**:
   ```typescript
   // routes/feature.routes.ts
   import { Router } from 'express';
   import { authenticateToken } from '../middleware/auth.middleware';
   
   const router = Router();
   router.get('/', authenticateToken, controller.list);
   export default router;
   ```

2. **Frontend Service**:
   ```typescript
   // services/api.ts
   export const featureAPI = {
     list: () => api.get('/feature'),
   };
   ```

3. **Frontend Page**:
   ```tsx
   // pages/Feature.tsx
   export default function Feature() {
     const { data } = useQuery(['feature'], () => featureAPI.list());
     return <div>{/* UI */}</div>;
   }
   ```

---

## Testing

### Running Tests

```bash
# Backend tests
cd backend-node
npm test

# Frontend tests
cd frontend
npm test
```

### Test Coverage

Generate coverage reports:

```bash
npm test -- --coverage
```

---

## Deployment

### Production Checklist

- [ ] Update `SECRET_KEY` in production
- [ ] Set `NODE_ENV=production`
- [ ] Configure production database
- [ ] Enable HTTPS
- [ ] Set appropriate `CORS` origins
- [ ] Configure rate limiting for production load
- [ ] Set up monitoring and alerting
- [ ] Configure backup schedule
- [ ] Review and update security headers
- [ ] Set up log rotation

### Docker Deployment (Optional)

```dockerfile
# Dockerfile example
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build
EXPOSE 8081
CMD ["npm", "start"]
```

---

## Monitoring

### Health Checks

**Endpoint**: `GET /api/health`

**Response**:
```json
{
  "status": "OK",
  "service": "EduAI Pro Backend",
  "version": "2.0.0",
  "uptime": 3600,
  "timestamp": "2024-12-12T10:30:00.000Z"
}
```

### Performance Metrics

Monitor these key metrics:
- Response time (target: < 200ms)
- Error rate (target: < 0.1%)
- CPU usage (target: < 70%)
- Memory usage (target: < 80%)
- Database size
- Active connections

---

## Troubleshooting

### Common Issues

**1. Port Already in Use**
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8081 | xargs kill
```

**2. Database Connection Issues**
- Check if `data/eduai_pro.json` exists
- Verify file permissions
- Check logs in `logs/error.log`

**3. Authentication Errors**
- Verify `SECRET_KEY` is set
- Check token expiration
- Ensure Authorization header format: `Bearer <token>`

**4. CORS Errors**
- Verify `FRONTEND_URL` matches frontend URL
- Check CORS middleware configuration

---

## Performance Optimization

### Backend

- Response compression enabled
- Rate limiting to prevent abuse
- Efficient pagination
- Caching strategies (recommended: Redis for production)
- Database indexing (when using SQL database)

### Frontend

- Code splitting
- Lazy loading of routes
- Query caching with React Query
- Optimized re-renders
- Production build optimization

---

## Security Best Practices

1. **Never commit sensitive data** (.env files, API keys)
2. **Use HTTPS in production**
3. **Regularly update dependencies**
4. **Implement proper input validation**
5. **Use parameterized queries** (SQL injection prevention)
6. **Sanitize user input** (XSS prevention)
7. **Implement CSRF protection** (for forms)
8. **Regular security audits**
9. **Monitor for vulnerabilities**: `npm audit`
10. **Implement proper error handling** (don't expose stack traces)

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Review Checklist

- [ ] Code follows style guidelines
- [ ] Tests pass
- [ ] Documentation updated
- [ ] No console.log statements
- [ ] Error handling implemented
- [ ] Security considerations addressed

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Support

**Documentation**: 
- [API Documentation](./API_DOCUMENTATION.md)
- [System Functionalities](./SYSTEM_FUNCTIONALITIES.md)

**Contact**:
- Email: support@eduaipro.com
- Website: https://eduaipro.com

**Issue Tracker**: https://github.com/your-org/eduai-pro/issues

---

## Changelog

### Version 2.0.0 (2024-12-12)

**Major Improvements:**
- Complete UI/UX redesign with professional design system
- Comprehensive error handling and validation
- Advanced logging system with Winston
- Rate limiting and security enhancements
- Pagination and filtering for all list endpoints
- Data export functionality (JSON, CSV)
- Automated backup system
- Comprehensive API documentation
- Professional error pages and loading states

**Technical Improvements:**
- TypeScript strict mode
- Express-validator for input validation
- Helmet for security headers
- Compression middleware
- Input sanitization
- Enhanced authentication
- Better error messages

### Version 1.0.0 (2024-11-01)

- Initial release
- Basic student management
- Assignment and grade tracking
- AI features integration
- Authentication system

---

**Built with care for educational institutions worldwide.**

© 2024 EduAI Pro. All Rights Reserved.

