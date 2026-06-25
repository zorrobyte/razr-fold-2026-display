package androidx.window.core;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SpecificationComputer.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SpecificationComputer {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: SpecificationComputer.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ SpecificationComputer startSpecification$default(Companion companion, Object obj, String str, VerificationMode verificationMode, Logger logger, int i, Object obj2) {
            if ((i & 2) != 0) {
                verificationMode = BuildConfig.INSTANCE.getVerificationMode();
            }
            if ((i & 4) != 0) {
                logger = AndroidLogger.INSTANCE;
            }
            return companion.startSpecification(obj, str, verificationMode, logger);
        }

        public final SpecificationComputer startSpecification(Object obj, String str, VerificationMode verificationMode, Logger logger) {
            obj.getClass();
            str.getClass();
            verificationMode.getClass();
            logger.getClass();
            return new ValidSpecification(obj, str, verificationMode, logger);
        }
    }

    public abstract Object compute();

    protected final String createMessage(Object obj, String str) {
        obj.getClass();
        str.getClass();
        return str + " value: " + obj;
    }

    public abstract SpecificationComputer require(String str, Function1 function1);
}
