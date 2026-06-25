package androidx.window.core;

import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: SpecificationComputer.kt */
/* JADX INFO: loaded from: classes.dex */
final class ValidSpecification extends SpecificationComputer {
    private final Logger logger;
    private final String tag;
    private final Object value;
    private final VerificationMode verificationMode;

    public ValidSpecification(Object obj, String str, VerificationMode verificationMode, Logger logger) {
        obj.getClass();
        str.getClass();
        verificationMode.getClass();
        logger.getClass();
        this.value = obj;
        this.tag = str;
        this.verificationMode = verificationMode;
        this.logger = logger;
    }

    @Override // androidx.window.core.SpecificationComputer
    public Object compute() {
        return this.value;
    }

    @Override // androidx.window.core.SpecificationComputer
    public SpecificationComputer require(String str, Function1 function1) {
        str.getClass();
        function1.getClass();
        return ((Boolean) function1.invoke(this.value)).booleanValue() ? this : new FailedSpecification(this.value, this.tag, str, this.logger, this.verificationMode);
    }
}
