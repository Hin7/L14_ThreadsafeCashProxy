package ru.sbt.course.Atomic;
/**
 * Пример с использованием AtomicInteger. д/з по лекции 14 СБТ (Java util concurent) от Еськова Андрея
 * 02/06/2020 HIN
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AnotherAtomic {

    static volatile AtomicInteger flag = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                int value = flag.getAndIncrement();
                if (value % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + " true");
                    System.out.println(Thread.currentThread().getName() + " value = " + value);
                } else {
                    System.out.println(Thread.currentThread().getName() + " false");
                    System.out.println(Thread.currentThread().getName() + " value = " + value);
                }
            });
        }
        executorService.shutdown();
    }
}
