package com.motorola.plugin.core.misc;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: IPrinter.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class IPrinterKt {
    public static final void withIndent(IPrinter iPrinter, Function1 function1) {
        iPrinter.getClass();
        function1.getClass();
        iPrinter.increaseIndent();
        function1.invoke(iPrinter);
        iPrinter.decreaseIndent();
    }
}
