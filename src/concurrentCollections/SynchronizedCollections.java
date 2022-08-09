/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Java collectionlarının büyük bölümü default olarak thread-safe değildir.
 * Thread-safe koleksiyonlarla çalışmak için "java.util.concurrent" altındaki koleksiyonları kullanabilir veya...
 * Collections sınıfı üstündeki static metotlar ile thread-safe koleksiyonlar oluşturabiliriz.
 * Collections.synchronizedXXX metodu ile oluşturulan koleksiyonlar "java.util.concurrent" altındaki thread-safe koleksiyonlara...
 * göre daha az performanslıdır.
 */
public class SynchronizedCollections {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Integer> numbers = Collections.synchronizedList(new ArrayList<Integer>());
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000;i++)
                    numbers.add(i);
            }  
        });
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000;i++)
                    numbers.add(i);
            }  
        });
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedCollections.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
