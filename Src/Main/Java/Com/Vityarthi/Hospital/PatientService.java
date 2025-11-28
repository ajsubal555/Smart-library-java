package com.vityarthi.hospital;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class PatientService {
    private final Map<Long, Patient> patients = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final Path dataFile = Paths.get("data/patients.csv");

    public Patient createPatient(String name, int age, String gender, String contact) {
        long id = idGen.getAndIncrement();
        Patient p = new Patient(id, name, age, gender, contact);
        patients.put(id, p);
        return p;
    }

    public Collection<Patient> getAll() { return patients.values(); }

    public Optional<Patient> findById(long id) { return Optional.ofNullable(patients.get(id)); }

    public void save() {
        try {
            Files.createDirectories(dataFile.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(dataFile)) {
                for (Patient p : patients.values()) {
                    w.write(String.format("%d,%s,%d,%s,%s%n", p.getId(), escape(p.getName()), p.getAge(), escape(p.getGender()), escape(p.getContact())));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save patients: " + e.getMessage());
        }
    }

    public void load() {
        if (!Files.exists(dataFile)) return;
        try (BufferedReader r = Files.newBufferedReader(dataFile)) {
            String line;
            long maxId = 0;
            while ((line = r.readLine()) != null) {
                String[] parts = splitCsv(line,5);
                long id = Long.parseLong(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String gender = parts[3];
                String contact = parts[4];
                patients.put(id, new Patient(id, name, age, gender, contact));
                if (id > maxId) maxId = id;
            }
            idGen.set(maxId + 1);
        } catch (IOException e) {
            System.err.println("Failed to load patients: " + e.getMessage());
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\n"," ").replace(",",";");
    }
    private static String[] splitCsv(String line, int expected) {
        String[] arr = line.split(",", -1);
        if (arr.length < expected) {
            String[] out = new String[expected];
            System.arraycopy(arr, 0, out, 0, arr.length);
            for (int i=arr.length;i<expected;i++) out[i] = "";
            return out;
        }
        return arr;
    }
}
