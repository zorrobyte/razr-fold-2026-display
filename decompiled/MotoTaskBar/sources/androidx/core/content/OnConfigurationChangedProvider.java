package androidx.core.content;

import androidx.core.util.Consumer;

/* JADX INFO: compiled from: OnConfigurationChangedProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface OnConfigurationChangedProvider {
    void addOnConfigurationChangedListener(Consumer consumer);

    void removeOnConfigurationChangedListener(Consumer consumer);
}
