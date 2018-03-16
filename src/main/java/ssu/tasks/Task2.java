package ssu.tasks;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final double g = 9.8;
    private static final double l = 12.0;
    private static final double m = 130.0;
    private static final double h = 0.01;
    private static final double count = 40.0;

    private double xn;
    private double yn;
    private double x = 1;
    private double y = 1;

    private List<Double> pointsX = new ArrayList<Double>();
    private List<Double> pointsY = new ArrayList<Double>();


    private void countPoints(double c, List<Double> pointsX, List<Double> pointsY) {
        double k = 0;
        while (k < count) {
            k += h;
            xn = x + h * y;
            yn = y + h * countFunction(c, x);
            pointsX.add(x);
            pointsY.add(y);
            x = xn;
            y = yn;
        }
    }

    private double countFunction(double c, double x) {
        return -(g / l) * Math.sin(x) - (c * y) / (l * m);
    }

    public Pair executeTaskA() {
        List<Double> pointsX = new ArrayList<Double>();
        List<Double> pointsY = new ArrayList<Double>();

        countPoints(0.0, pointsX, pointsY);
        return new Pair(pointsX, pointsY);
    }

    public Pair executeTaskB() {
        List<Double> pointsX = new ArrayList<Double>();
        List<Double> pointsY = new ArrayList<Double>();

        countPoints(0.5, pointsX, pointsY);
        return new Pair(pointsX, pointsY);
    }
}
