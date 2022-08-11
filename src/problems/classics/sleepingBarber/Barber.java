/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.sleepingBarber;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class Barber implements Runnable {
    private int id;
    private BarberShop barberShop;

    public Barber(int id, BarberShop barberShop) {
        this.id = id;
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        try {
            barberShop.barberProcess(this);
            Thread.sleep((long) (Math.random()*2000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Barber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void cutHair() {
        System.out.println("Barber " + id + " is cutting hair...");
    }
}
