package com.vityarthi.hospital;

public class Doctor {
    private long id;
    private String name;
    private String specialization;

    public Doctor() {}

    public Doctor(long id, String name, String specialization) {
        this.id = id; this.name = name; this.specialization = specialization;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return String.format("[Doctor id=%d name=%s spec=%s]", id, name, specialization);
    }
}
