package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RemoteViews;
import com.android.internal.widget.ImageFloatingTextView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PrecomputedImageFloatingTextView.kt */
/* JADX INFO: loaded from: classes.dex */
@RemoteViews.RemoteView
public final class PrecomputedImageFloatingTextView extends ImageFloatingTextView implements TextPrecomputer {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PrecomputedImageFloatingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrecomputedImageFloatingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
    }

    public /* synthetic */ PrecomputedImageFloatingTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Runnable setTextAsync(CharSequence charSequence) {
        return TextPrecomputer.precompute$default(this, this, charSequence, false, 4, null);
    }
}
