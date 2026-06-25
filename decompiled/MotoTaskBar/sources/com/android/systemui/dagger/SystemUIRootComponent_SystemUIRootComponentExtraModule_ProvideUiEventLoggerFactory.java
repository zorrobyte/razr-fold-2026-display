package com.android.systemui.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dagger.SystemUIRootComponent;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SystemUIRootComponent_SystemUIRootComponentExtraModule_ProvideUiEventLoggerFactory implements Factory {

    abstract class InstanceHolder {
        static final SystemUIRootComponent_SystemUIRootComponentExtraModule_ProvideUiEventLoggerFactory INSTANCE = new SystemUIRootComponent_SystemUIRootComponentExtraModule_ProvideUiEventLoggerFactory();
    }

    public static SystemUIRootComponent_SystemUIRootComponentExtraModule_ProvideUiEventLoggerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static UiEventLogger provideUiEventLogger() {
        UiEventLogger uiEventLoggerProvideUiEventLogger = SystemUIRootComponent.SystemUIRootComponentExtraModule.provideUiEventLogger();
        uiEventLoggerProvideUiEventLogger.getClass();
        return uiEventLoggerProvideUiEventLogger;
    }

    @Override // javax.inject.Provider
    public UiEventLogger get() {
        return provideUiEventLogger();
    }
}
