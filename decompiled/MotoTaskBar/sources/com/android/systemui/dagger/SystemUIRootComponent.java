package com.android.systemui.dagger;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.fake.UiEventLoggerFake;
import com.motorola.taskbar.TaskBarChildUserServerService;
import com.motorola.taskbar.TaskBarService;
import com.motorola.taskbar.recent.RecentsActivity;

/* JADX INFO: loaded from: classes.dex */
public interface SystemUIRootComponent {

    public interface SystemUIRootComponentExtraModule {
        static UiEventLogger provideUiEventLogger() {
            return new UiEventLoggerFake();
        }
    }

    Dependency.DependencyInjector createDependency();

    void inject(TaskBarChildUserServerService taskBarChildUserServerService);

    void inject(TaskBarService taskBarService);

    void inject(RecentsActivity recentsActivity);
}
