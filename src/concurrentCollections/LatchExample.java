/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Bir thread'in bir grup threadin işlemlerini bitirmesini beklemesini sağlamak için CountdonLatch kullanılabilir.
 * CountDownLatch'in başlangıç değeri kadar "countDown" metodu çalıştığında "await" metodunun çalıştığı metot bekleme durumundan çıkar ve "await" metodunun bir sonraki satırından çalışmaya..
 * devam eder.
 */
public class LatchExample {

    static class Worker implements Runnable {
        private int id;
        private CountDownLatch latch;

        public Worker(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }
        
        @Override
        public void run() {
            doWork();
            latch.countDown();
        }  

        private void doWork() {
            try {
                Thread.sleep(2000);
                System.out.println("Thread with ID " + this.id + " finished execution.");
            } catch (InterruptedException ex) {
                Logger.getLogger(LatchExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(2);
        
        for(int i=0;i<5;i++)
            service.execute(new Worker(i+1,latch));
        
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(LatchExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("All tasks have been finished...");
        service.shutdown();
    }
    
}
