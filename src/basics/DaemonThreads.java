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
 * Daemon threadler düşük öncelikli, tüm worker threadler çalışmasını bitirdikten(main de dahil) sonra JVM tarafından otomatik olarak yok edilen threadlerdir
 * Java Garbage Collector daemon thread'e bir örnektir. 
 */


class DaemonWorker implements Runnable {
    
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DaemonWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Daemon Thread is running...");
        }
    }
    
}

class NormalWorker implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NormalWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Normal Thread finished execution.");
    }
    
}

public class DaemonThreads {

    public static void main(String[] args) {
        Thread daemon = new Thread(new DaemonWorker());
        Thread normal = new Thread(new NormalWorker());
        
        daemon.setDaemon(true);
        
        daemon.start();
        normal.start();

    }
    
}
