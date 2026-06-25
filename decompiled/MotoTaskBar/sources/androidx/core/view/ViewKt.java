package androidx.core.view;

import android.view.View;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: View.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewKt {
    public static final Sequence getAllViews(View view) {
        return SequencesKt.sequence(new ViewKt$allViews$1(view, null));
    }

    public static final Sequence getAncestors(View view) {
        return SequencesKt.generateSequence(view.getParent(), ViewKt$ancestors$1.INSTANCE);
    }
}
