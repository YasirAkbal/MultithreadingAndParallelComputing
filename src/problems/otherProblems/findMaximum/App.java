/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.otherProblems.findMaximum;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long[] nums = createArray(30000000);
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(numOfThreads);
        ParallelMaximumTask task = new ParallelMaximumTask(nums,0,nums.length-1);
        
        long max = pool.invoke(task);
        System.out.println("Max = " + max);
    }
    
    
    private static long[] createArray(int n) {
        Random random = new Random();
        long[] arr = new long[n];
        
        for(int i=0;i<n;i++) {
            arr[i] = random.nextInt(1000000);
        }
        
        return arr;
    }
}
