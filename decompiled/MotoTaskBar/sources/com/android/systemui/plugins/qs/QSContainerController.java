package com.android.systemui.plugins.qs;

/* JADX INFO: compiled from: QSContainerController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface QSContainerController {

    /* JADX INFO: compiled from: QSContainerController.kt */
    public final class DefaultImpls {
        public static void setCustomizerShowing(QSContainerController qSContainerController, boolean z) {
            qSContainerController.setCustomizerShowing(z, 0L);
        }
    }

    void setCustomizerAnimating(boolean z);

    void setCustomizerShowing(boolean z);

    void setCustomizerShowing(boolean z, long j);

    void setDetailShowing(boolean z);
}
