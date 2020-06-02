package ru.sbt.course.Atomic;

/**
 * Подправил пример с Atomic от Еськова Андрея в качестве д/з по лекции 14 СБТ (Java util concurent)
 * 02/06/2020 HIN
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Atomic {
    static volatile AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) {
        //Object mutex = new Object();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
               // synchronized (mutex) {
                if (flag.compareAndSet(true, false)) { //пробуем переключить в false, предполагая, что был true
                    System.out.println(Thread.currentThread().getName() + " true");
                    //int get = flag.getAndDecrement();
                    //System.out.println(Thread.currentThread().getName() + "value = " + get);
                } else if (flag.compareAndSet(false, true)){ //пробуем наоборот
                    System.out.println(Thread.currentThread().getName() + " false");
                    //System.out.println(Thread.currentThread().getName() + "value = " + get);
                } else {
                    System.out.println(Thread.currentThread().getName() + " true");
                }
                //               }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // do nothing
//                }
            });
        }
        executorService.shutdown();
    }
}
