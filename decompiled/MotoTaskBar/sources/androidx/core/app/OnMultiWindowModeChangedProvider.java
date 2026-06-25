package androidx.core.app;

import androidx.core.util.Consumer;

/* JADX INFO: compiled from: OnMultiWindowModeChangedProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface OnMultiWindowModeChangedProvider {
    void addOnMultiWindowModeChangedListener(Consumer consumer);

    void removeOnMultiWindowModeChangedListener(Consumer consumer);
}
