package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class RenderStageManager_Factory implements Factory {

    abstract class InstanceHolder {
        static final RenderStageManager_Factory INSTANCE = new RenderStageManager_Factory();
    }

    public static RenderStageManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RenderStageManager newInstance() {
        return new RenderStageManager();
    }

    @Override // javax.inject.Provider
    public RenderStageManager get() {
        return newInstance();
    }
}
