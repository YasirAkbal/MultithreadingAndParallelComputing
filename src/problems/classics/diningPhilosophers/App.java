/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.diningPhilosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        Philosopher[] philosophers = null;
        ChopStick[] chopSticks;
        
        try {
            philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
            chopSticks = new ChopStick[Constants.NUMBER_OF_CHOPSTICKS];
            
            for(int i=0;i<Constants.NUMBER_OF_CHOPSTICKS;i++)
                chopSticks[i] = new ChopStick(i);
            
            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
            
            for(int i=0;i<Constants.NUMBER_OF_PHILOSOPHERS;i++) {
                philosophers[i] = new Philosopher(i,chopSticks[i], 
                        chopSticks[(i+1)%Constants.NUMBER_OF_CHOPSTICKS]);
                executorService.execute(philosophers[i]);
            }
            
            Thread.sleep(Constants.SIMULATION_RUNNING_TIME);
            
            for(Philosopher p: philosophers)
                p.setFull(true);
        } finally {
            executorService.shutdown();
            
            while(!executorService.isTerminated()) {
                Thread.sleep(1000);
            }
            
            for(Philosopher p: philosophers)
                System.out.println(p);
        }
    }
    
}
