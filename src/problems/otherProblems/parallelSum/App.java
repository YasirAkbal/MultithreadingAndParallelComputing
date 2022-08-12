/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.otherProblems.parallelSum;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] nums = {3,5,1,-1,6,5};
        int n = Runtime.getRuntime().availableProcessors();
        
        ParallelSum sum = new ParallelSum(n);
        System.out.println("Sum = " + sum.sum(nums));
    }
    
}
