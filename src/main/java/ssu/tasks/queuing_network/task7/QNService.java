package ssu.tasks.queuing_network.task7;

import ssu.tasks.queuing_network.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QNService {
    Queue<Task> output = new ConcurrentLinkedQueue();
    private List<ProcessTask> processTaskList = new ArrayList();
    private List<Queue<Task>> queues = new ArrayList<Queue<Task>>();
    private Map<Integer, Double> timeIntervals = new ConcurrentHashMap();
    volatile double currentTime = 0;
    volatile double generateTime = 0;
    private static final double PROBABILITY = 0.5;
    private static final double LAMBDA_FOR_PROCESS = -2.00;
    private static final double LAMBDA_FOR_GENERATE = -2.00;
    private static final int COUNT_OF_TASKS = 1000;
    private static final int COUNT_OF_QUEUE = 2;
    private static final int COUNT_PROCESS_FOR_QUEUE = 2;

    public QNService() {
        initProcessTask();
    }

    private void initProcessTask(){
        for(int i = 0; i < COUNT_OF_QUEUE; i++){
            queues.add(new ConcurrentLinkedQueue<Task>());
        }

        for(Queue<Task> queue : queues) {
            for (int i = 0; i < COUNT_PROCESS_FOR_QUEUE; i++) {
                processTaskList.add(new ProcessTask(COUNT_OF_TASKS, LAMBDA_FOR_PROCESS, this, queue));
            }
        }
    }

    private Thread generateTasks = new Thread(new Runnable() {
        @Override
        public void run() {
            while (output.size() != COUNT_OF_TASKS) {
                if (currentTime >= generateTime) {
                    Task task = new Task();
                    putTaskInQueue(task);
                    generateTime = currentTime + countGenerateTime();
                }
            }
        }
    });

    void putTaskInQueue(Task task) {
        Random random = new Random();
        task.setEntranceTime(currentTime);
        if (random.nextDouble() > PROBABILITY) {
            queues.get(0).add(task);
        } else {
            queues.get(1).add(task);
        }
    }

    private double countGenerateTime() {
        Random rand = new Random();
        return LAMBDA_FOR_GENERATE * Math.log(rand.nextDouble());
    }

    Thread controlTime = new Thread(new Runnable() {
        @Override
        public void run() {
            while (output.size() != COUNT_OF_TASKS) {
                currentTime = countMin();
            }
            printResults();
        }
    });

    private double countMin() {
        List<Double> mins = new ArrayList<Double>();
        mins.add(generateTime);
        for(ProcessTask processTask : processTaskList){
            mins.add(processTask.getTime());
        }
        return Collections.min(mins);
    }

    Thread controlTimeWthCountOfTasks = new Thread(new Runnable() {
        @Override
        public void run() {
            int countTasks = 0;
            int currentCountTasks = 0;
            double splitTime = 0;
            while (output.size() != COUNT_OF_TASKS) {
                for(Queue queue : queues){
                    countTasks += queue.size();
                }

                for (ProcessTask processTask : processTaskList){
                    if (processTask.getTime() > currentTime) countTasks++;
                }

                if (countTasks != currentCountTasks) {
                    if (timeIntervals.containsKey(countTasks)) {
                        timeIntervals.put(countTasks, timeIntervals.get(countTasks) + currentTime - splitTime);
                    } else {
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

        double nSum = 0;
        double sum = 0;
        for (Map.Entry<Integer, Double> entry : timeIntervals.entrySet()) {
            nSum += entry.getValue() * entry.getKey() / currentTime;
            sum += entry.getValue();
        }
        ;

        System.out.println("time in map: " + sum + " currentTime: " + currentTime);
        System.out.println("n = " + nSum);
    }

    public void run() {
        controlTime.start();
        generateTasks.start();
        controlTimeWthCountOfTasks.start();
        for(ProcessTask processTask : processTaskList){
            processTask.start();
        }
    }
}
