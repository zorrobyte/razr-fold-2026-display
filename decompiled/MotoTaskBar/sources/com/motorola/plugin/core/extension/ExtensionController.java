package com.motorola.plugin.core.extension;

import android.content.Context;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.sdk.Plugin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ExtensionController.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ExtensionController {

    /* JADX INFO: compiled from: ExtensionController.kt */
    public interface Extension extends Disposable {
        void addCallback(Function1 function1);

        void clearItem();

        void destroy();

        Object get();

        Context getContext();

        Object reload();
    }

    /* JADX INFO: compiled from: ExtensionController.kt */
    public interface ExtensionBuilder {

        /* JADX INFO: compiled from: ExtensionController.kt */
        public final class DefaultImpls {
            public static ExtensionBuilder withPlugin(ExtensionBuilder extensionBuilder, Class cls) {
                extensionBuilder.getClass();
                cls.getClass();
                return extensionBuilder.withPlugin(cls, new Function1() { // from class: com.motorola.plugin.core.extension.ExtensionController$ExtensionBuilder$withPlugin$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Plugin plugin) {
                        plugin.getClass();
                        return plugin;
                    }
                });
            }
        }

        Extension build();

        ExtensionBuilder withCallback(Function1 function1);

        ExtensionBuilder withDefault(Function0 function0);

        ExtensionBuilder withFeature(String str, Function0 function0);

        ExtensionBuilder withFeature(Function0 function0, Function0 function02);

        ExtensionBuilder withPlugin(Class cls);

        ExtensionBuilder withPlugin(Class cls, Function1 function1);

        ExtensionBuilder withUiMode(int i, Function0 function0);
    }

    ExtensionBuilder newExtension(Class cls);
}
