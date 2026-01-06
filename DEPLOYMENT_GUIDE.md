# EduAI Pro - Production Deployment Guide

**Version:** 2.0.0  
**Last Updated:** December 2024

---

## Table of Contents

1. [Pre-Deployment Checklist](#pre-deployment-checklist)
2. [Server Requirements](#server-requirements)
3. [Deployment Methods](#deployment-methods)
4. [Security Hardening](#security-hardening)
5. [Database Migration](#database-migration)
6. [Monitoring & Maintenance](#monitoring--maintenance)
7. [Troubleshooting](#troubleshooting)

---

## Pre-Deployment Checklist

### Code Preparation

- [ ] All tests passing
- [ ] No console.log statements in production code
- [ ] Environment variables configured
- [ ] Build process tested locally
- [ ] Dependencies audited (`npm audit`)
- [ ] Code linting passed
- [ ] Documentation updated

### Security Review

- [ ] `SECRET_KEY` changed from default
- [ ] Strong password policies enforced
- [ ] Rate limiting configured appropriately
- [ ] CORS origins restricted to production domains
- [ ] HTTPS enabled
- [ ] Security headers configured
- [ ] Input validation implemented
- [ ] SQL injection prevention verified
- [ ] XSS prevention verified

### Infrastructure

- [ ] Server provisioned
- [ ] Domain name configured
- [ ] SSL certificate obtained
- [ ] Firewall rules configured
- [ ] Backup strategy defined
- [ ] Monitoring tools ready
- [ ] Log management configured
- [ ] CDN configured (optional)

---

## Server Requirements

### Minimum Requirements

- **CPU**: 2 cores
- **RAM**: 4 GB
- **Storage**: 20 GB SSD
- **OS**: Ubuntu 20.04 LTS or higher
- **Node.js**: 18.x or higher
- **npm**: 9.x or higher

### Recommended for Production

- **CPU**: 4+ cores
- **RAM**: 8+ GB
- **Storage**: 50+ GB SSD
- **OS**: Ubuntu 22.04 LTS
- **Node.js**: 20.x LTS
- **Load Balancer**: Nginx or HAProxy
- **Database**: PostgreSQL or MySQL (for scaling)
- **Cache**: Redis
- **Monitoring**: Prometheus + Grafana

---

## Deployment Methods

### Method 1: Traditional Server Deployment (Ubuntu/Linux)

#### 1.1. Server Setup

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Install Node.js 20.x
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs

# Verify installation
node --version  # Should show v20.x.x
npm --version   # Should show 10.x.x

# Install PM2 (Process Manager)
sudo npm install -g pm2

# Install Nginx
sudo apt install -y nginx

# Install Git
sudo apt install -y git
```

#### 1.2. Clone and Setup Application

```bash
# Create application directory
sudo mkdir -p /var/www/eduaipro
sudo chown -R $USER:$USER /var/www/eduaipro

# Clone repository
cd /var/www/eduaipro
git clone https://github.com/your-org/eduai-pro.git .

# Backend setup
cd backend-node
npm ci --only=production

# Create production .env
cat > .env << EOF
PORT=8081
NODE_ENV=production
SECRET_KEY=$(openssl rand -base64 32)
GROQ_API_KEY=your_groq_api_key
FRONTEND_URL=https://yourdomain.com
SSL_VERIFY=true
LOG_LEVEL=info
EOF

# Frontend setup
cd ../frontend
npm ci
npm run build
```

#### 1.3. Configure PM2

```bash
# Create PM2 ecosystem file
cd /var/www/eduaipro
cat > ecosystem.config.js << EOF
module.exports = {
  apps: [{
    name: 'eduaipro-backend',
    cwd: '/var/www/eduaipro/backend-node',
    script: 'npm',
    args: 'start',
    instances: 'max',
    exec_mode: 'cluster',
    env: {
      NODE_ENV: 'production',
    },
    error_file: '/var/log/eduaipro/backend-error.log',
    out_file: '/var/log/eduaipro/backend-out.log',
    log_date_format: 'YYYY-MM-DD HH:mm:ss Z',
  }]
};
EOF

# Create log directory
sudo mkdir -p /var/log/eduaipro
sudo chown -R $USER:$USER /var/log/eduaipro

# Start application with PM2
pm2 start ecosystem.config.js

# Setup PM2 to start on boot
pm2 startup
pm2 save
```

#### 1.4. Configure Nginx

```bash
# Create Nginx configuration
sudo nano /etc/nginx/sites-available/eduaipro

# Add the following configuration:
```

```nginx
# Upstream backend
upstream backend {
    least_conn;
    server localhost:8081 max_fails=3 fail_timeout=30s;
}

# Redirect HTTP to HTTPS
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;
    return 301 https://$server_name$request_uri;
}

# Main HTTPS server
server {
    listen 443 ssl http2;
    server_name yourdomain.com www.yourdomain.com;

    # SSL Configuration
    ssl_certificate /etc/letsencrypt/live/yourdomain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/yourdomain.com/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;
    ssl_session_cache shared:SSL:10m;

    # Security Headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Referrer-Policy "no-referrer-when-downgrade" always;
    add_header Content-Security-Policy "default-src 'self' http: https: data: blob: 'unsafe-inline'" always;

    # Gzip Compression
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css text/xml text/javascript application/x-javascript application/xml+rss application/json;

    # Frontend (React)
    root /var/www/eduaipro/frontend/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # Backend API
    location /api {
        proxy_pass http://backend;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_cache_bypass $http_upgrade;
        proxy_read_timeout 90;
    }

    # Logs
    access_log /var/log/nginx/eduaipro-access.log;
    error_log /var/log/nginx/eduaipro-error.log;
}
```

```bash
# Enable the site
sudo ln -s /etc/nginx/sites-available/eduaipro /etc/nginx/sites-enabled/

# Test Nginx configuration
sudo nginx -t

# Reload Nginx
sudo systemctl reload nginx
```

#### 1.5. SSL Certificate (Let's Encrypt)

```bash
# Install Certbot
sudo apt install -y certbot python3-certbot-nginx

# Obtain SSL certificate
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com

# Auto-renewal is configured automatically
# Test renewal
sudo certbot renew --dry-run
```

---

### Method 2: Docker Deployment

#### 2.1. Create Dockerfiles

**Backend Dockerfile** (`backend-node/Dockerfile`):

```dockerfile
FROM node:20-alpine

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci --only=production

# Copy application files
COPY . .

# Create necessary directories
RUN mkdir -p logs backups data

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD node -e "require('http').get('http://localhost:8081/api/health', (r) => process.exit(r.statusCode === 200 ? 0 : 1))"

# Start application
CMD ["npm", "start"]
```

**Frontend Dockerfile** (`frontend/Dockerfile`):

```dockerfile
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy application files
COPY . .

# Build application
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy built files
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

#### 2.2. Docker Compose

**docker-compose.yml**:

```yaml
version: '3.8'

services:
  backend:
    build:
      context: ./backend-node
      dockerfile: Dockerfile
    container_name: eduaipro-backend
    restart: unless-stopped
    ports:
      - "8081:8081"
    environment:
      - NODE_ENV=production
      - PORT=8081
      - SECRET_KEY=${SECRET_KEY}
      - GROQ_API_KEY=${GROQ_API_KEY}
      - FRONTEND_URL=https://yourdomain.com
    volumes:
      - ./backend-node/data:/app/data
      - ./backend-node/logs:/app/logs
      - ./backend-node/backups:/app/backups
    networks:
      - eduaipro-network
    healthcheck:
      test: ["CMD", "wget", "--quiet", "--tries=1", "--spider", "http://localhost:8081/api/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s

  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: eduaipro-frontend
    restart: unless-stopped
    ports:
      - "80:80"
      - "443:443"
    depends_on:
      - backend
    networks:
      - eduaipro-network
    volumes:
      - /etc/letsencrypt:/etc/letsencrypt:ro

networks:
  eduaipro-network:
    driver: bridge
```

#### 2.3. Deploy with Docker

```bash
# Create .env file
cat > .env << EOF
SECRET_KEY=$(openssl rand -base64 32)
GROQ_API_KEY=your_groq_api_key
EOF

# Build and start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

---

### Method 3: Cloud Platform Deployment (AWS, Azure, GCP)

#### AWS Elastic Beanstalk

```bash
# Install EB CLI
pip install awsebcli --upgrade --user

# Initialize EB
eb init -p node.js-18 eduaipro-app --region us-east-1

# Create environment
eb create eduaipro-prod --instance-type t3.medium

# Deploy
eb deploy

# Open application
eb open
```

#### Heroku

```bash
# Install Heroku CLI
# Then:

# Login
heroku login

# Create app
heroku create eduaipro-prod

# Add buildpack
heroku buildpacks:add heroku/nodejs

# Set environment variables
heroku config:set NODE_ENV=production
heroku config:set SECRET_KEY=your-secret-key
heroku config:set GROQ_API_KEY=your-groq-api-key

# Deploy
git push heroku main

# Open app
heroku open
```

---

## Security Hardening

### 1. Firewall Configuration (UFW)

```bash
# Enable UFW
sudo ufw enable

# Allow SSH
sudo ufw allow 22/tcp

# Allow HTTP/HTTPS
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp

# Deny all other incoming
sudo ufw default deny incoming
sudo ufw default allow outgoing

# Check status
sudo ufw status verbose
```

### 2. Fail2Ban (Brute Force Protection)

```bash
# Install Fail2Ban
sudo apt install -y fail2ban

# Configure
sudo nano /etc/fail2ban/jail.local
```

```ini
[DEFAULT]
bantime = 3600
findtime = 600
maxretry = 5

[sshd]
enabled = true

[nginx-limit-req]
enabled = true
filter = nginx-limit-req
logpath = /var/log/nginx/*error.log
```

```bash
# Restart Fail2Ban
sudo systemctl restart fail2ban
```

### 3. Regular Updates

```bash
# Create update script
cat > /usr/local/bin/update-system.sh << 'EOF'
#!/bin/bash
apt update
apt upgrade -y
apt autoremove -y
npm audit fix
EOF

chmod +x /usr/local/bin/update-system.sh

# Schedule with cron (weekly)
sudo crontab -e
# Add: 0 2 * * 0 /usr/local/bin/update-system.sh
```

---

## Database Migration

### From JSON to PostgreSQL (Production Scale)

```bash
# Install PostgreSQL
sudo apt install -y postgresql postgresql-contrib

# Create database
sudo -u postgres psql
CREATE DATABASE eduaipro;
CREATE USER eduaipro_user WITH PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE eduaipro TO eduaipro_user;
\q

# Export JSON data
node -e "const data = require('./data/eduai_pro.json'); console.log(JSON.stringify(data));" > export.json

# Use migration script (create migration script as needed)
```

---

## Monitoring & Maintenance

### 1. Application Monitoring with PM2

```bash
# Monitor processes
pm2 monit

# View logs
pm2 logs eduaipro-backend

# View status
pm2 status

# Restart application
pm2 restart eduaipro-backend

# Reload (zero-downtime)
pm2 reload eduaipro-backend
```

### 2. System Monitoring

```bash
# Install monitoring tools
sudo apt install -y htop iotop nethogs

# Check system resources
htop

# Check disk usage
df -h

# Check memory usage
free -h

# Check network connections
sudo netstat -tulpn | grep LISTEN
```

### 3. Log Rotation

```bash
# Configure logrotate
sudo nano /etc/logrotate.d/eduaipro
```

```
/var/log/eduaipro/*.log {
    daily
    missingok
    rotate 14
    compress
    delaycompress
    notifempty
    create 0640 www-data www-data
    sharedscripts
    postrotate
        pm2 reloadLogs
    endscript
}
```

### 4. Automated Backups

```bash
# Create backup script
cat > /usr/local/bin/backup-eduaipro.sh << 'EOF'
#!/bin/bash
BACKUP_DIR="/var/backups/eduaipro"
DATE=$(date +%Y-%m-%d_%H-%M-%S)
APP_DIR="/var/www/eduaipro"

mkdir -p $BACKUP_DIR

# Backup database
cp -r $APP_DIR/backend-node/data $BACKUP_DIR/data-$DATE

# Backup configuration
cp $APP_DIR/backend-node/.env $BACKUP_DIR/env-$DATE

# Compress
tar -czf $BACKUP_DIR/backup-$DATE.tar.gz $BACKUP_DIR/data-$DATE $BACKUP_DIR/env-$DATE

# Remove old backups (keep 30 days)
find $BACKUP_DIR -name "backup-*.tar.gz" -mtime +30 -delete

# Cleanup
rm -rf $BACKUP_DIR/data-$DATE $BACKUP_DIR/env-$DATE
EOF

chmod +x /usr/local/bin/backup-eduaipro.sh

# Schedule daily backups
sudo crontab -e
# Add: 0 3 * * * /usr/local/bin/backup-eduaipro.sh
```

---

## Troubleshooting

### Application Won't Start

```bash
# Check PM2 logs
pm2 logs eduaipro-backend --lines 100

# Check system logs
sudo journalctl -u nginx -n 50

# Verify Node.js version
node --version

# Check port availability
sudo netstat -tulpn | grep 8081
```

### High Memory Usage

```bash
# Check PM2 memory usage
pm2 list

# Restart application
pm2 restart eduaipro-backend

# Check for memory leaks in logs
grep -i "memory" /var/log/eduaipro/*.log
```

### Database Issues

```bash
# Check database file
ls -lh /var/www/eduaipro/backend-node/data/

# Verify permissions
sudo chown -R www-data:www-data /var/www/eduaipro/backend-node/data

# Restore from backup
cp /var/backups/eduaipro/latest-backup/data/* /var/www/eduaipro/backend-node/data/
pm2 restart eduaipro-backend
```

### Performance Issues

```bash
# Enable PM2 monitoring
pm2 install pm2-server-monit

# Check Nginx access logs
tail -f /var/log/nginx/eduaipro-access.log

# Check slow queries
grep "slow" /var/log/eduaipro/*.log

# Optimize Nginx
# Add caching headers in nginx.conf
```

---

## Post-Deployment

### 1. Smoke Testing

```bash
# Health check
curl https://yourdomain.com/api/health

# Authentication
curl -X POST https://yourdomain.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password"}'

# Load test (optional)
sudo apt install -y apache2-utils
ab -n 1000 -c 10 https://yourdomain.com/api/health
```

### 2. Monitoring Setup

- Configure uptime monitoring (e.g., UptimeRobot)
- Set up error tracking (e.g., Sentry)
- Configure log aggregation (e.g., ELK Stack)
- Set up performance monitoring (e.g., New Relic, DataDog)

### 3. Documentation

- Document deployment process
- Create runbook for common issues
- Document backup/restore procedures
- Create disaster recovery plan

---

## Support

For deployment assistance:
- Email: devops@eduaipro.com
- Documentation: https://docs.eduaipro.com

---

**© 2024 EduAI Pro. All Rights Reserved.**

