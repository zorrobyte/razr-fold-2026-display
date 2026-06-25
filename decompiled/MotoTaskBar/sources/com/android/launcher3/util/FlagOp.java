package com.android.launcher3.util;

/* JADX INFO: loaded from: classes.dex */
public interface FlagOp {
    public static final FlagOp NO_OP = new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda0
        @Override // com.android.launcher3.util.FlagOp
        public final int apply(int i) {
            return FlagOp.$r8$lambda$dM18aAGXPcDkE5OkqATJmFJG34Y(i);
        }
    };

    static /* synthetic */ int $r8$lambda$dM18aAGXPcDkE5OkqATJmFJG34Y(int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default int lambda$addFlag$1(int i, int i2) {
        return apply(i2) | i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default int lambda$removeFlag$2(int i, int i2) {
        return apply(i2) & (~i);
    }

    default FlagOp addFlag(final int i) {
        return new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda1
            @Override // com.android.launcher3.util.FlagOp
            public final int apply(int i2) {
                return this.f$0.lambda$addFlag$1(i, i2);
            }
        };
    }

    int apply(int i);

    default FlagOp removeFlag(final int i) {
        return new FlagOp() { // from class: com.android.launcher3.util.FlagOp$$ExternalSyntheticLambda2
            @Override // com.android.launcher3.util.FlagOp
            public final int apply(int i2) {
                return this.f$0.lambda$removeFlag$2(i, i2);
            }
        };
    }

    default FlagOp setFlag(int i, boolean z) {
        return z ? addFlag(i) : removeFlag(i);
    }
}
