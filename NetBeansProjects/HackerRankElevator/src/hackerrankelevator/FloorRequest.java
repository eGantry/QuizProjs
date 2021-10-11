/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankelevator;

/**
 *
 * @author quizz
 */
public class FloorRequest {
    int requestedFloor;

    int requestType;
    static final int REQUEST_UP = Elevator.DIRECTION_UP;       //User floor request to go up, created by pressing the up arrow button from an elevator bank.
    static final int REQUEST_DOWN = Elevator.DIRECTION_DOWN;            //User floor request to go down, created by pressing the down arrow button from an elevator bank.
    static final int REQUEST_FLOOR_NUMBER = 100;     //User floor request creayted by pressing a floor number from inside the elevator.
    static final int REQUEST_AUTO = -100;          //Automated request, like to go back to Lobby when there are no more user-created requests.
    
    FloorRequest(int floor, int type) {
        requestedFloor = floor;
        requestType = type;
    }
    
    public int getRequestedFloor() {
        return requestedFloor;
    }

    public int getRequestType() {
        return requestType;
    }
    
}
