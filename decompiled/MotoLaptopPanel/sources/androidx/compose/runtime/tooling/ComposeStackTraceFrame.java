package androidx.compose.runtime.tooling;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ComposeStackTrace.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ComposeStackTraceFrame {
    private final Integer groupOffset;
    private final ParsedSourceInformation sourceInfo;

    public ComposeStackTraceFrame(ParsedSourceInformation parsedSourceInformation, Integer num) {
        this.sourceInfo = parsedSourceInformation;
        this.groupOffset = num;
    }

    public static /* synthetic */ ComposeStackTraceFrame copy$default(ComposeStackTraceFrame composeStackTraceFrame, ParsedSourceInformation parsedSourceInformation, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            parsedSourceInformation = composeStackTraceFrame.sourceInfo;
        }
        if ((i & 2) != 0) {
            num = composeStackTraceFrame.groupOffset;
        }
        return composeStackTraceFrame.copy(parsedSourceInformation, num);
    }

    public final ComposeStackTraceFrame copy(ParsedSourceInformation parsedSourceInformation, Integer num) {
        return new ComposeStackTraceFrame(parsedSourceInformation, num);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComposeStackTraceFrame)) {
            return false;
        }
        ComposeStackTraceFrame composeStackTraceFrame = (ComposeStackTraceFrame) obj;
        return Intrinsics.areEqual(this.sourceInfo, composeStackTraceFrame.sourceInfo) && Intrinsics.areEqual(this.groupOffset, composeStackTraceFrame.groupOffset);
    }

    public final Integer getGroupOffset() {
        return this.groupOffset;
    }

    public final ParsedSourceInformation getSourceInfo() {
        return this.sourceInfo;
    }

    public int hashCode() {
        int iHashCode = this.sourceInfo.hashCode() * 31;
        Integer num = this.groupOffset;
        return iHashCode + (num == null ? 0 : num.hashCode());
    }

    public String toString() {
        return "ComposeStackTraceFrame(sourceInfo=" + this.sourceInfo + ", groupOffset=" + this.groupOffset + ')';
    }
}
