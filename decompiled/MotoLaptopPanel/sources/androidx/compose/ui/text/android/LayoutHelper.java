package androidx.compose.ui.text.android;

import android.text.Layout;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: LayoutHelper.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutHelper {
    private final boolean[] bidiProcessedParagraphs;
    private final Layout layout;
    private final List paragraphBidi;
    private final int paragraphCount;
    private final List paragraphEnds;
    private char[] tmpBuffer;

    /* JADX INFO: compiled from: LayoutHelper.android.kt */
    public final class BidiRun {
        private final int end;
        private final boolean isRtl;
        private final int start;

        public BidiRun(int i, int i2, boolean z) {
            this.start = i;
            this.end = i2;
            this.isRtl = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BidiRun)) {
                return false;
            }
            BidiRun bidiRun = (BidiRun) obj;
            return this.start == bidiRun.start && this.end == bidiRun.end && this.isRtl == bidiRun.isRtl;
        }

        public final int getEnd() {
            return this.end;
        }

        public final int getStart() {
            return this.start;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.start) * 31) + Integer.hashCode(this.end)) * 31) + Boolean.hashCode(this.isRtl);
        }

        public final boolean isRtl() {
            return this.isRtl;
        }

        public String toString() {
            return "BidiRun(start=" + this.start + ", end=" + this.end + ", isRtl=" + this.isRtl + ')';
        }
    }

    public LayoutHelper(Layout layout) {
        this.layout = layout;
        ArrayList arrayList = new ArrayList();
        int length = 0;
        do {
            int iIndexOf$default = StringsKt.indexOf$default(this.layout.getText(), '\n', length, false, 4, (Object) null);
            length = iIndexOf$default < 0 ? this.layout.getText().length() : iIndexOf$default + 1;
            arrayList.add(Integer.valueOf(length));
        } while (length < this.layout.getText().length());
        this.paragraphEnds = arrayList;
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(null);
        }
        this.paragraphBidi = arrayList2;
        this.bidiProcessedParagraphs = new boolean[this.paragraphEnds.size()];
        this.paragraphCount = this.paragraphEnds.size();
    }

    private final float getDownstreamHorizontal(int i, boolean z) {
        int iCoerceAtMost = RangesKt.coerceAtMost(i, this.layout.getLineEnd(this.layout.getLineForOffset(i)));
        return z ? this.layout.getPrimaryHorizontal(iCoerceAtMost) : this.layout.getSecondaryHorizontal(iCoerceAtMost);
    }

    private final int lineEndToVisibleEnd(int i, int i2) {
        while (i > i2 && isLineEndSpace(this.layout.getText().charAt(i - 1))) {
            i--;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.text.Bidi analyzeBidi(int r11) {
        /*
            r10 = this;
            boolean[] r0 = r10.bidiProcessedParagraphs
            boolean r0 = r0[r11]
            if (r0 == 0) goto Lf
            java.util.List r10 = r10.paragraphBidi
            java.lang.Object r10 = r10.get(r11)
            java.text.Bidi r10 = (java.text.Bidi) r10
            return r10
        Lf:
            r0 = 0
            if (r11 != 0) goto L14
            r1 = r0
            goto L22
        L14:
            java.util.List r1 = r10.paragraphEnds
            int r2 = r11 + (-1)
            java.lang.Object r1 = r1.get(r2)
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
        L22:
            java.util.List r2 = r10.paragraphEnds
            java.lang.Object r2 = r2.get(r11)
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            int r8 = r2 - r1
            char[] r3 = r10.tmpBuffer
            if (r3 == 0) goto L3a
            int r4 = r3.length
            if (r4 >= r8) goto L38
            goto L3a
        L38:
            r4 = r3
            goto L3d
        L3a:
            char[] r3 = new char[r8]
            goto L38
        L3d:
            android.text.Layout r3 = r10.layout
            java.lang.CharSequence r3 = r3.getText()
            android.text.TextUtils.getChars(r3, r1, r2, r4, r0)
            boolean r0 = java.text.Bidi.requiresBidi(r4, r0, r8)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L60
            boolean r9 = r10.isRtlParagraph(r11)
            java.text.Bidi r3 = new java.text.Bidi
            r6 = 0
            r7 = 0
            r5 = 0
            r3.<init>(r4, r5, r6, r7, r8, r9)
            int r0 = r3.getRunCount()
            if (r0 != r2) goto L61
        L60:
            r3 = r1
        L61:
            java.util.List r0 = r10.paragraphBidi
            r0.set(r11, r3)
            boolean[] r0 = r10.bidiProcessedParagraphs
            r0[r11] = r2
            if (r3 == 0) goto L73
            char[] r11 = r10.tmpBuffer
            if (r4 != r11) goto L72
            r4 = r1
            goto L73
        L72:
            r4 = r11
        L73:
            r10.tmpBuffer = r4
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.android.LayoutHelper.analyzeBidi(int):java.text.Bidi");
    }

    public final float getHorizontalPosition(int i, boolean z, boolean z2) {
        int iLineEndToVisibleEnd = i;
        if (!z2) {
            return getDownstreamHorizontal(i, z);
        }
        int lineForOffset = LayoutCompat_androidKt.getLineForOffset(this.layout, iLineEndToVisibleEnd, z2);
        int lineStart = this.layout.getLineStart(lineForOffset);
        int lineEnd = this.layout.getLineEnd(lineForOffset);
        if (iLineEndToVisibleEnd != lineStart && iLineEndToVisibleEnd != lineEnd) {
            return getDownstreamHorizontal(i, z);
        }
        if (iLineEndToVisibleEnd == 0 || iLineEndToVisibleEnd == this.layout.getText().length()) {
            return getDownstreamHorizontal(i, z);
        }
        int paragraphForOffset = getParagraphForOffset(iLineEndToVisibleEnd, z2);
        boolean zIsRtlParagraph = isRtlParagraph(paragraphForOffset);
        int iLineEndToVisibleEnd2 = lineEndToVisibleEnd(lineEnd, lineStart);
        int paragraphStart = getParagraphStart(paragraphForOffset);
        int i2 = lineStart - paragraphStart;
        int i3 = iLineEndToVisibleEnd2 - paragraphStart;
        Bidi bidiAnalyzeBidi = analyzeBidi(paragraphForOffset);
        Bidi bidiCreateLineBidi = bidiAnalyzeBidi != null ? bidiAnalyzeBidi.createLineBidi(i2, i3) : null;
        boolean z3 = false;
        if (bidiCreateLineBidi == null || bidiCreateLineBidi.getRunCount() == 1) {
            boolean zIsRtlCharAt = this.layout.isRtlCharAt(lineStart);
            if (z || zIsRtlParagraph == zIsRtlCharAt) {
                zIsRtlParagraph = !zIsRtlParagraph;
            }
            if (iLineEndToVisibleEnd == lineStart) {
                z3 = zIsRtlParagraph;
            } else if (!zIsRtlParagraph) {
                z3 = true;
            }
            Layout layout = this.layout;
            return z3 ? layout.getLineLeft(lineForOffset) : layout.getLineRight(lineForOffset);
        }
        int runCount = bidiCreateLineBidi.getRunCount();
        BidiRun[] bidiRunArr = new BidiRun[runCount];
        for (int i4 = 0; i4 < runCount; i4++) {
            bidiRunArr[i4] = new BidiRun(bidiCreateLineBidi.getRunStart(i4) + lineStart, bidiCreateLineBidi.getRunLimit(i4) + lineStart, bidiCreateLineBidi.getRunLevel(i4) % 2 == 1);
        }
        int runCount2 = bidiCreateLineBidi.getRunCount();
        byte[] bArr = new byte[runCount2];
        for (int i5 = 0; i5 < runCount2; i5++) {
            bArr[i5] = (byte) bidiCreateLineBidi.getRunLevel(i5);
        }
        Bidi.reorderVisually(bArr, 0, bidiRunArr, 0, runCount);
        int i6 = -1;
        if (iLineEndToVisibleEnd == lineStart) {
            int i7 = 0;
            while (true) {
                if (i7 >= runCount) {
                    break;
                }
                if (bidiRunArr[i7].getStart() == iLineEndToVisibleEnd) {
                    i6 = i7;
                    break;
                }
                i7++;
            }
            BidiRun bidiRun = bidiRunArr[i6];
            if (z || zIsRtlParagraph == bidiRun.isRtl()) {
                zIsRtlParagraph = !zIsRtlParagraph;
            }
            return (i6 == 0 && zIsRtlParagraph) ? this.layout.getLineLeft(lineForOffset) : (i6 != ArraysKt.getLastIndex(bidiRunArr) || zIsRtlParagraph) ? zIsRtlParagraph ? this.layout.getPrimaryHorizontal(bidiRunArr[i6 - 1].getStart()) : this.layout.getPrimaryHorizontal(bidiRunArr[i6 + 1].getStart()) : this.layout.getLineRight(lineForOffset);
        }
        if (iLineEndToVisibleEnd > iLineEndToVisibleEnd2) {
            iLineEndToVisibleEnd = lineEndToVisibleEnd(iLineEndToVisibleEnd, lineStart);
        }
        int i8 = 0;
        while (true) {
            if (i8 >= runCount) {
                break;
            }
            if (bidiRunArr[i8].getEnd() == iLineEndToVisibleEnd) {
                i6 = i8;
                break;
            }
            i8++;
        }
        BidiRun bidiRun2 = bidiRunArr[i6];
        if (!z && zIsRtlParagraph != bidiRun2.isRtl()) {
            zIsRtlParagraph = !zIsRtlParagraph;
        }
        return (i6 == 0 && zIsRtlParagraph) ? this.layout.getLineLeft(lineForOffset) : (i6 != ArraysKt.getLastIndex(bidiRunArr) || zIsRtlParagraph) ? zIsRtlParagraph ? this.layout.getPrimaryHorizontal(bidiRunArr[i6 - 1].getEnd()) : this.layout.getPrimaryHorizontal(bidiRunArr[i6 + 1].getEnd()) : this.layout.getLineRight(lineForOffset);
    }

    public final int getLineVisibleEnd(int i) {
        return lineEndToVisibleEnd(this.layout.getLineEnd(i), this.layout.getLineStart(i));
    }

    public final int getParagraphForOffset(int i, boolean z) {
        int iBinarySearch$default = CollectionsKt.binarySearch$default(this.paragraphEnds, Integer.valueOf(i), 0, 0, 6, null);
        int i2 = iBinarySearch$default < 0 ? -(iBinarySearch$default + 1) : iBinarySearch$default + 1;
        if (z && i2 > 0) {
            int i3 = i2 - 1;
            if (i == ((Number) this.paragraphEnds.get(i3)).intValue()) {
                return i3;
            }
        }
        return i2;
    }

    public final int getParagraphStart(int i) {
        if (i == 0) {
            return 0;
        }
        return ((Number) this.paragraphEnds.get(i - 1)).intValue();
    }

    public final boolean isLineEndSpace(char c) {
        if (c == ' ' || c == '\n' || c == 5760) {
            return true;
        }
        return (Intrinsics.compare(c, 8192) >= 0 && Intrinsics.compare(c, 8202) <= 0 && c != 8199) || c == 8287 || c == 12288;
    }

    public final boolean isRtlParagraph(int i) {
        return this.layout.getParagraphDirection(this.layout.getLineForOffset(getParagraphStart(i))) == -1;
    }
}
