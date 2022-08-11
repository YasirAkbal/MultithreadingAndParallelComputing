/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.classics.studentLibrary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Student[] students = null;
        Book[] books = null;
        ExecutorService executor = Executors.newFixedThreadPool(Constants.NUM_OF_STUDENTS);
        
        try {
            books = new Book[Constants.NUM_OF_BOOKS];
            students = new Student[Constants.NUM_OF_STUDENTS];
            
            for(int i=0;i<Constants.NUM_OF_BOOKS;i++)
                books[i] = new Book(i);
            
            for(int i=0;i<Constants.NUM_OF_STUDENTS;i++) {
                students[i] = new Student(i, books);
                executor.execute(students[i]);
            }       
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
    
}
