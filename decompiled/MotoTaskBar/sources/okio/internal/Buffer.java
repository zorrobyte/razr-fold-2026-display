package okio.internal;

import okio.Options;
import okio.Segment;
import okio._JvmPlatformKt;

/* JADX INFO: renamed from: okio.internal.-Buffer, reason: invalid class name */
/* JADX INFO: compiled from: Buffer.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class Buffer {
    private static final byte[] HEX_DIGIT_BYTES = _JvmPlatformKt.asUtf8ToByteArray("0123456789abcdef");
    private static final long[] DigitCountToLargestValue = {-1, 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, 9999999999L, 99999999999L, 999999999999L, 9999999999999L, 99999999999999L, 999999999999999L, 9999999999999999L, 99999999999999999L, 999999999999999999L, Long.MAX_VALUE};

    public static final boolean rangeEquals(Segment segment, int i, byte[] bArr, int i2, int i3) {
        segment.getClass();
        bArr.getClass();
        int i4 = segment.limit;
        byte[] bArr2 = segment.data;
        while (i2 < i3) {
            if (i == i4) {
                segment = segment.next;
                segment.getClass();
                byte[] bArr3 = segment.data;
                bArr2 = bArr3;
                i = segment.pos;
                i4 = segment.limit;
            }
            if (bArr2[i] != bArr[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005e, code lost:
    
        if (r19 == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0060, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0081, code lost:
    
        return r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009f A[LOOP:0: B:8:0x0020->B:46:0x009f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int selectPrefix(okio.Buffer r17, okio.Options r18, boolean r19) {
        /*
            r17.getClass()
            r18.getClass()
            r0 = r17
            okio.Segment r0 = r0.head
            r1 = -2
            r2 = -1
            if (r0 != 0) goto L12
            if (r19 == 0) goto L11
            return r1
        L11:
            return r2
        L12:
            byte[] r3 = r0.data
            int r4 = r0.pos
            int r5 = r0.limit
            int[] r6 = r18.getTrie$external__okio__android_common__okio_lib()
            r7 = 0
            r9 = r0
            r10 = r2
            r8 = r7
        L20:
            int r11 = r8 + 1
            r12 = r6[r8]
            int r8 = r8 + 2
            r11 = r6[r11]
            if (r11 == r2) goto L2b
            r10 = r11
        L2b:
            if (r9 != 0) goto L2e
            goto L5e
        L2e:
            r11 = 0
            if (r12 >= 0) goto L77
            int r12 = r12 * (-1)
            int r13 = r8 + r12
        L35:
            int r12 = r4 + 1
            r4 = r3[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r14 = r8 + 1
            r8 = r6[r8]
            if (r4 == r8) goto L42
            goto L81
        L42:
            if (r14 != r13) goto L46
            r4 = 1
            goto L47
        L46:
            r4 = r7
        L47:
            if (r12 != r5) goto L67
            r9.getClass()
            okio.Segment r3 = r9.next
            r3.getClass()
            int r5 = r3.pos
            byte[] r8 = r3.data
            int r9 = r3.limit
            if (r3 != r0) goto L61
            if (r4 == 0) goto L5e
            r3 = r8
            r8 = r11
            goto L6a
        L5e:
            if (r19 == 0) goto L81
            return r1
        L61:
            r16 = r8
            r8 = r3
            r3 = r16
            goto L6a
        L67:
            r8 = r9
            r9 = r5
            r5 = r12
        L6a:
            if (r4 == 0) goto L72
            r4 = r6[r14]
            r13 = r5
            r5 = r9
            r9 = r8
            goto L9c
        L72:
            r4 = r5
            r5 = r9
            r9 = r8
            r8 = r14
            goto L35
        L77:
            int r13 = r4 + 1
            r4 = r3[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r14 = r8 + r12
        L7f:
            if (r8 != r14) goto L82
        L81:
            return r10
        L82:
            r15 = r6[r8]
            if (r4 != r15) goto La3
            int r8 = r8 + r12
            r4 = r6[r8]
            if (r13 != r5) goto L9c
            okio.Segment r9 = r9.next
            r9.getClass()
            int r3 = r9.pos
            byte[] r5 = r9.data
            int r8 = r9.limit
            r13 = r3
            r3 = r5
            r5 = r8
            if (r9 != r0) goto L9c
            r9 = r11
        L9c:
            if (r4 < 0) goto L9f
            return r4
        L9f:
            int r8 = -r4
            r4 = r13
            goto L20
        La3:
            int r8 = r8 + 1
            goto L7f
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.Buffer.selectPrefix(okio.Buffer, okio.Options, boolean):int");
    }

    public static /* synthetic */ int selectPrefix$default(okio.Buffer buffer, Options options, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return selectPrefix(buffer, options, z);
    }
}
