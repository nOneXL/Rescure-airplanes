package iaf.ofek.hadracha.base_course.web_server.Utilities;

import iaf.ofek.hadracha.base_course.web_server.Data.Coordinates;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeographicCalculationsTest {
    @Test
    public void testDistanceBetween_knownCoordinates(){
        // Arrange
        Coordinates coord1 = new Coordinates(31.827334, 34.699995); // מחלף אשדוד דרום
        Coordinates coord2 = new Coordinates(31.8889400, 34.836166); // צומת רחובות מזרח
        GeographicCalculations geographicCalculations = new GeographicCalculations();

        // Act
        double distanceBetween = geographicCalculations.distanceBetween(coord1, coord2);

        // Assert
        assertEquals(14600, distanceBetween, 20);

    }

    @Test
    public void testMoveCoordinateBy() {
        GeographicCalculations geographicCalculations=new GeographicCalculations();
        Coordinates target = geographicCalculations.moveCoordinateBy(new Coordinates(32.310779, 36.837706), 50000/2.0, 90);
        System.out.println(target);
        System.out.println(geographicCalculations.distanceBetween(target,new Coordinates(32.310779, target.lon)));
    }
}