package org.example;

import org.example.system.Guest;
import org.example.system.HotelSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        HotelSystem hotel = new HotelSystem();

        while(true) {
            System.out.println("What would you like to do?");
            System.out.println("""
                    Options:\s
                    1. prices - list every room and it's price
                    2. view - list all information about chosen room \
                    
                    3. checkin - check in a new guest
                    4. checkout - check out a guest
                    5. list - list all rooms with all information
                    6. save - saves the data to the csv file
                    7. exit - exits the program""");
            String choice = s.nextLine();
            switch(choice) {
                case "prices":
                    hotel.show_prices();
                    break;
                case "view":
                    System.out.println("Enter room number: ");
                    int roomNumber = Integer.parseInt(s.nextLine());
                    if(hotel.room_exists(roomNumber)) {
                        hotel.show_room_info(roomNumber);
                    }
                    else{
                        System.out.println("Room not found");
                    }
                    break;
                case "checkin":
                    try {
                        System.out.println("Enter room number: ");
                        int roomNum = Integer.parseInt(s.nextLine());
                        if(!hotel.room_exists(roomNum)){
                            throw new IllegalArgumentException("There is no such room");
                        }
                        if(hotel.room_occupied(roomNum)) {
                            throw new IllegalArgumentException("This room is occupied");
                        }

                        System.out.println("Enter number of guests: ");
                        int numGuests = Integer.parseInt(s.nextLine());

                        if(!hotel.can_room_occupy(roomNum,numGuests)) {
                            throw new IllegalArgumentException("This in too much guests");
                        }
                        if (numGuests <= 0) {
                            throw new IllegalArgumentException("Number of guests must be greater than zero.");
                        }

                        ArrayList<Guest> guests = new ArrayList<>();

                        System.out.println("Enter main guest name: ");
                        String mainGuestName = s.nextLine();
                        System.out.println("Enter main guest surname: ");
                        String mainGuestSurname = s.nextLine();
                        System.out.println("Enter main guest phone number: ");
                        int mainGuestPhone = Integer.parseInt(s.nextLine());
                        Guest mainGuest = new Guest(mainGuestName,mainGuestSurname,mainGuestPhone);
                        guests.add(mainGuest);
                        for (int i = 0; i < numGuests-1; i++) {
                            System.out.println("Enter guest name: ");
                            String guestName = s.nextLine();
                            System.out.println("Enter guest surname: ");
                            String guestSurname = s.nextLine();
                            guests.add(new Guest(guestName,guestSurname));
                        }



                        System.out.println("Enter stay time (in days): ");
                        int stayTime = Integer.parseInt(s.nextLine());
                        if (stayTime <= 0) {
                            throw new IllegalArgumentException("Stay time must be greater than zero.");
                        }

                        System.out.println("Enter arrival date (format: yyyy-mm-dd) or press Enter for today: ");
                        String arrivalInput = s.nextLine();
                        Date arrivalDate;
                        if (arrivalInput.isEmpty()) {
                            arrivalDate = new Date();
                        } else {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            arrivalDate = format.parse(arrivalInput);
                        }

                        hotel.check_in(roomNum, guests, mainGuest, stayTime, arrivalDate);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    } catch (ParseException e) {
                        System.out.println("Invalid date format.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "checkout":
                    try {
                        System.out.println("Enter room number: ");
                        int roomNum = Integer.parseInt(s.nextLine());
                        if(!hotel.room_exists(roomNum)){
                            throw new IllegalArgumentException("There is no such room");
                        }
                        if(!hotel.room_occupied(roomNum)) {
                            throw new IllegalArgumentException("This room is not occupied");
                        }

                        int stayTime = hotel.check_out(roomNum);
                        System.out.printf("You stayed here %d days. You owe us %d dolars\n",stayTime,stayTime * hotel.getRoomPrice(roomNum));

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "list":
                    hotel.list_all_rooms();
                    break;
                case "save":
                    hotel.save_to_csv();
                    break;
                default:
                    break;
            }
            if(choice.equals("exit")) {
                break;
            }
        }
    }
}