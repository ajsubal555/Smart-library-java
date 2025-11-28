package com.vityarthi.hospital;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class AppointmentService {
    private final Map<Long, Appointment> appointments = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final Path dataFile = Paths.get("data/appointments.csv");

    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(PatientService ps, DoctorService ds) {
        this.patientService = ps;
        this.doctorService = ds;
    }

    public Appointment bookAppointment(long patientId, long doctorId, String datetime) {
        // Check patient and doctor exist
        if (!patientService.findById(patientId).isPresent()) throw new RuntimeException("Patient not found");
        if (!doctorService.findById(doctorId).isPresent()) throw new RuntimeException("Doctor not found");
        // Check doctor's slot availability (no double booking at same datetime)
        for (Appointment a : appointments.values()) {
            if (a.getDoctorId() == doctorId && a.getDatetime().equals(datetime) && "BOOKED".equals(a.getStatus())) {
                throw new RuntimeException("Doctor already booked at this datetime");
            }
        }
        long id = idGen.getAndIncrement();
        Appointment ap = new Appointment(id, patientId, doctorId, datetime, "BOOKED");
        appointments.put(id, ap);
        return ap;
    }

    public boolean cancelAppointment(long id) {
        Appointment a = appointments.get(id);
        if (a == null) return false;
        if ("CANCELLED".equals(a.getStatus())) return false;
        a.setStatus("CANCELLED");
        return true;
    }

    public Collection<Appointment> getAll() { return appointments.values(); }

    public Optional<Appointment> findById(long id) { return Optional.ofNullable(appointments.get(id)); }

    public void save() {
        try {
            Files.createDirectories(dataFile.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(dataFile)) {
                for (Appointment a : appointments.values()) {
                    w.write(String.format("%d,%d,%d,%s,%s%n", a.getId(), a.getPatientId(), a.getDoctorId(), escape(a.getDatetime()), a.getStatus()));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save appointments: " + e.getMessage());
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
                long pid = Long.parseLong(parts[1]);
                long did = Long.parseLong(parts[2]);
                String datetime = parts[3];
                String status = parts[4];
                appointments.put(id, new Appointment(id, pid, did, datetime, status));
                if (id > maxId) maxId = id;
            }
            idGen.set(maxId + 1);
        } catch (IOException e) {
            System.err.println("Failed to load appointments: " + e.getMessage());
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
