package androidx.compose.ui.graphics;

/* JADX INFO: compiled from: AndroidColorFilter.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidColorFilter_androidKt {
    /* JADX INFO: renamed from: actualTintColorFilter-xETnrds, reason: not valid java name */
    public static final android.graphics.ColorFilter m804actualTintColorFilterxETnrds(long j, int i) {
        return BlendModeColorFilterHelper.INSTANCE.m865BlendModeColorFilterxETnrds(j, i);
    }

    public static final android.graphics.ColorFilter asAndroidColorFilter(ColorFilter colorFilter) {
        return colorFilter.getNativeColorFilter$ui_graphics_release();
    }
}
