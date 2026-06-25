package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LogMessageImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LogMessageImpl implements LogMessage {
    public static final Factory Factory = new Factory(null);
    private boolean bool1;
    private boolean bool2;
    private boolean bool3;
    private boolean bool4;
    private double double1;
    private Throwable exception;
    private int int1;
    private int int2;
    private LogLevel level;
    private long long1;
    private long long2;
    private Function1 messagePrinter;
    private String str1;
    private String str2;
    private String str3;
    private String tag;

    /* JADX INFO: compiled from: LogMessageImpl.kt */
    public final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LogMessageImpl create() {
            return new LogMessageImpl(LogLevel.DEBUG, "UnknownTag", LogMessageImplKt.DEFAULT_PRINTER, null, null, null, null, 0, 0, 0L, 0L, 0.0d, false, false, false, false);
        }
    }

    public LogMessageImpl(LogLevel logLevel, String str, Function1 function1, Throwable th, String str2, String str3, String str4, int i, int i2, long j, long j2, double d, boolean z, boolean z2, boolean z3, boolean z4) {
        logLevel.getClass();
        str.getClass();
        function1.getClass();
        this.level = logLevel;
        this.tag = str;
        this.messagePrinter = function1;
        this.exception = th;
        this.str1 = str2;
        this.str2 = str3;
        this.str3 = str4;
        this.int1 = i;
        this.int2 = i2;
        this.long1 = j;
        this.long2 = j2;
        this.double1 = d;
        this.bool1 = z;
        this.bool2 = z2;
        this.bool3 = z3;
        this.bool4 = z4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LogMessageImpl)) {
            return false;
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) obj;
        return this.level == logMessageImpl.level && Intrinsics.areEqual(this.tag, logMessageImpl.tag) && Intrinsics.areEqual(this.messagePrinter, logMessageImpl.messagePrinter) && Intrinsics.areEqual(this.exception, logMessageImpl.exception) && Intrinsics.areEqual(this.str1, logMessageImpl.str1) && Intrinsics.areEqual(this.str2, logMessageImpl.str2) && Intrinsics.areEqual(this.str3, logMessageImpl.str3) && this.int1 == logMessageImpl.int1 && this.int2 == logMessageImpl.int2 && this.long1 == logMessageImpl.long1 && this.long2 == logMessageImpl.long2 && Double.compare(this.double1, logMessageImpl.double1) == 0 && this.bool1 == logMessageImpl.bool1 && this.bool2 == logMessageImpl.bool2 && this.bool3 == logMessageImpl.bool3 && this.bool4 == logMessageImpl.bool4;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public boolean getBool1() {
        return this.bool1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public boolean getBool2() {
        return this.bool2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public boolean getBool3() {
        return this.bool3;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public boolean getBool4() {
        return this.bool4;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public double getDouble1() {
        return this.double1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public Throwable getException() {
        return this.exception;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public int getInt1() {
        return this.int1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public int getInt2() {
        return this.int2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public LogLevel getLevel() {
        return this.level;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public long getLong1() {
        return this.long1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public long getLong2() {
        return this.long2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public Function1 getMessagePrinter() {
        return this.messagePrinter;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public String getStr1() {
        return this.str1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public String getStr2() {
        return this.str2;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public String getStr3() {
        return this.str3;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public String getTag() {
        return this.tag;
    }

    public int hashCode() {
        int iHashCode = ((((this.level.hashCode() * 31) + this.tag.hashCode()) * 31) + this.messagePrinter.hashCode()) * 31;
        Throwable th = this.exception;
        int iHashCode2 = (iHashCode + (th == null ? 0 : th.hashCode())) * 31;
        String str = this.str1;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.str2;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.str3;
        return ((((((((((((((((((iHashCode4 + (str3 != null ? str3.hashCode() : 0)) * 31) + Integer.hashCode(this.int1)) * 31) + Integer.hashCode(this.int2)) * 31) + Long.hashCode(this.long1)) * 31) + Long.hashCode(this.long2)) * 31) + Double.hashCode(this.double1)) * 31) + Boolean.hashCode(this.bool1)) * 31) + Boolean.hashCode(this.bool2)) * 31) + Boolean.hashCode(this.bool3)) * 31) + Boolean.hashCode(this.bool4);
    }

    public final void reset(String str, LogLevel logLevel, Function1 function1, Throwable th) {
        str.getClass();
        logLevel.getClass();
        function1.getClass();
        setLevel(logLevel);
        setTag(str);
        setMessagePrinter(function1);
        setException(th);
        setStr1(null);
        setStr2(null);
        setStr3(null);
        setInt1(0);
        setInt2(0);
        setLong1(0L);
        setLong2(0L);
        setDouble1(0.0d);
        setBool1(false);
        setBool2(false);
        setBool3(false);
        setBool4(false);
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setBool1(boolean z) {
        this.bool1 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setBool2(boolean z) {
        this.bool2 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setBool3(boolean z) {
        this.bool3 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setBool4(boolean z) {
        this.bool4 = z;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setDouble1(double d) {
        this.double1 = d;
    }

    public void setException(Throwable th) {
        this.exception = th;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setInt1(int i) {
        this.int1 = i;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setInt2(int i) {
        this.int2 = i;
    }

    public void setLevel(LogLevel logLevel) {
        logLevel.getClass();
        this.level = logLevel;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setLong1(long j) {
        this.long1 = j;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setLong2(long j) {
        this.long2 = j;
    }

    public void setMessagePrinter(Function1 function1) {
        function1.getClass();
        this.messagePrinter = function1;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setStr1(String str) {
        this.str1 = str;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setStr2(String str) {
        this.str2 = str;
    }

    @Override // com.android.systemui.log.core.LogMessage
    public void setStr3(String str) {
        this.str3 = str;
    }

    public void setTag(String str) {
        str.getClass();
        this.tag = str;
    }

    public String toString() {
        return "LogMessageImpl(level=" + this.level + ", tag=" + this.tag + ", messagePrinter=" + this.messagePrinter + ", exception=" + this.exception + ", str1=" + this.str1 + ", str2=" + this.str2 + ", str3=" + this.str3 + ", int1=" + this.int1 + ", int2=" + this.int2 + ", long1=" + this.long1 + ", long2=" + this.long2 + ", double1=" + this.double1 + ", bool1=" + this.bool1 + ", bool2=" + this.bool2 + ", bool3=" + this.bool3 + ", bool4=" + this.bool4 + ")";
    }
}
