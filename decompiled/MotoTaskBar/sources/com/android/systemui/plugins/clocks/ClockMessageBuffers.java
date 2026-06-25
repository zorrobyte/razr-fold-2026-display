package com.android.systemui.plugins.clocks;

import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClockMessageBuffers.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockMessageBuffers {
    public static final int $stable = 8;
    private final MessageBuffer infraMessageBuffer;
    private final MessageBuffer largeClockMessageBuffer;
    private final MessageBuffer smallClockMessageBuffer;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClockMessageBuffers(MessageBuffer messageBuffer) {
        this(messageBuffer, messageBuffer, messageBuffer);
        messageBuffer.getClass();
    }

    public ClockMessageBuffers(MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3) {
        messageBuffer.getClass();
        messageBuffer2.getClass();
        messageBuffer3.getClass();
        this.infraMessageBuffer = messageBuffer;
        this.smallClockMessageBuffer = messageBuffer2;
        this.largeClockMessageBuffer = messageBuffer3;
    }

    public static /* synthetic */ ClockMessageBuffers copy$default(ClockMessageBuffers clockMessageBuffers, MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3, int i, Object obj) {
        if ((i & 1) != 0) {
            messageBuffer = clockMessageBuffers.infraMessageBuffer;
        }
        if ((i & 2) != 0) {
            messageBuffer2 = clockMessageBuffers.smallClockMessageBuffer;
        }
        if ((i & 4) != 0) {
            messageBuffer3 = clockMessageBuffers.largeClockMessageBuffer;
        }
        return clockMessageBuffers.copy(messageBuffer, messageBuffer2, messageBuffer3);
    }

    public final MessageBuffer component1() {
        return this.infraMessageBuffer;
    }

    public final MessageBuffer component2() {
        return this.smallClockMessageBuffer;
    }

    public final MessageBuffer component3() {
        return this.largeClockMessageBuffer;
    }

    public final ClockMessageBuffers copy(MessageBuffer messageBuffer, MessageBuffer messageBuffer2, MessageBuffer messageBuffer3) {
        messageBuffer.getClass();
        messageBuffer2.getClass();
        messageBuffer3.getClass();
        return new ClockMessageBuffers(messageBuffer, messageBuffer2, messageBuffer3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockMessageBuffers)) {
            return false;
        }
        ClockMessageBuffers clockMessageBuffers = (ClockMessageBuffers) obj;
        return Intrinsics.areEqual(this.infraMessageBuffer, clockMessageBuffers.infraMessageBuffer) && Intrinsics.areEqual(this.smallClockMessageBuffer, clockMessageBuffers.smallClockMessageBuffer) && Intrinsics.areEqual(this.largeClockMessageBuffer, clockMessageBuffers.largeClockMessageBuffer);
    }

    public final MessageBuffer getInfraMessageBuffer() {
        return this.infraMessageBuffer;
    }

    public final MessageBuffer getLargeClockMessageBuffer() {
        return this.largeClockMessageBuffer;
    }

    public final MessageBuffer getSmallClockMessageBuffer() {
        return this.smallClockMessageBuffer;
    }

    public int hashCode() {
        return (((this.infraMessageBuffer.hashCode() * 31) + this.smallClockMessageBuffer.hashCode()) * 31) + this.largeClockMessageBuffer.hashCode();
    }

    public String toString() {
        return "ClockMessageBuffers(infraMessageBuffer=" + this.infraMessageBuffer + ", smallClockMessageBuffer=" + this.smallClockMessageBuffer + ", largeClockMessageBuffer=" + this.largeClockMessageBuffer + ")";
    }
}
