/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.producersAndConsumers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * synchronized&custom locklar ile
 * Üretici-tüketici problemi senaryosu(synchronized keyword ile):
 * Üretici belirlenen üst limit kadar ürünü ürütmeden tüketici ürün tüketmeyecek.
 * Üretici ürünleri ardışık olarak üretecek.
 * Tüketici tüm ürünleri ardışık olarak tüketecek.(ürün sayısı alt limite ulaşına kadar üretici yeni ürün üretmeyecek)
 */
class Processor {

    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5; //count
    private static final int LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void producer() throws InterruptedException {
        /*
        Onemli not: synchronized blogunu while dongusunu kapsayacak sekilde acinca istenilen senaryo tam olarak saglaniyor. Cunku synchronized blogu icindeki sonsuz dongu yuzunden...
        synchronized blogu icindeki data sadece wait ile baska threadler icin kullanilabilir oluyor. Ote yandan karsilikli dislamalar olabildigince kucuk kaynaklari kapsayacak sekilde..
        yapilmali. Bu problemde bu yaklasim sıkıntı çıkarmasa da bu durum göz önünde bulundurulmalı. ProducerAndConsumer2'de ReentratLock ve while dongusunu lock icine almadan cozdum.
        */
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                }
                
                Thread.sleep(100);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for adding items...");
                    lock.wait();
                } else {
                    int deleted = list.remove(list.size() - 1);
                    System.out.println("Deleted = " + deleted);
                    lock.notify(); //burada notify dememiz sadece bu thread wait ile askiya alindiginde anlam kazanacak.(yine synchronized blogunun donguyu kapsamasindan dolayi.)
                    //kısacası bu thread wait ile askıya alına kadar producer threadi çalışamaz.
                }
                
                Thread.sleep(100);
            }
        }
    }

}

public class ProducerAndConsumer1 {

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
