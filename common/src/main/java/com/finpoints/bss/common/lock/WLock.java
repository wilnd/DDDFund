package com.finpoints.bss.common.lock;

import java.util.concurrent.locks.Lock;

/**
 * Wrapper for Lock/RLock/ReentrantLock.
 *
 * @see java.util.concurrent.locks.Lock
 * @see java.util.concurrent.locks.ReentrantLock
 * @see org.redisson.api.RLock
 */
public interface WLock extends Lock {

    boolean isLocked();

    boolean isHeldByCurrentThread();
}
