package com.motorola.plugin.core.misc;

/* JADX INFO: compiled from: Disposable.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface DisposableContainer extends Disposable {
    void add(Disposable disposable);

    void remove(Disposable disposable);
}
