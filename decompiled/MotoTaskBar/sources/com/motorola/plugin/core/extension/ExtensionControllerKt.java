package com.motorola.plugin.core.extension;

import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.sdk.Plugin;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExtensionController.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ExtensionControllerKt {
    public static final /* synthetic */ ExtensionController.ExtensionBuilder newExtension(ExtensionController extensionController, Function1 function1) {
        extensionController.getClass();
        function1.getClass();
        Intrinsics.reifiedOperationMarker(4, "T");
        ExtensionController.ExtensionBuilder extensionBuilderNewExtension = extensionController.newExtension(Object.class);
        function1.invoke(extensionBuilderNewExtension);
        return extensionBuilderNewExtension;
    }

    public static final /* synthetic */ void withPlugin(ExtensionController.ExtensionBuilder extensionBuilder, Function1 function1) {
        extensionBuilder.getClass();
        function1.getClass();
        Intrinsics.reifiedOperationMarker(4, "P");
        extensionBuilder.withPlugin(Plugin.class, function1);
    }
}
