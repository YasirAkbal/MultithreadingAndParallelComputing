/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forkJoinFramework.recursiveTask;

import java.util.concurrent.RecursiveTask;

/**
 *
 * @author yasir
 */
public class SimpleRecursiveTask extends RecursiveTask<Integer> {
    private int simulatedWork;

    public SimpleRecursiveTask(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }
    
    @Override
    protected Integer compute() {
        if(simulatedWork > 100) {
            System.out.println("Parallel execution and split the tasks..."+simulatedWork);
            
            SimpleRecursiveTask task1 = new SimpleRecursiveTask(simulatedWork/2);
            SimpleRecursiveTask task2 = new SimpleRecursiveTask(simulatedWork/2);
            
            task1.fork();
            task2.fork();
            
            int subSolution = 0;
            subSolution += task1.join();
            subSolution += task2.join();
            
            return subSolution;
        } else {
            System.out.println("The task is rather small so sequential execution is fine. The size of task = " + simulatedWork);
            return 2*simulatedWork;
        }
    }
    
}
