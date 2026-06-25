package com.android.systemui.statusbar.policy;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public interface ExtensionController {

    public interface Extension {
        Object get();
    }

    public interface ExtensionBuilder {
        Extension build();

        ExtensionBuilder withCallback(Consumer consumer);

        ExtensionBuilder withPlugin(Class cls);
    }

    public interface PluginConverter {
    }

    ExtensionBuilder newExtension(Class cls);
}
