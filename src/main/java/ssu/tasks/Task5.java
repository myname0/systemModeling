package ssu.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task5 {
    public void execute() {
        Random random = new Random();
        double constant = -15.00;
        double X;
        double sum = 0.00;
        List<Double> listX;
        double manTime;
        for (int i = 0; i < 1000; i++) {
            X = 0.0;
            listX = new ArrayList<Double>();
            for(int j = 0; j < 96; j++) {
                X = X + constant * Math.log(random.nextDouble());
                listX.add(X);
            }
            manTime = random.nextDouble() * listX.get(listX.size() - 1);
            sum += findClosest(listX, manTime);
        }
        System.out.println(sum / 1000.00);
    }

    private double findClosest(List<Double> list, double x){
        for (Double number : list) {
            if(number >= x) return number - x;
        }
        return 0;
    }
}
