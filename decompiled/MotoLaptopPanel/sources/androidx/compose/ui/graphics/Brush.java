package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Size;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Brush.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Brush {
    public static final Companion Companion = new Companion(null);
    private final long intrinsicSize;

    /* JADX INFO: compiled from: Brush.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private Brush() {
        this.intrinsicSize = Size.Companion.m793getUnspecifiedNHjbRc();
    }

    public /* synthetic */ Brush(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: renamed from: applyTo-Pq9zytI, reason: not valid java name */
    public abstract void mo866applyToPq9zytI(long j, Paint paint, float f);
}
