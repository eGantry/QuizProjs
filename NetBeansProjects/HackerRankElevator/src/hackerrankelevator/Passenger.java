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
public class Passenger {
    private int currentFloor = 0;   //Process sets this at the start.

    private int desiredFloor = 0;   //Process sets this at the start.

    public int getDesiredFloor() {
        return desiredFloor;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Passenger(int floorCurrent, int floorDesired) {
        currentFloor = floorCurrent;
        desiredFloor = floorDesired;
    }
    
}
