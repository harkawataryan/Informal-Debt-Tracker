# Informal Debt Tracker
A small Spring Boot thingy to keep tabs on who owes who between friends/family. Uses MySQL + Spring Data JPA for storage and Spring Mail (JavaMail) for reminder emails. Not production-grade but clean-ish (and a tad janky).

## Features
- Create people (name + email)
- Log debts (from → to, amount, note, due date)
- Mark debts as settled
- Send reminder emails to debtors
- REST-only (tiny static index) — bring your own UI/postman/curl

## Quick start
```bash
# copy env template
cp sample.env .env

# spin up db + MailHog (local SMTP) if you want
docker compose up -d

# build & run
mvn -q spring-boot:run
# or: mvn -q -DskipTests package && java -jar target/informal-debt-tracker-0.1.0.jar
```

Open MailHog UI at http://localhost:8025 to see test emails.

### Endpoints
- `GET  /api/people`
- `POST /api/people` { name, email }
- `GET  /api/debts?status=OPEN|SETTLED`
- `POST /api/debts` { fromId, toId, amount, dueDate(YYYY-MM-DD), note }
- `POST /api/debts/{id}/settle`
- `POST /api/reminders/send` { daysAhead?: int }

### Env vars
Set via `.env` or actual env:
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=janky_debts
DB_USER=janky
DB_PASS=janky

MAIL_HOST=localhost
MAIL_PORT=1025
MAIL_USER=
MAIL_PASS=
MAIL_FROM=debts@localhost
```
