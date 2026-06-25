package androidx.core.content;

import androidx.core.util.Consumer;

/* JADX INFO: compiled from: OnTrimMemoryProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface OnTrimMemoryProvider {
    void addOnTrimMemoryListener(Consumer consumer);

    void removeOnTrimMemoryListener(Consumer consumer);
}
