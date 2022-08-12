/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.otherProblems.parallelSum;

/**
 *
 * @author yasir
 */
public class ParallelSum {
    private ParallelSumWorker[] workers;
    private int numOfThreads;
    
    public ParallelSum(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        this.workers = new ParallelSumWorker[numOfThreads];
    }
    
    public int sum(int[] nums) {
        int size = (int)Math.ceil((double)nums.length/numOfThreads); //her bir alt dizinin boyutu
        
        for(int i=0;i<numOfThreads;i++) {
            workers[i] = new ParallelSumWorker(nums, i*size, (i+1)*size);
            workers[i].start();
        }
        
        try {
            for(int i=0;i<numOfThreads;i++) {
                workers[i].join();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        int total = 0;
        
        for(ParallelSumWorker worker: workers) {
            total += worker.getPartialSum();
        }
        
        return total;
    }
}
