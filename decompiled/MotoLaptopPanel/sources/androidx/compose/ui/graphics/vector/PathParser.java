package androidx.compose.ui.graphics.vector;

import java.util.ArrayList;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PathParser.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PathParser {
    private float[] nodeData = new float[64];

    public static /* synthetic */ ArrayList pathStringToNodes$default(PathParser pathParser, String str, ArrayList arrayList, int i, Object obj) {
        if ((i & 2) != 0) {
            arrayList = new ArrayList();
        }
        return pathParser.pathStringToNodes(str, arrayList);
    }

    public final ArrayList pathStringToNodes(String str, ArrayList arrayList) {
        int i;
        char cCharAt;
        int i2;
        int length = str.length();
        int i3 = 0;
        while (i3 < length && Intrinsics.compare(str.charAt(i3), 32) <= 0) {
            i3++;
        }
        while (length > i3 && Intrinsics.compare(str.charAt(length - 1), 32) <= 0) {
            length--;
        }
        int i4 = 0;
        while (i3 < length) {
            while (true) {
                i = i3 + 1;
                cCharAt = str.charAt(i3);
                int i5 = cCharAt | ' ';
                if ((i5 - 97) * (i5 - 122) <= 0 && i5 != 101) {
                    break;
                }
                if (i >= length) {
                    cCharAt = 0;
                    break;
                }
                i3 = i;
            }
            if (cCharAt != 0) {
                if ((cCharAt | ' ') != 122) {
                    i4 = 0;
                    while (true) {
                        if (i >= length || Intrinsics.compare(str.charAt(i), 32) > 0) {
                            long jNextFloat = FastFloatParserKt.nextFloat(str, i, length);
                            i2 = (int) (jNextFloat >>> 32);
                            float fIntBitsToFloat = Float.intBitsToFloat((int) (jNextFloat & 4294967295L));
                            if (!Float.isNaN(fIntBitsToFloat)) {
                                float[] fArr = this.nodeData;
                                int i6 = i4 + 1;
                                fArr[i4] = fIntBitsToFloat;
                                if (i6 >= fArr.length) {
                                    float[] fArr2 = new float[i6 * 2];
                                    this.nodeData = fArr2;
                                    ArraysKt.copyInto(fArr, fArr2, 0, 0, fArr.length);
                                }
                                i4 = i6;
                            }
                            while (i2 < length && str.charAt(i2) == ',') {
                                i2++;
                            }
                            if (i2 >= length || Float.isNaN(fIntBitsToFloat)) {
                                break;
                            }
                            i = i2;
                        } else {
                            i++;
                        }
                    }
                    i = i2;
                }
                PathNodeKt.addPathNodes(cCharAt, arrayList, this.nodeData, i4);
            }
            i3 = i;
        }
        return arrayList;
    }
}
