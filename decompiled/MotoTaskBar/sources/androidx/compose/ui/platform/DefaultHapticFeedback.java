package androidx.compose.ui.platform;

import android.view.View;
import androidx.compose.ui.hapticfeedback.HapticFeedback;

/* JADX INFO: compiled from: HapticFeedback.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DefaultHapticFeedback implements HapticFeedback {
    private final View view;

    public DefaultHapticFeedback(View view) {
        this.view = view;
    }
}
