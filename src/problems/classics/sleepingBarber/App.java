/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.sleepingBarber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BarberShop barberShop = new BarberShop(Constants.NUM_OF_SEATS);
        Barber barber = new Barber(1, barberShop); // daha güzel bir mimari kurulabilirdi
        Customer[] customers = new Customer[Constants.NUM_OF_CUSTOMERS];
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        for(int i=0;i<Constants.NUM_OF_CUSTOMERS;i++)
            customers[i] = new Customer(i, barberShop);
        
        executor.submit(barber);
        for(int i=0;i<Constants.NUM_OF_CUSTOMERS;i++)
            executor.submit(customers[i]);
        
        executor.shutdown();      
    }
    
}
