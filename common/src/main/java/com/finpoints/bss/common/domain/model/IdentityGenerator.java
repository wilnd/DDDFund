package com.finpoints.bss.common.domain.model;

import java.util.UUID;

/**
 * 唯一标识生成器
 */
public class IdentityGenerator {

    private static Generator generator;

    private static void init(Generator generator) {
        synchronized (IdentityGenerator.class) {
            IdentityGenerator.generator = generator;
        }
    }

    public static String nextIdentity() {
        if (generator == null) {
            // Use default generator if not initialized
            init(new DefaultGenerator());
        }
        return generator.generateIdentity();
    }

    public interface Generator {
        String generateIdentity();
    }

    public static class DefaultGenerator implements Generator {
        @Override
        public String generateIdentity() {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }
}
