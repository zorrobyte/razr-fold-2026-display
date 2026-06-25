package androidx.core.app;

import androidx.core.util.Consumer;

/* JADX INFO: compiled from: OnPictureInPictureModeChangedProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface OnPictureInPictureModeChangedProvider {
    void addOnPictureInPictureModeChangedListener(Consumer consumer);

    void removeOnPictureInPictureModeChangedListener(Consumer consumer);
}
