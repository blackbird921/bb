package com.bb.service;

import com.bb.domain.Configuration;
import com.bb.domain.Location;
import com.bb.util.AutowiredLogger;
import com.bb.util.GpsDistanceCalc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LocationService {

    @AutowiredLogger
    Logger logger;

    @Autowired
    private ConfigurationService configurationService;


    public Location getCurrentLocation(double lat, double lon) {
        Location location = null;
        GpsDistanceCalc.GpsRange range = GpsDistanceCalc.getGpsRange(lat, lon, (long) configurationService.getCheckinValidRange());
        location = getClosestLocation(lat, lon, Location.findAllLocationsInRange(range));
        return location;
    }

    public Location getClosestLocation(double lat, double lon, List<Location> locations) {
        int closest = configurationService.getCheckinValidRange() + 100;
        Location closestLocation = null;
        for (Location loc : locations) {
            double distance = GpsDistanceCalc.computeDistance(lat, lon, loc.getLatitude(), loc.getLongitude());
            if (distance < closest) {
                closest = (int)distance;
                closestLocation = loc;
            }
        }
        return closestLocation;
    }
}
