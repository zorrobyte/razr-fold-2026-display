package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StartOffsetType {
    public static final Companion Companion = new Companion(null);
    private static final int Delay = m54constructorimpl(-1);
    private static final int FastForward = m54constructorimpl(1);

    /* JADX INFO: compiled from: AnimationSpec.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getDelay-Eo1U57Q, reason: not valid java name */
        public final int m55getDelayEo1U57Q() {
            return StartOffsetType.Delay;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m54constructorimpl(int i) {
        return i;
    }
}
