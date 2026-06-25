package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Select.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SelectBuilder {
    void invoke(SelectClause0 selectClause0, Function1 function1);

    void invoke(SelectClause1 selectClause1, Function2 function2);
}
