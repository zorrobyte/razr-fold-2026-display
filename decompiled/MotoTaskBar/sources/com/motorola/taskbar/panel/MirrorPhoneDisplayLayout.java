package com.motorola.taskbar.panel;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes2.dex */
public class MirrorPhoneDisplayLayout extends LinearLayout implements SurfaceHolder.Callback {
    private Handler mBgHandler;
    private Display mDefaultDisplay;
    private Point mDefaultDisplaySize;
    private DisplayManager mDisplayManager;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private Instrumentation mInstrumentation;
    private PowerManager mPowerManager;
    private int mScreenDensity;
    private SurfaceView mSurfaceView;
    private VirtualDisplay mVirtualDisplay;

    public MirrorPhoneDisplayLayout(Context context) {
        super(context);
        this.mInstrumentation = new Instrumentation();
        this.mHandler = new Handler();
    }

    public MirrorPhoneDisplayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInstrumentation = new Instrumentation();
        this.mHandler = new Handler();
    }

    public MirrorPhoneDisplayLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInstrumentation = new Instrumentation();
        this.mHandler = new Handler();
    }

    public MirrorPhoneDisplayLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mInstrumentation = new Instrumentation();
        this.mHandler = new Handler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSurfaceView() {
        if (this.mSurfaceView != null) {
            return;
        }
        this.mDefaultDisplay = this.mDisplayManager.getDisplay(0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDefaultDisplay.getMetrics(displayMetrics);
        this.mScreenDensity = displayMetrics.densityDpi;
        DisplayInfo displayInfo = new DisplayInfo();
        this.mDefaultDisplay.getDisplayInfo(displayInfo);
        float width = getWidth() / displayInfo.logicalWidth;
        float height = getHeight();
        int i = displayInfo.logicalHeight;
        float f = height / i;
        if (width > f) {
            width = f;
        }
        SurfaceView surfaceView = new SurfaceView(getContext());
        this.mSurfaceView = surfaceView;
        surfaceView.setLayoutParams(new LinearLayout.LayoutParams((int) (displayInfo.logicalWidth * width), (int) (width * i), 1.0f));
        this.mSurfaceView.setZOrderOnTop(true);
        this.mSurfaceView.getHolder().setFormat(-3);
        this.mSurfaceView.getHolder().addCallback(this);
        addView(this.mSurfaceView);
    }

    private void destroyVirtualDisplays() {
        Log.d("MirrorPhoneDisplayLayout", "destroyVirtualDisplays");
        VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        if (virtualDisplay != null) {
            virtualDisplay.release();
            this.mVirtualDisplay = null;
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(this);
            this.mSurfaceView.setVisibility(8);
            removeView(this.mSurfaceView);
            this.mSurfaceView = null;
        }
    }

    private void setMotionEventDisplayId(MotionEvent motionEvent, int i) {
        Log.d("MirrorPhoneDisplayLayout", "setMotionEventDisplayId:" + i);
        try {
            Class.forName("android.view.MotionEvent").getMethod("setDisplayId", Integer.TYPE).invoke(motionEvent, Integer.valueOf(i));
        } catch (Exception e) {
            Log.w("MirrorPhoneDisplayLayout", "setMotionEventDisplayId error:" + e.toString());
        }
    }

    private void transmitGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 8) {
            return;
        }
        float width = this.mDefaultDisplaySize.x / getWidth();
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        MotionEvent.PointerProperties[] pointerPropertiesArr = {pointerProperties};
        pointerProperties.clear();
        MotionEvent.PointerProperties pointerProperties2 = pointerPropertiesArr[0];
        pointerProperties2.id = 0;
        pointerProperties2.toolType = 3;
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        MotionEvent.PointerCoords[] pointerCoordsArr = {pointerCoords};
        pointerCoords.clear();
        pointerCoordsArr[0].setAxisValue(9, motionEvent.getAxisValue(9));
        pointerCoordsArr[0].x = motionEvent.getX() * width;
        pointerCoordsArr[0].y = motionEvent.getY() * width;
        MotionEvent.PointerCoords pointerCoords2 = pointerCoordsArr[0];
        pointerCoords2.pressure = 1.0f;
        pointerCoords2.size = 1.0f;
        long jUptimeMillis = SystemClock.uptimeMillis();
        final MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 8, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, 0, 0, 8194, 0);
        setMotionEventDisplayId(motionEventObtain, 0);
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.panel.MirrorPhoneDisplayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                MirrorPhoneDisplayLayout.this.wakeUpIfNeeded();
                Log.d("MirrorPhoneDisplayLayout", "sendTrackballEventSync: " + motionEventObtain);
                InputManager.getInstance().injectInputEvent(motionEventObtain, 0);
            }
        });
    }

    private void transmitTouchEvent(MotionEvent motionEvent) {
        float width = this.mDefaultDisplaySize.x / getWidth();
        final MotionEvent motionEventObtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), motionEvent.getAction(), motionEvent.getX() * width, motionEvent.getY() * width, 0);
        motionEventObtain.setSource(4098);
        setMotionEventDisplayId(motionEventObtain, 0);
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.panel.MirrorPhoneDisplayLayout.2
            @Override // java.lang.Runnable
            public void run() {
                MirrorPhoneDisplayLayout.this.wakeUpIfNeeded();
                Log.d("MirrorPhoneDisplayLayout", "sendPointerSync: " + motionEventObtain);
                InputManager.getInstance().injectInputEvent(motionEventObtain, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUpIfNeeded() {
        Display display = this.mDefaultDisplay;
        if (display == null || this.mPowerManager == null || !Display.isOffState(display.getState()) || !this.mPowerManager.isInteractive()) {
            return;
        }
        Log.d("MirrorPhoneDisplayLayout", "wakeUpIfNeeded: wakeup");
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 7, "MirrorPhoneDisplayLayout");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Display display = ((DisplayManager) getContext().getSystemService("display")).getDisplay(0);
        Point point = new Point();
        this.mDefaultDisplaySize = point;
        display.getRealSize(point);
        HandlerThread handlerThread = new HandlerThread("MirrorPhoneDisplayLayout");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mBgHandler = new Handler(this.mHandlerThread.getLooper());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroyVirtualDisplays();
        this.mHandlerThread.quit();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDisplayManager = (DisplayManager) getContext().getApplicationContext().getSystemService("display");
        this.mPowerManager = (PowerManager) getContext().getApplicationContext().getSystemService("power");
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        transmitGenericMotionEvent(motionEvent);
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mHandler.post(new Runnable() { // from class: com.motorola.taskbar.panel.MirrorPhoneDisplayLayout.1
            @Override // java.lang.Runnable
            public void run() {
                MirrorPhoneDisplayLayout.this.createSurfaceView();
            }
        });
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        transmitTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mVirtualDisplay.resize(i2, i3, this.mScreenDensity);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Display display = getContext().getDisplay();
        if (display == null) {
            return;
        }
        this.mVirtualDisplay = this.mDisplayManager.createVirtualDisplay("MirrorPhoneDisplayLayout", surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), this.mScreenDensity, surfaceHolder.getSurface(), (display.getFlags() & 2) != 0 ? 20 : 16);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
