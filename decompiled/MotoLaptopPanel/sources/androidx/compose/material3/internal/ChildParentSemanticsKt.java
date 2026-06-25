package androidx.compose.material3.internal;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ChildParentSemantics.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ChildParentSemanticsKt {
    public static final Modifier childSemantics(Modifier modifier, Function1 function1) {
        return modifier.then(new ChildSemanticsNodeElement(function1));
    }

    public static /* synthetic */ Modifier childSemantics$default(Modifier modifier, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: androidx.compose.material3.internal.ChildParentSemanticsKt.childSemantics.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                    invoke((SemanticsPropertyReceiver) obj2);
                    return Unit.INSTANCE;
                }

                public final void invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                }
            };
        }
        return childSemantics(modifier, function1);
    }
}
