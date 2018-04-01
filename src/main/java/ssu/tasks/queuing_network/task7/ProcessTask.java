package ssu.tasks.queuing_network.task7;

import ssu.tasks.queuing_network.Task;

import java.util.Queue;
import java.util.Random;

public class ProcessTask extends Thread {
    private int countOfTasks;
    private double time;
    private double lambdaForProcess;
    private QNService service;
    private Queue<Task> queue;

    public ProcessTask(int countOfTasks, double lambdaForProcess, QNService service, Queue<Task> queue) {
        time = 0;
        this.countOfTasks = countOfTasks;
        this.lambdaForProcess = lambdaForProcess;
        this.service = service;
        this.queue = queue;
    }

    public void run() {
        while (service.output.size() < countOfTasks) {
            if (service.currentTime >= time) {
                Task task;
                if ((task = queue.poll()) != null) {
                    time = service.currentTime;
                    task.setStartProcessingTime(time);

                    time += countTaskProcessingTime();
                    task.setFinishProcessingTime(time);
                    service.putTaskInQueue(task);
                    service.output.add(new Task(task));
                } else time = service.generateTime;
            }
        }
    }

    private double countTaskProcessingTime() {
        Random rand = new Random();
        return lambdaForProcess * Math.log(rand.nextDouble());
    }

    public double getTime() {
        return time;
    }
}
