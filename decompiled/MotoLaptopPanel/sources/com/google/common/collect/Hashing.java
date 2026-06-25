package com.google.common.collect;

/* JADX INFO: loaded from: classes.dex */
abstract class Hashing {
    static int smear(int i) {
        return (int) (((long) Integer.rotateLeft((int) (((long) i) * (-862048943)), 15)) * 461845907);
    }

    static int smearedHash(Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }
}
