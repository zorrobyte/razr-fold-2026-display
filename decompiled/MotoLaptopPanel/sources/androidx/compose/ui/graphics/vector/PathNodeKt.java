package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.vector.PathNode;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: PathNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PathNodeKt {
    public static final void addPathNodes(char c, ArrayList arrayList, float[] fArr, int i) {
        int i2 = 0;
        switch (c) {
            case 'A':
                int i3 = i - 7;
                for (int i4 = 0; i4 <= i3; i4 += 7) {
                    arrayList.add(new PathNode.ArcTo(fArr[i4], fArr[i4 + 1], fArr[i4 + 2], Float.compare(fArr[i4 + 3], 0.0f) != 0, Float.compare(fArr[i4 + 4], 0.0f) != 0, fArr[i4 + 5], fArr[i4 + 6]));
                }
                return;
            case 'C':
                int i5 = i - 6;
                while (i2 <= i5) {
                    arrayList.add(new PathNode.CurveTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3], fArr[i2 + 4], fArr[i2 + 5]));
                    i2 += 6;
                }
                return;
            case 'H':
                int i6 = i - 1;
                while (i2 <= i6) {
                    arrayList.add(new PathNode.HorizontalTo(fArr[i2]));
                    i2++;
                }
                return;
            case 'L':
                int i7 = i - 2;
                while (i2 <= i7) {
                    arrayList.add(new PathNode.LineTo(fArr[i2], fArr[i2 + 1]));
                    i2 += 2;
                }
                return;
            case 'M':
                pathMoveNodeFromArgs(arrayList, fArr, i);
                return;
            case 'Q':
                int i8 = i - 4;
                while (i2 <= i8) {
                    arrayList.add(new PathNode.QuadTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]));
                    i2 += 4;
                }
                return;
            case 'S':
                int i9 = i - 4;
                while (i2 <= i9) {
                    arrayList.add(new PathNode.ReflectiveCurveTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]));
                    i2 += 4;
                }
                return;
            case 'T':
                int i10 = i - 2;
                while (i2 <= i10) {
                    arrayList.add(new PathNode.ReflectiveQuadTo(fArr[i2], fArr[i2 + 1]));
                    i2 += 2;
                }
                return;
            case 'V':
                int i11 = i - 1;
                while (i2 <= i11) {
                    arrayList.add(new PathNode.VerticalTo(fArr[i2]));
                    i2++;
                }
                return;
            case 'Z':
            case 'z':
                arrayList.add(PathNode.Close.INSTANCE);
                return;
            case 'a':
                int i12 = i - 7;
                for (int i13 = 0; i13 <= i12; i13 += 7) {
                    arrayList.add(new PathNode.RelativeArcTo(fArr[i13], fArr[i13 + 1], fArr[i13 + 2], Float.compare(fArr[i13 + 3], 0.0f) != 0, Float.compare(fArr[i13 + 4], 0.0f) != 0, fArr[i13 + 5], fArr[i13 + 6]));
                }
                return;
            case 'c':
                int i14 = i - 6;
                while (i2 <= i14) {
                    arrayList.add(new PathNode.RelativeCurveTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3], fArr[i2 + 4], fArr[i2 + 5]));
                    i2 += 6;
                }
                return;
            case 'h':
                int i15 = i - 1;
                while (i2 <= i15) {
                    arrayList.add(new PathNode.RelativeHorizontalTo(fArr[i2]));
                    i2++;
                }
                return;
            case 'l':
                int i16 = i - 2;
                while (i2 <= i16) {
                    arrayList.add(new PathNode.RelativeLineTo(fArr[i2], fArr[i2 + 1]));
                    i2 += 2;
                }
                return;
            case 'm':
                pathRelativeMoveNodeFromArgs(arrayList, fArr, i);
                return;
            case 'q':
                int i17 = i - 4;
                while (i2 <= i17) {
                    arrayList.add(new PathNode.RelativeQuadTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]));
                    i2 += 4;
                }
                return;
            case 's':
                int i18 = i - 4;
                while (i2 <= i18) {
                    arrayList.add(new PathNode.RelativeReflectiveCurveTo(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]));
                    i2 += 4;
                }
                return;
            case 't':
                int i19 = i - 2;
                while (i2 <= i19) {
                    arrayList.add(new PathNode.RelativeReflectiveQuadTo(fArr[i2], fArr[i2 + 1]));
                    i2 += 2;
                }
                return;
            case 'v':
                int i20 = i - 1;
                while (i2 <= i20) {
                    arrayList.add(new PathNode.RelativeVerticalTo(fArr[i2]));
                    i2++;
                }
                return;
            default:
                throw new IllegalArgumentException("Unknown command for: " + c);
        }
    }

    private static final void pathMoveNodeFromArgs(List list, float[] fArr, int i) {
        int i2 = i - 2;
        if (i2 >= 0) {
            list.add(new PathNode.MoveTo(fArr[0], fArr[1]));
            for (int i3 = 2; i3 <= i2; i3 += 2) {
                list.add(new PathNode.LineTo(fArr[i3], fArr[i3 + 1]));
            }
        }
    }

    private static final void pathRelativeMoveNodeFromArgs(List list, float[] fArr, int i) {
        int i2 = i - 2;
        if (i2 >= 0) {
            list.add(new PathNode.RelativeMoveTo(fArr[0], fArr[1]));
            for (int i3 = 2; i3 <= i2; i3 += 2) {
                list.add(new PathNode.RelativeLineTo(fArr[i3], fArr[i3 + 1]));
            }
        }
    }
}
