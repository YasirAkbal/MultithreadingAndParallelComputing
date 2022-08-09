/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Yine Producer-Consumerin modifiyeli hali ama bu sefer semaforlar ile çözdüm.
 */
class Processor3 {

    private int value;
    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5; //count
    private static final int LOWER_LIMIT = 0;
    Semaphore exclusion = new Semaphore(1);
    Semaphore producersWaiting = new Semaphore(0);
    Semaphore consumersWaiting = new Semaphore(1);

    public void producer() {
        while (true) {
            try {
                consumersWaiting.acquire();
                exclusion.acquire();

                value++;
                list.add(value);
                System.out.println("Eklendi: " + value);
            } catch (InterruptedException ex) {
                Logger.getLogger(Processor3.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (list.size() == UPPER_LIMIT) {
                    producersWaiting.release();
                } else {
                    consumersWaiting.release();
                }
                
                exclusion.release();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void consumer() {
        while (true) {
            try {
                producersWaiting.acquire();
                exclusion.acquire();
                    
                int deleted = list.remove(list.size() - 1);
                System.out.println("Silindi: " + deleted);
            } catch (InterruptedException ex) {
                Logger.getLogger(Processor3.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (list.size() == LOWER_LIMIT) {
                    value = 0;
                    consumersWaiting.release();
                } else {
                    producersWaiting.release();
                }
                
                exclusion.release();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

public class ProducerAndConsumer3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Processor3 processor = new Processor3();

        new Thread(processor::producer, "producer").start();
        new Thread(processor::consumer, "consumer").start();

    }

}
