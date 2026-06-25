package androidx.compose.foundation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: Clickable.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class AbstractClickableNode$focusableNode$1 extends FunctionReferenceImpl implements Function1 {
    AbstractClickableNode$focusableNode$1(Object obj) {
        super(1, obj, AbstractClickableNode.class, "onFocusChange", "onFocusChange(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }

    public final void invoke(boolean z) {
        ((AbstractClickableNode) this.receiver).onFocusChange(z);
    }
}
