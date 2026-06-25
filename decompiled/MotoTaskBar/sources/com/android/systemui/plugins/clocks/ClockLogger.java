package com.android.systemui.plugins.clocks;

import android.view.View;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.LogcatOnlyMessageBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ClockLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockLogger extends Logger {
    private float loggedAlpha;
    private final View view;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final LogcatOnlyMessageBuffer DEFAULT_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.INFO);
    private static final LogcatOnlyMessageBuffer DEBUG_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.DEBUG);
    private static final ClockLogger INIT_LOGGER = new ClockLogger(null, new LogcatOnlyMessageBuffer(LogLevel.ERROR), "CLOCK_INIT");

    /* JADX INFO: compiled from: ClockLogger.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String escapeTime(String str) {
            if (str != null) {
                return StringsKt.replace$default(str, "\n", "\\n", false, 4, null);
            }
            return null;
        }

        public final LogcatOnlyMessageBuffer getDEBUG_MESSAGE_BUFFER() {
            return ClockLogger.DEBUG_MESSAGE_BUFFER;
        }

        public final LogcatOnlyMessageBuffer getDEFAULT_MESSAGE_BUFFER() {
            return ClockLogger.DEFAULT_MESSAGE_BUFFER;
        }

        public final ClockLogger getINIT_LOGGER() {
            return ClockLogger.INIT_LOGGER;
        }

        public final String getSpecText(int i) {
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            return "(" + size + ", " + (mode != Integer.MIN_VALUE ? mode != 0 ? mode != 1073741824 ? String.valueOf(mode) : "EXACTLY" : "UNSPECIFIED" : "AT MOST") + ")";
        }

        public final String getVisText(int i) {
            return i != 0 ? i != 4 ? i != 8 ? String.valueOf(i) : "GONE" : "INVISIBLE" : "VISIBLE";
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockLogger(View view, MessageBuffer messageBuffer, String str) {
        super(messageBuffer, str);
        messageBuffer.getClass();
        str.getClass();
        this.view = view;
        this.loggedAlpha = 1000.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animateDoze$lambda$16(LogMessage logMessage) {
        logMessage.getClass();
        return "animateDoze(isDozing=" + logMessage.getBool1() + ", isAnimated=" + logMessage.getBool2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animateFidget$lambda$18(LogMessage logMessage) {
        logMessage.getClass();
        return "animateFidget(" + VPointF.m1498toStringimpl(VPointF.Companion.m1503fromLongAsyRdg(logMessage.getLong1())) + ")";
    }

    public static final String escapeTime(String str) {
        return Companion.escapeTime(str);
    }

    public static final String getSpecText(int i) {
        return Companion.getSpecText(i);
    }

    public static final String getVisText(int i) {
        return Companion.getVisText(i);
    }

    private final boolean isDrawn() {
        View view = this.view;
        return ((view != null ? view.mPrivateFlags : 0) & 32) > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onDraw$lambda$4(LogMessage logMessage) {
        logMessage.getClass();
        return "onDraw(" + Companion.escapeTime(logMessage.getStr1()) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onDraw$lambda$6(LogMessage logMessage) {
        logMessage.getClass();
        Companion companion = Companion;
        return "onDraw(ls = " + companion.escapeTime(logMessage.getStr1()) + ", aod = " + companion.escapeTime(logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onLayout$lambda$2(LogMessage logMessage) {
        logMessage.getClass();
        return "onLayout(" + logMessage.getBool1() + ", " + VRect.m1536toStringimpl(VRect.Companion.m1540fromLongqYjogQA(logMessage.getLong1())) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onMeasure$lambda$0(LogMessage logMessage) {
        logMessage.getClass();
        Companion companion = Companion;
        return "onMeasure(" + companion.getSpecText(logMessage.getInt1()) + ", " + companion.getSpecText(logMessage.getInt2()) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onViewAdded$lambda$14(LogMessage logMessage) {
        logMessage.getClass();
        return "onViewAdded(" + logMessage.getStr1() + " @" + logMessage.getInt1() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String setAlpha$lambda$10(LogMessage logMessage) {
        logMessage.getClass();
        return "setAlpha(" + logMessage.getDouble1() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String setVisibility$lambda$8(LogMessage logMessage) {
        logMessage.getClass();
        return "setVisibility(" + Companion.getVisText(logMessage.getInt1()) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String updateAxes$lambda$12(LogMessage logMessage) {
        logMessage.getClass();
        return "updateAxes(LS = " + logMessage.getStr1() + ", AOD = " + logMessage.getStr2() + ", isAnimated=" + logMessage.getBool1() + ")";
    }

    public final void animateCharge() {
        Logger.d$default(this, "animateCharge()", null, 2, null);
    }

    public final void animateDoze(boolean z, boolean z2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.animateDoze$lambda$16((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        logMessageObtain.setBool1(z);
        logMessageObtain.setBool2(z2);
        getBuffer().commit(logMessageObtain);
    }

    public final void animateFidget(float f, float f2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.animateFidget$lambda$18((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        logMessageObtain.setLong1(VPointF.m1496toLongimpl(VPointF.m1465constructorimpl(f, f2)));
        getBuffer().commit(logMessageObtain);
    }

    public final void invalidate() {
        View view;
        if (isDrawn() && (view = this.view) != null && view.getVisibility() == 0) {
            Logger.d$default(this, "invalidate()", null, 2, null);
        }
    }

    public final void onDraw() {
        Logger.d$default(this, "onDraw()", null, 2, null);
    }

    public final void onDraw(String str) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.onDraw$lambda$4((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        if (str == null) {
            str = "";
        }
        logMessageObtain.setStr1(str);
        getBuffer().commit(logMessageObtain);
    }

    public final void onDraw(String str, String str2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.onDraw$lambda$6((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        logMessageObtain.setStr1(str);
        logMessageObtain.setStr2(str2);
        getBuffer().commit(logMessageObtain);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.onLayout$lambda$2((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        logMessageObtain.setBool1(z);
        logMessageObtain.setLong1(VRect.m1534toLongimpl(VRect.m1519constructorimpl(i, i2, i3, i4)));
        getBuffer().commit(logMessageObtain);
    }

    public final void onMeasure(int i, int i2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.onMeasure$lambda$0((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        logMessageObtain.setInt1(i);
        logMessageObtain.setInt2(i2);
        getBuffer().commit(logMessageObtain);
    }

    public final void onViewAdded(View view) {
        view.getClass();
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.onViewAdded$lambda$14((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
        String simpleName = Reflection.getOrCreateKotlinClass(view.getClass()).getSimpleName();
        simpleName.getClass();
        logMessageObtain.setStr1(simpleName);
        logMessageObtain.setInt1(view.getId());
        getBuffer().commit(logMessageObtain);
    }

    public final void refreshTime() {
        Logger.d$default(this, "refreshTime()", null, 2, null);
    }

    public final void requestLayout() {
        View view = this.view;
        if (view == null || view.isLayoutRequested()) {
            return;
        }
        Logger.d$default(this, "requestLayout()", null, 2, null);
    }

    public final void setAlpha(float f) {
        if (Math.abs(this.loggedAlpha - f) >= ((f <= 0.0f || f >= 1.0f) ? 0.001f : 0.5f)) {
            this.loggedAlpha = f;
            LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ClockLogger.setAlpha$lambda$10((LogMessage) obj);
                }
            }, null);
            logMessageObtain.setDouble1(f);
            getBuffer().commit(logMessageObtain);
        }
    }

    public final void setVisibility(int i) {
        View view = this.view;
        if (view == null || i != view.getVisibility()) {
            Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ClockLogger.setVisibility$lambda$8((LogMessage) obj);
                }
            };
            LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, function1, null);
            logMessageObtain.setInt1(i);
            getBuffer().commit(logMessageObtain);
        }
    }

    public final void updateAxes(String str, String str2, boolean z) {
        str.getClass();
        str2.getClass();
        Function1 function1 = new Function1() { // from class: com.android.systemui.plugins.clocks.ClockLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockLogger.updateAxes$lambda$12((LogMessage) obj);
            }
        };
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.INFO, function1, null);
        logMessageObtain.setStr1(str);
        logMessageObtain.setStr2(str2);
        logMessageObtain.setBool1(z);
        getBuffer().commit(logMessageObtain);
    }
}
