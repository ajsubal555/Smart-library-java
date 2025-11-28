# Smart Hospital Appointment System (Java Console)

This is a simple **console-based Hospital Appointment System** implemented in Java.
It is suitable for a 2nd-year project submission and ready to upload to GitHub.

## Features
- Manage Patients (Add / List)
- Manage Doctors (Add / List)
- Book Appointment (checks doctor availability)
- Cancel Appointment
- View Appointments (by patient / doctor / all)
- Simple CSV persistence (data/ folder)

## How to run

### Using Java (no Maven required)
1. Compile:
```bash
javac -d out $(find src -name "*.java")
```
2. Run:
```bash
java -cp out com.vityarthi.hospital.Main
```

### Using an IDE
Import the project folder as a Java project and run `com.vityarthi.hospital.Main`.

## Project Structure
```
smart-hospital-appointment/
├─ README.md
├─ statement.md
├─ .gitignore
├─ pom.xml (optional)
├─ src/
│  └─ main/
│     └─ java/com/vityarthi/hospital/  (Java files)
└─ data/  (created at runtime)
```

## Notes
- The app stores simple CSV files in `data/` for persistence.
- This is a console app to make submission and evaluation easy.
