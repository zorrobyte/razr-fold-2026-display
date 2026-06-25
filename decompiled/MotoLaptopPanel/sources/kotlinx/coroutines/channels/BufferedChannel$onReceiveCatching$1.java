package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.selects.SelectInstance;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class BufferedChannel$onReceiveCatching$1 extends FunctionReferenceImpl implements Function3 {
    public static final BufferedChannel$onReceiveCatching$1 INSTANCE = new BufferedChannel$onReceiveCatching$1();

    BufferedChannel$onReceiveCatching$1() {
        super(3, BufferedChannel.class, "registerSelectForReceive", "registerSelectForReceive(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        invoke((BufferedChannel) obj, (SelectInstance) obj2, obj3);
        return Unit.INSTANCE;
    }

    public final void invoke(BufferedChannel bufferedChannel, SelectInstance selectInstance, Object obj) {
        bufferedChannel.getClass();
        selectInstance.getClass();
        bufferedChannel.registerSelectForReceive(selectInstance, obj);
    }
}
