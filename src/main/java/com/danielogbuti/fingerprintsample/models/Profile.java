package com.danielogbuti.fingerprintsample.models;

public class Profile {
    private String BloodGroup,DateOfBirth,FingerPrint,FirstName,Gender,Image,LastName,MaritalStatus,PhoneNumber;

    public Profile(String bloodGroup, String dateOfBirth, String fingerPrint, String firstName, String gender, String image, String lastName, String maritalStatus, String phoneNumber) {
        BloodGroup = bloodGroup;
        DateOfBirth = dateOfBirth;
        FingerPrint = fingerPrint;
        FirstName = firstName;
        Gender = gender;
        Image = image;
        LastName = lastName;
        MaritalStatus = maritalStatus;
        PhoneNumber = phoneNumber;
    }

    public Profile() {
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getFingerPrint() {
        return FingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        FingerPrint = fingerPrint;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}