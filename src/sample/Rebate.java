package sample;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.LocalDate;

/**
 * Created by dxr141430 on 2/15/2016.
 */
public class Rebate {
    String firstName;
    String lastName;
    String middleInitial;
    String emailAddress;
    String phoneNumber;
    String addressLine1;
    String addressLine2;
    String state;
    String city;
    String zipCode;
    boolean isProofOfPurchaseAttached;
    LocalDate dateReceived;

    public Rebate() {
    }

    public Rebate(String firstName, String lastName, String middleInitial, String emailAddress, String phoneNumber, String addressLine1, String addressLine2, String state, String city, String zipCode, boolean isProofOfPurchaseAttached, LocalDate dateReceived) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.isProofOfPurchaseAttached = isProofOfPurchaseAttached;
        this.dateReceived = dateReceived;
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

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isProofOfPurchaseAttached() {
        return isProofOfPurchaseAttached;
    }

    public void setProofOfPurchaseAttached(boolean proofOfPurchaseAttached) {
        isProofOfPurchaseAttached = proofOfPurchaseAttached;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public boolean saveRebate() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("rebate_store.txt");
        String rebateString = this.firstName+" "+this.middleInitial+" "+this.lastName+" "+this.addressLine1+" "+this.addressLine2+" "+this.city+" "+this.state+" "+this.zipCode+" "+this.phoneNumber+" "+this.emailAddress+this.isProofOfPurchaseAttached+" "+this.dateReceived;
        out.println(rebateString);
        out.close();
        return true;
    }
}
