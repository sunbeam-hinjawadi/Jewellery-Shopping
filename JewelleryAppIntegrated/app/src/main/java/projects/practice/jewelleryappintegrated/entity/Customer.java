package projects.practice.jewelleryappintegrated.entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customer_id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private String mobileNo;
    private String gender;
    private String profileImage;
    private String createdDate;

    public Customer() {
    }

    public Customer(int customer_id, String firstName, String lastName, String email, String password, String mobileNo, String gender, String profileImage, String createdDate) {
        this.customer_id = customer_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
        this.gender = gender;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
