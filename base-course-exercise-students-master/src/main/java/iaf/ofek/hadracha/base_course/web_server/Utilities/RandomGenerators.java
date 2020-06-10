package iaf.ofek.hadracha.base_course.web_server.Utilities;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGenerators {
    private Random random = new Random();
    /**
     * Generate a double between min (inclusive) and max (exclusive).
     */
    public double generateRandomDoubleInRange(double min, double max) {
        if (min>max){
            double temp = min;
            min = max;
            max = temp;
        }
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * Generate a double between min (inclusive) and max (exclusive) with normal distribution.
     */
    public double generateRandomDoubleInRangeWithNormalDistribution(double min, double max) {
        // In case that min and max are wrong, flip them
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }

        // Java's Gaussian generator produces a normal distribution with mean 0 and standard variation 1,
        // i.e. 68% of the results are in [-1..1], 95% are in [-2..2] and more than 99.9% in [-4..4].
        // We use this as our base number, as it is (pseudo) random.
        double baseNumber = random.nextGaussian();

        // Inflate the "bell" to the relevant range. Then it is divided by 8 because:
        //  - by 2 because [-1..1] is a range of size 2
        //  - by 2 to increase the number of results in range from 68% to 95%
        //  - by 2 to increase the number of results in range from 95% to 99.9%
        // Total: 2*2*2 = 8
        double num = baseNumber * (max - min) / 8;
        // Move mean to mid-range
        num+= (max + min) / 2;

        // All the results outside the limits will be moved to the limit
        num = Math.max(min+0.1, num);
        num = Math.min(max-0.1, num);

        return num;
    }
}
