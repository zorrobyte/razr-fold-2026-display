package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SemanticsConfiguration.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SemanticsConfigurationKt {
    public static final Object getOrNull(SemanticsConfiguration semanticsConfiguration, SemanticsPropertyKey semanticsPropertyKey) {
        return semanticsConfiguration.getOrElseNullable(semanticsPropertyKey, new Function0() { // from class: androidx.compose.ui.semantics.SemanticsConfigurationKt.getOrNull.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return null;
            }
        });
    }
}
