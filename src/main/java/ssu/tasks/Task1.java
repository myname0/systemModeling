package ssu.tasks;

import java.util.ArrayList;
import java.util.List;

public class Task1 {
    private static final double K = (double) 1 / 252;
    private static final double V = 3;
    private static final double N = 1000;
    private static final int h = 1;

    public List<Double> countEquation() {
        List<Double> x = new ArrayList<Double>();
        x.add(0.0);

        for (int i = 1; i < N; i += h) {
            x.add(x.get(i - 1) + h * countFunction(x.get(i - 1)));
        }
        return x;
    }

    private double countFunction(double x) {
        return K * (6 * V - 2 * x) * (3 * V - x);
    }
}
