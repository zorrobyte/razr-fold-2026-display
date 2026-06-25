package com.android.systemui.qs;

import android.view.KeyEvent;
import android.view.View;
import androidx.core.util.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LeftRightArrowPressedListener.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LeftRightArrowPressedListener implements View.OnKeyListener, View.OnFocusChangeListener {
    public static final Companion Companion = new Companion(null);
    private Integer lastKeyCode;
    private Consumer listener;

    /* JADX INFO: compiled from: LeftRightArrowPressedListener.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LeftRightArrowPressedListener createAndRegisterListenerForView(View view) {
            view.getClass();
            LeftRightArrowPressedListener leftRightArrowPressedListener = new LeftRightArrowPressedListener(null);
            view.setOnKeyListener(leftRightArrowPressedListener);
            view.setOnFocusChangeListener(leftRightArrowPressedListener);
            return leftRightArrowPressedListener;
        }
    }

    private LeftRightArrowPressedListener() {
        this.lastKeyCode = 0;
    }

    public /* synthetic */ LeftRightArrowPressedListener(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final LeftRightArrowPressedListener createAndRegisterListenerForView(View view) {
        return Companion.createAndRegisterListenerForView(view);
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        view.getClass();
        if (z) {
            this.lastKeyCode = null;
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        Integer num;
        view.getClass();
        keyEvent.getClass();
        if (i != 21 && i != 22) {
            return false;
        }
        if (keyEvent.getAction() == 1 && (num = this.lastKeyCode) != null && i == num.intValue()) {
            Consumer consumer = this.listener;
            if (consumer != null) {
                consumer.accept(Integer.valueOf(i));
            }
            this.lastKeyCode = null;
        } else if (keyEvent.getRepeatCount() == 0) {
            this.lastKeyCode = Integer.valueOf(i);
        }
        return true;
    }

    public final void setArrowKeyPressedListener(Consumer consumer) {
        consumer.getClass();
        this.listener = consumer;
    }
}
