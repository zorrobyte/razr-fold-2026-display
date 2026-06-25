package com.motorola.taskbar.bar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.util.HoverWrapper;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class DateView extends ThemeTextView {
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final Date mCurrentTime;
    private DateFormat mDateFormat;
    private String mDatePattern;
    private HoverWrapper mHoverWrapper;
    private BroadcastReceiver mIntentReceiver;
    private String mLastText;
    private final boolean mNeedHover;

    /* JADX INFO: renamed from: com.motorola.taskbar.bar.DateView$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            DateView.this.mDateFormat = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1() {
            DateView.this.updateClock();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (("android.intent.action.TIME_TICK".equals(action) || "android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action) || "android.intent.action.LOCALE_CHANGED".equals(action)) && DateView.this.getHandler() != null) {
                if ("android.intent.action.LOCALE_CHANGED".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                    DateView.this.getHandler().post(new Runnable() { // from class: com.motorola.taskbar.bar.DateView$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReceive$0();
                        }
                    });
                }
                DateView.this.getHandler().post(new Runnable() { // from class: com.motorola.taskbar.bar.DateView$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$1();
                    }
                });
            }
        }
    }

    public DateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentTime = new Date();
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new AnonymousClass1();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DateView, 0, 0);
        try {
            this.mDatePattern = typedArrayObtainStyledAttributes.getString(R$styleable.DateView_datePattern);
            boolean z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.DateView_needHover, true);
            this.mNeedHover = z;
            typedArrayObtainStyledAttributes.recycle();
            if (this.mDatePattern == null) {
                this.mDatePattern = getContext().getString(R$string.system_ui_date_pattern);
            }
            if (z) {
                this.mHoverWrapper = HoverWrapper.register((View) this, "", true, true);
            }
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        HoverWrapper hoverWrapper = this.mHoverWrapper;
        if (hoverWrapper != null) {
            hoverWrapper.drawBackground(canvas);
        }
        super.draw(canvas);
    }

    @Override // com.motorola.taskbar.bar.ThemeTextView, android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(this.mIntentReceiver, intentFilter);
        updateClock();
    }

    @Override // com.motorola.taskbar.bar.ThemeTextView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDateFormat = null;
        this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
    }

    protected void updateClock() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        String str = this.mDateFormat.format(this.mCurrentTime);
        if (str.equals(this.mLastText)) {
            return;
        }
        setText(str);
        this.mLastText = str;
    }
}
