package com.example.bus_ticketapplication;

public class CustomRowItem {
    String busId;
    public String travelsName;
    public String busNumber;
    public  String fare;
    public String date;
    public String time;
    public String from;
    public String to;

    public CustomRowItem(String busId, String travelsName, String busNumber, String fare,String date, String time, String from, String to) {
        this.busId = busId;
        this.travelsName = travelsName;
        this.busNumber = busNumber;
        this.fare=fare;
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getTravelsName() {
        return travelsName;
    }

    public void setTravelsName(String travelsName) {
        this.travelsName = travelsName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
