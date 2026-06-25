package androidx.window.embedding;

import androidx.window.extensions.embedding.ActivityStack;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ActivityStack.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActivityStack {
    private final List activitiesInProcess;
    private final boolean isEmpty;
    private final ActivityStack.Token token;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ActivityStack(List list, boolean z) {
        this(list, z, null);
        list.getClass();
    }

    public ActivityStack(List list, boolean z, ActivityStack.Token token) {
        list.getClass();
        this.activitiesInProcess = list;
        this.isEmpty = z;
        this.token = token;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityStack)) {
            return false;
        }
        ActivityStack activityStack = (ActivityStack) obj;
        return Intrinsics.areEqual(this.activitiesInProcess, activityStack.activitiesInProcess) && this.isEmpty == activityStack.isEmpty && Intrinsics.areEqual(this.token, activityStack.token);
    }

    public int hashCode() {
        int iHashCode = ((this.activitiesInProcess.hashCode() * 31) + Boolean.hashCode(this.isEmpty)) * 31;
        ActivityStack.Token token = this.token;
        return iHashCode + (token != null ? token.hashCode() : 0);
    }

    public String toString() {
        return "ActivityStack{activitiesInProcess=" + this.activitiesInProcess + ", isEmpty=" + this.isEmpty + ", token=" + this.token + '}';
    }
}
