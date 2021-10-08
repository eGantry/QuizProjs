/*
    Console application which accepts line commands from the console to enable the user to create a Driver object, 
    to add a Trip to the Driver object, to list the trips for a Driver object, to report the totals for a Driver object,
    or to report all driver objects' totals.  Objects were used to simplify the organization of methods and properties.
 */
package rootcodingchallenge;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Ron Ruffin
 */
public class RootCodingChallenge {
    /**
     * @param args the command line arguments
     */
    static List<Driver> drivers = new ArrayList<Driver>();
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print("Type in a command:  ");
            String command = in.nextLine();
            if (!command.toLowerCase().equals("exit")) {
                //Do Stuff
                processCommand(command);
            }
            else {
                keepGoing = false;
            }
        }
        System.out.println("Done.");
        
    }
    
    private static void processCommand(String command) {
        String values[] = command.split(" ");
        
        if (values[0].equalsIgnoreCase("Driver")) {
            if (values.length == 2) {
                String name = values[1];
                if (!hasDriver(name)) {
                    System.out.println("Creating driver '" + name + "'.");
                    drivers.add(new Driver(name));
                }
                else {
                    System.out.println("Driver '" + name + "' already exists.");
                }
            }
        }
        else if (values[0].equalsIgnoreCase("List")) {
            if (values.length == 2) {
                String name = values[1];
                if (hasDriver(name)) {
                    System.out.println("Listing trips for driver '" + name + "'.");
                    Driver driver = getDriver(name);
                    List<String> trips = driver.listTrips();
                    for (String eachTrip : trips) {
                        System.out.println(eachTrip);
                    }
                }
                else {
                    System.out.println("Driver '" + name + "' doesn't exist.");
                }
            }
            else if (values.length == 1) {
                System.out.println("To list trips, please include the Driver's name.");
            }
        }
        else if (values[0].equalsIgnoreCase("Trip")) {
            if (values.length == 5) {
                String name = values[1];
                String startTime = values[2];
                String endTime = values[3];
                String distance = values[4];
                if (hasDriver(name)) {
                    System.out.println("Adding trip for driver '" + name+ "'.");
                    Driver driver = getDriver(name);
                    boolean success = driver.addTrip(startTime, endTime, distance);
                    if (!success) {
                        System.out.println("Unable to record trip as entered for Driver '" + name + "'.");
                    }
                }
                else {
                    System.out.println("Driver '" + name + "' doesn't exist.");
                }
            }
        }
        else if (values[0].equalsIgnoreCase("Report")) {
            if (values.length == 2) {
                String name = values[1];
                if (hasDriver(name)) {
                    System.out.println("Reporting trips total for driver '" + name+ "'.");
                    Driver driver = getDriver(name);
                    System.out.println(name + ":  " + driver.reportTripTotal());
                }
                else {
                    System.out.println("Driver '" + name + "' doesn't exist.");
                }
            }
            else if (values.length == 1) {
                System.out.println("Reporting trips total for all drivers.");
                for (Driver eachDriver : drivers) {
                    System.out.println(eachDriver.name + ":  " + eachDriver.reportTripTotal());
                }
            }
        }
        else {
            System.out.println("Invalid command.");
        }
        
    }
    
    private static boolean hasDriver(String name){
        boolean result = false;
        for (Driver eachDriver : drivers) {
            if (eachDriver.name.equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    private static Driver getDriver(String name){
        Driver result = null;
        for (Driver eachDriver : drivers) {
            if (eachDriver.name.equals(name)) {
                result = eachDriver;
                break;
            }
        }
        return result;
    }
    
    
    
}
