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
 * "synchronized" keywordu metot tanımında kullanıldıgında o metot içindeki tüm işlemlerin consistent yapar. Metot başlarken bir lock(burada monitor yapısı kullanılıyor)
 * aktif olur metot bittiğinde ise salınır. Lock salındıktan sonra bu lock üstünde bekleyen diğer threadlere haber verilir. Senkronizasyonu sağlayan bu monitorler class bazında...
 * veya object bazında tanımlanabilir. Class bazında bir monitor ile "synchronized" olarak işaretlenen ilgili metodun yada metottaki kod parçacağının aynı anda sadece tek 1...
 * thread tarafından çalıştırılacağını garanti eder. Object bazında semaforlar ise sınıfa ait örneklerin o örneğe ait static olmayan bir metodu aynı anda 1 thread'in çalıştırabileceğini
 * garanti eder. Bu java dosyasındaki örnekte increment metotları statik olarak tanımladığı için "synchronized" keywordü class bazında bir monitor oluşturacaktır. Dolayısıyla...
 * "increment1" ve "increment2" metotları class bazındaki bu ortak monitoru kullandıkları için aynı anda çalışamazlar. Bu örnekte oldugu gibi bu metotlardaki işlemler bağımsız,
 * inconsistent veri oluşturmayacak işlemler ise bunların birbirini dışlaması anlamsızdır ve performansı düşürür. Bu yüzden farklı metot ve/veya metot parçaları için
 * "custom objeler" kullanarak farklı mutal exclusion durumlarını sağlarız.
 * Not: increment metotları statik oldugu için class bazında monitorlerdir.
 */
public class ProblemWithSynchronizedKeyword {
    static int counter1 = 0;
    static int counter2 = 0;
    static final int numOfThreads = 4;
    static final int max = 9999999;
    
    static synchronized void increment1() {
        counter1++;
    }
    
    static synchronized void increment2() {
        counter2++;
    }
    
    static void nonOptimalSolution() {
        Thread[] threads1 = new Thread[numOfThreads];
        Thread[] threads2 = new Thread[numOfThreads];
        
        for(int i=0;i<numOfThreads;i++) {
            threads1[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<max;i++) {
                        increment1();
                    }
                        
                }
            });
            threads2[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<max;i++) {
                        increment2();
                    }
                        
                }
            });
        }

        for(int i=0;i<numOfThreads;i++) {
            threads1[i].start();
            threads2[i].start();
        }
            
        
        try {
            for(int i=0;i<numOfThreads;i++) {
                threads1[i].join();
                threads2[i].join();
            }
                
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedKeyword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Counter1 = " + counter1);
        System.out.println("Counter2 = " + counter2);
    }
    
    
    /*
    2 farklı increment metotu için 2 farklı object örneği oluşturarak bunları lock için kullanıyorum.
    */
    static final Object lock1 = new Object();
    static final  Object lock2 = new Object();
    
    static void increment1Custom() {
        synchronized(lock1) { //Not: sadece blok içindeki işlemler için mutual exclusion uygulanıyor.
            counter1++;
        }
    }
    
    static void increment2Custom() {
        synchronized(lock2) { 
            counter2++;
        }
    }
    
    static void optimalSolution() {
        Thread[] threads1 = new Thread[numOfThreads];
        Thread[] threads2 = new Thread[numOfThreads];
        
        for(int i=0;i<numOfThreads;i++) {
            threads1[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<max;i++) {
                        increment1Custom();
                    }
                        
                }
            });
            threads2[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<max;i++) {
                        increment2Custom();
                    }
                        
                }
            });
        }

        for(int i=0;i<numOfThreads;i++) {
            threads1[i].start();
            threads2[i].start();
        }
            
        
        try {
            for(int i=0;i<numOfThreads;i++) {
                threads1[i].join();
                threads2[i].join();
            }
                
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedKeyword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Counter1 = " + counter1);
        System.out.println("Counter2 = " + counter2);
    }
    
    public static void main(String[] args) {
        long startTime, endTime;
        
        startTime = System.nanoTime();
        optimalSolution();
        endTime = System.nanoTime();
        
        System.out.println("Custom object level locking = " + (endTime - startTime));
        
        counter1 = 0;
        counter2 = 0;
        
        startTime = System.nanoTime();
        nonOptimalSolution();
        endTime = System.nanoTime();
        
        System.out.println("Class level locking = " + (endTime - startTime));
        
    }
    
}
