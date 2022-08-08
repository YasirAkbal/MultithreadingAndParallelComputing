/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Threadler Runnable interface'ini implement eden bir sınıfla, anonymous bir sınıf ile veya Thread sınıfını extend eden bir başka sınıf ile oluşturulabilir.
 */

//Yöntem 1
class Runner1 implements Runnable {

    @Override
    public void run() {
        for(int i=0;i<10;i++)
            System.out.println("Runner1: " + i);
    }
    
}


//Yöntem 2
class Runner2 extends Thread {
    @Override
    public void run() {
        for(int i=0;i<10;i++)
            System.out.println("Runner2: " + i);
    }
}


public class ThreadCreation {
    
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Runner2();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++)
                    System.out.println("Runner3: " + i);
            }   
        });
        
        t1.start();
        t2.start();
        t3.start();
        
        //Bir thread'in calismasini tamamlamaini beklemek icin join metodu kullanılır.
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("All threads end.");
    }
    
}
