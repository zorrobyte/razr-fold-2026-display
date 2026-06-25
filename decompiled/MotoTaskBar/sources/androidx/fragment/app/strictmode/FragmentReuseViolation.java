package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;

/* JADX INFO: compiled from: FragmentReuseViolation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FragmentReuseViolation extends Violation {
    private final String previousFragmentId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FragmentReuseViolation(Fragment fragment, String str) {
        super(fragment, "Attempting to reuse fragment " + fragment + " with previous ID " + str);
        fragment.getClass();
        str.getClass();
        this.previousFragmentId = str;
    }
}
