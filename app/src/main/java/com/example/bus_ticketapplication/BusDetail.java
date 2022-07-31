package com.example.bus_ticketapplication;

public class BusDetail {
    public String bus_name;
    public String bus_number;
    public String bus_date;
    public String bus_from;
    public String bus_to;

    public BusDetail(String bus_name, String bus_number, String bus_date, String bus_from, String bus_to) {
        this.bus_name = bus_name;
        this.bus_number = bus_number;
        this.bus_date = bus_date;
        this.bus_from = bus_from;
        this.bus_to = bus_to;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getBus_date() {
        return bus_date;
    }

    public void setBus_date(String bus_date) {
        this.bus_date = bus_date;
    }

    public String getBus_from() {
        return bus_from;
    }

    public void setBus_from(String bus_from) {
        this.bus_from = bus_from;
    }

    public void setBus_to(String bus_to) {
        this.bus_to = bus_to;
    }

    public String getBus_to() {
        return bus_to;
    }
}
