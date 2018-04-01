package ssu.tasks.queuing_network;

public class Task {
    private double entranceTime;
    private double startProcessingTime;
    private double finishProcessingTime;

    public Task() {
    }

    public Task(Task task){
        this.entranceTime = task.entranceTime;
        this.startProcessingTime = task.startProcessingTime;
        this.finishProcessingTime = task.finishProcessingTime;
    }

    public void setEntranceTime(double entranceTime) {
        this.entranceTime = entranceTime;
    }

    public void setStartProcessingTime(double startProcessingTime) {
        this.startProcessingTime = startProcessingTime;
    }

    public void setFinishProcessingTime(double finishProcessingTime) {
        this.finishProcessingTime = finishProcessingTime;
    }

    public double getDurationOfProcessing() {
        return finishProcessingTime - entranceTime;
    }

    public double getEntranceTime() {
        return entranceTime;
    }

    public double getStartProcessingTime() {
        return startProcessingTime;
    }

    public double getFinishProcessingTime() {
        return finishProcessingTime;
    }
}
