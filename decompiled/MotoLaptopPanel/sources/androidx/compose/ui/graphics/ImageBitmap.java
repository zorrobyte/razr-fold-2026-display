package androidx.compose.ui.graphics;

/* JADX INFO: compiled from: ImageBitmap.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ImageBitmap {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ImageBitmap.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /* JADX INFO: renamed from: getConfig-_sVssgQ */
    int mo805getConfig_sVssgQ();

    int getHeight();

    int getWidth();

    void prepareToDraw();
}
