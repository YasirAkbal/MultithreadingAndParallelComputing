/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.classics.studentLibrary;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class Student implements Runnable {
    private int id;
    private Book[] books;
    private Random random;
    
    public Student(int id, Book[] books) {
        this.id = id;
        this.books = books;
        this.random = new Random();
    }

    @Override
    public void run() {
        while(true) {
            int bookId = random.nextInt(Constants.NUM_OF_BOOKS);  
            
            try {
                books[bookId].read(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + '}';
    }
}
