package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: OnTimeout.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class OnTimeoutKt {
    public static final void onTimeout(SelectBuilder selectBuilder, long j, Function1 function1) {
        selectBuilder.getClass();
        function1.getClass();
        selectBuilder.invoke(new OnTimeout(j).getSelectClause(), function1);
    }
}
