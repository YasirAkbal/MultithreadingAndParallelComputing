/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.studentLibrary;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yasir
 */
public class Book {

    private int id;
    private Lock lock;

    public Book(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true); //thread starvation'i engeller. defaultda da true.
    }

    public void read(Student student) throws InterruptedException {
        /*bu çözümde bekleme yapmaz. öğrenciler sürekli tekrar tekrar kitap okumayı deniyor. Lock kullanıldığında ise bekleme kuyruğuna atılıyorlar ve diğer threadler "unlock" yaparak...
        kritik bölgeyi ulaşılabilir yaptığında kitabı okuyorlar. "tryLock" yerine "lock" kullanmak daha performanslı olabilir.
        */
        /*if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(student + " starts reading " + this);
            Thread.sleep(1000);
            lock.unlock();
            System.out.println(student + " has just finished reading " + this);
        } else {
            System.out.println(student + " tried to read " + this);
        }*/

        try {
            lock.lock();
            System.out.println(student + " starts reading " + this);
            Thread.sleep(1000);
            System.out.println(student + " has just finished reading " + this);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + '}';
    }
}
