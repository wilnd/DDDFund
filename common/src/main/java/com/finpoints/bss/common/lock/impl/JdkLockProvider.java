package com.finpoints.bss.common.lock.impl;

import com.finpoints.bss.common.lock.LockProvider;
import com.finpoints.bss.common.lock.WLock;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class JdkLockProvider implements LockProvider {

    private final WeakHashMap<String, WReentrantLock> locks = new WeakHashMap<>();

    @Override
    public WLock getLock(String key) {
        WReentrantLock lock = locks.get(key);
        if (lock == null) {
            WReentrantLock newLock = new WReentrantLock(new ReentrantLock());
            lock = locks.putIfAbsent(key, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }
        return lock;
    }

    public static class WReentrantLock implements WLock {

        private final ReentrantLock lock;

        public WReentrantLock(ReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public boolean isLocked() {
            return lock.isLocked();
        }

        @Override
        public boolean isHeldByCurrentThread() {
            return lock.isHeldByCurrentThread();
        }

        @Override
        public void lock() {
            lock.lock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            lock.lockInterruptibly();
        }

        @Override
        public boolean tryLock() {
            return lock.tryLock();
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return lock.tryLock(time, unit);
        }

        @Override
        public void unlock() {
            lock.unlock();
        }

        @Override
        public Condition newCondition() {
            return lock.newCondition();
        }
    }
}
