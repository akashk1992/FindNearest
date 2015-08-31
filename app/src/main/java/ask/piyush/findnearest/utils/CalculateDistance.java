package ask.piyush.findnearest.utils;

/**
 * Created by piyush on 5/7/15.
 */
public class CalculateDistance {

    public static double getDistanceFromLatLonInKm(double lat1, double lon1,double currentLatitude, double currentLongitude) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(currentLatitude - lat1);  // deg2rad below
        double dLon = deg2rad(currentLongitude - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(currentLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;
    }

    private static double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }
}
