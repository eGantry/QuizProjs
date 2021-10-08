/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rootcodingchallenge;

import java.util.Date;

/**
 *
 * @author Ron Ruffin
 */
public class Trip {
    Date startTime;
    Date endTime;
    double distance;
    
    Trip(Date tripStartTime, Date tripEndTime, double tripDistance) {
        startTime = tripStartTime;
        endTime = tripEndTime;
        distance = tripDistance;
    }
    
}
