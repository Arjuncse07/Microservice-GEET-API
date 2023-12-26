package com.arjun.GEET_API.model;


public class Ticket {

    private String ticketId;
    private String ticketPrintTiming;
    private String ticketPrintLocation;
    private String ticketJourneyDetails;
    private String ticketGenerateDate;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketPrintTiming() {
        return ticketPrintTiming;
    }

    public void setTicketPrintTiming(String ticketPrintTiming) {
        this.ticketPrintTiming = ticketPrintTiming;
    }

    public String getTicketPrintLocation() {
        return ticketPrintLocation;
    }

    public void setTicketPrintLocation(String ticketPrintLocation) {
        this.ticketPrintLocation = ticketPrintLocation;
    }

    public String getTicketJourneyDetails() {
        return ticketJourneyDetails;
    }

    public void setTicketJourneyDetails(String ticketJourneyDetails) {
        this.ticketJourneyDetails = ticketJourneyDetails;
    }

    public String getTicketGenerateDate() {
        return ticketGenerateDate;
    }

    public Ticket(String ticketId, String ticketPrintTiming, String ticketPrintLocation, String ticketJourneyDetails, String ticketGenerateDate) {
        this.ticketId = ticketId;
        this.ticketPrintTiming = ticketPrintTiming;
        this.ticketPrintLocation = ticketPrintLocation;
        this.ticketJourneyDetails = ticketJourneyDetails;
        this.ticketGenerateDate = ticketGenerateDate;
    }

    public void setTicketGenerateDate(String ticketGenerateDate) {
        this.ticketGenerateDate = ticketGenerateDate;
    }
}
