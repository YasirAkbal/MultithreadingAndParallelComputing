/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Deadlocklardan farklı olarak livelocklarda threadler askıya alınmazlar, çalışır durumdadırlar. Fakat yapması gereken işlemleri yapamazlar. Sürekli birbirlerini engellerler.
 */
public class LiveLock {
    private static class Example {
        private ReentrantLock lock1 = new ReentrantLock();
        private ReentrantLock lock2 = new ReentrantLock();

        private void worker1() {
            while(true) {
                try {
                    lock1.tryLock(50, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LiveLock.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Worker1 acquired the lock1...");
                
                System.out.println("Worker1 tries to get lock2...");
                if(lock2.tryLock()) {
                    System.out.println("Worker1 acquired the lock2...");
                    lock2.unlock();
                } else {
                    System.out.println("Worker1 can not acquire lock2...");
                    continue;
                }
                
                break;
            }
            
            lock1.unlock();
            lock2.unlock(); 
        }

        private void worker2() {
            while(true) {
                try {
                    lock2.tryLock(50, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LiveLock.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Worker2 acquired the lock2...");
                
                System.out.println("Worker2 tries to get lock1...");
                if(lock1.tryLock()) {
                    System.out.println("Worker2 acquired the lock1...");
                    lock1.unlock();
                } else {
                    System.out.println("Worker2 can not acquire lock1...");
                    continue;
                }
                
                break;
            }
            
            lock1.unlock();
            lock2.unlock(); 
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Example example = new Example();
        
        new Thread(example::worker1,"worker1").start();
        new Thread(example::worker2,"worker2").start();
    }
    
}
