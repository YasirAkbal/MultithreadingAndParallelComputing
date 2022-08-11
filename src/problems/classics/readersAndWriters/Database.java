/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.readersAndWriters;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public final class Database {

    private volatile int readersCount;
    private Lock transitionLock; //readerlerin, writerlara öncelik kurmaması için yapılacak optimizasyon için
    private Random random;
    private Lock readerLock;
    private Semaphore writerSem; //threadler arasında paylasilmasi gereken ve farkli threadlerin lock&unlock yapması gereken bir mutual exclusion yapısı oldugu için lock yerine semaphore kullandım

    private static volatile Database INSTANCE;

    private Database() {
        this.readersCount = 0;
        this.random = new Random();
        this.writerSem = new Semaphore(1);
        this.readerLock = new ReentrantLock();
        this.transitionLock = new ReentrantLock();
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Database();
                }
            }
        }

        return INSTANCE;
    }

    public void read(int readerId) {
        try {
            transitionLock.lock();
            transitionLock.unlock();
            
            readerLock.lock();
            readersCount++;
            if (readersCount == 1) {
                try {
                    writerSem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            readerLock.unlock();
        }

        
        System.out.println("Reader " + readerId + " starts reading...");
        try {
            Thread.sleep(random.nextInt(200));
        } catch (InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Reader " + readerId + " stops reading.");
        
        
        try {
            readerLock.lock();
            readersCount--;
            if (readersCount == 0) {
                writerSem.release();
            }
        } finally {
            readerLock.unlock();
        }
    }

    public void write(int writerId) {
        try {
            try {
                //ön işlemler burada yapılır
                transitionLock.lock();
                writerSem.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            System.out.println("Writer " + writerId + " starts writing...");
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Writer " + writerId + " stops writing.");
            
            
        } finally {
            transitionLock.unlock();
            writerSem.release();
        }
    }
}
