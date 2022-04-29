package pic2beat.utils;

import java.util.function.Function;

public class MathUtils {

    public static double integrate(Function<Double, Double> f, double from, double to, int nRect) {
        double step = (to - from) / nRect;

        double integral = 0;

        for (int i = 0; i < nRect; i++) {
            integral += f.apply(from + step * i) * step;
        }

        return integral;
    }

}
