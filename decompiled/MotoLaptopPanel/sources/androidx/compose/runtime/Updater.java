package androidx.compose.runtime;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Updater {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Composer m616constructorimpl(Composer composer) {
        return composer;
    }

    /* JADX INFO: renamed from: set-impl, reason: not valid java name */
    public static final void m617setimpl(Composer composer, Object obj, Function2 function2) {
        if (composer.getInserting() || !Intrinsics.areEqual(composer.rememberedValue(), obj)) {
            composer.updateRememberedValue(obj);
            composer.apply(obj, function2);
        }
    }
}
