package com.android.systemui.qs.dagger;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class QSSceneModule_ProvidesQSUsingMediaPlayerFactory implements Factory {

    abstract class InstanceHolder {
        static final QSSceneModule_ProvidesQSUsingMediaPlayerFactory INSTANCE = new QSSceneModule_ProvidesQSUsingMediaPlayerFactory();
    }

    public static QSSceneModule_ProvidesQSUsingMediaPlayerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean providesQSUsingMediaPlayer() {
        return QSSceneModule.providesQSUsingMediaPlayer();
    }

    @Override // javax.inject.Provider
    public Boolean get() {
        return Boolean.valueOf(providesQSUsingMediaPlayer());
    }
}
