package org.example.system;

public class Guest {
    private String name;
    private String surname;
    private int phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if(surname == null || surname.isEmpty()){
            throw new IllegalArgumentException("Surname cannot be empty");
        }
        this.surname = surname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Guest(String name, String surname, int phoneNumber) {
        if(surname == null || surname.isEmpty() || name == null || name.isEmpty()){
            throw new IllegalArgumentException("Surname and name cannot be empty");
        }
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void print_guest(){
        System.out.printf("Name: %s Surname: %s", name, surname);
    }
}
