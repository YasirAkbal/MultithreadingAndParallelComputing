/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.otherProblems.parallelQuickSort;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class QuickSort {

    private int[] arr;

    public QuickSort(int[] arr) {
        this.arr = arr;
    }

    public void sort() {
        _sort(0, arr.length - 1);
    }

    private void _sort(int left, int right) {
        if (left >= right) {
            return;
        }

        int partitionIndex = partition(left, right);

        _sort(left, partitionIndex);
        _sort(partitionIndex + 1, right);
    }

    public void parallelSort(int numOfThreads) {
        _parallelSort(0, arr.length - 1, numOfThreads);
    }

    private void _parallelSort(int left, int right, int numOfThreads) {
        if (numOfThreads <= 1) {
            _sort(left, right);
        } else {
            if (left >= right) {
                return;
            }

            int partitionIndex = partition(left, right);

            Thread leftSorter = createThread(left, partitionIndex, numOfThreads);
            Thread rightSorter = createThread(partitionIndex + 1, right, numOfThreads);

            leftSorter.start();
            rightSorter.start();

            try {
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int partition(int left, int right) {
        int pivot = arr[left];
        int i = left - 1, j = right + 1;

        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);

            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j) {
                return j;
            }

            swap(arr, i, j);
        }
    }

    private Thread createThread(int left, int right, int numOfThreads) {
        return new Thread() {
            @Override
            public void run() {
                _parallelSort(left, right, numOfThreads / 2);
            }
        };
    }
    
    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
