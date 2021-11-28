/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankelevator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quizz
 */
public class Process {
    private ArrayList<Passenger> passengersInLift;    //Passengers are here after boarding, when riding in lift, and before exiting lift.
    private ArrayList<Passenger> passengersWaiting;   //Passengers are here before boarding lift, and after exiting lift.
    private ArrayList<Passenger> passengersDone;      //Process ends when other two passenger lists are empty and all passengers are here.
    private ArrayList<Passenger> passengersAll;       //All passengers are here throughout Process.
    private PeopleCounter peopleCounter;        
    private Elevator elevator;
    
    Passenger[] passenger;
    

        /*
        
        This process tracks the interactions between an elevator and its passengers as they occur in a defined automation sequence.
        Although derived from a HackerRank challenge, its goal is to optimize the practical usefulness of the sequence, not to
        conform to an arbitrary performance expectation.
        
        Elevator Rules
        
        * The elevator tracks all requested floors as one list, whether from waiting passengers or passengers on-board. 
        * Waiting passengers from any floor can call the elevator to their current floors.
        * If a passenger calls from any floor, the elevator eventually goes to that floor 
          and picks up any waiting passengers from that floor.
        * The elevator can hold a certain maximum number of passengers, and carries only as many as it can hold.
        * When passengers board the elevator, they request their desired floors.
        * The elevator travels in one direction at a time, eventually stopping at each requested floor in that direction, 
          then reversing if any remaining floors are in the opposite direction.
        * If the elevator is not at the lobby, and no one called the elevator, it switches direction to return to the lobby and waits to be called.
        
        */


    public Process() {
        passengersInLift = new ArrayList();    //Passengers are here after boarding, when riding in lift, and before exiting lift.
        passengersWaiting = new ArrayList<>();   //Passengers are here before boarding lift, and after exiting lift.
        passengersDone = new ArrayList<>();      //Process ends when other two passenger lists are empty and all passengers are here.
        passengersAll = new ArrayList<>();       //All passengers are here throughout Process.

        elevator = new Elevator(7, 20);
        peopleCounter = new PeopleCounter();
        int [][] psgrs = {{0, 18},{5, 17},{0, 15},{0, 11},{0, 7},{0, 15},{9, 18},{0, 15},{4, 12},{0, 15},
                {0, 8},{0, 15},{0, 15},{17, 3},{0, 15},{0, 9},{0, 15},{0, 9},{8, 15},{8, 5}};
        
        passenger = new Passenger[20];
        for (int whichPsgr = 0; whichPsgr < psgrs.length; whichPsgr++) {
            int [] eachPsgr = psgrs[whichPsgr];
            passenger[whichPsgr] = new Passenger(eachPsgr[0], eachPsgr[1]);
            passengersAll.add(passenger[whichPsgr]);
         }
        
    }
    
    
    public boolean processAllPassengers() {
        boolean done = false;
        

            //Identify all waiting passengers, and call the elevator for all of them, in case any were left still waiting.
            for (Passenger eachPassenger : passengersAll) {
                if (eachPassenger.getCurrentFloor() != eachPassenger.getDesiredFloor()) {
                    if (!passengersWaiting.contains(eachPassenger)) {
                        passengersWaiting.add(eachPassenger);
                    }
                }
            }
            
            while ((passengersDone.size() < passengersAll.size()) || elevator.getCurrentFloor() != elevator.LOBBY) {
            
            //Call the elevator for all waiting passengers, in case any were left still waiting due to elevator capacity.
            for (Passenger eachPassenger : passengersWaiting) {
                int floorDiff = eachPassenger.getDesiredFloor() - eachPassenger.getCurrentFloor();
                int whichWay = 0;

                if (floorDiff > 0) {
                    whichWay = Elevator.DIRECTION_UP;
                }
                else if (floorDiff < 0) {
                    whichWay = Elevator.DIRECTION_DOWN;
                }

                elevator.requestFloor(eachPassenger.getCurrentFloor(), whichWay);

            }
        
            //Get elevator moving if it is stopped.
            if (elevator.getCurrentDirection() == Elevator.DIRECTION_STOPPED) {
                if (elevator.getCurrentFloor() == elevator.LOBBY) {
                    elevator.setCurrentDirection(Elevator.DIRECTION_UP);
                }
                else {
                    elevator.setCurrentDirection(Elevator.DIRECTION_DOWN);
                }
            }

            //Let out any passengers from the lift who have requested this floor.
            elevator.clearCurrentFloor();
            peopleCounter.resetCount();
            for (int whichPassenger = passengersInLift.size() -1; whichPassenger >= 0; whichPassenger--) {
                Passenger eachPassenger = passengersInLift.get(whichPassenger);
                if (eachPassenger.getDesiredFloor() == elevator.getCurrentFloor()) {
                    passengersDone.add(eachPassenger);
                    passengersInLift.remove(eachPassenger);
                    peopleCounter.incrementCount();
                }
            }
            peopleCounter.reportCount("exit", elevator.getCurrentFloor());
            
           
            //Board any waiting passengers onto elevator from its current floor.
            peopleCounter.resetCount();
            for (int whichPassenger = passengersWaiting.size() -1; whichPassenger >= 0; whichPassenger--) {
                Passenger eachPassenger = passengersWaiting.get(whichPassenger);
                if ((eachPassenger.getCurrentFloor() == elevator.getCurrentFloor()) && psngrGoingSameWay(eachPassenger)) {
                    if (passengersInLift.size() < elevator.getMaxPassengers()) {
                        passengersInLift.add(eachPassenger);
                        passengersWaiting.remove(eachPassenger);
                        peopleCounter.incrementCount();
                    }
                }
            }
            peopleCounter.reportCount("enter", elevator.getCurrentFloor());


            //Get all in-lift passengers' floor requests.
            for (Passenger eachPassenger : passengersInLift) {
                elevator.requestFloor(eachPassenger.getDesiredFloor(), FloorRequest.REQUEST_FLOOR_NUMBER);
            }
            
            //Clear any pending requests for the current floor.
            elevator.clearFloorRequests(elevator.getCurrentFloor());

            //If going UP, and the current floor is the top floor, or the highest requested floor, 
            //switch directions to DOWN.
            if (elevator.getCurrentDirection() == Elevator.DIRECTION_UP && 
                        (elevator.getCurrentFloor() == elevator.getTopFloor()) || (elevator.getCurrentFloor() >= elevator.highestReqFloor())) {
                elevator.setCurrentDirection(Elevator.DIRECTION_DOWN);
            }
            //If going DOWN, and the current floor is the Lobby,  
            //switch directions to UP.
            if (elevator.getCurrentDirection() == Elevator.DIRECTION_DOWN && 
                        (elevator.getCurrentFloor() == elevator.LOBBY)) {
                elevator.setCurrentDirection(Elevator.DIRECTION_STOPPED);
            }
            

            //If all passengers are at their destinations, return empty to the lobby.
            if (passengersDone.size() == passengersAll.size()) {
                if (elevator.getCurrentFloor() != elevator.LOBBY) {
                    elevator.requestFloor(elevator.LOBBY, FloorRequest.REQUEST_AUTO);
                    elevator.setCurrentDirection(Elevator.DIRECTION_DOWN);
                }
            }
            
            elevator.clearCurrentFloor();
            elevator.nextFloor();
            
            //If all passengers are at their destinations, and we're back at the Lobby, set the Doine state.
            if ((passengersDone.size() == passengersAll.size()) && (elevator.getCurrentFloor() == elevator.LOBBY)) {
                done = true;
            }
         }

        
        
        return done;
    }
    
    private boolean psngrGoingSameWay(Passenger passenger) {
        boolean result = false;
        
        int floorDiff = passenger.getDesiredFloor() - passenger.getCurrentFloor();
        int whichWay = -100;
        if (floorDiff < 0) {
            whichWay = Elevator.DIRECTION_DOWN;
        }
        else if (floorDiff > 0) {
            whichWay = Elevator.DIRECTION_UP;
        }
        
        if (whichWay == elevator.getCurrentDirection()) {
            result = true;
        }
        
        return result;
    }
    
}
