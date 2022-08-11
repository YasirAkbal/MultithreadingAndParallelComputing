/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.diningPhilosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yasir
 */
public class ChopStick {
    
    private int id;
    private Lock lock;
    
    public ChopStick(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }
    
    public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {
        /*
        Trylock kullanılarak deadlock durumunun oluşması engellemeye calıştık fakat filozoflar sürekli üst üste düşünme durumuna giriyorlar. Oysaki wait yapısı kullanılarak..
        threadler beklemeye alınsaydı threadler boş yere sürekli çalışmak zorunda kalmazdı. Fakat o zaman da deadlock'un engellenmesi gerekecekti.
        */
        if(lock.tryLock(10, TimeUnit.MILLISECONDS)) { 
            System.out.println(philosopher + " picked up " + this);
            return true;
        }
        
        return false;
    }
    
    public void putDown(Philosopher philosopher) {
        lock.unlock();
        System.out.println(philosopher + " put down " + this);
    }

    @Override
    public String toString() {
        return "ChopStick{" + "id=" + id + '}';
    }
}
