package androidx.compose.ui.platform.accessibility;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CollectionInfo.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CollectionInfo_androidKt {
    private static final boolean calculateIfHorizontallyStacked(List list) {
        List listEmptyList;
        long jM192unboximpl;
        if (list.size() < 2) {
            return true;
        }
        if (list.size() <= 1) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList();
            Object obj = list.get(0);
            int lastIndex = CollectionsKt.getLastIndex(list);
            int i = 0;
            while (i < lastIndex) {
                i++;
                Object obj2 = list.get(i);
                SemanticsNode semanticsNode = (SemanticsNode) obj2;
                SemanticsNode semanticsNode2 = (SemanticsNode) obj;
                arrayList.add(Offset.m181boximpl(Offset.m182constructorimpl((((long) Float.floatToRawIntBits(Math.abs(Float.intBitsToFloat((int) (semanticsNode2.getBoundsInRoot().m197getCenterF1C5BW0() >> 32)) - Float.intBitsToFloat((int) (semanticsNode.getBoundsInRoot().m197getCenterF1C5BW0() >> 32))))) << 32) | (((long) Float.floatToRawIntBits(Math.abs(Float.intBitsToFloat((int) (semanticsNode2.getBoundsInRoot().m197getCenterF1C5BW0() & 4294967295L)) - Float.intBitsToFloat((int) (semanticsNode.getBoundsInRoot().m197getCenterF1C5BW0() & 4294967295L))))) & 4294967295L))));
                obj = obj2;
            }
            listEmptyList = arrayList;
        }
        if (listEmptyList.size() == 1) {
            jM192unboximpl = ((Offset) CollectionsKt.first(listEmptyList)).m192unboximpl();
        } else {
            if (listEmptyList.isEmpty()) {
                ListUtilsKt.throwUnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object objFirst = CollectionsKt.first(listEmptyList);
            int lastIndex2 = CollectionsKt.getLastIndex(listEmptyList);
            if (1 <= lastIndex2) {
                int i2 = 1;
                while (true) {
                    objFirst = Offset.m181boximpl(Offset.m190plusMKHz9U(((Offset) objFirst).m192unboximpl(), ((Offset) listEmptyList.get(i2)).m192unboximpl()));
                    if (i2 == lastIndex2) {
                        break;
                    }
                    i2++;
                }
            }
            jM192unboximpl = ((Offset) objFirst).m192unboximpl();
        }
        return Float.intBitsToFloat((int) (4294967295L & jM192unboximpl)) < Float.intBitsToFloat((int) (jM192unboximpl >> 32));
    }

    public static final void setCollectionInfo(SemanticsNode semanticsNode, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        SemanticsConfiguration config = semanticsNode.getConfig();
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(SemanticsConfigurationKt.getOrNull(config, semanticsProperties.getCollectionInfo()));
        ArrayList arrayList = new ArrayList();
        if (SemanticsConfigurationKt.getOrNull(semanticsNode.getConfig(), semanticsProperties.getSelectableGroup()) != null) {
            List replacedChildren$ui_release = semanticsNode.getReplacedChildren$ui_release();
            int size = replacedChildren$ui_release.size();
            for (int i = 0; i < size; i++) {
                SemanticsNode semanticsNode2 = (SemanticsNode) replacedChildren$ui_release.get(i);
                if (semanticsNode2.getConfig().contains(SemanticsProperties.INSTANCE.getSelected())) {
                    arrayList.add(semanticsNode2);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        boolean zCalculateIfHorizontallyStacked = calculateIfHorizontallyStacked(arrayList);
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(zCalculateIfHorizontallyStacked ? 1 : arrayList.size(), zCalculateIfHorizontallyStacked ? arrayList.size() : 1, false, 0));
    }

    public static final void setCollectionItemInfo(SemanticsNode semanticsNode, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        SemanticsConfiguration config = semanticsNode.getConfig();
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(SemanticsConfigurationKt.getOrNull(config, semanticsProperties.getCollectionItemInfo()));
        SemanticsNode parent = semanticsNode.getParent();
        if (parent == null || SemanticsConfigurationKt.getOrNull(parent.getConfig(), semanticsProperties.getSelectableGroup()) == null) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(SemanticsConfigurationKt.getOrNull(parent.getConfig(), semanticsProperties.getCollectionInfo()));
        if (semanticsNode.getConfig().contains(semanticsProperties.getSelected())) {
            ArrayList arrayList = new ArrayList();
            List replacedChildren$ui_release = parent.getReplacedChildren$ui_release();
            int size = replacedChildren$ui_release.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                SemanticsNode semanticsNode2 = (SemanticsNode) replacedChildren$ui_release.get(i2);
                if (semanticsNode2.getConfig().contains(SemanticsProperties.INSTANCE.getSelected())) {
                    arrayList.add(semanticsNode2);
                    if (semanticsNode2.getLayoutNode$ui_release().getPlaceOrder$ui_release() < semanticsNode.getLayoutNode$ui_release().getPlaceOrder$ui_release()) {
                        i++;
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            boolean zCalculateIfHorizontallyStacked = calculateIfHorizontallyStacked(arrayList);
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfoCompatObtain = AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(zCalculateIfHorizontallyStacked ? 0 : i, 1, zCalculateIfHorizontallyStacked ? i : 0, 1, false, ((Boolean) semanticsNode.getConfig().getOrElse(SemanticsProperties.INSTANCE.getSelected(), new Function0() { // from class: androidx.compose.ui.platform.accessibility.CollectionInfo_androidKt$setCollectionItemInfo$itemInfo$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Boolean mo2224invoke() {
                    return Boolean.FALSE;
                }
            })).booleanValue());
            if (collectionItemInfoCompatObtain != null) {
                accessibilityNodeInfoCompat.setCollectionItemInfo(collectionItemInfoCompatObtain);
            }
        }
    }
}
