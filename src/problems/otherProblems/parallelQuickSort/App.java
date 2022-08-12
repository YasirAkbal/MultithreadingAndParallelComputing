/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problems.otherProblems.parallelQuickSort;

import java.util.Random;
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
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        
        int[] numbers = createArray(100000000);
        int[] numbersTemp = numbers.clone();
        
        QuickSort parallelQuickSort = new QuickSort(numbers);
        
        long startTimeParallel = System.currentTimeMillis();
        parallelQuickSort.parallelSort(numOfThreads);
        long endTimeParallel = System.currentTimeMillis();
        
        System.out.println("Parallel Quick Sort runtime = " + (endTimeParallel-startTimeParallel) + ", isSorted = " + isSorted(numbers));
        
        
        QuickSort sequentialQuickSort = new QuickSort(numbersTemp);
        
        long startTimeSequential = System.currentTimeMillis();
        sequentialQuickSort.sort();
        long endTimeSequential = System.currentTimeMillis();
        
        System.out.println("Sequential Quick Sort runtime = " + (endTimeSequential-startTimeSequential) + ", isSorted = " + isSorted(numbersTemp));
    }

    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static int[] createArray(int n) {
        Random random = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(1000);
        }

        return arr;
    }
    
    private static boolean isSorted(int[] arr) {
        for(int i=0;i<arr.length-1;i++) {
            if(arr[i] > arr[i+1])
                return false;
        }
        
        return true;
    }
}
