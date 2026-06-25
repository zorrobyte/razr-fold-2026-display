package androidx.lifecycle;

import androidx.lifecycle.viewmodel.internal.ViewModelImpl;

/* JADX INFO: compiled from: ViewModel.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewModel {
    private final ViewModelImpl impl = new ViewModelImpl();

    public final void addCloseable(String str, AutoCloseable autoCloseable) {
        str.getClass();
        autoCloseable.getClass();
        ViewModelImpl viewModelImpl = this.impl;
        if (viewModelImpl != null) {
            viewModelImpl.addCloseable(str, autoCloseable);
        }
    }

    public final void clear$lifecycle_viewmodel_release() {
        ViewModelImpl viewModelImpl = this.impl;
        if (viewModelImpl != null) {
            viewModelImpl.clear();
        }
        onCleared();
    }

    public final AutoCloseable getCloseable(String str) {
        str.getClass();
        ViewModelImpl viewModelImpl = this.impl;
        if (viewModelImpl != null) {
            return viewModelImpl.getCloseable(str);
        }
        return null;
    }

    protected void onCleared() {
    }
}
