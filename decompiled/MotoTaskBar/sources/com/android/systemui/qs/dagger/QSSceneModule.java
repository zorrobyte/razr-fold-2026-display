package com.android.systemui.qs.dagger;

/* JADX INFO: compiled from: QSSceneModule.kt */
/* JADX INFO: loaded from: classes.dex */
public interface QSSceneModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: QSSceneModule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final boolean providesQSUsingMediaPlayer() {
            return true;
        }
    }

    static boolean providesQSUsingMediaPlayer() {
        return Companion.providesQSUsingMediaPlayer();
    }
}
