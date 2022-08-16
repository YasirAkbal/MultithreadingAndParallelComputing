/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forkJoinFramework.recursiveAction;

import java.util.concurrent.RecursiveAction;

/**
 *
 * @author yasir
 */
public class SimpleRecursiveAction extends RecursiveAction {
    private int simulatedWork;

    public SimpleRecursiveAction(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }
    
    @Override
    protected void compute() {
        if(simulatedWork > 100) {
            System.out.println("Parallel execution and split the tasks...");
            
            SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork/2);
            SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork/2);
            
            action1.fork();
            action2.fork();
        } else {
            System.out.println("The task is rather small so sequential execution is fine...");
            System.out.println("The size of task = " + simulatedWork);
        }
    }
}
