/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.otherProblems.findMaximum;

import java.util.concurrent.RecursiveTask;

/**
 *
 * @author yasir
 */
public class ParallelMaximumTask extends RecursiveTask<Long> {

    private long[] nums;
    private int lowIndex;
    private int highIndex;

    public ParallelMaximumTask(long[] nums, int lowIndex, int highIndex) {
        this.nums = nums;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
    }

    @Override
    protected Long compute() {
        if (highIndex - lowIndex < nums.length*0.05) {
            return sequentialMaxFinding();
        }

        int midIndex = (lowIndex + highIndex) / 2;

        ParallelMaximumTask leftTask = new ParallelMaximumTask(nums, lowIndex, midIndex);
        ParallelMaximumTask rightTask = new ParallelMaximumTask(nums, midIndex + 1, highIndex);

        invokeAll(leftTask, rightTask);

        return Math.max(leftTask.join(), rightTask.join());
    }

    private Long sequentialMaxFinding() {
        long max = Long.MIN_VALUE;

        for (int i = lowIndex; i <= highIndex; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        return max;
    }
}
