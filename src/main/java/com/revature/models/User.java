package com.revature.models;

import java.util.Objects;

public class User {
    private int userid;
    private String firstName;
    private String lastName;
    private String passcode;
    private String email;
    private String zodiac;

    public User() {
    }

    public User(String firstName, String lastName, String passcode, String email, String zodiac) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passcode = passcode;
        this.email = email;
        this.zodiac = zodiac;
    }

    public User(int userid, String firstName, String lastName, String passcode, String email, String zodiac) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passcode = passcode;
        this.email = email;
        this.zodiac = zodiac;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", passcode='" + passcode + '\'' + ", email='" + email + '\'' + ", zodiac='" + zodiac + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid == user.userid && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(passcode, user.passcode) && Objects.equals(email, user.email) && Objects.equals(zodiac, user.zodiac);
    }
}
