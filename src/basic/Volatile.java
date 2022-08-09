/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basic;

/**
 *
 * @author yasir
 * Threadler işlemci çekirdekleri üstünde çalışır ve kendilerine ait core'ları vardır. Bu corelar cache mekanizmasını da içerir. Threadler performans kaygısından dolayı direkt olarak...
 * RAM üstünde çalışmak yerine cacheleri kullanmayı tercih ederler. Bir değişkenin değeri üzerinde değişiklik yapıldığında bu değişiklik cache üzerinde yapılır. Fakat bu değişikliğin...
 * RAM'e ne zaman uygulanacağı belli değildir. İşlemciler yine performans kaygılarından dolayı programdaki satırların beklenen sıradan farklı olarak çalıştıracak şekilde optimizasyonlar...
 * yapabilir. Bu sebeplerden dolayı multithreading kullanılan programlarda beklenmeyen sorunlar ortaya çıkabilir. Bu örnekte programın çok kısa bir beklemeden sonra mainde oluşturulan...
 * yeni threadin ve main threadin sonlanmasıyla bitmesi beklenir. Fakat bahsedilen sebeplerden dolayı bu olay: daha uzun bir beklemeden sonra gerçekleşebilir, hiç gerçekleşmeyebilir,..
 * println fonksiyonu beklenen 42 değeri yerine default integer(0) değerini ekrana yazabilir(reorderingden kaynaklı).
 * Bu tür sorunları yaşamamak için değişkenleri "volatile" keywordu ile tanımlarız. Volatile keywordu kullanıldığında bir threadde bir değişken üzerinde değişiklik yapıldığında başka bir...
 * thread'in bu değişkeni okuduğu anda yapılan bir değişikliğin uygulanmış olduğu garanti edilir.
 */
public class Volatile {
    
    //volatile kullanılmalı. Bu örnekte sadece ready için kullanmak da yeterli olacaktır. Her program için farklı optimizasyon gerekir.
    private static int number; 
    private static boolean ready;

    private static class Reader extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }

            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Reader().start();
        number = 42;
        ready = true;
    }
}
