package androidx.compose.ui.modifier;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ModifierLocal.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ModifierLocal {
    private final Function0 defaultFactory;

    private ModifierLocal(Function0 function0) {
        this.defaultFactory = function0;
    }

    public /* synthetic */ ModifierLocal(Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0);
    }

    public final Function0 getDefaultFactory$ui_release() {
        return this.defaultFactory;
    }
}
