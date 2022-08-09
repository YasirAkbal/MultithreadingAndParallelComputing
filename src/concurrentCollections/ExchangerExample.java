/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Threadler arasında aynı tipte 2 farklı veri swaplenebilir. Bir thread veriyi swaplemek istediğinde o thread diğer thread...
 * aktif hale gelip o da swap yapmak isteyene kadar beklemede kalır.
 */
public class ExchangerExample {
   static class FirstThread implements Runnable {
        private int counter;
        private Exchanger<Integer> exchanger;

        public FirstThread(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }
        
        
        @Override
        public void run() {
            while(true)  {
                counter++;
                System.out.println("FirstThread incremented the counter: " + counter);
                
                try {
                    counter = exchanger.exchange(counter);
                    System.out.println("FirstThread get the counter: " + counter);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExchangerExample.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExchangerExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
    }
   
   static class SecondThread implements Runnable {
        private int counter;
        private Exchanger<Integer> exchanger;

        public SecondThread(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }
        
        
        @Override
        public void run() {
            while(true)  {
                counter--;
                System.out.println("SecondThread decremented the counter: " + counter);
                
                try {
                    counter = exchanger.exchange(counter);
                    System.out.println("SecondThread get the counter: " + counter);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExchangerExample.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExchangerExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
    }
   
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();
        
        FirstThread first = new FirstThread(exchanger);
        SecondThread second = new SecondThread(exchanger);
        
        new Thread(first).start();
        new Thread(second).start();
    }
    
}
