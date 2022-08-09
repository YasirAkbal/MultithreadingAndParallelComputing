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
public class Deadlock {

    private static class Example {

        private int num1 = 5;
        private int num2 = 3;

        private ReentrantLock lock1 = new ReentrantLock();
        private ReentrantLock lock2 = new ReentrantLock();

        private void calculate1() {
            lock1.lock();
            System.out.println("Thread1 lock1'i lockladi.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Deadlock.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Thread1 lock2'yi locklayip kritik bolgeye girmeye calisiyor ama yapamayacak.");
            lock2.lock();
            num1++;
            System.out.println("Thread1-num1 = " + num1);

            num2 += 2;
            System.out.println("Thread1-num2 = " + num1);
            
            lock1.unlock();
            lock2.unlock();
        }

        private void calculate2() {
            lock2.lock();
            System.out.println("Thread2 lock2'yi lockladi.");
            System.out.println("Thread2 lock1'i locklayip kritik bolgeye girmeye calisiyor ama yapamayacak.");
            lock1.lock();
            
            num2 -= 2;
            System.out.println("Thread2-num2 = " + num1);

            num1--;
            System.out.println("Thread2-num1 = " + num1);
            
            lock1.unlock();
            lock2.unlock();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Example example = new Example();

        //Threadleri bu şekilde oluşturunca deadlock oluşmuyor. O yüzden Stream API ile oluşturdum.
        /*Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.calculate1();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                example.calculate2();
            }
        });

        thread1.run();
        thread2.run();*/
        
        new Thread(example::calculate1,"calculate1").start();
        new Thread(example::calculate2,"calculate2").start();
    }

}
