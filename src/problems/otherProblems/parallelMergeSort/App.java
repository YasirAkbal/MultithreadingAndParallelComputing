/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.otherProblems.parallelMergeSort;

import java.util.Random;

/**
 *
 * @author yasir
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        
        int[] numbers = createArray(100000000);
        int[] numbersTemp = numbers.clone();
        
        MergeSort parallelMergeSort = new MergeSort(numbers);
        
        long startTimeParallel = System.currentTimeMillis();
        parallelMergeSort.parallelSort(numOfThreads);
        long endTimeParallel = System.currentTimeMillis();
        
        System.out.println("Parallel Merge Sort runtime = " + (endTimeParallel-startTimeParallel));
        
        
        MergeSort sequentialMergeSort = new MergeSort(numbersTemp);
        
        long startTimeSequential = System.currentTimeMillis();
        sequentialMergeSort.sort();
        long endTimeSequential = System.currentTimeMillis();
        
        System.out.println("Sequential Merge Sort runtime = " + (endTimeSequential-startTimeSequential));
    }
    
    private static int[] createArray(int n) {
        Random random = new Random();
        int[] arr = new int[n];
        
        for(int i=0;i<n;i++) {
            arr[i] = random.nextInt(1000);
        }
        
        return arr;
    }
}
