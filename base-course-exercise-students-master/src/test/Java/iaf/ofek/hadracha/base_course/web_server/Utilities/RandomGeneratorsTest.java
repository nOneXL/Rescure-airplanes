package iaf.ofek.hadracha.base_course.web_server.Utilities;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RandomGeneratorsTest {

    @Test
    public void generateRandomDoubleInRange() {
        //checks the range, since the randomization itself is done by Java
        RandomGenerators randomGenerators = new RandomGenerators();
        for (double i = -20; i < 20; i+=0.1) {
            for (double j = -20; j < 20; j+=0.01) {
                assertThat(randomGenerators.generateRandomDoubleInRange(i, j),
                        both(is(greaterThanOrEqualTo(Math.min(i, j))))
                                .and(is(lessThanOrEqualTo(Math.max(i, j)))));
            }
        }
    }

    @Test
    public void generateRandomDoubleInRangeWithNormalDistribution() {
        // checks the mean and standard deviation

        // Arrange
        RandomGenerators randomGenerators = new RandomGenerators();

        final int numOfBuckets = 11; // must be odd
        final int numOfNumbers = 10000;
        final int min = -5;
        final int max = min + numOfBuckets;
        int[] histogram = new int[numOfBuckets];

        // Act
        for (int i = 0; i < numOfNumbers; i++) {
            double num = randomGenerators.generateRandomDoubleInRangeWithNormalDistribution(min, max);
            histogram[(int) Math.floor(num)-min]++;
        }

        // Assert
        int middleBucket = numOfBuckets / 2 + 1;

        // check mean
        int count = 0;
        int i = 0;
        for (i = 0; count < numOfNumbers / 2; i++) {
            count += histogram[i];
        }
        assertEquals("Mean is wrong", middleBucket, i);

        //check deviation
        count=histogram[middleBucket];
        for (i = 1; i < numOfBuckets / 2 && count<numOfNumbers*0.68; i++) {
            count+=histogram[middleBucket-i]+histogram[middleBucket+i];
        }
        assertEquals("Deviation is wrong", (max-min+1)/8.0, i-1, 1);
    }
}