/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basics;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * wait ve notify sadece synchronized blokları içinde kullanılabilen metotlardır.
 * wait ile synchronized blogunun kullandıgı monitor üstünde beklenir.
 * notify ile bu monitor üstünde bekleyen tek bir thread uyandırılır ve ilgili thread kaldıgı yerden çalışmaya hazır hale gelir.
 * notifyAll ile ilgili monitor üstünde bekleyen tüm threadler uyandırılır.
 */


class Process {
    
    public void produce() throws InterruptedException {
        synchronized(this) {
            System.out.println("Running the produce method...");
            wait();
            System.out.println("Again in the produce method.");
        }
    }
    
    public void consume() throws InterruptedException {
        synchronized(this) {
            System.out.println("Consume method is executed...");
            notify(); // notify synchronized blogu bittikten sonra calisir.
            System.out.println("Waiting for the synchronized block to end.");
            Thread.sleep(5000);
        }
    }
    
}

public class WaitAndNotify {

 
    public static void main(String[] args) {
        Process process = new Process();
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.produce();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        t1.start();
        t2.start();
    }
    
}
