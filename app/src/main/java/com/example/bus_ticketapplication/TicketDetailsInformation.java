package com.example.bus_ticketapplication;

public class TicketDetailsInformation {
    String busId, travelsName, from, to, date, busNumber,journeyDate, busName,seats,seatNo, time, TotalCost, ticketID;


    public TicketDetailsInformation() {

    }

    public TicketDetailsInformation(String busId, String travelsName,String busName,String journeyDate,String seatNo, String from, String to, String date, String busNumber, String seats, String time, String totalCost, String ticketID) {
        this.busId = busId;
        this.travelsName = travelsName;
        this.busName=busName;
        this.from = from;
        this.to = to;
        this.journeyDate=journeyDate;
        this.date = date;
        this.busNumber = busNumber;
        this.seats = seats;
        this.seatNo=seatNo;
        this.time = time;
        this.TotalCost = totalCost;
        this.ticketID = ticketID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
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

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(String totalCost) {
        TotalCost = totalCost;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
