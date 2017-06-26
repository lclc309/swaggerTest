package com.lclc.test.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * 生成订单号的service
 *
 * @author lichao
 */
@Service
public class OrderSeqServiceSeq {

    private static final ConcurrentHashMap<Integer, AtomicInteger> map = new ConcurrentHashMap<Integer, AtomicInteger>();

    private static final Interner<String> pool = Interners.<String> newWeakInterner();

    private int mod = 9999;

    private int max = 90;

    private int min = 0;

    public long getNextSeq(Long userid) {

        // 1.首先对userid 进行取模
        int i = (int) (userid % mod);
        int next = min;
        // 2.获取此模的计数器
        synchronized (pool.intern(i + "")) {
            AtomicInteger atomic = map.get(i);
            if (atomic == null) {
                atomic = new AtomicInteger(min);
                map.put(i, atomic);
            }
            // 3.获取下一个值
            next = atomic.incrementAndGet();
            if (next >= max) {
                next = min;
                atomic.set(min);
            }

        }
        // 4.进行拼接
        long seq = this.makeSeq(i, next);
        return seq;
    }

    private long makeSeq(int i, int next) {

        // 1.获取当前时间 10位
        long first = System.currentTimeMillis() / 1000;
        // 2.取序列 2位
        int sed = next;
        // 3.取模 4位
        int third = i;
        long result = first * 1000000 + sed * 10000 + third;

        return result;
    }

}
