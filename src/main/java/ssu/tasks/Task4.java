package ssu.tasks;

import java.util.Random;

public class Task4 {
    public void execute(){
        Random randomX = new Random();
        Random randomY = new Random();

        double X;
        double Y;
        double rightCount = 0;
        for (int i = 0; i < 1000; i++) {
            X = generateRandomNumber(randomX);
            Y = generateRandomNumber(randomY);

            if (Math.abs(X - Y) <= 1) {
                rightCount++;
            }
        }

        System.out.println(rightCount / 1000);
    }

    private double generateRandomNumber(Random rand){
        double R = 0;
        for (int l = 0; l < 12; l++) {
            R += rand.nextDouble();
        }
        return R - 6;
    }
}
