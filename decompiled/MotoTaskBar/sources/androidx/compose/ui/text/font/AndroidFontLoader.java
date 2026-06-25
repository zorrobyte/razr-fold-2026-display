package androidx.compose.ui.text.font;

import android.content.Context;

/* JADX INFO: compiled from: AndroidFontLoader.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidFontLoader implements PlatformFontLoader {
    private final Context context;

    public AndroidFontLoader(Context context) {
        this.context = context.getApplicationContext();
    }
}
