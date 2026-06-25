package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginSubscriber;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.utils.HiddenApiKt;
import java.lang.Thread;
import kotlin.Unit;

/* JADX INFO: compiled from: PluginUncaughtExceptionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginExceptionHandler {
    private final PluginEvent mPluginEvent;
    private final PluginSubscriber mPluginSubscriber;

    /* JADX INFO: compiled from: PluginUncaughtExceptionHandler.kt */
    final class CrashWhilePluginActiveException extends RuntimeException {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CrashWhilePluginActiveException(Throwable th) {
            super(th);
            th.getClass();
        }
    }

    /* JADX INFO: compiled from: PluginUncaughtExceptionHandler.kt */
    final class PluginUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler crashHandler;
        private final ISnapshotAware snapshot;
        final /* synthetic */ PluginExceptionHandler this$0;

        public PluginUncaughtExceptionHandler(PluginExceptionHandler pluginExceptionHandler, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, ISnapshotAware iSnapshotAware) {
            pluginExceptionHandler.getClass();
            uncaughtExceptionHandler.getClass();
            iSnapshotAware.getClass();
            this.this$0 = pluginExceptionHandler;
            this.crashHandler = uncaughtExceptionHandler;
            this.snapshot = iSnapshotAware;
        }

        private final boolean checkStack(Throwable th) {
            boolean z;
            if (th == null) {
                return false;
            }
            PluginExceptionHandler pluginExceptionHandler = this.this$0;
            synchronized (this) {
                try {
                    StackTraceElement[] stackTrace = th.getStackTrace();
                    stackTrace.getClass();
                    int length = stackTrace.length;
                    int i = 0;
                    z = false;
                    while (i < length) {
                        StackTraceElement stackTraceElement = stackTrace[i];
                        i++;
                        if (!z) {
                            PluginSubscriber pluginSubscriber = pluginExceptionHandler.mPluginSubscriber;
                            String className = stackTraceElement.getClassName();
                            className.getClass();
                            if (!pluginSubscriber.willDisableAnyPlugin(className)) {
                                z = false;
                            }
                        }
                        z = true;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return z || checkStack(th.getCause());
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            thread.getClass();
            th.getClass();
            boolean z = true;
            this.this$0.mPluginEvent.recordEvent("plugin crash with [" + ((Object) thread.getName()) + "] " + th, ISnapshotAware.DefaultImpls.snapshot$default(this.snapshot, null, 1, null));
            this.this$0.mPluginEvent.flush();
            if (PluginConfigKt.getDEBUG() || !PluginConfigKt.getDISABLE_PLUGIN_WHILE_CRASH()) {
                this.crashHandler.uncaughtException(thread, th);
                return;
            }
            boolean zCheckStack = checkStack(th);
            if (!zCheckStack) {
                PluginExceptionHandler pluginExceptionHandler = this.this$0;
                synchronized (this) {
                    if (!zCheckStack) {
                        try {
                            if (!pluginExceptionHandler.mPluginSubscriber.willDisableAllPlugins()) {
                                z = false;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                zCheckStack = z;
            }
            if (zCheckStack) {
                th = new CrashWhilePluginActiveException(th);
            }
            this.crashHandler.uncaughtException(thread, th);
        }
    }

    public PluginExceptionHandler(PluginEvent pluginEvent, PluginSubscriber pluginSubscriber) {
        pluginEvent.getClass();
        pluginSubscriber.getClass();
        this.mPluginEvent = pluginEvent;
        this.mPluginSubscriber = pluginSubscriber;
    }

    public final void attach(ISnapshotAware iSnapshotAware) {
        iSnapshotAware.getClass();
        Thread.UncaughtExceptionHandler uncaughtExceptionPreHandlerExt = HiddenApiKt.getUncaughtExceptionPreHandlerExt();
        if (uncaughtExceptionPreHandlerExt == null) {
            return;
        }
        HiddenApiKt.setUncaughtExceptionPreHandlerExt(new PluginUncaughtExceptionHandler(this, uncaughtExceptionPreHandlerExt, iSnapshotAware));
    }
}
