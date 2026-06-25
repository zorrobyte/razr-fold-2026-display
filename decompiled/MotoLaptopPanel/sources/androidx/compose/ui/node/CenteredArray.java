package androidx.compose.ui.node;

/* JADX INFO: compiled from: MyersDiff.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class CenteredArray {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int[] m1303constructorimpl(int[] iArr) {
        return iArr;
    }

    /* JADX INFO: renamed from: get-impl, reason: not valid java name */
    public static final int m1304getimpl(int[] iArr, int i) {
        return iArr[i + m1305getMidimpl(iArr)];
    }

    /* JADX INFO: renamed from: getMid-impl, reason: not valid java name */
    private static final int m1305getMidimpl(int[] iArr) {
        return iArr.length / 2;
    }

    /* JADX INFO: renamed from: set-impl, reason: not valid java name */
    public static final void m1306setimpl(int[] iArr, int i, int i2) {
        iArr[i + m1305getMidimpl(iArr)] = i2;
    }
}
