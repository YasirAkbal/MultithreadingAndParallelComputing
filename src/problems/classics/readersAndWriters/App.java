/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.readersAndWriters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Reader[] readers = null;
        Writer[] writers = null;
        ExecutorService executorReader = Executors.newFixedThreadPool(Constants.NUM_OF_READERS);
        ExecutorService executorWriter = Executors.newFixedThreadPool(Constants.NUM_OF_WRITERS);
        Database database = Database.getInstance();

        try {
            readers = new Reader[Constants.NUM_OF_READERS];
            writers = new Writer[Constants.NUM_OF_WRITERS];
            
            for (int i = 0; i < Constants.NUM_OF_WRITERS; i++) {
                writers[i] = new Writer(i, database);
                executorWriter.execute(writers[i]);
            }
            
            for (int i = 0; i < Constants.NUM_OF_READERS; i++) {
                readers[i] = new Reader(i, database);
                executorReader.execute(readers[i]);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorWriter.shutdown();
            executorReader.shutdown();
        }
    }

}
