/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author yasir
 * Thread-safe olmasının yanında Üretici-Tüketici problemininin çözümünde oldugu gibi seknronize çalışırlar:
 * Queue'da hic eleman yoksa veya belirlenen üst limit kadar eleman varsa bir işlem yapılmaz threadler ilgili işlemin(add, put vb.)...
 * çağrıldığı satırda beklerler. Yeni eleman eklendiğinde veya silindiği çalışmaya devam ederler. (Blocking-Queue interface'ini uygulayan..
 * diğer sınıflar da thread-safe ve senkronize bir şekilde çalışır.)
 */
public class ArrayBlockingQueueExample {
    static class FirstWorker implements Runnable {
        private BlockingQueue<Integer> queue;
        
        public FirstWorker(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int counter = 0;
            
            while(true) {
                try {
                    queue.put(counter);
                    System.out.println("Putting item into the queue... " + counter);
                    counter++;
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    
    static class SecondWorker implements Runnable {
        private BlockingQueue<Integer> queue;
        
        public SecondWorker(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    int counter = queue.take();
                    System.out.println("Taking  item from the queue... " + counter);
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } 
    }
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        
        FirstWorker firstWorker = new FirstWorker(queue);
        SecondWorker secondWorker = new SecondWorker(queue);
        
        new Thread(firstWorker).start();
        new Thread(secondWorker).start();
    }
    
}
