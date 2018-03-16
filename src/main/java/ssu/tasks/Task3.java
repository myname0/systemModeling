package ssu.tasks;

import java.util.Random;

public class Task3 {
    public void execute(){
        Random random = new Random();
        int rightCount = 0;

        for(int i = 0; i < 1000; i++){
            int distance = 0;
            for(int j = 0; j < 10; j++){
                int direction = random.nextInt(4);
                switch (direction){
                    case 0: distance++; break;
                    case 1: distance--; break;
                    case 2: distance++; break;
                    case 3: distance--; break;
                }
            }
            if(distance <= 2 && distance >= -2){
                rightCount++;
            }
        }

        System.out.println((double)rightCount/1000);
    }
}
