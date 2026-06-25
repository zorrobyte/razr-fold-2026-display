package androidx.compose.ui.platform;

import android.content.Context;
import android.os.Vibrator;

/* JADX INFO: compiled from: HapticFeedback.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HapticDefaults {
    public static final HapticDefaults INSTANCE = new HapticDefaults();

    private HapticDefaults() {
    }

    public final boolean isPremiumVibratorEnabled(Context context) {
        return ((Vibrator) context.getSystemService(Vibrator.class)).areAllPrimitivesSupported(1, 7, 2);
    }
}
