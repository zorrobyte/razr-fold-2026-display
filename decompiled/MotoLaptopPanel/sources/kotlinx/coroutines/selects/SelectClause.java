package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: Select.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SelectClause {
    Object getClauseObject();

    Function3 getOnCancellationConstructor();

    Function3 getProcessResFunc();

    Function3 getRegFunc();
}
