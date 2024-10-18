package org.example.system;

import org.example.dataStructures.MyMap;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HotelSystem {

    MyMap<Integer, Room> roomList;

    public HotelSystem() {
        roomList = new MyMap<>();
        importDataFromFile();
    }

    private void importDataFromFile(){

        String line;
        String fileName = "src/main/resources/hotelData.csv";
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            br.readLine();


            while ((line = br.readLine()) != null) {

                String[] data = line.split(splitBy);

                Room room;
                if(data.length > 3){
                    Reservation reservation = getReservation(data);
                    room = new Room(Integer.parseInt(data[1]),Integer.parseInt(data[2]),reservation);
                }
                else{

                    room = new Room(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
                }

                roomList.put(Integer.parseInt(data[0]),room);


            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    private static Reservation getReservation(String[] data) throws ParseException {
        String[] guestList = data[3].split(":");
        ArrayList<Guest> guests = new ArrayList<>();
        for(String guest : guestList){
            String[] guestInfo = guest.split(";");
            Guest new_guest;
            if(guestInfo.length > 2){
                new_guest = new Guest(guestInfo[0],guestInfo[1],Integer.parseInt(guestInfo[2]));
            }
            else {
                new_guest = new Guest(guestInfo[0],guestInfo[1]);
            }
            guests.add(new_guest);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date arrival_date = dateFormat.parse(data[4]);

        return new Reservation(guests.get(0),guests,arrival_date,Integer.parseInt(data[5]));
    }

    private void importDummyData(){
        for (int i = 0; i < 5; i++) {

            Room room = new Room(100, 5);
            roomList.put(i, room);
        }
    }

    public boolean room_exists(int room_id){
        if(roomList.isEmpty()){
            return false;
        }
        return roomList.containsKey(room_id);
    }

    public void show_prices(){
        for (Integer i: roomList.keys()) {
            Room room = roomList.get(i);
            System.out.printf("Room number: %d Price: %d\n",i,room.getPrice());
        }
    }
    public void show_room_info(int room_id){
        if(room_exists(room_id)){
            Room room = roomList.get(room_id);
            room.print_info();
        }
    }
    public boolean room_occupied(int room_id){
        return roomList.get(room_id).getReservation() != null;
    }
    public boolean can_room_occupy(int room_id,int num){
        return roomList.get(room_id).getCapacity() >= num;
    }
    public int getRoomPrice(int room_id){
        return roomList.get(room_id).getPrice();
    }
    public void check_in(int room_id, ArrayList<Guest> guests, Guest mainGuest, int stayTime, Date arrivalTime){
        Room room = roomList.get(room_id);
        Reservation r = new Reservation(mainGuest,guests,arrivalTime,stayTime);
        room.setReservation(r);
    }
    public int check_out(int room_id){
        Room room = roomList.get(room_id);
        Reservation r = room.getReservation();
        int stayTime = (int)(new Date().getTime() - r.getArrivalDate().getTime())/(24 * 60 * 60 * 1000);
        room.setReservation(null);
        return stayTime;
    }
    public void list_all_rooms(){
        for (int key : roomList.keys()) {
            Room room = roomList.get(key);
            System.out.printf("Room %d ",key);
            room.print_more_info();
        }
    }
    public void save_to_csv(){

        String fileName = "src/main/resources/hotelData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

            writer.println("Room number,Price,Capacity,Guest list,Arrival Date;Stay time");

            for(Integer key : roomList.keys()){
                StringBuilder data = new StringBuilder();
                Room room = roomList.get(key);
                data.append(key).append(",").append(room.getPrice()).append(",").append(room.getCapacity());
                if(room.getReservation() != null){
                    data.append(",");
                    int i=0;
                    for (Guest guest: room.getReservation().getGuestList()){
                        if(i!=0){
                            data.append(":");
                        }
                        data.append(guest.getName()).append(";").append(guest.getSurname());
                        if(i==0){
                            data.append(";").append(guest.getPhoneNumber());
                        }
                        i++;
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String arrivalS = formatter.format(room.getReservation().getArrivalDate());
                    data.append(",").append(arrivalS).append(",").append(room.getReservation().getStayTime());

                }

                writer.println(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
