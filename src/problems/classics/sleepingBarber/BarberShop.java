/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.sleepingBarber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yasir
 */
public class BarberShop {

    private volatile int waitersCount;
    private final int numberOfSeats;
    private Lock mutex;
    private Semaphore customerSem;
    private Semaphore barberSem;

    public BarberShop(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        this.waitersCount = 0;
        this.mutex = new ReentrantLock();
        this.customerSem = new Semaphore(0);
        this.barberSem = new Semaphore(0);
    }

    public void barberProcess(Barber barber) throws InterruptedException {
        while (true) {
            customerSem.acquire();
            mutex.lock();
            waitersCount--;
            barberSem.release();
            mutex.unlock();
            barber.cutHair();
        }
    }

    public void customerProcess(Customer customer) throws InterruptedException {
        mutex.lock();
        if (waitersCount < numberOfSeats) {
            waitersCount++;
            customerSem.release();
            mutex.unlock();
            barberSem.acquire();
            customer.cutYourHair();
        } else {
            mutex.unlock();
        }
    }

}
