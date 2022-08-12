/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.otherProblems.parallelSum;

/**
 *
 * @author yasir
 */
public class ParallelSumWorker extends Thread {
    private int[] numbers;
    private int left;
    private int right;
    private int partialSum;

    public ParallelSumWorker(int[] numbers, int left, int right) {
        this.numbers = numbers;
        this.left = left;
        this.right = Math.min(numbers.length, right);
    }
    
    @Override
    public void run() {
        for(int i=left;i<right;i++)
            partialSum += numbers[i];
    }

    public int getPartialSum() {
        return partialSum;
    }
}
