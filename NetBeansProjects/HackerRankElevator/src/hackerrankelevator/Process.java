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

        elevator = new Elevator(1, 5);
        peopleCounter = new PeopleCounter();
        passenger = new Passenger[1];
        passenger[0] = new Passenger(0, 5);
        
        passengersAll.add(passenger[0]);
        
        
    }
    
    
    public boolean processAllPassengers() {
        boolean done = false;
        
        //Identify all waiting passengers, and call the elevator for all of them.
        for (Passenger eachPassenger : passengersAll) {
            if (eachPassenger.getCurrentFloor() != eachPassenger.getDesiredFloor()) {
                passengersWaiting.add(eachPassenger);
                elevator.requestFloor(eachPassenger.getCurrentFloor());
            }
        }
        
        while ((passengersDone.size() < passengersAll.size()) || elevator.getCurrentFloor() != elevator.LOBBY) {
            
            //Clear any pending requests for the current floor.
            elevator.clearFloorRequests(elevator.getCurrentFloor());

            //Let out any passengers from the lift who have requested this floor.
            peopleCounter.resetCount();
            for (int whichPassenger = passengersInLift.size() -1; whichPassenger >= 0; whichPassenger--) {
                Passenger eachPassenger = passengersInLift.get(whichPassenger);
                if (eachPassenger.getDesiredFloor() == elevator.getCurrentFloor()) {
                    passengersDone.add(eachPassenger);
                    passengersInLift.remove(eachPassenger);
                    peopleCounter.incrementCount();
                }
            }
            peopleCounter.reportCount("exit");
            
           
            //Board any waiting passengers onto elevator from its current floor.
            peopleCounter.resetCount();
            for (int whichPassenger = passengersWaiting.size() -1; whichPassenger >= 0; whichPassenger--) {
                Passenger eachPassenger = passengersWaiting.get(whichPassenger);
                if (eachPassenger.getCurrentFloor() == elevator.getCurrentFloor()) {
                    passengersInLift.add(eachPassenger);
                    passengersWaiting.remove(eachPassenger);
                    peopleCounter.incrementCount();
                }
            }
            peopleCounter.reportCount("enter");
            
            //Get all in-lift passengers' floor requests.
            for (Passenger eachPassenger : passengersInLift) {
                elevator.requestFloor(eachPassenger.getDesiredFloor());
            }
            
            //Get elevator moving if it is stopped.
            if (elevator.getCurrentDirection() == elevator.DIRECTION_STOPPED) {
                if (elevator.getCurrentFloor() == elevator.LOBBY) {
                    elevator.setCurrentDirection(elevator.DIRECTION_UP);
                }
                else {
                    elevator.setCurrentDirection(elevator.DIRECTION_DOWN);
                }
            }
            
            elevator.nextFloor();
            
            if (passengersDone.size() == passengersAll.size()) {
                if (elevator.getCurrentFloor() != elevator.LOBBY) {
                    elevator.requestFloor(elevator.LOBBY);
                    elevator.setCurrentDirection(elevator.DIRECTION_DOWN);
                }
                else {
                    done = true;
                }
            }
         }
        
        
        return done;
    }
    
}
