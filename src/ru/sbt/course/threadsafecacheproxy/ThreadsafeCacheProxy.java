package ru.sbt.course.threadsafecacheproxy;
/**
 * CacheProxy - кэширующий прокси.Размер массива для кэширования входных значений 3 для кажного метода.
 * Реагирует при создании на параметры, установленные в аннотации Cache.
 * Задание по уроку 9 Сбт. Serialization.
 * <p>
 * ThreadsafeCacheProxy - модифифцированная потокобезопасная версия.
 * Задание по уроку 14 СБТ. Java util concurrent
 *
 * @author - Hin7
 */

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadsafeCacheProxy implements InvocationHandler {
    private Object delegate;
    private String cachDir;
    Map<String, MethodCach> cacheMap;

    public ThreadsafeCacheProxy(String cachDir) {
        this.cachDir = cachDir;
        File dir = new File(cachDir);

        if (!dir.exists()) {
            System.out.println("Директория " + cachDir + " не существует. Создаю!!!");
            dir.mkdir();
        }
    }

    /**
     * Создает прокси класс с кэшированием
     */
    public Service cache(Service notCached) {
        delegate = notCached;
        //cacheMap = new HashMap<String, MethodCach>();
        cacheMap = new ConcurrentHashMap<String, MethodCach>();
        for (Method m : Service.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Cache.class)) {
                Cache cache = m.getAnnotation(Cache.class);
                MethodCach methodCach = null;
                boolean isFile = cache.cachType() == Cache.FILE;
                String fileName = null;
                if (isFile) { //попробовать загрузить из файла
                    File file = new File(cachDir, cache.fileName());
                    fileName = file.toString();
                    if (file.exists()) {
                        methodCach = deserialize(fileName);
                    }
                }
                if (methodCach == null)
                    methodCach = new MethodCach(3, m.getName(), isFile, fileName, cache.identityBy());
                cacheMap.put(m.getName(), methodCach);
            }
        }
        Service srv = (Service) Proxy.newProxyInstance(ServiceImpl.class.getClassLoader(),
                ServiceImpl.class.getInterfaces(), this);
        return srv;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (cacheMap == null) return null;
        MethodCach methodCach = cacheMap.get(method.getName());
        Object result;
        if (methodCach == null) {
            result = method.invoke(delegate, args);
            System.out.println("Не кэшируемый метод");
            return result;
        }


        //синхронизация работы с данными конкретного метода
        synchronized (methodCach) {
            result = methodCach.getCacheResult(args);

            if (result == null) {
                result = method.invoke(delegate, args);

                methodCach.add(args, result);
                if (methodCach.isFile()) {
                    serialize(methodCach);
                }
                System.out.println("Прямой вызов из " +
                        Thread.currentThread().getName());
            } else
                System.out.println("Кэшированое значение");
        }
        return result;

    }

    private MethodCach deserialize(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (MethodCach) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void serialize(MethodCach mc) {
        try {
            FileOutputStream fos = new FileOutputStream(mc.getFileName());
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(mc);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

