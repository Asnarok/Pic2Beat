package pic2beat.utils;

import java.util.function.Function;

public class MathUtils {

    /**
     * Calculates an integral with the Riemann method
     * @param f the function to integrate
     * @param from integral left bound
     * @param to integral right bound
     * @param nRect number of steps
     * @return an approximate integral value of the function between the bounds
     */
    public static double integrate(Function<Double, Double> f, double from, double to, int nRect) {
        double step = (to - from) / nRect;

        double integral = 0;

        for (int i = 0; i < nRect; i++) {
            integral += f.apply(from + step * i) * step;
        }

        return integral;
    }

}
