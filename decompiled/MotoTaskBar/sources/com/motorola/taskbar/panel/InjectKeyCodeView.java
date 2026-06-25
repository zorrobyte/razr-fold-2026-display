package com.motorola.taskbar.panel;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class InjectKeyCodeView extends View {
    private Handler mBgHandler;
    private HandlerThread mHandlerThread;
    private View.OnKeyListener mOnKeyListener;

    public InjectKeyCodeView(Context context) {
        super(context);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.isCanceled()) {
                    return true;
                }
                final KeyEvent keyEvent2 = new KeyEvent(keyEvent);
                keyEvent2.setDisplayId(0);
                InjectKeyCodeView.this.mBgHandler.post(new Runnable(this) { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InputManager.getInstance().injectInputEvent(keyEvent2, 0);
                    }
                });
                return true;
            }
        };
    }

    public InjectKeyCodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.isCanceled()) {
                    return true;
                }
                final KeyEvent keyEvent2 = new KeyEvent(keyEvent);
                keyEvent2.setDisplayId(0);
                InjectKeyCodeView.this.mBgHandler.post(new Runnable(this) { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InputManager.getInstance().injectInputEvent(keyEvent2, 0);
                    }
                });
                return true;
            }
        };
    }

    public InjectKeyCodeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (keyEvent.isCanceled()) {
                    return true;
                }
                final KeyEvent keyEvent2 = new KeyEvent(keyEvent);
                keyEvent2.setDisplayId(0);
                InjectKeyCodeView.this.mBgHandler.post(new Runnable(this) { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InputManager.getInstance().injectInputEvent(keyEvent2, 0);
                    }
                });
                return true;
            }
        };
    }

    public InjectKeyCodeView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i22, KeyEvent keyEvent) {
                if (keyEvent.isCanceled()) {
                    return true;
                }
                final KeyEvent keyEvent2 = new KeyEvent(keyEvent);
                keyEvent2.setDisplayId(0);
                InjectKeyCodeView.this.mBgHandler.post(new Runnable(this) { // from class: com.motorola.taskbar.panel.InjectKeyCodeView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        InputManager.getInstance().injectInputEvent(keyEvent2, 0);
                    }
                });
                return true;
            }
        };
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HandlerThread handlerThread = new HandlerThread("InjectKeyCodeView");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mBgHandler = new Handler(this.mHandlerThread.getLooper());
        setOnKeyListener(this.mOnKeyListener);
    }
}
