/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankelevator;
import java.io.*;
import java.util.*;

public class HackerRankElevator {

    static List<Integer[]> waitingPassengers = new ArrayList<Integer[]>();
    static List<Integer[]> passengersInLift = new ArrayList<Integer[]>();
    static final int DESIRED_FLOOR = 0;     
    static final int CURRENT_FLOOR = 1;
    static final int PASSENGER_STATUS = 2;
    static final int STATUS_WAITING = 0;
    static final int STATUS_LIFT = 1;
    static final int STATUS_ARRIVED = 2;

    public static void main(String[] args) throws FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
//        BufferedReader reader = new BufferedReader(
//            new InputStreamReader(System.in));
        FileReader file = new FileReader(new File("TestValues1.txt"));
//        FileReader file = new FileReader(new File("TestValues2.txt"));

        BufferedReader reader = new BufferedReader(file);
 
        int timeThroughLoop = 0;
        int totalPassengerCount = -1;
        int passengerCapacity = -1;
        int elevatorCurrentFloor = 1;
        int elevatorElapsedSeconds = 0;
        // Reading data using readLine
        try {
            String line = reader.readLine();
            while (line != null) {
                timeThroughLoop++;
                //First time through loop:  line contains #passengers and #passengerCapacity
                //Second time through loop:  line contains each passenger's desired floor.
                String contents[] = line.split(" ");
                if (timeThroughLoop == 1) {
                    //Populate the passenger count and elevator capacity from the read-in values.
                    totalPassengerCount = Integer.parseInt(contents[0]);
                    passengerCapacity = Integer.parseInt(contents[1]);
                }
                else {
                    List<Integer> desiredFloors = new ArrayList<Integer>();

                    //Populate the list of desired floors from the read-in values.
                    for (String eachFloorStr : contents) {
                        desiredFloors.add(Integer.parseInt(eachFloorStr));
                    }
                    //Sort the desired floors, and assign each desired floor to a waiting passenger.
                    Collections.sort(desiredFloors);
                    for (Integer eachDesiredFloor : desiredFloors) {
                        waitingPassengers.add(new Integer[]{eachDesiredFloor, 1, STATUS_WAITING});
                    }
                }
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        
        //While passengers are still waiting in the lobby,...
//        while (anyoneWaitingInLobby()) {
        while (waitingPassengers.size() > 0  && anyoneWaitingInLobby()) {
            //Admit no more than #passengerCapacity passengers into lift at a time
            
            //As each passenger steps onto the lift,...
            for (Integer[] eachPassenger : waitingPassengers) {
                if (passengersInLift.size() < passengerCapacity) {
                    passengersInLift.add(eachPassenger);
                    eachPassenger[PASSENGER_STATUS] = STATUS_LIFT;
                }
                else {
                    break;
                }
            }
            //...we remove him/her from the list of waiting passengers in the lobby.
            for (Integer[] eachPassengerInLift : passengersInLift) {
                waitingPassengers.remove(eachPassengerInLift);
            }

            //Loop through all floors until each desired floor in lift is reached, accumulating time spent one second for  each floor.
            for (Integer[] eachPassenger : passengersInLift) {
                while (elevatorCurrentFloor < eachPassenger[DESIRED_FLOOR]) {
                    elevatorCurrentFloor++;
                    eachPassenger[CURRENT_FLOOR] = elevatorCurrentFloor;
                    elevatorElapsedSeconds++;
                    if (anyoneWaitingInLobby()) {
                        break;
                    }
                }
                if (eachPassenger[DESIRED_FLOOR] == elevatorCurrentFloor 
                    && eachPassenger[PASSENGER_STATUS] == STATUS_LIFT) {
                    eachPassenger[PASSENGER_STATUS] = STATUS_ARRIVED;
                }
                else {
                    if (anyoneWaitingInLobby()) {
                        waitingPassengers.add(eachPassenger);
                        eachPassenger[PASSENGER_STATUS] = STATUS_WAITING;
                        //Break out of this loop, go back to the first floor, and within capacity, pick up waiting passengers.
                        //Then come back to this floor and pick up any waiting passengers dropped off here.
//                            break;
                    }
                }
            }
            //Remove any passengers from the elevator passenger list if their states have changed from LIFT.
            for (int whichPassenger = passengersInLift.size() - 1; whichPassenger >= 0; whichPassenger--) {
                Integer[] eachPassenger = passengersInLift.get(whichPassenger);
                if (eachPassenger [PASSENGER_STATUS] != STATUS_LIFT) {
                    passengersInLift.remove(eachPassenger);
                }
            }
            
            if (anyoneWaitingInLobby()) {
                //go back to the first floor.
                while (elevatorCurrentFloor > 1) {
                    elevatorCurrentFloor--;
                    elevatorElapsedSeconds++;
                }
            }
        }

        //Elevator has carried all passengers to their desired floors.  Return to 1st floor.
        while (elevatorCurrentFloor > 1) {
            elevatorCurrentFloor--;
            elevatorElapsedSeconds++;
        }
        System.out.println(elevatorElapsedSeconds);
        
    }
                                              
    static boolean anyoneWaitingInLobby() {
        boolean result = false;
        
        for (Integer[] eachPassenger : waitingPassengers) {
            if (eachPassenger[CURRENT_FLOOR] == 1) {
                result = true;
                break;
            }
        }
        
        return result;
    }                                         
    
}
