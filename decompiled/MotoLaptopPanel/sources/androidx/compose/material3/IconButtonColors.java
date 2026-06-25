package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: IconButton.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IconButtonColors {
    private final long containerColor;
    private final long contentColor;
    private final long disabledContainerColor;
    private final long disabledContentColor;

    private IconButtonColors(long j, long j2, long j3, long j4) {
        this.containerColor = j;
        this.contentColor = j2;
        this.disabledContainerColor = j3;
        this.disabledContentColor = j4;
    }

    public /* synthetic */ IconButtonColors(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    /* JADX INFO: renamed from: copy-jRlVdoo$default, reason: not valid java name */
    public static /* synthetic */ IconButtonColors m291copyjRlVdoo$default(IconButtonColors iconButtonColors, long j, long j2, long j3, long j4, int i, Object obj) {
        if ((i & 1) != 0) {
            j = iconButtonColors.containerColor;
        }
        long j5 = j;
        if ((i & 2) != 0) {
            j2 = iconButtonColors.contentColor;
        }
        long j6 = j2;
        if ((i & 4) != 0) {
            j3 = iconButtonColors.disabledContainerColor;
        }
        return iconButtonColors.m294copyjRlVdoo(j5, j6, j3, (i & 8) != 0 ? iconButtonColors.disabledContentColor : j4);
    }

    /* JADX INFO: renamed from: containerColor-vNxB06k$material3_release, reason: not valid java name */
    public final long m292containerColorvNxB06k$material3_release(boolean z) {
        return z ? this.containerColor : this.disabledContainerColor;
    }

    /* JADX INFO: renamed from: contentColor-vNxB06k$material3_release, reason: not valid java name */
    public final long m293contentColorvNxB06k$material3_release(boolean z) {
        return z ? this.contentColor : this.disabledContentColor;
    }

    /* JADX INFO: renamed from: copy-jRlVdoo, reason: not valid java name */
    public final IconButtonColors m294copyjRlVdoo(long j, long j2, long j3, long j4) {
        return new IconButtonColors(j != 16 ? j : this.containerColor, j2 != 16 ? j2 : this.contentColor, j3 != 16 ? j3 : this.disabledContainerColor, j4 != 16 ? j4 : this.disabledContentColor, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IconButtonColors)) {
            return false;
        }
        IconButtonColors iconButtonColors = (IconButtonColors) obj;
        return Color.m882equalsimpl0(this.containerColor, iconButtonColors.containerColor) && Color.m882equalsimpl0(this.contentColor, iconButtonColors.contentColor) && Color.m882equalsimpl0(this.disabledContainerColor, iconButtonColors.disabledContainerColor) && Color.m882equalsimpl0(this.disabledContentColor, iconButtonColors.disabledContentColor);
    }

    /* JADX INFO: renamed from: getContentColor-0d7_KjU, reason: not valid java name */
    public final long m295getContentColor0d7_KjU() {
        return this.contentColor;
    }

    public int hashCode() {
        return (((((Color.m888hashCodeimpl(this.containerColor) * 31) + Color.m888hashCodeimpl(this.contentColor)) * 31) + Color.m888hashCodeimpl(this.disabledContainerColor)) * 31) + Color.m888hashCodeimpl(this.disabledContentColor);
    }
}
