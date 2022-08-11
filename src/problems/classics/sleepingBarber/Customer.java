/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.sleepingBarber;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

/**
 *
 * @author yasir
 */
public class Customer implements Runnable {

    private int id;
    private BarberShop barberShop;

    public Customer(int id, BarberShop barberShop) {
        this.id = id;
        this.barberShop = barberShop;
    }

    void cutYourHair() {
        System.out.println("I'm customer " + id + ". He's cutting my hair.");
    }

    @Override
    public void run() {
        try {
            barberShop.customerProcess(this);
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    
}
