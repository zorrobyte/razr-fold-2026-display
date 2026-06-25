package com.motorola.plugin.sdk.channel;

import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface ThrowingBiConsumer extends BiConsumer {
    @Override // java.util.function.BiConsumer
    default void accept(Object obj, Object obj2) {
        try {
            acceptOrThrow(obj, obj2);
        } catch (Exception e) {
            if (!(e instanceof RuntimeException)) {
                throw new RuntimeException(e);
            }
            throw ((RuntimeException) e);
        }
    }

    void acceptOrThrow(Object obj, Object obj2) throws Exception;
}
