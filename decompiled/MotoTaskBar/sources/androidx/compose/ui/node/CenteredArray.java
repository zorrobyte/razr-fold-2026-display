package androidx.compose.ui.node;

/* JADX INFO: compiled from: MyersDiff.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class CenteredArray {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int[] m557constructorimpl(int[] iArr) {
        return iArr;
    }

    /* JADX INFO: renamed from: get-impl, reason: not valid java name */
    public static final int m558getimpl(int[] iArr, int i) {
        return iArr[i + m559getMidimpl(iArr)];
    }

    /* JADX INFO: renamed from: getMid-impl, reason: not valid java name */
    private static final int m559getMidimpl(int[] iArr) {
        return iArr.length / 2;
    }

    /* JADX INFO: renamed from: set-impl, reason: not valid java name */
    public static final void m560setimpl(int[] iArr, int i, int i2) {
        iArr[i + m559getMidimpl(iArr)] = i2;
    }
}
