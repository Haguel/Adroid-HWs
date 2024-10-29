package com.example.hw5;

public class UserModel {
    private int avatarId;
    private String firstName;
    private String lastName;
    private int age;
    private String country;
    private String city;

    public UserModel(int avatarId, String firstName, String lastName, int age, String country, String city) {
        this.avatarId = avatarId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.country = country;
        this.city = city;
    }

    // Getters and setters
    public int getAvatarId() { return avatarId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
}