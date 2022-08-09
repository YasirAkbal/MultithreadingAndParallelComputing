/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Java'da primitive veri yapılarını thread safe olarak kullanabileceğimiz Atomic(PrimitiveVeriYapısı) sınıflar barındırır.
 * Bu sınıflar ilgili primitive veri tipi üstünde yapabilecek olası işlemleri yapan metotlar barındırır. Bu işlemler thread safe olmasının yanında ayrıca volatile'in sağladığı...
 * faydaları da sağlayacak şekilde yapılır.
 */
public class AtomicIntegerExample {

    static AtomicInteger count = new AtomicInteger(0); //sadece count referansı değiştirilecekse volatile olarak tanımlanabilir. onun dışında gerek yok.
    
    static void increment() {
        for(int i=0;i<10000;i++)
            count.incrementAndGet();
    }
    
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(AtomicIntegerExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("count = " + count);
    }
    
}
