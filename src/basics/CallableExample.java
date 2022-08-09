/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasir
 * Runnable interfaceni kullanarak threadlere çalıştır unut metotlar tanımlarken Callable ile dönüş değeri olan hata...
 * fırlatabilen metotlar tanımlayabiliriz.
 */
public class CallableExample {
    
    static class Processor implements Callable<String> {
        private int id;
        
        public Processor(int id) {
            this.id = id;
        }
        
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "Id: " + id;
        }
        
    }
    
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        List<Future<String>> list = new ArrayList<>();
        
        for(int i=0;i<10;i++) {
            Future<String> future = service.submit(new Processor(i+1));
            list.add(future);
        }
        
        for(Future<String> f: list) {
            try {
                System.out.println(f.get()); //join metodunda oldugu gibi thread'i bekliyor. ayrıca call metodundan dönen değeri veriyor.
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(CallableExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        service.shutdown();
    }
    
}
