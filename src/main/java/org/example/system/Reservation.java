package org.example.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Reservation {
    private final int id;
    private Guest mainGuest;
    private ArrayList<Guest> guestList;
    private Date arrivalDate;
    private int stayTime;

    public int getId() {
        return id;
    }

    public Guest getMainGuest() {
        return mainGuest;
    }

    public void setMainGuest(Guest mainGuest) {
        this.mainGuest = mainGuest;
    }

    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(ArrayList<Guest> guestList) {
        this.guestList = guestList;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        if(stayTime < 0){
            throw new IllegalArgumentException("Stay time cannot be negative");
        }
        this.stayTime = stayTime;
    }

    public Reservation(Guest mainGuest, ArrayList<Guest> guestList, Date arrivalDate, int stayTime) {
        this.id = UUID.randomUUID().hashCode();
        this.mainGuest = mainGuest;
        this.guestList = guestList;
        this.arrivalDate = arrivalDate;
        setStayTime(stayTime);
    }
}
