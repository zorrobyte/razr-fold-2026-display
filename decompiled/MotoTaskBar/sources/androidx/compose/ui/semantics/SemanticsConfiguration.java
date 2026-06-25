package androidx.compose.ui.semantics;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import java.util.Iterator;
import java.util.Map;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: SemanticsConfiguration.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsConfiguration implements SemanticsPropertyReceiver, Iterable, KMappedMarker {
    private boolean isClearingSemantics;
    private boolean isMergingSemanticsOfDescendants;
    private Map mapWrapper;
    private final MutableScatterMap props = ScatterMapKt.mutableScatterMapOf();

    public final boolean contains(SemanticsPropertyKey semanticsPropertyKey) {
        return this.props.containsKey(semanticsPropertyKey);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean containsImportantForAccessibility$ui_release() {
        /*
            r14 = this;
            androidx.collection.MutableScatterMap r14 = r14.props
            java.lang.Object[] r0 = r14.keys
            java.lang.Object[] r1 = r14.values
            long[] r14 = r14.metadata
            int r2 = r14.length
            int r2 = r2 + (-2)
            r3 = 0
            if (r2 < 0) goto L50
            r4 = r3
        Lf:
            r5 = r14[r4]
            long r7 = ~r5
            r9 = 7
            long r7 = r7 << r9
            long r7 = r7 & r5
            r9 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r7 = r7 & r9
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 == 0) goto L4b
            int r7 = r4 - r2
            int r7 = ~r7
            int r7 = r7 >>> 31
            r8 = 8
            int r7 = 8 - r7
            r9 = r3
        L29:
            if (r9 >= r7) goto L49
            r10 = 255(0xff, double:1.26E-321)
            long r10 = r10 & r5
            r12 = 128(0x80, double:6.32E-322)
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 >= 0) goto L45
            int r10 = r4 << 3
            int r10 = r10 + r9
            r11 = r0[r10]
            r10 = r1[r10]
            androidx.compose.ui.semantics.SemanticsPropertyKey r11 = (androidx.compose.ui.semantics.SemanticsPropertyKey) r11
            boolean r10 = r11.isImportantForAccessibility$ui_release()
            if (r10 == 0) goto L45
            r14 = 1
            return r14
        L45:
            long r5 = r5 >> r8
            int r9 = r9 + 1
            goto L29
        L49:
            if (r7 != r8) goto L50
        L4b:
            if (r4 == r2) goto L50
            int r4 = r4 + 1
            goto Lf
        L50:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.semantics.SemanticsConfiguration.containsImportantForAccessibility$ui_release():boolean");
    }

    public final SemanticsConfiguration copy() {
        SemanticsConfiguration semanticsConfiguration = new SemanticsConfiguration();
        semanticsConfiguration.isMergingSemanticsOfDescendants = this.isMergingSemanticsOfDescendants;
        semanticsConfiguration.isClearingSemantics = this.isClearingSemantics;
        semanticsConfiguration.props.putAll(this.props);
        return semanticsConfiguration;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SemanticsConfiguration)) {
            return false;
        }
        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) obj;
        return Intrinsics.areEqual(this.props, semanticsConfiguration.props) && this.isMergingSemanticsOfDescendants == semanticsConfiguration.isMergingSemanticsOfDescendants && this.isClearingSemantics == semanticsConfiguration.isClearingSemantics;
    }

    public final Object get(SemanticsPropertyKey semanticsPropertyKey) {
        Object obj = this.props.get(semanticsPropertyKey);
        if (obj != null) {
            return obj;
        }
        throw new IllegalStateException("Key not present: " + semanticsPropertyKey + " - consider getOrElse or getOrNull");
    }

    public final Object getOrElse(SemanticsPropertyKey semanticsPropertyKey, Function0 function0) {
        Object obj = this.props.get(semanticsPropertyKey);
        return obj == null ? function0.mo2224invoke() : obj;
    }

    public final Object getOrElseNullable(SemanticsPropertyKey semanticsPropertyKey, Function0 function0) {
        Object obj = this.props.get(semanticsPropertyKey);
        return obj == null ? function0.mo2224invoke() : obj;
    }

    public final MutableScatterMap getProps$ui_release() {
        return this.props;
    }

    public int hashCode() {
        return (((this.props.hashCode() * 31) + Boolean.hashCode(this.isMergingSemanticsOfDescendants)) * 31) + Boolean.hashCode(this.isClearingSemantics);
    }

    public final boolean isClearingSemantics() {
        return this.isClearingSemantics;
    }

    public final boolean isMergingSemanticsOfDescendants() {
        return this.isMergingSemanticsOfDescendants;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        Map mapAsMap = this.mapWrapper;
        if (mapAsMap == null) {
            mapAsMap = this.props.asMap();
            this.mapWrapper = mapAsMap;
        }
        return mapAsMap.entrySet().iterator();
    }

    public final void mergeChild$ui_release(SemanticsConfiguration semanticsConfiguration) {
        MutableScatterMap mutableScatterMap = semanticsConfiguration.props;
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = objArr[i4];
                        Object obj2 = objArr2[i4];
                        SemanticsPropertyKey semanticsPropertyKey = (SemanticsPropertyKey) obj;
                        Object obj3 = this.props.get(semanticsPropertyKey);
                        semanticsPropertyKey.getClass();
                        Object objMerge = semanticsPropertyKey.merge(obj3, obj2);
                        if (objMerge != null) {
                            this.props.set(semanticsPropertyKey, objMerge);
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    @Override // androidx.compose.ui.semantics.SemanticsPropertyReceiver
    public void set(SemanticsPropertyKey semanticsPropertyKey, Object obj) {
        if (!(obj instanceof AccessibilityAction) || !contains(semanticsPropertyKey)) {
            this.props.set(semanticsPropertyKey, obj);
            return;
        }
        Object obj2 = this.props.get(semanticsPropertyKey);
        obj2.getClass();
        AccessibilityAction accessibilityAction = (AccessibilityAction) obj2;
        MutableScatterMap mutableScatterMap = this.props;
        AccessibilityAction accessibilityAction2 = (AccessibilityAction) obj;
        String label = accessibilityAction2.getLabel();
        if (label == null) {
            label = accessibilityAction.getLabel();
        }
        Function action = accessibilityAction2.getAction();
        if (action == null) {
            action = accessibilityAction.getAction();
        }
        mutableScatterMap.set(semanticsPropertyKey, new AccessibilityAction(label, action));
    }

    public final void setClearingSemantics(boolean z) {
        this.isClearingSemantics = z;
    }

    public final void setMergingSemanticsOfDescendants(boolean z) {
        this.isMergingSemanticsOfDescendants = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007b A[PHI: r4
      0x007b: PHI (r4v4 java.lang.String) = (r4v3 java.lang.String), (r4v5 java.lang.String) binds: [B:12:0x0042, B:19:0x0079] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String toString() {
        /*
            r19 = this;
            r0 = r19
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            boolean r2 = r0.isMergingSemanticsOfDescendants
            java.lang.String r3 = ", "
            java.lang.String r4 = ""
            if (r2 == 0) goto L18
            r1.append(r4)
            java.lang.String r2 = "mergeDescendants=true"
            r1.append(r2)
            r4 = r3
        L18:
            boolean r2 = r0.isClearingSemantics
            if (r2 == 0) goto L25
            r1.append(r4)
            java.lang.String r2 = "isClearingSemantics=true"
            r1.append(r2)
            r4 = r3
        L25:
            androidx.collection.MutableScatterMap r2 = r0.props
            java.lang.Object[] r5 = r2.keys
            java.lang.Object[] r6 = r2.values
            long[] r2 = r2.metadata
            int r7 = r2.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto L80
            r8 = 0
            r9 = r8
        L34:
            r10 = r2[r9]
            long r12 = ~r10
            r14 = 7
            long r12 = r12 << r14
            long r12 = r12 & r10
            r14 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r12 = r12 & r14
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 == 0) goto L7b
            int r12 = r9 - r7
            int r12 = ~r12
            int r12 = r12 >>> 31
            r13 = 8
            int r12 = 8 - r12
            r14 = r8
        L4e:
            if (r14 >= r12) goto L79
            r15 = 255(0xff, double:1.26E-321)
            long r15 = r15 & r10
            r17 = 128(0x80, double:6.32E-322)
            int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r15 >= 0) goto L75
            int r15 = r9 << 3
            int r15 = r15 + r14
            r16 = r5[r15]
            r15 = r6[r15]
            androidx.compose.ui.semantics.SemanticsPropertyKey r16 = (androidx.compose.ui.semantics.SemanticsPropertyKey) r16
            r1.append(r4)
            java.lang.String r4 = r16.getName()
            r1.append(r4)
            java.lang.String r4 = " : "
            r1.append(r4)
            r1.append(r15)
            r4 = r3
        L75:
            long r10 = r10 >> r13
            int r14 = r14 + 1
            goto L4e
        L79:
            if (r12 != r13) goto L80
        L7b:
            if (r9 == r7) goto L80
            int r9 = r9 + 1
            goto L34
        L80:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = 0
            java.lang.String r0 = androidx.compose.ui.platform.JvmActuals_jvmKt.simpleIdentityToString(r0, r3)
            r2.append(r0)
            java.lang.String r0 = "{ "
            r2.append(r0)
            r2.append(r1)
            java.lang.String r0 = " }"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.semantics.SemanticsConfiguration.toString():java.lang.String");
    }
}
