package com.vityarthi.hospital;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientService patientService = new PatientService();
    private static final DoctorService doctorService = new DoctorService();
    private static final AppointmentService appointmentService = new AppointmentService(patientService, doctorService);

    public static void main(String[] args) {
        // load data
        patientService.load();
        doctorService.load();
        appointmentService.load();

        System.out.println("=== Smart Hospital Appointment System ===");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addPatient(); break;
                case "2": listPatients(); break;
                case "3": addDoctor(); break;
                case "4": listDoctors(); break;
                case "5": bookAppointment(); break;
                case "6": cancelAppointment(); break;
                case "7": listAppointments(); break;
                case "8": running = false; break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }

        // save data
        patientService.save();
        doctorService.save();
        appointmentService.save();

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add Patient");
        System.out.println("2. List Patients");
        System.out.println("3. Add Doctor");
        System.out.println("4. List Doctors");
        System.out.println("5. Book Appointment");
        System.out.println("6. Cancel Appointment");
        System.out.println("7. List Appointments");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addPatient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine().trim();
        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine().trim();
        Patient p = patientService.createPatient(name, age, gender, contact);
        System.out.println("Patient added with ID: " + p.getId());
    }

    private static void listPatients() {
        System.out.println("\nPatients:");
        for (Patient p : patientService.getAll()) {
            System.out.println(p);
        }
    }

    private static void addDoctor() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter specialization: ");
        String spec = scanner.nextLine().trim();
        Doctor d = doctorService.createDoctor(name, spec);
        System.out.println("Doctor added with ID: " + d.getId());
    }

    private static void listDoctors() {
        System.out.println("\nDoctors:");
        for (Doctor d : doctorService.getAll()) {
            System.out.println(d);
        }
    }

    private static void bookAppointment() {
        System.out.print("Enter patient ID: ");
        long pid = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Enter doctor ID: ");
        long did = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Enter datetime (YYYY-MM-DD HH:MM): ");
        String datetime = scanner.nextLine().trim();
        try {
            Appointment ap = appointmentService.bookAppointment(pid, did, datetime);
            System.out.println("Appointment booked. ID: " + ap.getId());
        } catch (RuntimeException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        boolean ok = appointmentService.cancelAppointment(id);
        System.out.println(ok ? "Cancelled." : "Appointment not found or already cancelled.");
    }

    private static void listAppointments() {
        System.out.println("\nAppointments:");
        for (Appointment a : appointmentService.getAll()) {
            System.out.println(a);
        }
    }
}
