package w;

import android.view.accessibility.AccessibilityManager;
import com.google.android.material.snackbar.BaseTransientBottomBar$SnackbarBaseLayout;
import e0.k;

/* JADX INFO: renamed from: w.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class AccessibilityManagerTouchExplorationStateChangeListenerC0162a implements AccessibilityManager.TouchExplorationStateChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final k f2839a;

    public AccessibilityManagerTouchExplorationStateChangeListenerC0162a(k kVar) {
        this.f2839a = kVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessibilityManagerTouchExplorationStateChangeListenerC0162a.class != obj.getClass()) {
            return false;
        }
        return this.f2839a.equals(((AccessibilityManagerTouchExplorationStateChangeListenerC0162a) obj).f2839a);
    }

    public final int hashCode() {
        return this.f2839a.hashCode();
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z2) {
        ((BaseTransientBottomBar$SnackbarBaseLayout) this.f2839a.f2495a).setClickableOrFocusableBasedOnAccessibility(z2);
    }
}
