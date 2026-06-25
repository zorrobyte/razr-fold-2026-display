package com.motorola.taskbar.settings;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InjectorProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class InjectorProvider {
    public static final Companion Companion = new Companion(null);
    private static Injector sInjector;

    /* JADX INFO: compiled from: InjectorProvider.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Injector getInjector() {
            Injector injector = InjectorProvider.sInjector;
            injector.getClass();
            return injector;
        }

        public final void installInjector(Injector injector) {
            injector.getClass();
            InjectorProvider.sInjector = injector;
        }

        public final void releaseInjector() {
            InjectorProvider.sInjector = null;
        }
    }

    public static final void releaseInjector() {
        Companion.releaseInjector();
    }
}
