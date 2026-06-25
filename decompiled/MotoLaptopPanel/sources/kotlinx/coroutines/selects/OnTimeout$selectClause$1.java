package kotlinx.coroutines.selects;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: OnTimeout.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class OnTimeout$selectClause$1 extends FunctionReferenceImpl implements Function3 {
    public static final OnTimeout$selectClause$1 INSTANCE = new OnTimeout$selectClause$1();

    OnTimeout$selectClause$1() {
        super(3, OnTimeout.class, "register", "register(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        invoke((OnTimeout) obj, (SelectInstance) obj2, obj3);
        return Unit.INSTANCE;
    }

    public final void invoke(OnTimeout onTimeout, SelectInstance selectInstance, Object obj) {
        onTimeout.getClass();
        selectInstance.getClass();
        onTimeout.register(selectInstance, obj);
    }
}
