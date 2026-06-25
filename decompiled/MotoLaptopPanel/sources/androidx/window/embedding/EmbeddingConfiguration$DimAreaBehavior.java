package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: EmbeddingConfiguration.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmbeddingConfiguration$DimAreaBehavior {
    private final int value;
    public static final Companion Companion = new Companion(null);
    public static final EmbeddingConfiguration$DimAreaBehavior UNDEFINED = new EmbeddingConfiguration$DimAreaBehavior(0);
    public static final EmbeddingConfiguration$DimAreaBehavior ON_ACTIVITY_STACK = new EmbeddingConfiguration$DimAreaBehavior(1);
    public static final EmbeddingConfiguration$DimAreaBehavior ON_TASK = new EmbeddingConfiguration$DimAreaBehavior(2);

    /* JADX INFO: compiled from: EmbeddingConfiguration.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private EmbeddingConfiguration$DimAreaBehavior(int i) {
        this.value = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DimAreaBehavior=");
        int i = this.value;
        sb.append(i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "ON_TASK" : "ON_ACTIVITY_STACK" : "UNDEFINED");
        return sb.toString();
    }
}
