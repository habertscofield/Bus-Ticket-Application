package com.example.bus_ticketapplication.Admin;

public class Bus2 {
    String busId;
    String travelsName;
    String busNumber;
    String date;
    String time;
    String from;
    String to;


    public Bus2() {
    }

    public Bus2(String busId,String travelsName, String busNumber, String date,String time, String from, String to) {
        this.busId = busId;
        this.travelsName = travelsName;
        this.busNumber = busNumber;
        this.date = date;
        this.time=time;
        this.from = from;
        this.to = to;

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


