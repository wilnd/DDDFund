package com.finpoints.bss.common.lock.impl;

import com.finpoints.bss.common.lock.LockProvider;
import com.finpoints.bss.common.lock.WLock;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class RedissonLockProvider implements LockProvider {

    private final Redisson redisson;

    public RedissonLockProvider(Redisson redisson) {
        this.redisson = redisson;
    }

    @Override
    public WLock getLock(String key) {
        return new WRLock(redisson.getLock(key));
    }

    public static class WRLock implements WLock {

        private final RLock rLock;

        public WRLock(RLock rLock) {
            this.rLock = rLock;
        }

        @Override
        public boolean isLocked() {
            return rLock.isLocked();
        }

        @Override
        public boolean isHeldByCurrentThread() {
            return rLock.isHeldByCurrentThread();
        }

        @Override
        public void lock() {
            rLock.lock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            rLock.lockInterruptibly();
        }

        @Override
        public boolean tryLock() {
            return rLock.tryLock();
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return rLock.tryLock(time, unit);
        }

        @Override
        public void unlock() {
            rLock.unlock();
        }

        @Override
        public Condition newCondition() {
            return rLock.newCondition();
        }
    }
}
