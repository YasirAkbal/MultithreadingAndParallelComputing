/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basic;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class ReentrantLockExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    lock.lock();
                    try {
                        System.out.println("2 defa lockladim. 2 defa unlock yapilmadan diger threadler buna eriseyemeyecek.");
                        System.out.println("Ilk thread: 2 locktan sonra holdCount = " + lock.getHoldCount());
                    } finally {
                        lock.unlock();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ReentrantLockExample.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("Ilk thread: 2 lock, 1 unlocktan sonra holdCount = " + lock.getHoldCount());
                } finally {
                    lock.unlock();
                }
            }
        });
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                
                System.out.println("Ikinci thread: baslangictaki holdCount degeri = " + lock.getHoldCount()); 
                System.out.println("Ilk threadin unlock yapmasi bekleniyor...");
                
                lock.lock();  //ilk thread 2 kere locklayıp 1 kere unlock yaptıgı icin burada ilk thread'in ikinci unlocku yapmasını bekleyecek.
                try {
                    //..
                } finally {
                    lock.unlock();
                }
                System.out.println("Ikinci thread son");
            }
        });
        
        t1.start();
        t2.start();
        
    }
    
}
