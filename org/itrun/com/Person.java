package org.itrun.com;

public class Person {

    private String personId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String pesel;

    public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public String setFirstName(String updated) {
        this.firstName = updated;
        return updated;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    public String setLastName(String updated) {
        this.lastName = updated;
        return updated;
    }

    public String setMobile(String updated) {
        this.mobile = updated;
        return updated;
    }

    public String setEmail(String updated) {
        this.email = updated;
        return updated;
    }

    public String setPesel(String updated) {
        this.pesel = updated;
        return updated;
    }
}