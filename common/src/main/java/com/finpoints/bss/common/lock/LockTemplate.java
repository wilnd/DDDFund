package com.finpoints.bss.common.lock;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

public class LockTemplate {

    public static <T> T execute(Lock lock, Callable<T> callable) {
        Validate.notNull(lock, "Lock cannot be null");
        Validate.notNull(callable, "Callable cannot be null");

        boolean locked = false;
        try {
            locked = lock.tryLock();
            if (locked) {
                try {
                    return callable.call();
                } catch (RuntimeException re) {
                    throw re;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("Failed to acquire lock");
            }
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void execute(Lock lock, Runnable runnable) {
        Validate.notNull(lock, "Lock cannot be null");
        Validate.notNull(runnable, "Runnable cannot be null");

        boolean locked = false;
        try {
            locked = lock.tryLock();
            if (locked) {
                runnable.run();
            } else {
                throw new RuntimeException("Failed to acquire lock");
            }
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }
}
