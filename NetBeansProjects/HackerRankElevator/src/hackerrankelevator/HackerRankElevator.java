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

        Process process = new Process();
        boolean done = process.processAllPassengers();
        if (done) {
            reportState("Done");
        }
        else {
            reportState("Not Done");
        }
    }
    
    private static void reportState(String report) {
        System.out.println(report);
    }
    
}
