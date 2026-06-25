package com.motorola.taskbar.recent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import com.motorola.taskbar.R$dimen;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RecentsStyleList.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RecentsStyleList extends RecyclerView {
    private final int scrollBarPaddingTop;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RecentsStyleList(Context context) {
        this(context, null, 0, 6, null);
        context.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RecentsStyleList(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        context.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecentsStyleList(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        context.getClass();
        this.scrollBarPaddingTop = getResources().getDimensionPixelOffset(R$dimen.task_view_margin);
    }

    public /* synthetic */ RecentsStyleList(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    protected void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
        canvas.getClass();
        drawable.getClass();
        drawable.setBounds(i, i2 + this.scrollBarPaddingTop, i3, (i4 - getRootWindowInsets().getSystemWindowInsetBottom()) - this.scrollBarPaddingTop);
        drawable.draw(canvas);
    }
}
