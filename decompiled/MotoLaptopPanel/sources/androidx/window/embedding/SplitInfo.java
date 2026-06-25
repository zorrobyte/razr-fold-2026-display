package androidx.window.embedding;

import android.os.IBinder;
import androidx.window.WindowSdkExtensions;
import androidx.window.extensions.embedding.SplitInfo;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: SplitInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SplitInfo {
    private final IBinder binder;
    private final ActivityStack primaryActivityStack;
    private final ActivityStack secondaryActivityStack;
    private final SplitAttributes splitAttributes;
    private final SplitInfo.Token token;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes) {
        this(activityStack, activityStack2, splitAttributes, null, null);
        activityStack.getClass();
        activityStack2.getClass();
        splitAttributes.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, IBinder iBinder) {
        this(activityStack, activityStack2, splitAttributes, iBinder, null);
        activityStack.getClass();
        activityStack2.getClass();
        splitAttributes.getClass();
        iBinder.getClass();
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(new IntRange(3, 4));
    }

    private SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, IBinder iBinder, SplitInfo.Token token) {
        this.primaryActivityStack = activityStack;
        this.secondaryActivityStack = activityStack2;
        this.splitAttributes = splitAttributes;
        this.binder = iBinder;
        this.token = token;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, SplitInfo.Token token) {
        this(activityStack, activityStack2, splitAttributes, null, token);
        activityStack.getClass();
        activityStack2.getClass();
        splitAttributes.getClass();
        token.getClass();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitInfo)) {
            return false;
        }
        SplitInfo splitInfo = (SplitInfo) obj;
        return Intrinsics.areEqual(this.primaryActivityStack, splitInfo.primaryActivityStack) && Intrinsics.areEqual(this.secondaryActivityStack, splitInfo.secondaryActivityStack) && Intrinsics.areEqual(this.splitAttributes, splitInfo.splitAttributes) && Intrinsics.areEqual(this.token, splitInfo.token) && Intrinsics.areEqual(this.binder, splitInfo.binder);
    }

    public int hashCode() {
        int iHashCode = ((((this.primaryActivityStack.hashCode() * 31) + this.secondaryActivityStack.hashCode()) * 31) + this.splitAttributes.hashCode()) * 31;
        SplitInfo.Token token = this.token;
        int iHashCode2 = (iHashCode + (token != null ? token.hashCode() : 0)) * 31;
        IBinder iBinder = this.binder;
        return iHashCode2 + (iBinder != null ? iBinder.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SplitInfo:{");
        sb.append("primaryActivityStack=" + this.primaryActivityStack + ", ");
        sb.append("secondaryActivityStack=" + this.secondaryActivityStack + ", ");
        sb.append("splitAttributes=" + this.splitAttributes + ", ");
        if (this.token != null) {
            sb.append("token=" + this.token);
        }
        if (this.binder != null) {
            sb.append("binder=" + this.binder);
        }
        sb.append("}");
        String string = sb.toString();
        string.getClass();
        return string;
    }
}
