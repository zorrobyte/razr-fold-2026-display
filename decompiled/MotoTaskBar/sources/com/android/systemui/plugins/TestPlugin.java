package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* JADX INFO: compiled from: TestPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@ProvidesInterface(action = "testAction", version = 1)
public interface TestPlugin extends Plugin {
    public static final String ACTION = "testAction";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* JADX INFO: compiled from: TestPlugin.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "testAction";
        public static final int VERSION = 1;

        private Companion() {
        }
    }

    Object methodThrowsError();
}
