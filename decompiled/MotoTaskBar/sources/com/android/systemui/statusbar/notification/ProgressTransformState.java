package com.android.systemui.statusbar.notification;

import android.util.Pools;

/* JADX INFO: loaded from: classes.dex */
public class ProgressTransformState extends TransformState {
    private static Pools.SimplePool sInstancePool = new Pools.SimplePool(40);

    public static ProgressTransformState obtain() {
        ProgressTransformState progressTransformState = (ProgressTransformState) sInstancePool.acquire();
        return progressTransformState != null ? progressTransformState : new ProgressTransformState();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected boolean sameAs(TransformState transformState) {
        if (transformState instanceof ProgressTransformState) {
            return true;
        }
        return super.sameAs(transformState);
    }
}
