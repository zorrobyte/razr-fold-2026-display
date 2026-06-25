package androidx.core.view.insets;

import android.graphics.RectF;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.graphics.Insets;
import androidx.core.view.insets.SystemBarStateMonitor;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ProtectionGroup implements SystemBarStateMonitor.Callback {
    private int mAnimationCount;
    private boolean mDisposed;
    private Insets mInsets;
    private Insets mInsetsIgnoringVisibility;
    private final SystemBarStateMonitor mMonitor;
    private final ArrayList mProtections = new ArrayList();

    ProtectionGroup(SystemBarStateMonitor systemBarStateMonitor, List list) {
        Insets insets = Insets.NONE;
        this.mInsets = insets;
        this.mInsetsIgnoringVisibility = insets;
        addProtections(list, false);
        addProtections(list, true);
        systemBarStateMonitor.addCallback(this);
        this.mMonitor = systemBarStateMonitor;
    }

    private void addProtections(List list, boolean z) {
        if (list.size() <= 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(list.get(0));
        throw null;
    }

    private void updateInsets() {
        Insets insets = Insets.NONE;
        int size = this.mProtections.size() - 1;
        if (size < 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mProtections.get(size));
        throw null;
    }

    void dispose() {
        if (this.mDisposed) {
            return;
        }
        this.mDisposed = true;
        this.mMonitor.removeCallback(this);
        int size = this.mProtections.size() - 1;
        if (size < 0) {
            this.mProtections.clear();
        } else {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mProtections.get(size));
            throw null;
        }
    }

    Protection getProtection(int i) {
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mProtections.get(i));
        return null;
    }

    @Override // androidx.core.view.insets.SystemBarStateMonitor.Callback
    public void onAnimationEnd() {
        int i = this.mAnimationCount;
        boolean z = i > 0;
        int i2 = i - 1;
        this.mAnimationCount = i2;
        if (z && i2 == 0) {
            updateInsets();
        }
    }

    @Override // androidx.core.view.insets.SystemBarStateMonitor.Callback
    public void onAnimationProgress(int i, Insets insets, RectF rectF) {
        int size = this.mProtections.size() - 1;
        if (size < 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mProtections.get(size));
        throw null;
    }

    @Override // androidx.core.view.insets.SystemBarStateMonitor.Callback
    public void onAnimationStart() {
        this.mAnimationCount++;
    }

    @Override // androidx.core.view.insets.SystemBarStateMonitor.Callback
    public void onColorHintChanged(int i) {
        int size = this.mProtections.size() - 1;
        if (size < 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mProtections.get(size));
        throw null;
    }

    @Override // androidx.core.view.insets.SystemBarStateMonitor.Callback
    public void onInsetsChanged(Insets insets, Insets insets2) {
        this.mInsets = insets;
        this.mInsetsIgnoringVisibility = insets2;
        updateInsets();
    }

    int size() {
        return this.mProtections.size();
    }
}
