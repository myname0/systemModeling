package ssu.tasks;

import java.util.Random;

public class Task5 {
    public void execute() {
        Random random = new Random();
        double constant = -1.00 / 15.00;
        double X = 0.00;
        double sum = 0.00;
        for (int i = 0; i < 1000; i++) {
            X = (X + constant * Math.log(random.nextDouble()));
            sum += X;

        }
        System.out.println(sum / 1000.00);
    }
}
