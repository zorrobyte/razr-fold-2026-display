package com.motorola.plugin.core.misc;

import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: compiled from: Disposable.kt */
/* JADX INFO: loaded from: classes2.dex */
final class CompositeDisposable implements DisposableContainer {
    private final Set listOfDisposable = new LinkedHashSet();

    @Override // com.motorola.plugin.core.misc.DisposableContainer
    public void add(Disposable disposable) {
        this.listOfDisposable.add(disposable);
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        for (Disposable disposable : this.listOfDisposable) {
            if (disposable != null) {
                disposable.dispose();
            }
        }
        this.listOfDisposable.clear();
    }

    @Override // com.motorola.plugin.core.misc.DisposableContainer
    public void remove(Disposable disposable) {
        this.listOfDisposable.remove(disposable);
    }
}
