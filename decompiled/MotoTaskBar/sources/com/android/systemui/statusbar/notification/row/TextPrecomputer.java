package com.android.systemui.statusbar.notification.row;

import android.text.PrecomputedText;
import android.util.Log;
import android.widget.TextView;

/* JADX INFO: compiled from: TextPrecomputer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface TextPrecomputer {
    public static final /* synthetic */ Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TextPrecomputer.kt */
    final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    static /* synthetic */ Runnable precompute$default(TextPrecomputer textPrecomputer, TextView textView, CharSequence charSequence, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: precompute");
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return textPrecomputer.precompute(textView, charSequence, z);
    }

    default Runnable precompute(final TextView textView, final CharSequence charSequence, final boolean z) {
        textView.getClass();
        final PrecomputedText precomputedTextCreate = charSequence != null ? PrecomputedText.create(charSequence, textView.getTextMetricsParams()) : null;
        return new Runnable() { // from class: com.android.systemui.statusbar.notification.row.TextPrecomputer.precompute.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    textView.setText(precomputedTextCreate);
                } catch (IllegalArgumentException e) {
                    if (z) {
                        Log.wtf("TextPrecomputer", "PrecomputedText setText failed for TextView:" + textView, e);
                    }
                    textView.setText(charSequence);
                }
            }
        };
    }
}
