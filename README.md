# 🎟 Event Booking System

## 🚀 Project Overview
This is a **Java-based Event Booking System** developed using **JDBC, MySQL, and Swing GUI**.

The system automates event management, seat booking, and ticket generation.  
It ensures efficient handling of events with real-time seat availability tracking and prevents duplicate bookings.

---

## ⚙️ Technologies Used
- **Java (Core + OOP Concepts)**
- **JDBC (Database Connectivity)**
- **MySQL (Database Management)**
- **Swing (GUI Dashboard)**

---

## 🎯 Key Features
- ✔ Add new events (Admin)
- ✔ View all events
- ✔ Book seats automatically
- ✔ Prevent duplicate seat booking
- ✔ Cancel bookings
- ✔ Check available seats in real time
- ✔ Automatic ticket generation system
- ✔ Event date validation (no past event booking)

---

## 🗄️ Database Structure

### 📌 Events Table
- id (Primary Key)
- event_name
- total_seats
- price
- event_date

### 📌 Bookings Table
- id (Primary Key)
- user_name
- event_id (Foreign Key)
- seat_number
- booking_date
- status

---

## 🧠 Project Logic
- Each event has a fixed number of seats.
- Seats are automatically assigned based on availability.
- System checks booked seats before assigning new ones.
- Event date validation ensures booking is only allowed for future events.
- Tickets are generated automatically after successful booking.

---

## 🖥️ How to Run the Project

1. Clone the repository:
```bash
git clone https://github.com/your-username/Event-Booking-System.git
Open project in VS Code / IntelliJ IDEA / Eclipse
Create MySQL database:
CREATE DATABASE event_system;
Create required tables:
events
bookings
Update database credentials in:
DBConnection.java
Run:
Event_Dashboard_GUI.java
📸 Screenshots
🏠 View Events
<img width="1176" height="731" alt="view_events png" src="https://github.com/user-attachments/assets/bc36be13-e26d-4db2-8a63-f53b6380bff6" />


🎟 Booking Screen
<img width="1168" height="750" alt="Booking-seats png" src="https://github.com/user-attachments/assets/a5bc6d4e-2439-41cc-9f8b-32b4c1ee4878" />


📋 Available Seats
<img width="1147" height="711" alt="view_seats png" src="https://github.com/user-attachments/assets/f4c72462-3d36-4b07-acff-805bb97d0177" />

👨‍💻 Developer
Name: Bharat Kumar
Project Type: Database Management System (DBMS) Project
Language: Java
⭐ Future Improvements
🔥 QR Code Ticket System
🔥 Online Payment Integration
🔥 Mobile App Version
🔥 Advanced Seat Selection UI (Cinema Style)
🔥 Admin Login System
📌 Project Purpose

This project is developed for academic purposes to demonstrate:

Database Management System concepts
JDBC connectivity
Object-Oriented Programming principles
Real-world event booking system design
🏁 Conclusion

The Event Booking System successfully automates the process of event management and booking while reducing manual errors and improving efficiency.
