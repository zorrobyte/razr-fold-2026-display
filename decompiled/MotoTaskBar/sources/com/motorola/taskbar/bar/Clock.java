package com.motorola.taskbar.bar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.motorola.taskbar.R$styleable;
import com.motorola.taskbar.util.HoverWrapper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes2.dex */
public class Clock extends ThemeTextView {
    private final int mAmPmStyle;
    private boolean mAttached;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private Calendar mCalendar;
    private SimpleDateFormat mClockFormat;
    private String mClockFormatString;
    private SimpleDateFormat mContentDescriptionFormat;
    private HoverWrapper mHoverWrapper;
    private final BroadcastReceiver mIntentReceiver;
    private Locale mLocale;
    private final boolean mNeedHover;

    /* JADX INFO: renamed from: com.motorola.taskbar.bar.Clock$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(String str) {
            Clock.this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(str));
            if (Clock.this.mClockFormat != null) {
                Clock.this.mClockFormat.setTimeZone(Clock.this.mCalendar.getTimeZone());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(Locale locale) {
            if (locale.equals(Clock.this.mLocale)) {
                return;
            }
            Clock.this.mLocale = locale;
            Clock.this.mClockFormatString = "";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2() {
            Clock.this.updateClock();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Handler handler = Clock.this.getHandler();
            if (handler == null) {
                return;
            }
            String action = intent.getAction();
            if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                final String stringExtra = intent.getStringExtra("time-zone");
                handler.post(new Runnable() { // from class: com.motorola.taskbar.bar.Clock$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$0(stringExtra);
                    }
                });
            } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                final Locale locale = Clock.this.getResources().getConfiguration().locale;
                handler.post(new Runnable() { // from class: com.motorola.taskbar.bar.Clock$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$1(locale);
                    }
                });
            }
            handler.post(new Runnable() { // from class: com.motorola.taskbar.bar.Clock$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onReceive$2();
                }
            });
        }
    }

    public Clock(Context context) {
        this(context, null);
    }

    public Clock(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Clock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
        this.mIntentReceiver = new AnonymousClass1();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.Clock, 0, 0);
        try {
            this.mAmPmStyle = typedArrayObtainStyledAttributes.getInt(R$styleable.Clock_amPmStyle, 2);
            boolean z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.DateView_needHover, true);
            this.mNeedHover = z;
            if (z) {
                this.mHoverWrapper = HoverWrapper.register((View) this, "", true, true);
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private final CharSequence getSmallTime() {
        SimpleDateFormat simpleDateFormat;
        String timeFormatString = DateFormat.getTimeFormatString(getContext());
        if (timeFormatString.equals(this.mClockFormatString)) {
            simpleDateFormat = this.mClockFormat;
        } else {
            this.mContentDescriptionFormat = new SimpleDateFormat(timeFormatString);
            if (this.mAmPmStyle != 0) {
                int i = 0;
                boolean z = false;
                while (true) {
                    if (i >= timeFormatString.length()) {
                        i = -1;
                        break;
                    }
                    char cCharAt = timeFormatString.charAt(i);
                    if (cCharAt == '\'') {
                        z = !z;
                    }
                    if (!z && cCharAt == 'a') {
                        break;
                    }
                    i++;
                }
                if (i >= 0) {
                    int i2 = i;
                    while (i2 > 0 && Character.isWhitespace(timeFormatString.charAt(i2 - 1))) {
                        i2--;
                    }
                    timeFormatString = timeFormatString.substring(0, i2) + (char) 61184 + timeFormatString.substring(i2, i) + "a\uef01" + timeFormatString.substring(i + 1);
                }
            }
            simpleDateFormat = new SimpleDateFormat(timeFormatString);
            this.mClockFormat = simpleDateFormat;
            this.mClockFormatString = timeFormatString;
        }
        String str = simpleDateFormat.format(this.mCalendar.getTime());
        if (this.mAmPmStyle != 0) {
            int iIndexOf = str.indexOf(61184);
            int iIndexOf2 = str.indexOf(61185);
            if (iIndexOf >= 0 && iIndexOf2 > iIndexOf) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
                int i3 = this.mAmPmStyle;
                if (i3 == 2) {
                    spannableStringBuilder.delete(iIndexOf, iIndexOf2 + 1);
                    return spannableStringBuilder;
                }
                if (i3 == 1) {
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(0.7f), iIndexOf, iIndexOf2, 34);
                }
                spannableStringBuilder.delete(iIndexOf2, iIndexOf2 + 1);
                spannableStringBuilder.delete(iIndexOf, iIndexOf + 1);
                return spannableStringBuilder;
            }
        }
        return str;
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
        if (!this.mAttached) {
            this.mAttached = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_TICK");
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            this.mBroadcastDispatcher.registerReceiver(this.mIntentReceiver, intentFilter);
        }
        this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
        updateClock();
    }

    @Override // com.motorola.taskbar.bar.ThemeTextView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
            this.mAttached = false;
        }
    }

    final void updateClock() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        setText(getSmallTime());
        setContentDescription(this.mContentDescriptionFormat.format(this.mCalendar.getTime()));
    }
}
