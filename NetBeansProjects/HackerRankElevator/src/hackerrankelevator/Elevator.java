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
    static final int DIRECTION_UP = 1;
    static final int DIRECTION_DOWN = -1;
    static final int DIRECTION_STOPPED = 0;    //Elevator is at start of or end of trip.
    private int currentFloor = LOBBY;   //Process updates this as elevator moves.

    private int maxPassengers = -1; //Process initilizes this.
    private int topFloor = 0;  //Highest floor the elvator can reach.  Process initilizes this.

    private int currentDirection = DIRECTION_STOPPED;  //Process updates this to change elevator's direction.
    
    /*
    A floor request tells the elevator what floor to go to, and is created by the Process as Passengers are updated.
    A waiting Passenger requests the elevator to their current floor, and a riding passenger requests the elevator to their desired floor.
    A floor request is cleared when the elvator arrives at the requested floor.
    */
    
//    private ArrayList<Integer> floorRequests;  //Process updates these.  Waiting passengers request elevator to their current floor.
    private ArrayList<FloorRequest> floorRequests;  //Process updates these.  Waiting passengers request elevator to their current floor.
    
    public Elevator(int passengerMax, int highestFloor) {
        maxPassengers = passengerMax;
        topFloor = highestFloor;
        currentFloor = LOBBY;
        currentDirection = DIRECTION_STOPPED;
        floorRequests = new ArrayList<FloorRequest>();
    }
    
    public void requestFloor(int whichFloor, int requestType) {
        if (!anyFloorReqsMatch(whichFloor, requestType)) {
            floorRequests.add(new FloorRequest(whichFloor, requestType));
            String reqTypeName = "";
            switch (requestType) {
                case FloorRequest.REQUEST_AUTO:
                    reqTypeName = "Auto";
                    break;
                case FloorRequest.REQUEST_UP:
                    reqTypeName = "Up";
                    break;
                case FloorRequest.REQUEST_DOWN:
                    reqTypeName = "Down";
                    break;
                case FloorRequest.REQUEST_FLOOR_NUMBER:
                    reqTypeName = "Floor Number";
                    break;
                default:
                    break;
            }
            
            if (whichFloor == LOBBY) {
                reportFloor(reqTypeName + " request received for Lobby.");
            }
            else {
                reportFloor(reqTypeName + " request received for floor " + whichFloor + ".");
            }
        }
    }
    

    private boolean anyFloorReqsMatch(int whichFloor, int requestType) {
        boolean result = false;
        
        for (FloorRequest eachRequest : floorRequests) {
            if (eachRequest.getRequestedFloor() == whichFloor && eachRequest.getRequestType() == requestType) {
                result = true;
                break;
            }
        }
        
        return result;
    }



    
    public void clearFloorRequests(int whichFloor) {
        for (int whichRequest = floorRequests.size() -1; whichRequest >= 0; whichRequest--) {
            FloorRequest eachRequest = floorRequests.get(whichRequest);
            //Clear all requests for this floor, except opposite direction requests.
            if (eachRequest.getRequestedFloor() == whichFloor) {
                if (!oppositeDirRequest(eachRequest)) {
                    floorRequests.remove(eachRequest);
                }
            }
        }
    }
    
    private boolean oppositeDirRequest(FloorRequest request) {
        //If request is for up, while elevator is going down, or vice versa, return true.
        boolean result = false;
        
        if ((request.getRequestType() == Elevator.DIRECTION_UP && currentDirection == Elevator.DIRECTION_DOWN) || 
                (request.getRequestType() == Elevator.DIRECTION_DOWN && currentDirection == Elevator.DIRECTION_UP)) {
            result = true;
        }
        
        return result;
    }

    public void clearCurrentFloor() {
        for (int whichRequest = floorRequests.size() -1; whichRequest >= 0; whichRequest--) {
            FloorRequest eachRequest = floorRequests.get(whichRequest);
            if (eachRequest.getRequestedFloor() == currentFloor) {
                if (!oppositeDirRequest(eachRequest)) {
                    floorRequests.remove(eachRequest);
                }
            }
        }    
    }
    
    public int highestReqFloor() {
        int result = -1;
        
        for (FloorRequest eachReqFloor : floorRequests) {
            if (eachReqFloor.getRequestedFloor() > result) {
                result = eachReqFloor.getRequestedFloor();
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

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getTopFloor() {
        return topFloor;
    }
    
}
