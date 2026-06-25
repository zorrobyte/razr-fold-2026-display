package com.android.systemui.dagger;

import android.view.Choreographer;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvidesChoreographerFactory implements Factory {
    private final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvidesChoreographerFactory(FrameworkServicesModule frameworkServicesModule) {
        this.module = frameworkServicesModule;
    }

    public static FrameworkServicesModule_ProvidesChoreographerFactory create(FrameworkServicesModule frameworkServicesModule) {
        return new FrameworkServicesModule_ProvidesChoreographerFactory(frameworkServicesModule);
    }

    public static Choreographer providesChoreographer(FrameworkServicesModule frameworkServicesModule) {
        Choreographer choreographerProvidesChoreographer = frameworkServicesModule.providesChoreographer();
        choreographerProvidesChoreographer.getClass();
        return choreographerProvidesChoreographer;
    }

    @Override // javax.inject.Provider
    public Choreographer get() {
        return providesChoreographer(this.module);
    }
}
