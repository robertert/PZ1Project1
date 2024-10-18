package org.example.system;

import org.junit.jupiter.api.*;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HotelSystemTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    HotelSystem hotelSystem;
    Integer key;
    Room room;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        hotelSystem = new HotelSystem();
        key = hotelSystem.roomList.keys().get(0);
        room = hotelSystem.roomList.get(key);
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out after the test
        System.setOut(originalOut);
    }

    @Test
    void testImportFromFile(){
        assertFalse(hotelSystem.roomList.isEmpty());
    }

    @Test
    void testGetPrice(){
        int roomPrice = hotelSystem.roomList.get(key).getPrice();
        hotelSystem.show_prices();
        assertEquals("Room number: " + key + " Price: " + Integer.toString(roomPrice), outputStreamCaptor.toString().trim().split("\n")[0]);
    }
    @Test
    void testCheckInOutAndRoomInfo(){
        //TEST ROOM INFO IF EMPTY
        hotelSystem.show_room_info(key);
        assertEquals("Price: "+room.getPrice() + " Capacity: " + room.getCapacity(), outputStreamCaptor.toString().trim());
        outputStreamCaptor.reset();

        //TEST CHECK IN FUNCTIONALITY
        ArrayList<Guest> guests = new ArrayList<>();
        Guest main = new Guest("mainName","mainSurename",12493502);
        guests.add(main);
        for (int i = 0; i < room.getCapacity() - 1; i++) {
            guests.add(new Guest("someName","someSurename"));
        }
        assertNull(room.getReservation());
        hotelSystem.check_in(key,guests,main,10,new Date());
        assertNotNull(room.getReservation());
        assertEquals(guests.size(),room.getReservation().getGuestList().size());
        assertEquals("mainName",room.getReservation().getMainGuest().getName());
        assertEquals(12493502,room.getReservation().getMainGuest().getPhoneNumber());

        //TEST ROOM INFO IF FULL
        hotelSystem.show_room_info(key);

        StringBuilder expected = new StringBuilder("Price: " + room.getPrice() + " Capacity: " + room.getCapacity() + "\nGuest list:\n");
        for(Guest guest:guests){
            expected.append("Name: ").append(guest.getName()).append(" Surname: ").append(guest.getSurname()).append("\n");
        }


        assertEquals( expected.toString().trim(), outputStreamCaptor.toString().trim());
        outputStreamCaptor.reset();
        assertNotNull(room.getReservation());

        int expectedStayTime = (int)(new Date().getTime() - room.getReservation().getArrivalDate().getTime())/24*3600*1000;
        int stayTime = hotelSystem.check_out(key);
        assertNull(room.getReservation());
        assertEquals(expectedStayTime,stayTime);
    }

    @Test
    void testListAllRooms(){
        StringBuilder expected = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        for(Integer key: hotelSystem.roomList.keys()){
            Room room = hotelSystem.roomList.get(key);
            expected.append("Room ").append(key).append(" Price: ").append(Integer.toString(room.getPrice())).append(" Capacity: ").append(room.getCapacity()).append("\n");
            if(room.getReservation() != null){
                expected.append("Guest list:\n");
                for(Guest guest:room.getReservation().getGuestList()){
                    expected.append("Name: ").append(guest.getName()).append(" Surname: ").append(guest.getSurname()).append("\n");
                }
                String arrivalS = formatter.format(room.getReservation().getArrivalDate());
                String departureS = formatter.format(new Date(room.getReservation().getArrivalDate().getTime() + (long) room.getReservation().getStayTime() * 24*3600*1000));
                expected.append("Arrival date: ").append(arrivalS).append(" Estimated leave date ").append(departureS);
            }

        }
        hotelSystem.list_all_rooms();
        assertEquals(expected.toString().trim(), outputStreamCaptor.toString().trim());

    }

    @Test
    void testSave(){
        hotelSystem.save_to_csv();

        String fileName = "src/main/resources/hotelData.csv";
        StringBuilder expected = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();

            for(Integer key: hotelSystem.roomList.keys()){
                String test = br.readLine();
                Room room = hotelSystem.roomList.get(hotelSystem.roomList.keys().get(0));
                expected.append(key).append(",").append(room.getPrice()).append(",").append(room.getCapacity()).append(",");
                if(room.getReservation() != null) {
                    int i = 0;
                    for (Guest guest : room.getReservation().getGuestList()) {
                        if (i != 0) {
                            expected.append(":");
                        }
                        expected.append(guest.getName()).append(";").append(guest.getSurname()).append(";");
                        if (i == 0) {
                            expected.append(guest.getPhoneNumber());
                        }
                        i++;
                    }
                    expected.append(",").append(formatter.format(room.getReservation().getArrivalDate())).append(",").append(room.getReservation().getStayTime());
                    assertEquals(expected.toString().trim(), test);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}