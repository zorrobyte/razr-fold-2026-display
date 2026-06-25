package androidx.compose.ui.semantics;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsPropertyKey {
    private boolean isImportantForAccessibility;
    private final Function2 mergePolicy;
    private final String name;

    public SemanticsPropertyKey(String str, Function2 function2) {
        this.name = str;
        this.mergePolicy = function2;
    }

    public /* synthetic */ SemanticsPropertyKey(String str, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Function2() { // from class: androidx.compose.ui.semantics.SemanticsPropertyKey.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return obj == null ? obj2 : obj;
            }
        } : function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SemanticsPropertyKey(String str, boolean z) {
        this(str, null, 2, 0 == true ? 1 : 0);
        this.isImportantForAccessibility = z;
    }

    public SemanticsPropertyKey(String str, boolean z, Function2 function2) {
        this(str, function2);
        this.isImportantForAccessibility = z;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean isImportantForAccessibility$ui_release() {
        return this.isImportantForAccessibility;
    }

    public final Object merge(Object obj, Object obj2) {
        return this.mergePolicy.invoke(obj, obj2);
    }

    public final void setValue(SemanticsPropertyReceiver semanticsPropertyReceiver, KProperty kProperty, Object obj) {
        semanticsPropertyReceiver.set(this, obj);
    }

    public String toString() {
        return "AccessibilityKey: " + this.name;
    }
}
