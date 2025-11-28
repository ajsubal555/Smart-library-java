package com.vityarthi.hospital;

public class Patient {
    private long id;
    private String name;
    private int age;
    private String gender;
    private String contact;

    public Patient() {}

    public Patient(long id, String name, int age, String gender, String contact) {
        this.id = id; this.name = name; this.age = age; this.gender = gender; this.contact = contact;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return String.format("[Patient id=%d name=%s age=%d gender=%s contact=%s]", id, name, age, gender, contact);
    }
}
