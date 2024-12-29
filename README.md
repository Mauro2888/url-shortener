# URL Shortener Service

A simple URL shortening service built with Jakarta EE (Quarkus and microprofile) that provides URL shortening capabilities with multiple hashing algorithm support, persistent storage, and a user-friendly web interface.

## Overview

This service allows you to:
- Create shortened URLs using different hashing algorithms (MD5, SHA1)
- Retrieve and redirect to original URLs using shortened codes
- Store URL mappings in a PostgreSQL database
- Access the service through a modern web interface

### Frontend Dependencies
- Bootstrap 5.3.2 (CSS and JS Bundle)
- Mustache template engine


## Architecture

### API Endpoints

#### 1. Create Shortened URL
- **Endpoint**: `POST /api/v1/shortener`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
    "originalUrl": "https://example.com/very-long-url",
    "algorithmViewModel": "MD5"  // Can be: "MD5", "SHA1"
}
```

#### 2. Retrieve Original URL
- **Endpoint**: `GET /api/v1/shortener?code={shortCode}`
- **Response**: HTTP 302 redirect to original URL
- **Example**: `/api/v1/shortener?code=3b4d5f6d`

### Supported Algorithms

The service supports three hashing algorithms:
```java
public enum AlgorithmViewModel {
    MD5,    // MD5 hashing algorithm
    SHA1    // SHA1 hashing algorithm
}
```

### Database Schema

```sql
CREATE SCHEMA IF NOT EXISTS short_url;

CREATE TABLE short_url.urls (
    id              UUID         NOT NULL,
    original_url    VARCHAR(255) NOT NULL,
    short_url       VARCHAR(255) NOT NULL,
    hashcode        VARCHAR(255) NOT NULL,
    insert_datetime TIMESTAMPTZ,
    update_datetime TIMESTAMPTZ  NOT NULL,
    version         INT8         NOT NULL,
    PRIMARY KEY (id)
);
```

## Technical Stack

- **Backend Framework**: Jakarta EE with Quarkus 3.16.2
- **Frontend Framework**: Bootstrap 5.3.2
- **Template Engine**: Mustache
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **API Documentation**: OpenAPI/Swagger annotations
- **Dependency Injection**: CDI


2. **URL Shortening**
  - Multiple algorithm support:
    - MD5: Creates hash using MD5 algorithm
    - SHA1: Creates hash using SHA1 algorithm
  - Configurable hash generation
  - Duplicate URL detection

3. **URL Retrieval**
  - Fast lookups using hash codes
  - HTTP redirects to original URLs
  - Error handling for invalid codes

4. **Data Persistence**
  - Version control for URL entries
  - Timestamp tracking
  - UUID-based identification

## Getting Started

1. **Database Setup**
```sql
-- Run the schema creation script
CREATE SCHEMA IF NOT EXISTS short_url;
set schema 'short_url';

-- Create the urls table
CREATE TABLE IF NOT EXISTS urls (
    -- table definition as shown above
);
```
## Usage

### Web Interface
1. Open your browser and navigate to the application URL
2. Enter a long URL in the input field
3. Click "Generate short URL"
4. Copy the generated short URL from the result field

### API Examples

1. **Creating a Short URL with MD5**
```bash
curl -X POST http://your-server/api/v1/shortener \
  -H "Content-Type: application/json" \
  -d '{
    "originalUrl": "https://example.com/very-long-url",
    "algorithmViewModel": "MD5"
  }'
```

2. **Accessing a Shortened URL**
```bash
curl -L http://your-server/api/v1/shortener?code=3b4d5f6d
```
