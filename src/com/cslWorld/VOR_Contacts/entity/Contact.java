package com.cslWorld.VOR_Contacts.entity;

/**
 * Created by heshan.lokuge on 4/3/14.
 */
public class Contact implements Comparable<Contact>{

    //private variables
    private String _name;
    private String _title;
    private String _mailAddress;
    private String _mobileNumber;
    private String _officeNumber;
    private String _company;

    // Empty constructor
    public Contact() {

    }

    // constructor
    public Contact(String name, String title, String mailAddress , String mobileNumber ,String officeNumber,String company) {

        this._name = name;
        this._title = title;
        this._mailAddress = mailAddress;
        this._mobileNumber = mobileNumber;
        this._officeNumber = officeNumber;
        this._company = company;

    }

    // constructor
    public Contact(String mailAddress , String mobileNumber ,String officeNumber , String company) {

        this._mailAddress = mailAddress;
        this._mobileNumber = mobileNumber;
        this._officeNumber = officeNumber;
        this._company = company;

    }


    // getting name
    public String getName() {

        return this._name;
    }

    // setting name
    public void setName(String name) {

        this._name = name;
    }


    // getting title
    public String getTitle() {

        return this._title;
    }

    // setting title
    public void setTitle(String title) {

        this._title = title;
    }


    // getting mail Address
    public String getMailAddress() {

        return this._mailAddress;
    }

    // setting mail Address
    public void setMailAddress(String mailAddress) {

        this._mailAddress = mailAddress;
    }


    // getting mobile number
    public String getMobileNumber() {

        return this._mobileNumber;
    }

    // setting mobile number
    public void setType(String mobileNumber) {

        this._mobileNumber = mobileNumber;
    }

    // getting home number
    public String getOfficeNumber() {

        return this._officeNumber;
    }

    // setting home number
    public void setHomeNumber(String officeNumber) {

        this._officeNumber = officeNumber;
    }

    // getting company name
    public String getCompany() {

        return this._company;
    }

    // setting company name
    public void setCompany(String company) {

        this._company = company;
    }

    @Override
    public int compareTo(Contact contact) {
        return this.getName().compareTo(contact.getName());
    }
}
