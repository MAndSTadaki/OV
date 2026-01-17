package models;

import java.io.IOException;
import java.util.function.DoubleFunction;

/**
 *
 * @author tadaki
 */
public class Step extends Simulation {

    public Step(DoubleFunction ovfunction,
            int length, int numCar, double alpha) {
        super(ovfunction, length, numCar, alpha);
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        int length = 1000;
        double alpha = 1.;
        double vmax = 10.;
        double d = 10.;
        int numCar = 100;
        DoubleFunction<Double> ovfunction
                = x -> {
                    double v = 0.;
                    if (x > d) {
                        v = vmax;
                    }
                    return v;
                };
        Step sys = new Step(ovfunction, length, numCar, alpha);
        sys.spacetime("Step-spacetime.txt");
        sys.hv("Step-hv.txt");
    }

}
