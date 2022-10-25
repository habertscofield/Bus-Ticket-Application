package com.example.bus_ticketapplication.Admin;

public class Bus {
    String busId;
    public String travelsName;
    public String busNumber;
    public String date;
    public String fare;
    public String time;
    public String from;
    public String to;

    public Bus() {
    }



    public Bus(String busId, String travelsName, String busNumber, String fare, String date,String time, String from, String to) {
        this.busId = busId;
        this.time = time;
        this.travelsName = travelsName;
        this.busNumber = busNumber;
        this.fare=fare;
        this.date = date;
        this.from = from;
        this.to = to;

    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }
}
