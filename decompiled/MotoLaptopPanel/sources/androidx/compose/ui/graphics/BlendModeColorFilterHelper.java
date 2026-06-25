package androidx.compose.ui.graphics;

/* JADX INFO: compiled from: AndroidColorFilter.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class BlendModeColorFilterHelper {
    public static final BlendModeColorFilterHelper INSTANCE = new BlendModeColorFilterHelper();

    private BlendModeColorFilterHelper() {
    }

    /* JADX INFO: renamed from: BlendModeColorFilter-xETnrds, reason: not valid java name */
    public final android.graphics.BlendModeColorFilter m865BlendModeColorFilterxETnrds(long j, int i) {
        return new android.graphics.BlendModeColorFilter(ColorKt.m900toArgb8_81llA(j), AndroidBlendMode_androidKt.m797toAndroidBlendModes9anfk8(i));
    }
}
