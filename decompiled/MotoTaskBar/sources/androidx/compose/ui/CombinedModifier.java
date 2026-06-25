package androidx.compose.ui;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Modifier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CombinedModifier implements Modifier {
    private final Modifier inner;
    private final Modifier outer;

    public CombinedModifier(Modifier modifier, Modifier modifier2) {
        this.outer = modifier;
        this.inner = modifier2;
    }

    @Override // androidx.compose.ui.Modifier
    public boolean all(Function1 function1) {
        return this.outer.all(function1) && this.inner.all(function1);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CombinedModifier)) {
            return false;
        }
        CombinedModifier combinedModifier = (CombinedModifier) obj;
        return Intrinsics.areEqual(this.outer, combinedModifier.outer) && Intrinsics.areEqual(this.inner, combinedModifier.inner);
    }

    @Override // androidx.compose.ui.Modifier
    public Object foldIn(Object obj, Function2 function2) {
        return this.inner.foldIn(this.outer.foldIn(obj, function2), function2);
    }

    public final Modifier getInner$ui_release() {
        return this.inner;
    }

    public final Modifier getOuter$ui_release() {
        return this.outer;
    }

    public int hashCode() {
        return this.outer.hashCode() + (this.inner.hashCode() * 31);
    }

    public String toString() {
        return '[' + ((String) foldIn("", new Function2() { // from class: androidx.compose.ui.CombinedModifier.toString.1
            @Override // kotlin.jvm.functions.Function2
            public final String invoke(String str, Modifier.Element element) {
                if (str.length() == 0) {
                    return element.toString();
                }
                return str + ", " + element;
            }
        })) + ']';
    }
}
