package com.android.systemui.statusbar.notification;

import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Pools;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.notification.TransformState;

/* JADX INFO: loaded from: classes.dex */
public class TextViewTransformState extends TransformState {
    private static Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    private TextView mText;

    private int getEllipsisCount() {
        Layout layout = this.mText.getLayout();
        if (layout == null || layout.getLineCount() <= 0) {
            return 0;
        }
        return layout.getEllipsisCount(0);
    }

    private boolean hasSameSpans(TextViewTransformState textViewTransformState) {
        KeyEvent.Callback callback = this.mText;
        boolean z = callback instanceof Spanned;
        if (z != (textViewTransformState.mText instanceof Spanned)) {
            return false;
        }
        if (!z) {
            return true;
        }
        Spanned spanned = (Spanned) callback;
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        Spanned spanned2 = (Spanned) textViewTransformState.mText;
        Object[] spans2 = spanned2.getSpans(0, spanned2.length(), Object.class);
        if (spans.length != spans2.length) {
            return false;
        }
        for (int i = 0; i < spans.length; i++) {
            Object obj = spans[i];
            Object obj2 = spans2[i];
            if (!obj.getClass().equals(obj2.getClass()) || spanned.getSpanStart(obj) != spanned2.getSpanStart(obj2) || spanned.getSpanEnd(obj) != spanned2.getSpanEnd(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static TextViewTransformState obtain() {
        TextViewTransformState textViewTransformState = (TextViewTransformState) sInstancePool.acquire();
        return textViewTransformState != null ? textViewTransformState : new TextViewTransformState();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected int getContentHeight() {
        return this.mText.getLineHeight();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected int getContentWidth() {
        Layout layout = this.mText.getLayout();
        return layout != null ? (int) layout.getLineWidth(0) : super.getContentWidth();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        this.mText = (TextView) view;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected void reset() {
        super.reset();
        this.mText = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected boolean sameAs(TransformState transformState) {
        if (super.sameAs(transformState)) {
            return true;
        }
        if (transformState instanceof TextViewTransformState) {
            TextViewTransformState textViewTransformState = (TextViewTransformState) transformState;
            if (TextUtils.equals(textViewTransformState.mText.getText(), this.mText.getText()) && getEllipsisCount() == textViewTransformState.getEllipsisCount() && this.mText.getLineCount() == textViewTransformState.mText.getLineCount() && hasSameSpans(textViewTransformState)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected boolean transformScale(TransformState transformState) {
        int lineCount;
        if (!(transformState instanceof TextViewTransformState)) {
            return false;
        }
        TextViewTransformState textViewTransformState = (TextViewTransformState) transformState;
        return TextUtils.equals(this.mText.getText(), textViewTransformState.mText.getText()) && (lineCount = this.mText.getLineCount()) == 1 && lineCount == textViewTransformState.mText.getLineCount() && getEllipsisCount() == textViewTransformState.getEllipsisCount() && getContentHeight() != textViewTransformState.getContentHeight();
    }
}
