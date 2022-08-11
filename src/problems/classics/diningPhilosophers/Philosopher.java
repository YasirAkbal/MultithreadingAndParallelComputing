/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.diningPhilosophers;

import java.util.Random;

/**
 *
 * @author yasir
 */
public class Philosopher implements Runnable {
    private int id;
    private volatile boolean full;
    private ChopStick leftChopStick;
    private ChopStick rightChopStick;
    private Random random;
    private int eatingCounter;

    public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick) {
        this.id = id;
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
        this.random = new Random();
    }
 
    @Override
    public void run() {
        try {
            while(!full) {
                think();
                
                if(leftChopStick.pickUp(this, State.LEFT)) {
                    if(rightChopStick.pickUp(this, State.RIGHT)) {
                        eat();
                        rightChopStick.putDown(this);
                        leftChopStick.putDown(this);
                    } else {
                        leftChopStick.putDown(this);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(this + " is eating...");
        synchronized(this) {
            eatingCounter++;
        }
        Thread.sleep(random.nextInt(1000));
    }
    
    @Override
    public String toString() {
        return "Philosopher{" + "id=" + id + ", eatingCounter=" + eatingCounter + '}';
    }
    
    public void setFull(boolean full) {
        this.full = full;
    }
 
    public boolean isFull() {
        return this.full;
    }

    public int getEatingCounter() {
        return eatingCounter;
    }
    
}
