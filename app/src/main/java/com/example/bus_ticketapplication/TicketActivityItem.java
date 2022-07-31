package com.example.bus_ticketapplication;

public class TicketActivityItem {
    String ticketID, busName, from, to, issueTime, issueDate;

    public TicketActivityItem() {

    }

    public TicketActivityItem(String ticketID, String busName, String from, String to, String issuetime, String issuedate) {
        this.ticketID = ticketID;
        this.busName = busName;
        this.from = from;
        this.to = to;
        this.issueDate = issuedate;
        this.issueTime = issuetime;

    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
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

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
