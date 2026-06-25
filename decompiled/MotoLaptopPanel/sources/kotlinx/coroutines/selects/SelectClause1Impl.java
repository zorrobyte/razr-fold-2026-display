package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: Select.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SelectClause1Impl implements SelectClause1 {
    private final Object clauseObject;
    private final Function3 onCancellationConstructor;
    private final Function3 processResFunc;
    private final Function3 regFunc;

    public SelectClause1Impl(Object obj, Function3 function3, Function3 function32, Function3 function33) {
        obj.getClass();
        function3.getClass();
        function32.getClass();
        this.clauseObject = obj;
        this.regFunc = function3;
        this.processResFunc = function32;
        this.onCancellationConstructor = function33;
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Object getClauseObject() {
        return this.clauseObject;
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Function3 getOnCancellationConstructor() {
        return this.onCancellationConstructor;
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Function3 getProcessResFunc() {
        return this.processResFunc;
    }

    @Override // kotlinx.coroutines.selects.SelectClause
    public Function3 getRegFunc() {
        return this.regFunc;
    }
}
