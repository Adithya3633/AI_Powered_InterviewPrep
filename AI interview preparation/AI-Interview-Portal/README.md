# AI-Interview-Portal

**Project Overview**
- **Description**: A simple Spring Boot web application that provides a coding interview portal with question browsing, an in-browser code editor, remote code execution (via JDoodle), and submission history.
- **Language**: Java 17
- **Frameworks**: Spring Boot 3 (Web, Data JPA, Security, Thymeleaf)

**Key Files**
- **Build**: [pom.xml](pom.xml)
- **Main**: [src/main/java/com/Portal/AI_Interview_Portal/AiInterviewPortalApplication.java](src/main/java/com/Portal/AI_Interview_Portal/AiInterviewPortalApplication.java)
- **Security**: [src/main/java/com/Portal/AI_Interview_Portal/config/SecurityConfig.java](src/main/java/com/Portal/AI_Interview_Portal/config/SecurityConfig.java)
- **Controllers**: [src/main/java/com/Portal/AI_Interview_Portal/controller](src/main/java/com/Portal/AI_Interview_Portal/controller)
- **Entities**: [src/main/java/com/Portal/AI_Interview_Portal/entity](src/main/java/com/Portal/AI_Interview_Portal/entity)
- **Templates**: [src/main/resources/templates](src/main/resources/templates)
- **DB Init**: [src/main/resources/data.sql](src/main/resources/data.sql)
- **Config**: [src/main/resources/application.properties](src/main/resources/application.properties)

**Features**
- User registration and login (Spring Security, BCrypt password encoding).
- Browse questions by category and open a code editor for a selected question.
- Submit code to JDoodle API and store execution result as a `Submission` record.
- View past submissions for the logged-in user.
- Embedded H2 in-memory database for quick local testing.

**Architecture (High level)**

```mermaid
flowchart TD
  A[Browser UI (Thymeleaf pages)] -->|HTTP| B[Controllers]
  B --> C[Service / Repositories]
  C --> D[(H2 Database)]
  B -->|REST| E[JDoodle API]
  subgraph App
    B
    C
  end
```

**Run locally (Windows)**
- Build the artifact:

```bash
.
# From project root (Windows PowerShell)
./mvnw.cmd clean package -DskipTests -q
```
- Run the JAR:

```bash
java -jar target/AI-Interview-Portal-0.0.1-SNAPSHOT.jar
```
- Default server: `http://localhost:8096`
- H2 console: `http://localhost:8096/h2-console` (JDBC URL: `jdbc:h2:mem:interview`, user `SA`)

**Configuration notes**
- JDoodle credentials are configured in `src/main/resources/application.properties` as `jdoodle.clientId` and `jdoodle.clientSecret` — replace these with your own credentials.
- The code execution controller currently contains hard-coded JDoodle values in `CodeController.java`. For production or tests, remove hardcoded values and use the configuration properties (via `@Value` or `@ConfigurationProperties`).
- DB auto schema is driven by `spring.jpa.hibernate.ddl-auto=create-drop` (suitable for local/dev only).

**Database and Data**
- Questions are pre-populated from `data.sql` on startup.
- Submissions are stored in the `submission` table with `code` and `output` stored as `TEXT`.

**Endpoints and Pages**
- **Home**: `/` -> index
- **Register**: `/register` -> register form (creates a user)
- **Login**: `/login` -> login form
- **Dashboard**: `/dashboard` -> post-login landing page
- **List questions**: `/questions?category=<category>` -> questions.html
- **Code editor**: `/code-editor?question=<q>&category=<c>` -> code-editor.html
- **Submit code**: POST `/submit-code` -> executed by `CodeController` and returns `code-result` page
- **Submissions list**: `/submissions` -> shows submissions for logged-in user
- **H2 Console**: `/h2-console`

**UI Flow (user journey)**
- Visit `/` -> Register `/register` or Login `/login` -> After login -> `/dashboard` -> Select category -> `/questions?category=java` -> Click a question -> `/code-editor` -> Write code & submit -> `code-result` shows output -> Visit `/submissions` to view history.

**Security**
- Passwords are encoded with BCrypt (`userservice` uses `PasswordEncoder` bean).
- `SecurityConfig` permits public access to `/`, `/register`, login, css/js; all other routes require authentication.

**Screenshots to capture (suggested filenames)**
- Index / Landing: screenshots/index.png
- Register form: screenshots/register.png
- Login page: screenshots/login.png
- Dashboard: screenshots/dashboard.png
- Questions list (category): screenshots/questions-java.png
- Code editor (with a sample solution): screenshots/code-editor.png
- Code result (output): screenshots/code-result.png
- Submissions list: screenshots/submissions.png
- H2 Console (showing `question` table): screenshots/h2-console.png

How to capture: open the page in a browser, press `PrtSc` or use Windows Snipping Tool, save into `screenshots/` folder (create at project root) and include them in the repository.

**Demo Video**
A walkthrough demo of the application is available at the following link:
https://drive.google.com/file/d/1sydyOvpfI3hksrm22Ft-dos_gELkIqS1/view?usp=drive_link

**Recommended improvements / TODOs**
- Remove hard-coded JDoodle credentials from `CodeController` and read from `application.properties` or environment variables.
- Add server-side validation for code inputs and length limits to prevent abuse.
- Add role-based pages (admin) to manage question bank.
- Add integration tests for controller flows and a small e2e script demonstrating login, submit-code.
- Consider using a local containerized executor for safer code execution in development (instead of a third-party API).

**Developer tips**
- To change port: edit `server.port` in `application.properties`.
- To persist DB between restarts, switch to a file-based H2 URL or a MySQL/Postgres datasource and update `application.properties`.

**Contributing**
- Fork, create a branch, run tests, open a PR. Keep changes minimal and document new behavior.

---

If you want, I can:
- Commit this `README.md` into the repository now.
- Replace hard-coded JDoodle values in `CodeController` with property-based configuration.
- Generate the `screenshots/` folder and example placeholder images.

