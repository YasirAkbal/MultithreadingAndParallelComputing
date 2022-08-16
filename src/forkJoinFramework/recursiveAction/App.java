/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package forkJoinFramework.recursiveAction;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        SimpleRecursiveAction action = new SimpleRecursiveAction(120);
        
        pool.execute(action);
        pool.shutdown();
        try {
            while(!pool.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Waiting for finish...");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
