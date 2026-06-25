package androidx.compose.runtime.internal;

import androidx.compose.runtime.Composer;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: Expect.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Expect_jvmKt {
    public static final void invokeComposable(Composer composer, Function2 function2) {
        function2.getClass();
        ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(composer, 1);
    }
}
