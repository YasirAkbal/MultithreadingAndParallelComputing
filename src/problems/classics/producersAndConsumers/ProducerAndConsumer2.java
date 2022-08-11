/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.producersAndConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * ReentrantLock ve Condition ile
 * Üretici-tüketici problemi senaryosu(ReentrantLock ve conditions ile):
 * Üretici belirlenen üst limit kadar ürünü ürütmeden tüketici ürün tüketmeyecek.
 * Üretici ürünleri ardışık olarak üretecek.
 * Tüketici tüm ürünleri ardışık olarak tüketecek.(ürün sayısı alt limite ulaşına kadar üretici yeni ürün üretmeyecek)
 */

class Processor2 {
    
    private int value;
    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5; //count
    private static final int LOWER_LIMIT = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();
    
    private final Condition isDeleteCompletedCondition = lock.newCondition();
    private boolean isDeleteCompleted = true;
    
    public void producer() throws InterruptedException {
        while(true) {
            try {
                lock.lock();
                
                while(!isDeleteCompleted)  {
                    System.out.println("Tum elemanlarin silinmesi bekleniyor...");
                    isDeleteCompletedCondition.await();
                }
                
                while(list.size() >= UPPER_LIMIT) {
                    System.out.println("Ust limite ulasildi. Consumer bekleniyor...");
                    emptyCondition.await();
                }

                value++;
                list.add(value);
                System.out.println("Eklendi: " + value);
                
                if(list.size() == UPPER_LIMIT) {
                    isDeleteCompleted = false;
                    fullCondition.signalAll();
                }
                    
            } finally {
                lock.unlock();
            }
            
            Thread.sleep(100);
        }
    }
    
    public void consumer() throws InterruptedException {
        while(true) {
            try {
                lock.lock();
                         
                while(list.size() < UPPER_LIMIT && isDeleteCompleted) {
                    System.out.println(String.format("%d kadar urunun tamaminin uretilmesi icin Producer bekleniyor.", UPPER_LIMIT));
                    fullCondition.await();
                }
                
                int deleted = list.remove(list.size()-1);
                System.out.println("Silindi: " + deleted);
                
                if(list.size() == LOWER_LIMIT && !isDeleteCompleted) {
                    isDeleteCompleted = true;
                    isDeleteCompletedCondition.signalAll();
                    emptyCondition.signalAll();   
                }
                    
            } finally {
                lock.unlock();
            }
            
            Thread.sleep(100);
        }
    }
}

public class ProducerAndConsumer2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Processor2 processor = new Processor2();
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        t1.start();
        t2.start();
    }
    
}
