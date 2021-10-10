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
public class Elevator {
    final int LOBBY = 0;
    final int DIRECTION_UP = 1;
    final int DIRECTION_DOWN = -1;
    final int DIRECTION_STOPPED = 0;    //Elevator is at start of or end of trip.
    private int currentFloor = LOBBY;   //Process updates this as elevator moves.

    private int maxPassengers = -1; //Process initilizes this.
    private int topFloor = 0;  //Highest floor the elvator can reach.  Process initilizes this.

    public int getTopFloor() {
        return topFloor;
    }
    private int currentDirection = DIRECTION_STOPPED;  //Process updates this to change elevator's direction.

    
    /*
    A floor request tells the elevator what floor to go to, and is created by the Process as Passengers are updated.
    A waiting Passenger requests the elevator to their current floor, and a riding passenger requests the elevator to their desired floor.
    A floor request is cleared when the elvator arrives at the requested floor.
    */
    
    private ArrayList<Integer> floorRequests;  //Process updates these.  Waiting passengers request elevator to their current floor.
    
    public Elevator(int passengerMax, int highestFloor) {
        maxPassengers = passengerMax;
        topFloor = highestFloor;
        currentFloor = LOBBY;
        currentDirection = DIRECTION_STOPPED;
        floorRequests = new ArrayList<Integer>();
    }
    
    public void requestFloor(int whichFloor) {
        if (!floorRequests.contains(whichFloor)) {
            floorRequests.add(whichFloor);
            if (whichFloor == LOBBY) {
                reportFloor("Request received for Lobby.");
            }
            else {
                reportFloor("Request received for floor " + whichFloor + ".");
            }        }
    }
    
    
    public void clearFloorRequests(int whichFloor) {
        for (int whichRequest = floorRequests.size() -1; whichRequest >= 0; whichRequest--) {
            Integer eachRequest = floorRequests.get(whichRequest);
            if (eachRequest.intValue() == whichFloor) {
                floorRequests.remove(eachRequest);
            }
        }
    }
    
    public void clearCurrentFloor() {
        for (int whichRequest = floorRequests.size() -1; whichRequest >= 0; whichRequest--) {
            Integer eachRequest = floorRequests.get(whichRequest);
            if (eachRequest.intValue() == currentFloor) {
                floorRequests.remove(eachRequest);
            }
        }    
    }
    
    public int highestReqFloor() {
        int result = -1;
        
        for (Integer eachReqFloor : floorRequests) {
            if (eachReqFloor.intValue() > result) {
                result = eachReqFloor.intValue();
            }
        }
        
        return result;
    }
    
    public void passFloor() {
        reportFloor("Elevator passes " + currentFloor + ".");
    }

    
    public void stopAtFloor() {
        reportFloor("Elevator stops at floor " + currentFloor + ".");
    }
    
    public void nextFloor() {
        if ((currentFloor + currentDirection <= topFloor) && (currentFloor + currentDirection >= LOBBY)) {
            currentFloor += currentDirection;
            if (currentFloor == LOBBY) {
                reportFloor("Elevator reaches Lobby.");
            }
            else {
                reportFloor("Elevator reaches floor " + currentFloor + ".");
            }
        }
    }

    private void reportFloor(String report) {
        System.out.println(report);
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }
    
    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }
    
}
