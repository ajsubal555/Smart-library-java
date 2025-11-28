package com.vityarthi.hospital;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class DoctorService {
    private final Map<Long, Doctor> doctors = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final Path dataFile = Paths.get("data/doctors.csv");

    public Doctor createDoctor(String name, String specialization) {
        long id = idGen.getAndIncrement();
        Doctor d = new Doctor(id, name, specialization);
        doctors.put(id, d);
        return d;
    }

    public Collection<Doctor> getAll() { return doctors.values(); }

    public Optional<Doctor> findById(long id) { return Optional.ofNullable(doctors.get(id)); }

    public void save() {
        try {
            Files.createDirectories(dataFile.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(dataFile)) {
                for (Doctor d : doctors.values()) {
                    w.write(String.format("%d,%s,%s%n", d.getId(), escape(d.getName()), escape(d.getSpecialization())));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save doctors: " + e.getMessage());
        }
    }

    public void load() {
        if (!Files.exists(dataFile)) return;
        try (BufferedReader r = Files.newBufferedReader(dataFile)) {
            String line;
            long maxId = 0;
            while ((line = r.readLine()) != null) {
                String[] parts = splitCsv(line,3);
                long id = Long.parseLong(parts[0]);
                String name = parts[1];
                String spec = parts[2];
                doctors.put(id, new Doctor(id, name, spec));
                if (id > maxId) maxId = id;
            }
            idGen.set(maxId + 1);
        } catch (IOException e) {
            System.err.println("Failed to load doctors: " + e.getMessage());
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
