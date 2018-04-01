package ssu.tasks.queuing_network.task6;

import ssu.tasks.queuing_network.Task;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Service {
    private Queue<Task> queue = new ConcurrentLinkedQueue();
    private Queue<Task> output = new ConcurrentLinkedQueue();
    private Map<Integer, Double> timeIntervals = new ConcurrentHashMap<Integer, Double>();
    private volatile double currentTime = 0;
    private volatile double processTime = 0;
    private volatile double generateTime = 0;
    private volatile double generateInterruptionTime = 20;
    private static final double LAMBDA_FOR_PROCESS = -2.00;
    private static final double LAMBDA_FOR_GENERATE = -2.00;
    private static final double INTERRUPTION_TIME = 20.00;
    private static final int COUNT_OF_TASKS = 1000;

    Thread generateTasks = new Thread(new Runnable() {
        @Override
        public void run() {
            while (output.size() != COUNT_OF_TASKS) {
                if (currentTime >= generateTime) {
                    Task task = new Task();
                    task.setEntranceTime(currentTime);
                    queue.add(task);
                    generateTime = currentTime + countGenerateTime();
                }
                if(generateInterruptionTime < currentTime){
                    generateTime += INTERRUPTION_TIME;
                    generateInterruptionTime = currentTime + INTERRUPTION_TIME * 2;
                }
            }
        }
    });

    private double countGenerateTime() {
        Random rand = new Random();
        return LAMBDA_FOR_GENERATE * Math.log(rand.nextDouble());
    }

    Thread processTasks = new Thread(new Runnable() {
        @Override
        public void run() {
            while (output.size() < COUNT_OF_TASKS) {
                if (currentTime >= processTime) {
                    if (!queue.isEmpty()) {
                            processTime = currentTime;
                            Task task = queue.poll();
                            task.setStartProcessingTime(processTime);

                            processTime += countTaskProcessingTime();
                            task.setFinishProcessingTime(processTime);
                            output.add(task);
                    } else processTime = generateTime;
                }
            }
        }
    });


    private double countTaskProcessingTime() {
        Random rand = new Random();
        return LAMBDA_FOR_PROCESS * Math.log(rand.nextDouble());
    }

    Thread controlTime = new Thread(new Runnable() {
        @Override
        public void run() {
            while (output.size() != COUNT_OF_TASKS) {
                currentTime = Math.min(processTime, generateTime);
            }
            printResults();
        }
    });

    Thread controlTimeWthCountOfTasks = new Thread(new Runnable() {
        @Override
        public void run() {
            int countTasks = 0;
            int currentCountTasks = 0;
            double splitTime = 0;
            while (output.size() != COUNT_OF_TASKS){
                countTasks = queue.size();
                if(processTime > currentTime) countTasks++;
                if(countTasks != currentCountTasks){
                    if(timeIntervals.containsKey(countTasks)) {
                        timeIntervals.put(countTasks, timeIntervals.get(countTasks) + currentTime - splitTime);
                    }else{
                        timeIntervals.put(countTasks, currentTime - splitTime);
                    }

                    splitTime = currentTime;
                    currentCountTasks = countTasks;
                }
            }
        }
    });

    private void printResults() {
        double tasksProcessingTime = 0;
        for (Task task : output) {
            tasksProcessingTime += task.getDurationOfProcessing();
        }
        System.out.println("u = " + tasksProcessingTime / output.size());

        double nSum =0;
        double sum = 0;
        for(Map.Entry<Integer, Double> entry : timeIntervals.entrySet()){
            nSum += entry.getValue() * entry.getKey() / currentTime;
            sum +=entry.getValue();
        };

        System.out.println("time in map: " + sum + " currentTime: " +currentTime);
        System.out.println("n = " + nSum);
    }

    public void run() {
        controlTime.start();
        generateTasks.start();
        processTasks.start();
        controlTimeWthCountOfTasks.start();
    }
}
