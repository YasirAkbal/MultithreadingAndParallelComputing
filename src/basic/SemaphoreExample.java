/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Semaphore'lar mutual exclusionı sağlamanın yanında sınırlı sayıda kaynağa erişimi kontrol etmek için kullanılırlar. 
 * 0 ve 1 değerlerini alan(binary semaphore) semaphore ile mutex yapısı hemen hemen aynı görevi görür. Fakat mutexlerde "principle of ownership" kavramı varken semaphore'larda yoktur.
 */

enum Downloader {
    INSTANCE;
    
    private Semaphore semaphore = new Semaphore(3, true);
    
    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
    
    private void downloadData() {
        try {
            Thread.sleep(2000);
            System.out.println("Downloading data from the web...");
        } catch (InterruptedException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class SemaphoreExample {
 
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        
        for(int i=0;i<12;i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Downloader.INSTANCE.download();
                }
            });
        }
    }
    
}
