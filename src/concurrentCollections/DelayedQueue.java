/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrentCollections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 */
public class DelayedQueue {
    
    static class DelayedWorker implements Delayed {
        private long duration;
        private String message;

        public DelayedWorker(long duration, String message) {
            this.duration = duration + System.currentTimeMillis();
            this.message =  message;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(duration-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if(duration < ((DelayedWorker)o).duration)
                return -1;
            else if(duration > ((DelayedWorker)o).duration)
                return 1;
            else 
                return 0;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "DelayedWorker{" + "duration=" + duration + ", message=" + message + '}';
        }   
    }
    
    public static void main(String[] args) {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();
        
        try {
            queue.put(new DelayedWorker(2000,"This is the first message.."));
            queue.put(new DelayedWorker(10000,"This is the second message.."));
            queue.put(new DelayedWorker(1000,"This is the third message.."));
            queue.put(new DelayedWorker(4500,"This is the fourth message.."));
        } catch (InterruptedException ex) {
            Logger.getLogger(DelayedQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(!queue.isEmpty()) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(DelayedQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
