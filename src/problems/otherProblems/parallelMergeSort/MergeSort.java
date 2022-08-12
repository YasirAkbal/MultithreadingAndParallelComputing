/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problems.otherProblems.parallelMergeSort;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class MergeSort {
    private int[] arr;
    
    public MergeSort(int[] arr) {
        this.arr = arr;
    }
    
    public void sort() {
        _sort(0,arr.length-1);
    }

    private void _sort(int left, int right) {
        if(left >= right) { return; }
        
        int mid = (left+right)/2;
        
        _sort(left,mid);
        _sort(mid+1,right);
        
        merge(left,mid,right);
    }
    
    private void merge(int left, int mid, int right) {
        int[] tempArr = new int[right-left+1];
        int i = left;
        int j = mid+1;
        int k = 0;
        
        while(i <= mid && j <= right) {
            if(arr[i] < arr[j]) {
                tempArr[k] = arr[i];
                i++;
            } else {
                tempArr[k] = arr[j];
                j++; 
            }
            k++;
        }
        
        for(;i<=mid;i++,k++) {
            tempArr[k] = arr[i];
        }
        
        for(;j<=right;j++,k++) {
            tempArr[k] = arr[j];
        }
        
        for(i=left;i<=right;i++) {
            arr[i] = tempArr[i-left];
        }
    }
    
    public void parallelSort(int numOfThreads) {
        _parallelMergeSort(0, arr.length-1, numOfThreads);
    }
    
    private void _parallelMergeSort(int left,int right, int numOfThreads) {
        if(numOfThreads <= 1) {
            _sort(left, right);
        } else {
            int mid = (left+right)/2;
            
            Thread leftSorter = createThread(left, mid, numOfThreads);
            Thread rightSorter = createThread(mid+1, right, numOfThreads);
            
            leftSorter.start();
            rightSorter.start();
            
            try {
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MergeSort.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            merge(left, mid, right);
        }
    }
    
    private Thread createThread(int left, int right, int numOfThreads) {
        return new Thread() {
            @Override
            public void run() {
                _parallelMergeSort(left, right, numOfThreads/2);
            }
        };
     }
}
