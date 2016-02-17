package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

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

    public Rebate(String firstName, String middleInitial, String lastName, String addressLine1, String addressLine2, String city, String state, String zipCode, String phoneNumber, String emailAddress, boolean isProofOfPurchaseAttached, LocalDate dateReceived) {
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

    public static boolean saveRebates(List<Rebate> rebates) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("rebate_store.txt");
        Iterator<Rebate> it = rebates.iterator();
        while (it.hasNext()) {
            Rebate rebate = it.next();
            String rebateString = convertToStorableFormat(rebate);
            out.println(rebateString);
        }
        out.close();
        return true;
    }

    public static List<Rebate> loadAllRebates() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("rebate_store.txt"));
        List<Rebate> rebates = new ArrayList<>();
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            rebates.add(Rebate.convertToObjectFromString(row));
        }
        return rebates;
    }

    public static String convertToStorableFormat(Rebate rebate) {
        if (rebate.middleInitial == null || rebate.middleInitial.length() == 0) {
            rebate.middleInitial = "-";
        }
        if (rebate.addressLine2 == null || rebate.addressLine2.length() == 0) {
            rebate.addressLine2 = "-";
        }
        String output =  rebate.firstName+"\t"+rebate.middleInitial+"\t"+rebate.lastName+"\t"+rebate.addressLine1+"\t"+rebate.addressLine2+"\t"+rebate.city+"\t"+rebate.state+"\t"+rebate.zipCode+"\t"+rebate.phoneNumber+"\t"+rebate.emailAddress+"\t"+rebate.isProofOfPurchaseAttached+"\t"+rebate.dateReceived;
        if (rebate.middleInitial.equalsIgnoreCase("-")) {
            rebate.middleInitial = "";
        }
        if (rebate.addressLine2.equalsIgnoreCase("-")) {
            rebate.addressLine2 = "";
        }
        return  output;
    }

    public static Rebate convertToObjectFromString(String recordString) {

        if(recordString == null || recordString.length() == 0) {
            return null;
        }
        Scanner scanner = new Scanner(recordString).useDelimiter("\t");
        Rebate rebate = new Rebate(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(), scanner.next(),scanner.next(),scanner.nextBoolean(),null);
        if (rebate.middleInitial.equalsIgnoreCase("-")) {
            rebate.middleInitial = "";
        }
        if (rebate.addressLine2.equalsIgnoreCase("-")) {
            rebate.addressLine2 = "";
        }
        String[] date = scanner.next().split("-");
        rebate.setDateReceived(LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        return rebate;
    }
}
