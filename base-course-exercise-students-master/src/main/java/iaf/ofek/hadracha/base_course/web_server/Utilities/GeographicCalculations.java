package iaf.ofek.hadracha.base_course.web_server.Utilities;

import iaf.ofek.hadracha.base_course.web_server.Data.Coordinates;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class GeographicCalculations {
    /**
     * @return the distance between two coordinates in meters
     */
    public double distanceBetween(@NotNull Coordinates coord1, @NotNull Coordinates coord2){
        return Geodesic.WGS84.Inverse(coord1.lat,coord1.lon,coord2.lat,coord2.lon).s12;
    }

    /**
     * Returns a coordinate that is in given distance and azimuth from the origin distance
     * @param origin the coordinate to start from
     * @param meters the distance to travel from the origin
     * @param inAzimuth azimuth from origin to result. 0 is north, 90 is east.
     */
    public Coordinates moveCoordinateBy(@NotNull Coordinates origin, double meters, double inAzimuth){
        GeodesicData geodesicData = Geodesic.WGS84.Direct(origin.lat, origin.lon, inAzimuth, meters);
        return new Coordinates(geodesicData.lat2, geodesicData.lon2);
    }

    /**
     * @return the azimuth between origin to target (azimuth at origin)
     */
    public double azimuthBetween(@NotNull Coordinates origin, @NotNull Coordinates target){
        return Geodesic.WGS84.Inverse(origin.lat,origin.lon,target.lat,target.lon).azi1;
    }

    /**
     * normalize azimuth to be in [0..360)
     * @param azimuth in degrees
     */
    public double normalizeAzimuth(double azimuth) {
        while (azimuth<0)
            azimuth+=360;
        return azimuth%360;
    }
}
