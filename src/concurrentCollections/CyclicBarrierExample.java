/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Birden fazla thread'in birbirini beklemesini sağlamak için kullanılır. Başlangıç değeri birbirini bekleyecek threadlerin...
 * sayısıdır. Threadler birbirini beklemek için "await" metodunu kullanırlar. Başlangıç değeri kadar "await" metodu çağrıldığında...
 * yani tüm metotlar "await1" metodunu çağırdığında artık bariyer kalkmış olur ve threadler artık çalışır duruma geçerler.
 */
public class CyclicBarrierExample {
    
    static class Worker implements Runnable {
        private int id;
        private Random random;
        private CyclicBarrier barrier;

        public Worker(int id, CyclicBarrier barrier) {
            this.id = id;
            this.random = new Random();
            this.barrier = barrier;
        }
        
        @Override
        public void run() {
            doWork();
        }

        private void doWork() {
            System.out.println("Thread with ID " + this.id + " starts the work...");
            
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException ex) {
                Logger.getLogger(CyclicBarrierExample.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(CyclicBarrierExample.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("After the await()");
        }   
    }
    
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("All tasks have been finished..."); //tüm threadler await metodunu çağırdı anlamında. threadlerin sonlanması gerekmiyor hala çalışabilir durumda olabilirler.
            }    
        });
        
        for(int i=0;i<5;i++) {
            service.execute(new Worker(i+1, barrier));
        }
        
        service.shutdown();
    }
    
}
