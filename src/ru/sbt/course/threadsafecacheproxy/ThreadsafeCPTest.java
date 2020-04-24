package ru.sbt.course.threadsafecacheproxy;

/**
 * Задание по 14 лекции СБТ. Java util concurrent.
 *
 * @author Hin
 * @version 1.0 24/04/2020
 */

public class ThreadsafeCPTest {

    public static void main(String[] args) {

        System.out.println("Тест конкурентного прокси класса");
        System.out.println("Не должно быть больше 3-х прямых вызовов");

        Service srv = new ServiceImpl();
        ThreadsafeCacheProxy cacheProxy = new ThreadsafeCacheProxy("C:\\Temp");
        Service service = cacheProxy.cache(srv);

        for (int i = 0; i < 5; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Work1 3.0" + " = " + service.doWork1(3.0) + " из " +
                            Thread.currentThread().getName());
                    System.out.println("Work1 2.0" + " = " + service.doWork1(2.0) + " из " +
                            Thread.currentThread().getName());
                    System.out.println("Work1 4.0" + " = " + service.doWork1(4.0) + " из " +
                            Thread.currentThread().getName());
                }
            }).start();

    }
}
