package ssu;


import javafx.util.Pair;
import ssu.tasks.*;

import java.util.List;

public class App {
    public static void main(String[] args) {
        //executeTask1();
        //executeTask2();
        //executeTask3();
        //executeTask4();
        executeTask5();
    }

    public static void executeTask1() {
        Task1 task1 = new Task1();
        Graph graph = new Graph();
        graph.setY(task1.countEquation());
        graph.setX(null);
        graph.draw();
    }

    public static void executeTask2() {
        Task2 task2 = new Task2();
        Graph graph = new Graph();
        Pair pair = task2.executeTaskA();
        graph.setX((List<Double>) pair.getKey());
        graph.setY((List<Double>) pair.getValue());
        graph.draw();

        Graph graph2 = new Graph();
        Pair pair2 = task2.executeTaskB();
        graph2.setX((List<Double>) pair2.getKey());
        graph2.setY((List<Double>) pair2.getValue());
        graph2.draw();
    }

    public static void executeTask3() {
        Task3 task3 = new Task3();
        task3.execute();
    }

    public static void executeTask4() {
        Task4 task4 = new Task4();
        task4.execute();
    }

    public static void executeTask5() {
        Task5 task5 = new Task5();
        task5.execute();
    }
}
