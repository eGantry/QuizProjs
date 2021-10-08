import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
 
        // Reading data using readLine
        String line = reader.readLine();
        int timeThroughLoop = 0;
        int totalPassengerCount = -1;
        int passengerCapacity = -1;
        int elevatorCurrentFloor = 1;
        int elevatorElapsedSeconds = 0;
        List<Integer> waitingDesiredFloors = new ArrayList<Integer>();
        List<Integer> desiredFloorsInLift = new ArrayList<Integer>();
        while (line != null) {
            timeThroughLoop++;
            //First time through loop:  line contains #passengers and #passengerCapacity
            //Second time through loop:  line contains each passenger's desired floor.
            String contents[] = line.split(" ");
            if (timeThroughLoop == 1) {
                totalPassengerCount = Integer.parseInt(contents[0]);
                passengerCapacity = Integer.parseInt(contents[1]);
            }
            else {
                for (String eachFloorStr : contents) {
                    waitingDesiredFloors.add(Integer.parseInt(eachFloorStr));
                }
                Collections.sort(waitingDesiredFloors);
            }
            line = reader.readLine();
        }
        
        //While passengers are still waiting in the lobby,...
        while (waitingDesiredFloors.size() > 0) {
            //Admit no more than #passengerCapacity passengers into lift at a time
            
            for (int passengersAdmitted = 0; passengersAdmitted <= passengerCapacity; passengersAdmitted++) {
                desiredFloorsInLift.add(waitingDesiredFloors.get(passengersAdmitted));
            }
            for (int passengersAdmitted = 0; passengersAdmitted <= passengerCapacity; passengersAdmitted++) {
                waitingDesiredFloors.remove(0);
            }
        
            //Loop through all floors until each desired floor in lift is reached, accumulating time spent one second for             //each floor.
            for (Integer eachDesiredFloor : desiredFloorInLift) {
                while (elevatorCurrentFloor < eachDesiredFloor) {
                    elevatorCurrentFloor++;
                    elevatorElapsedSeconds++;
                }
                //Elevator has reached a at least one passenger's desired floor.
                if (eachDesiredFloor == currentFloor) {
                    desiredFloorsInLift.remove(0);
                }
            }

            //Elevator has carried all passengers to their desired floors.  Return to 1st floor.
            while (elevatorCurrentFloor > 1) {
                elevatorCurrentFloor--;
                elevatorElapsedSeconds++;
            }
        }
        System.out.println(elevatorElapsedSeconds);
        
    }
}