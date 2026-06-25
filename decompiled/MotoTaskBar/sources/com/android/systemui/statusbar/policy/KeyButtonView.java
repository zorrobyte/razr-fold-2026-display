package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.systemui.statusbar.policy.KeyButtonRipple;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.util.HoverWrapper;

/* JADX INFO: loaded from: classes.dex */
public class KeyButtonView extends ImageView {
    private static final String TAG = KeyButtonView.class.getSimpleName();
    private AudioManager mAudioManager;
    private final Runnable mCheckLongPress;
    private int mCode;
    private int mContentDescriptionRes;
    private float mDarkIntensity;
    private long mDownTime;
    private boolean mGestureAborted;
    private boolean mHandleTouch;
    private boolean mHasOvalBg;
    protected HoverWrapper mHoverWrapper;
    private final InputManager mInputManager;
    boolean mLongClicked;
    protected boolean mNeedHover;
    private View.OnClickListener mOnClickListener;
    private final Paint mOvalBgPaint;
    private final boolean mPlaySounds;
    private final KeyButtonRipple mRipple;
    private int mTouchDownX;
    private int mTouchDownY;

    public KeyButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyButtonView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, InputManager.getInstance());
    }

    public KeyButtonView(Context context, AttributeSet attributeSet, int i, InputManager inputManager) {
        super(context, attributeSet);
        this.mOvalBgPaint = new Paint(3);
        this.mHasOvalBg = false;
        this.mCheckLongPress = new Runnable() { // from class: com.android.systemui.statusbar.policy.KeyButtonView.1
            @Override // java.lang.Runnable
            public void run() {
                if (KeyButtonView.this.isPressed()) {
                    if (KeyButtonView.this.isLongClickable()) {
                        KeyButtonView.this.performLongClick();
                        KeyButtonView.this.mLongClicked = true;
                    } else {
                        KeyButtonView.this.sendEvent(0, 128);
                        KeyButtonView.this.sendAccessibilityEvent(2);
                        KeyButtonView.this.mLongClicked = true;
                    }
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyButtonView, i, 0);
        this.mCode = typedArrayObtainStyledAttributes.getInteger(R$styleable.KeyButtonView_keyCode, 0);
        this.mPlaySounds = typedArrayObtainStyledAttributes.getBoolean(R$styleable.KeyButtonView_playSound, true);
        this.mNeedHover = typedArrayObtainStyledAttributes.getBoolean(R$styleable.KeyButtonView_needHover, true);
        this.mHandleTouch = typedArrayObtainStyledAttributes.getBoolean(R$styleable.KeyButtonView_handleTouch, true);
        TypedValue typedValue = new TypedValue();
        if (typedArrayObtainStyledAttributes.getValue(R$styleable.KeyButtonView_android_contentDescription, typedValue)) {
            this.mContentDescriptionRes = typedValue.resourceId;
        }
        this.mHoverWrapper = HoverWrapper.register((View) this, attributeSet, true, true);
        typedArrayObtainStyledAttributes.recycle();
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(context, this);
        this.mRipple = keyButtonRipple;
        this.mInputManager = inputManager;
        setBackground(keyButtonRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    private static final float getQuickStepTouchSlopPx(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
    }

    private boolean isRightMouseButton(MotionEvent motionEvent) {
        return motionEvent.isFromSource(8194) && (motionEvent.getButtonState() & 2) != 0;
    }

    private void sendEvent(int i, int i2, int i3, long j) {
        KeyEvent keyEvent = new KeyEvent(this.mDownTime, j, i, i2, (i3 & 128) != 0 ? 1 : 0, 0, -1, 0, i3 | 72, 257);
        int displayId = getDisplay() != null ? getDisplay().getDisplayId() : -1;
        if (displayId != -1) {
            keyEvent.setDisplayId(displayId);
        }
        this.mInputManager.injectInputEvent(keyEvent, 0);
    }

    private void sendEvent(int i, int i2, long j) {
        sendEvent(i, this.mCode, i2, j);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Canvas canvas2;
        HoverWrapper hoverWrapper;
        if (this.mNeedHover && (hoverWrapper = this.mHoverWrapper) != null) {
            hoverWrapper.drawBackground(canvas);
        }
        if (this.mHasOvalBg) {
            canvas.save();
            canvas.translate((getLeft() + getRight()) / 2, (getTop() + getBottom()) / 2);
            int iMin = Math.min(getWidth(), getHeight()) / 2;
            float f = -iMin;
            float f2 = iMin;
            canvas2 = canvas;
            canvas2.drawOval(f, f, f2, f2, this.mOvalBgPaint);
            canvas2.restore();
        } else {
            canvas2 = canvas;
        }
        super.draw(canvas2);
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mContentDescriptionRes;
        if (i != 0) {
            setContentDescription(((ImageView) this).mContext.getString(i));
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHoverWrapper.forceHideTooltip();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCode != 0) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, null));
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, null));
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (!this.mHandleTouch) {
            return false;
        }
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        }
        if (isRightMouseButton(motionEvent)) {
            return true;
        }
        if (action == 0) {
            this.mDownTime = SystemClock.uptimeMillis();
            this.mLongClicked = false;
            setPressed(true);
            this.mTouchDownX = (int) motionEvent.getRawX();
            this.mTouchDownY = (int) motionEvent.getRawY();
            if (this.mCode != 0) {
                sendEvent(0, 0, this.mDownTime);
            }
            removeCallbacks(this.mCheckLongPress);
            postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
        } else if (action == 1) {
            boolean z = isPressed() && !this.mLongClicked;
            setPressed(false);
            SystemClock.uptimeMillis();
            if (this.mCode != 0) {
                if (z) {
                    sendEvent(1, 0);
                    sendAccessibilityEvent(1);
                } else {
                    sendEvent(1, 32);
                }
            } else if (z && this.mOnClickListener != null && isEnabled()) {
                this.mOnClickListener.onClick(this);
                sendAccessibilityEvent(1);
            }
            removeCallbacks(this.mCheckLongPress);
            this.mHoverWrapper.forceHideTooltip();
        } else if (action == 2) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            float quickStepTouchSlopPx = getQuickStepTouchSlopPx(getContext());
            if (Math.abs(rawX - this.mTouchDownX) > quickStepTouchSlopPx || Math.abs(rawY - this.mTouchDownY) > quickStepTouchSlopPx) {
                setPressed(false);
                removeCallbacks(this.mCheckLongPress);
            }
        } else if (action == 3) {
            setPressed(false);
            if (this.mCode != 0) {
                sendEvent(1, 32);
            }
            removeCallbacks(this.mCheckLongPress);
        }
        return true;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (i == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, 0);
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (i != 32 || this.mCode == 0) {
            return super.performAccessibilityActionInternal(i, bundle);
        }
        sendEvent(0, 128);
        sendEvent(1, 0);
        sendAccessibilityEvent(2);
        return true;
    }

    @Override // android.view.View
    public void playSoundEffect(int i) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(i, ActivityManager.getCurrentUser());
        }
    }

    public void sendEvent(int i, int i2) {
        sendEvent(i, i2, SystemClock.uptimeMillis());
    }

    public void sendEvent(int i, int i2, int i3) {
        sendEvent(i, i2, i3, SystemClock.uptimeMillis());
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
        this.mRipple.setDarkIntensity(f);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable == null) {
            return;
        }
        KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) drawable;
        keyButtonDrawable.setDarkIntensity(this.mDarkIntensity);
        boolean zHasOvalBg = keyButtonDrawable.hasOvalBg();
        this.mHasOvalBg = zHasOvalBg;
        if (zHasOvalBg) {
            this.mOvalBgPaint.setColor(keyButtonDrawable.getDrawableBackgroundColor());
        }
        this.mRipple.setType(keyButtonDrawable.hasOvalBg() ? KeyButtonRipple.Type.OVAL : KeyButtonRipple.Type.ROUNDED_RECT);
    }

    public void setNeedHover(boolean z) {
        this.mNeedHover = z;
        invalidate();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    public void setToolTipText(String str) {
        if (this.mNeedHover) {
            this.mHoverWrapper.setToolTipText(str);
        } else {
            this.mHoverWrapper.setToolTipText("");
        }
    }
}
