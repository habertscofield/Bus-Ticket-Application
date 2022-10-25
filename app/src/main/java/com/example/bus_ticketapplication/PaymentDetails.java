package com.example.bus_ticketapplication;

public class PaymentDetails {

    public String amount;
    public String date;
    public String seats;
    public String phone_number;
    public  String name;

    public PaymentDetails() {
    }


    public PaymentDetails(String amount, String date, String phone_number, String name, String seats) {
        this.amount = amount;
        this.date = date;
        this.seats = seats;
        this.phone_number=phone_number;
        this.name=name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}


