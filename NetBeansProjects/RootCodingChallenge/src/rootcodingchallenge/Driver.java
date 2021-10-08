/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rootcodingchallenge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ron Ruffin
 */
public class Driver {
    List<Trip> trips;
    String name;
    
    Driver(String driversName) {
        trips = new ArrayList<>();
        name = driversName;
    }
    
    public boolean addTrip(String startTimeStr, String endTimeStr, String distanceStr) {
        boolean success = true;
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        try {
            Date startTime = sdfTime.parse(startTimeStr);
            Date endTime = sdfTime.parse(endTimeStr);
            double distance = Double.parseDouble(distanceStr);
            Trip newTrip = new Trip(startTime, endTime, distance);
            double avgSpeed = calculateAvgSpeed(newTrip);
            if ((endTime.after(startTime)) && (avgSpeed >= 5) && (avgSpeed <= 100)) {
                trips.add(newTrip);
            }
            else {
                success = false;
            }
        }
        catch(ParseException | NumberFormatException e) {
            success = false;
        }
        return success;
    }
    
    double calculateAvgSpeed(Trip trip) {
        long endTime = trip.endTime.getTime();
        long startTime = trip.startTime.getTime();
        double distance = trip.distance;

        long timeDiffMillis = endTime - startTime;
        
        double seconds = (double)timeDiffMillis/1000;
        double hours = seconds/3600;
         
        //Miles per hour = distance / hours.
        double result = distance/hours;
        
        return result;
    }
    
    List<String> listTrips() {
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        for (Trip eachTrip : trips) {
            String tripLine = sdfTime.format(eachTrip.startTime) + " " + sdfTime.format(eachTrip.endTime) + " " + eachTrip.distance + " miles.";
            result.add(tripLine);
        }
        return result;
    }
    
    String reportTripTotal() {
        double totalDistance = 0.0;
        double totalSpeed = 0.0;
       
        for (Trip eachTrip : trips) {
            totalDistance += eachTrip.distance;
            double tripSpeed = calculateAvgSpeed(eachTrip);
            totalSpeed += tripSpeed;
        }
        
        double avgSpeed = -1.0;
        
        String result;
        
        if (trips.size() > 0) {
            avgSpeed = totalSpeed / trips.size();
            
            result = Math.round(totalDistance) + " miles @ " + Math.round(avgSpeed) + " mph";
        }
        else {
            result = Math.round(totalDistance) + " miles";
        }
        
        return result;
    }
    
}
