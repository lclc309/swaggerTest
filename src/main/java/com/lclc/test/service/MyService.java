package com.lclc.test.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    private static final ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<String, AtomicInteger>();
    static {
        for (int i = 0; i < 100; i++) {
            map.put(i + "", new AtomicInteger(0));
        }
    }

    public int getnextInt(int key) throws InterruptedException {

        System.err.println("go1========================" + key);
        AtomicInteger atomic = map.get(key + "");
        System.err.println("go2========================" + key);
        int a = atomic.incrementAndGet();
        Thread.sleep(key * 1000);
        if (a >= 5) {
            atomic.set(0);
            a = atomic.incrementAndGet();
        }
        System.err.println(a);
        System.err.println("go3========================" + key);

        return a;

    }

}
