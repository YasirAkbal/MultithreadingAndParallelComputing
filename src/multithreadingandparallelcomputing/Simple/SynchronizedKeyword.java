/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package multithreadingandparallelcomputing.Simple;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class SynchronizedKeyword {

    static int counter = 0;
    static final int numOfThreads = 4;
    
    static synchronized void increment() {
        counter++;
    }
    
    /*
    Threadlerin ortak eriştiği veriler üzerinde değişiklik yapıldığında inconsistent veriler ortaya çıkabilir.
    Burada "counter++" işlemi atomik bir işlem olarak görülse de makina tarafında birden fazla adımda gerçekleştirilir. Bu birden fazla adımların tamamı bitmeden...
    bir diğer thread de bu veriye erişirse inconsistent durumlar ortaya çıkabilir. Bu örnekte inconsistent verinin oluşması engellenmemiştir. 
    */
    static void inconsistent() {
        Thread[] threads = new Thread[numOfThreads];
        
        for(int i=0;i<numOfThreads;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000;i++)
                        counter++;
                }
            });
        }

        for(int i=0;i<numOfThreads;i++)
            threads[i].start();
        
        try {
            for(int i=0;i<numOfThreads;i++)
                threads[i].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedKeyword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Counter = " + counter);
    }
    
    /*
    Inconsitent veri durumunu engellemek için bu yöntemde threadler arasında ortak paylaşılan veriler üzerindeki işlemleri bir metota taşıyoruz.
    Bu metotu "synchronized" olarak işaretlediğimizde Java bize bu metodu aynı anda tek bir thread'in çalıştırabileceğini garanti ediyor.
    */
    static void consistent() {
        Thread[] threads = new Thread[numOfThreads];
        
        for(int i=0;i<numOfThreads;i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000;i++)
                        increment();
                }
            });
        }

        for(int i=0;i<numOfThreads;i++)
            threads[i].start();
        
        try {
            for(int i=0;i<numOfThreads;i++)
                threads[i].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SynchronizedKeyword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Counter = " + counter);
    }
    
    public static void main(String[] args) {
        inconsistent();
        counter = 0;
        consistent();
    }
    
}
