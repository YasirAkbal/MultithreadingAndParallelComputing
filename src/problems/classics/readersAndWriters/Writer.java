/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.readersAndWriters;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class Writer implements Runnable {

    private int id;
    private Database database;

    public Writer(int id, Database database) {
        this.id = id;
        this.database = database;
    }

    @Override
    public void run() {
        while(true) {
            database.write(id);
            try {
                Thread.sleep((long)(Math.random()*2000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "Writer{" + "id=" + id + '}';
    }
}
