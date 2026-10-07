# 🏛️ Multilingual Chatbot-Based Museum Ticketing System

A **console-based museum ticketing application developed in Java** that combines a chatbot-style conversational interface with ticket booking, user authentication, multilingual support, simulated payments, receipt generation, analytics, and data persistence.

The project is designed as an academic Java application demonstrating **Object-Oriented Programming, Collections, Exception Handling, File I/O, JDBC, Localization, Multithreading, Generics, Interfaces, and Java language features** through a practical real-world use case.

---

## 📌 Project Overview

The **Multilingual Chatbot-Based Museum Ticketing System** allows museum visitors to interact with the application through a chatbot-style interface.

Users can:

- Select their preferred language
- Register and log in
- Browse available museum exhibits
- Search exhibits
- Check available time slots
- Book museum tickets
- Select ticket quantities
- Make simulated payments
- Generate booking receipts
- View booking history
- Cancel bookings
- Receive localized messages

Administrators have additional functionality for authentication and viewing museum-related analytics.

The application also demonstrates concurrent booking management to help prevent overbooking when multiple booking operations occur simultaneously.

---

## ✨ Key Features

### 🤖 Chatbot-Based Interaction

The application uses a conversational console interface instead of a traditional GUI.

The chatbot includes:

- Command processing
- Input normalization
- Command aliases
- Conversation state management
- Context-based navigation
- Help commands
- Yes/No response handling
- Error handling

Example commands include:

```text
view
search
book
mybookings
cancel
help
login
register
logout
```

---

### 🌐 Multilingual Support

The system provides localized interaction through a dedicated localization layer.

Users can select their preferred language when starting the application.

The localization system manages:

- Menu messages
- Authentication messages
- Booking messages
- Payment messages
- Error messages
- Help messages
- Application prompts

Supported languages are implemented through the project's `Language`, `MessageProvider`, and `LocalizationService` components.

---

### 👤 User Authentication

The application supports separate visitor and administrator accounts.

#### Visitor

Visitors can:

- Register
- Log in
- Change passwords
- View exhibits
- Search exhibits
- Book tickets
- View booking history
- Cancel bookings
- Log out

#### Administrator

Administrators have separate authentication and access to administrative functionality such as analytics.

---

### 🏛️ Museum Exhibit Management

The system maintains museum exhibits containing information such as:

- Exhibit ID
- Exhibit name
- Description
- Category
- Entry fee
- Location
- Availability status

Visitors can browse and search exhibits based on available information.

---

### 🎟️ Ticket Booking

Visitors can book tickets by selecting:

1. Exhibit
2. Visit date
3. Available time slot
4. Number of tickets

The system calculates the total amount and creates a booking record.

The booking workflow includes:

```text
Select Exhibit
      ↓
Select Date
      ↓
Select Time Slot
      ↓
Enter Ticket Quantity
      ↓
Check Availability
      ↓
Create Booking
      ↓
Process Payment
      ↓
Confirm Booking
      ↓
Generate Receipt
```

---

### ⏰ Time Slot & Availability Management

Each exhibit can have multiple time slots with a defined capacity.

The system tracks:

- Slot date
- Start time
- End time
- Maximum capacity
- Booked tickets
- Remaining capacity
- Slot status

The booking service checks capacity before allowing a booking.

This helps prevent invalid bookings and overbooking.

---

### 💳 Simulated Payment System

The project includes a simulated payment service rather than a real payment gateway.

Supported payment methods include:

- UPI
- Card
- Cash

The payment system demonstrates transaction processing, payment status tracking, transaction IDs, refunds, and revenue calculation.

Payment processing is intentionally simulated and includes a configurable success probability for academic demonstration purposes.

> **Note:** This project does not process real financial transactions.

---

### 🧾 Receipt Generation

After successful booking and payment, the application generates a receipt containing booking and payment information.

Receipt information includes:

- Receipt ID
- Booking ID
- Transaction ID
- Visitor details
- Exhibit
- Visit date
- Time slot
- Ticket quantity
- Price
- Total amount
- Payment method
- Payment status
- Booking status

Receipts are stored under the project's data directory.

---

### 📊 Analytics & Reporting

The project includes an analytics/reporting service for administrative use.

The system can calculate:

- Total bookings
- Confirmed bookings
- Cancelled bookings
- Tickets sold
- Total revenue
- Most visited exhibit
- Peak time slot
- Average tickets per booking

Example:

```text
========================================
        DAILY MUSEUM ANALYTICS
========================================

Total Bookings       : 10
Confirmed Bookings   : 8
Cancelled Bookings   : 2

Tickets Sold         : 32
Total Revenue        : ₹4800.00

Most Visited Exhibit : Ancient India
Peak Time Slot       : 10:00 AM - 11:00 AM

Average Tickets/
Booking              : 3.20

========================================
```

---

## 🗄️ Data Persistence

The project supports file-based persistence for the core application workflow.

Application data is organized under:

```text
data/
├── users.txt
├── bookings.txt
├── exhibits.txt
├── time_slots.txt
├── test_users.txt
├── test_bookings.txt
├── test_exhibits.txt
├── test_output.txt
└── reciepts/
```

Repository classes provide a separation between business logic and data storage.

---

## 🛢️ MySQL & JDBC Support

The project also contains a MySQL database design and JDBC demonstrations.

The database contains tables for:

```text
users
exhibits
time_slots
bookings
payments
receipts
```

The database schema and sample data are provided in:

```text
database/
├── museum_db.sql
└── sample_data.sql
```

The project demonstrates:

- JDBC driver loading
- Database connections
- SQL execution
- Prepared statements
- Result processing
- Resource management
- Try-with-resources

> The primary application workflow includes file-based persistence, while the project also provides JDBC/MySQL components for database integration and academic demonstration.

---

## 🧵 Concurrency & Thread Safety

The booking system demonstrates Java concurrency concepts.

Booking operations use synchronization around critical booking operations to prevent race conditions where multiple operations could attempt to consume the same available capacity.

This is particularly important for:

```text
Available Capacity
       ↓
Check Capacity
       ↓
Reserve Tickets
       ↓
Update Capacity
```

The project also contains dedicated threading/concurrency demonstrations.

---

## 🏗️ Project Architecture

The project follows a layered structure separating models, services, repositories, chatbot functionality, localization, utilities, and database-related components.

```text
src/
└── museum/
    ├── chatbot/
    │   ├── Chatbot.java
    │   ├── CommandProcessor.java
    │   ├── ConversationManager.java
    │   ├── InputNormalizer.java
    │   └── ChatbotIntegration.java
    │
    ├── config/
    │
    ├── demo/
    │
    ├── exception/
    │
    ├── generic/
    │   └── Repository.java
    │
    ├── interfaces/
    │   ├── Authenticatable.java
    │   └── Reportable.java
    │
    ├── jdbc/
    │   ├── BookingDAO.java
    │   ├── UserDAO.java
    │   ├── CRUDDemo.java
    │   └── DatabaseConnectionDemo.java
    │
    ├── localization/
    │   ├── LocalizationService.java
    │   └── MessageProvider.java
    │
    ├── model/
    │   ├── User.java
    │   ├── Visitor.java
    │   ├── Admin.java
    │   ├── Exhibit.java
    │   ├── Booking.java
    │   ├── Ticket.java
    │   ├── Payment.java
    │   ├── Receipt.java
    │   ├── TimeSlot.java
    │   └── Language.java
    │
    ├── repository/
    │   ├── UserRepository.java
    │   ├── ExhibitRepository.java
    │   └── BookingRepository.java
    │
    ├── service/
    │   ├── AuthenticationService.java
    │   ├── ExhibitService.java
    │   ├── AvailabilityService.java
    │   ├── BookingService.java
    │   ├── PaymentService.java
    │   └── ReportService.java
    │
    ├── threading/
    │
    ├── util/
    │   ├── FileUtil.java
    │   ├── IdGenerator.java
    │   ├── InputValidator.java
    │   └── ReceiptGenerator.java
    │
    └── Main.java
```

---

## 🧩 Technologies Used

| Technology | Purpose |
|---|---|
| **Java / JDK 24** | Core application development |
| **OOP** | Application architecture |
| **Java Collections** | Data management |
| **Generics** | Reusable repository abstraction |
| **Exception Handling** | Error management |
| **File I/O** | Local data persistence |
| **JDBC** | Database connectivity |
| **MySQL** | Relational database support |
| **Multithreading** | Concurrency demonstration |
| **Interfaces** | Abstraction |
| **Enums** | Language and command management |
| **StringBuilder** | Report and output generation |
| **IntelliJ IDEA** | Development environment |

---

## 📚 Java Concepts Demonstrated

This project was developed as a practical demonstration of several Java programming concepts.

### Object-Oriented Programming

- Classes and objects
- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces
- Method overriding

### Core Java

- Variables and data types
- Methods
- Constructors
- Control flow
- Switch statements
- Strings
- Arrays
- Enums

### Collections Framework

- `ArrayList`
- `HashMap`
- `List`
- `Map`
- Iteration
- Filtering
- Aggregation

### Exception Handling

The project includes custom exceptions for situations such as:

- Invalid input
- Invalid login
- Booking not found
- Slot unavailable
- Authentication errors

### Advanced Concepts

- Generics
- Repository pattern
- Service layer
- JDBC
- File persistence
- Localization
- Multithreading
- Synchronization
- State-based chatbot flow
- Input normalization

---

## 📁 Repository Structure

```text
finalproj/
│
├── src/
│   └── museum/
│
├── data/
│   ├── users.txt
│   ├── bookings.txt
│   ├── exhibits.txt
│   ├── time_slots.txt
│   └── reciepts/
│
├── database/
│   ├── museum_db.sql
│   └── sample_data.sql
│
├── lib/
│
├── .idea/
│
├── DATABASE_DOCUMENTATION.md
├── DATABASE_SETUP_GUIDE.txt
├── JDK_AND_DATABASE_SETUP.txt
├── TEST_REPORT.md
├── DEBUG_REPORT.md
├── CHANGED_FILES.md
└── Multi-Lingual Chat Bot Based Museum Ticketing System-Java.iml
```

---

## ⚙️ Requirements

Before running the project, install:

- **JDK 24**
- **IntelliJ IDEA** or another Java IDE
- **MySQL** — required only for the JDBC/MySQL components
- MySQL Connector/J — required when running the JDBC database components

Verify Java:

```bash
java -version
```

Verify the compiler:

```bash
javac -version
```

The project is intended for **JDK 24**.

---

## 🚀 How to Run

### 1. Clone the repository

```bash
git clone <YOUR-REPOSITORY-URL>
```

### 2. Open the project

Open the project in IntelliJ IDEA.

Select:

```text
File → Open
```

and choose the project directory.

### 3. Configure JDK

Set the project SDK to:

```text
JDK 24
```

### 4. Run the application

Locate:

```text
src/museum/Main.java
```

and run the `Main` class.

The application starts with the multilingual chatbot interface.

---

## 🗄️ MySQL Setup

If you want to use the database/JDBC components:

### Step 1 — Start MySQL

Make sure your MySQL server is running.

### Step 2 — Create the database

Execute:

```sql
SOURCE database/museum_db.sql;
```

or open `database/museum_db.sql` in MySQL Workbench and execute it.

### Step 3 — Configure JDBC

Update the database connection configuration with your local MySQL credentials.

Do **not** commit real database passwords or credentials to GitHub.

Use environment variables or a local configuration file for sensitive credentials.

---

## 🧪 Testing

The project contains multiple phase-based test programs covering different parts of the system.

Testing includes:

- Model classes
- File utilities
- Input validation
- Repository operations
- Authentication
- Exhibit management
- Availability management
- Payment simulation
- Localization
- Chatbot functionality
- End-to-end booking
- Receipt generation
- Concurrency demonstrations
- Java feature demonstrations

The project also includes:

```text
TEST_REPORT.md
DEBUG_REPORT.md
```

for additional testing and verification information.

### Verification Note

The supplied test report confirms successful compilation and several functional tests. Full live **JDK 24 + MySQL** verification was not performed in the repair environment because JDK 24 and a live MySQL installation/driver were unavailable there.

---

## 🔐 Security Note

This is an **academic/educational project**.

The payment system is simulated and does not process real payments.

For a production system, additional security measures would be required, including:

- Password hashing
- Secure credential management
- Environment variables
- Database connection security
- Input sanitization
- Proper authentication/session management
- Production payment gateway integration
- HTTPS/API security

Never commit real passwords, database credentials, API keys, or other secrets to the repository.

---

## 🎯 Project Objectives

The main objectives of this project are to:

1. Build a practical Java application using OOP principles.
2. Implement a chatbot-style user interaction system.
3. Provide multilingual application support.
4. Implement museum exhibit and ticket management.
5. Demonstrate authentication and authorization.
6. Implement booking and availability management.
7. Demonstrate simulated payment processing.
8. Generate digital booking receipts.
9. Implement analytics and reporting.
10. Demonstrate file persistence and JDBC concepts.
11. Apply Java collections and generics.
12. Demonstrate exception handling and concurrency.
13. Organize the application using a layered architecture.

---

## 🔮 Future Enhancements

Possible future improvements include:

- Web-based or mobile UI
- REST API backend
- Real-time database integration
- Secure password hashing
- JWT-based authentication
- Real payment gateway integration
- QR-code ticket generation
- Email/SMS ticket confirmation
- Admin dashboard
- Real AI/NLP chatbot integration
- Cloud deployment
- Docker support
- Advanced visitor analytics
- Online museum seat/slot visualization

---

## 👨‍💻 Author

**Sriram S**

B.E. Computer Science and Engineering

Chennai Institute of Technology

---

## 📄 License

This project was developed for **academic and educational purposes**.

You may adapt the project for learning and educational use. For other uses, please contact the author.
