package com.google.android.setupdesign.view;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public interface TouchableMovementMethod {

    public class TouchableLinkMovementMethod extends LinkMovementMethod implements TouchableMovementMethod {
        MotionEvent lastEvent;
        boolean lastEventResult = false;

        public static TouchableLinkMovementMethod getInstance() {
            return new TouchableLinkMovementMethod();
        }

        @Override // com.google.android.setupdesign.view.TouchableMovementMethod
        public MotionEvent getLastTouchEvent() {
            return this.lastEvent;
        }

        @Override // com.google.android.setupdesign.view.TouchableMovementMethod
        public boolean isLastTouchEventHandled() {
            return this.lastEventResult;
        }

        @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            this.lastEvent = motionEvent;
            boolean zOnTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
            if (motionEvent.getAction() == 0) {
                this.lastEventResult = Selection.getSelectionStart(spannable) != -1;
                return zOnTouchEvent;
            }
            this.lastEventResult = zOnTouchEvent;
            return zOnTouchEvent;
        }
    }

    MotionEvent getLastTouchEvent();

    boolean isLastTouchEventHandled();
}
