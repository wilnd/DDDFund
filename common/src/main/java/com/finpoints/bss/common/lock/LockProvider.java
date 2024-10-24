package com.finpoints.bss.common.lock;

public interface LockProvider {

    WLock getLock(String key);

}
