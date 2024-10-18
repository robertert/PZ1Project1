package org.example.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Room {
    private int price;
    private int capacity;
    private Reservation reservation;


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if(price < 0){
            throw new IllegalArgumentException("Price must be a positive number");
        }
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if(capacity < 0){
            throw new IllegalArgumentException("Capacity must be a positive number");
        }
        this.capacity = capacity;
    }


    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Room(int price, int capacity) {
        setPrice(price);
        setCapacity(capacity);

    }
    public Room(int price, int capacity,Reservation reservation) {
        setPrice(price);
        setCapacity(capacity);
        setReservation(reservation);
    }

    public void print_info(){
        System.out.printf("Price: %d Capacity: %d",getPrice(),getCapacity());
        if(getReservation() != null){
            System.out.print("\nGuest list:\n");
            ArrayList<Guest> guestList = getReservation().getGuestList();
            for (Guest guest : guestList) {
                guest.print_guest();
                System.out.print("\n");
            }
        }
        else{
            System.out.print("\n");
        }

    }
    public void print_more_info(){
        print_info();
        if(getReservation() != null){
            Date arrival = getReservation().getArrivalDate();
            Date departure = new Date(arrival.getTime()+ (long) getReservation().getStayTime() * 24 * 3600 * 1000);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String arrivalS = formatter.format(arrival);
            String departureS = formatter.format(departure);

            System.out.printf("Arrival date: %s Estimated leave date %s\n", arrivalS,departureS);
        }
    }
}
