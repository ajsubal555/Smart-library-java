package com.vityarthi.hospital;

public class Appointment {
    private long id;
    private long patientId;
    private long doctorId;
    private String datetime; // YYYY-MM-DD HH:MM
    private String status; // BOOKED or CANCELLED

    public Appointment() {}

    public Appointment(long id, long patientId, long doctorId, String datetime, String status) {
        this.id = id; this.patientId = patientId; this.doctorId = doctorId; this.datetime = datetime; this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPatientId() { return patientId; }
    public void setPatientId(long patientId) { this.patientId = patientId; }

    public long getDoctorId() { return doctorId; }
    public void setDoctorId(long doctorId) { this.doctorId = doctorId; }

    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[Appointment id=%d patient=%d doctor=%d datetime=%s status=%s]", id, patientId, doctorId, datetime, status);
    }
}
