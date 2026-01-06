#!/bin/bash

echo "EduAI Pro - Setup Validation"
echo "============================"
echo ""

# Check Java
echo -n "Checking Java... "
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo "OK - $JAVA_VERSION"
else
    echo "FAILED - Java not found"
    exit 1
fi

# Check Maven
echo -n "Checking Maven... "
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1)
    echo "OK - $MVN_VERSION"
else
    echo "FAILED - Maven not found"
    exit 1
fi

# Check Node.js
echo -n "Checking Node.js... "
if command -v node &> /dev/null; then
    NODE_VERSION=$(node --version)
    echo "OK - $NODE_VERSION"
else
    echo "FAILED - Node.js not found"
    exit 1
fi

# Check npm
echo -n "Checking npm... "
if command -v npm &> /dev/null; then
    NPM_VERSION=$(npm --version)
    echo "OK - $NPM_VERSION"
else
    echo "FAILED - npm not found"
    exit 1
fi

# Check PostgreSQL
echo -n "Checking PostgreSQL... "
if command -v psql &> /dev/null; then
    PSQL_VERSION=$(psql --version | head -n 1)
    echo "OK - $PSQL_VERSION"
else
    echo "WARNING - PostgreSQL not found (may be installed differently)"
fi

# Check backend directory
echo -n "Checking backend directory... "
if [ -d "backend" ]; then
    echo "OK"
else
    echo "FAILED - backend directory not found"
    exit 1
fi

# Check frontend directory
echo -n "Checking frontend directory... "
if [ -d "frontend" ]; then
    echo "OK"
else
    echo "FAILED - frontend directory not found"
    exit 1
fi

# Check application.yml
echo -n "Checking application.yml... "
if [ -f "backend/src/main/resources/application.yml" ]; then
    echo "OK"
else
    echo "FAILED - application.yml not found"
    exit 1
fi

echo ""
echo "Setup validation complete!"

