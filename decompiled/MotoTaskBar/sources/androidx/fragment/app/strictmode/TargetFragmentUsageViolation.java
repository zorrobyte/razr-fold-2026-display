package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;

/* JADX INFO: compiled from: TargetFragmentUsageViolation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TargetFragmentUsageViolation extends Violation {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TargetFragmentUsageViolation(Fragment fragment, String str) {
        super(fragment, str);
        fragment.getClass();
    }
}
