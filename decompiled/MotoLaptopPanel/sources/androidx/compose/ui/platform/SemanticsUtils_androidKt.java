package androidx.compose.ui.platform;

import android.graphics.Region;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.view.View;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutInfo;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.text.TextLayoutResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: SemanticsUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SemanticsUtils_androidKt {
    private static final Rect DefaultFakeNodeBounds = new Rect(0.0f, 0.0f, 10.0f, 10.0f);

    public static final ScrollObservationScope findById(List list, int i) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((ScrollObservationScope) list.get(i2)).getSemanticsNodeId() == i) {
                return (ScrollObservationScope) list.get(i2);
            }
        }
        return null;
    }

    public static final IntObjectMap getAllUncoveredSemanticsNodesToIntObjectMap(SemanticsOwner semanticsOwner) {
        SemanticsNode unmergedRootSemanticsNode = semanticsOwner.getUnmergedRootSemanticsNode();
        if (!unmergedRootSemanticsNode.getLayoutNode$ui_release().isPlaced() || !unmergedRootSemanticsNode.getLayoutNode$ui_release().isAttached()) {
            return IntObjectMapKt.emptyIntObjectMap();
        }
        MutableIntObjectMap mutableIntObjectMap = new MutableIntObjectMap(48);
        Rect boundsInRoot = unmergedRootSemanticsNode.getBoundsInRoot();
        getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(new Region(Math.round(boundsInRoot.getLeft()), Math.round(boundsInRoot.getTop()), Math.round(boundsInRoot.getRight()), Math.round(boundsInRoot.getBottom())), unmergedRootSemanticsNode, mutableIntObjectMap, unmergedRootSemanticsNode, new Region());
        return mutableIntObjectMap;
    }

    private static final void getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(Region region, SemanticsNode semanticsNode, MutableIntObjectMap mutableIntObjectMap, SemanticsNode semanticsNode2, Region region2) {
        LayoutInfo layoutInfo;
        boolean z = (semanticsNode2.getLayoutNode$ui_release().isPlaced() && semanticsNode2.getLayoutNode$ui_release().isAttached()) ? false : true;
        if (!region.isEmpty() || semanticsNode2.getId() == semanticsNode.getId()) {
            if (!z || semanticsNode2.isFake$ui_release()) {
                Rect touchBoundsInRoot = semanticsNode2.getTouchBoundsInRoot();
                int iRound = Math.round(touchBoundsInRoot.getLeft());
                int iRound2 = Math.round(touchBoundsInRoot.getTop());
                int iRound3 = Math.round(touchBoundsInRoot.getRight());
                int iRound4 = Math.round(touchBoundsInRoot.getBottom());
                region2.set(iRound, iRound2, iRound3, iRound4);
                int id = semanticsNode2.getId() == semanticsNode.getId() ? -1 : semanticsNode2.getId();
                if (!region2.op(region, Region.Op.INTERSECT)) {
                    if (semanticsNode2.isFake$ui_release()) {
                        SemanticsNode parent = semanticsNode2.getParent();
                        Rect boundsInRoot = (parent == null || (layoutInfo = parent.getLayoutInfo()) == null || !layoutInfo.isPlaced()) ? DefaultFakeNodeBounds : parent.getBoundsInRoot();
                        mutableIntObjectMap.set(id, new SemanticsNodeWithAdjustedBounds(semanticsNode2, new android.graphics.Rect(Math.round(boundsInRoot.getLeft()), Math.round(boundsInRoot.getTop()), Math.round(boundsInRoot.getRight()), Math.round(boundsInRoot.getBottom()))));
                        return;
                    } else {
                        if (id == -1) {
                            mutableIntObjectMap.set(id, new SemanticsNodeWithAdjustedBounds(semanticsNode2, region2.getBounds()));
                            return;
                        }
                        return;
                    }
                }
                mutableIntObjectMap.set(id, new SemanticsNodeWithAdjustedBounds(semanticsNode2, region2.getBounds()));
                List replacedChildren$ui_release = semanticsNode2.getReplacedChildren$ui_release();
                for (int size = replacedChildren$ui_release.size() - 1; -1 < size; size--) {
                    if (!((SemanticsNode) replacedChildren$ui_release.get(size)).getConfig().contains(SemanticsProperties.INSTANCE.getLinkTestMarker())) {
                        getAllUncoveredSemanticsNodesToIntObjectMap$findAllSemanticNodesRecursive(region, semanticsNode, mutableIntObjectMap, (SemanticsNode) replacedChildren$ui_release.get(size), region2);
                    }
                }
                if (isImportantForAccessibility(semanticsNode2)) {
                    region.op(iRound, iRound2, iRound3, iRound4, Region.Op.DIFFERENCE);
                }
            }
        }
    }

    public static final Float getScrollViewportLength(SemanticsConfiguration semanticsConfiguration) {
        Function1 function1;
        ArrayList arrayList = new ArrayList();
        AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.INSTANCE.getGetScrollViewportLength());
        if (accessibilityAction == null || (function1 = (Function1) accessibilityAction.getAction()) == null || !((Boolean) function1.invoke(arrayList)).booleanValue()) {
            return null;
        }
        return (Float) arrayList.get(0);
    }

    public static final TextLayoutResult getTextLayoutResult(SemanticsConfiguration semanticsConfiguration) {
        Function1 function1;
        ArrayList arrayList = new ArrayList();
        AccessibilityAction accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.INSTANCE.getGetTextLayoutResult());
        if (accessibilityAction == null || (function1 = (Function1) accessibilityAction.getAction()) == null || !((Boolean) function1.invoke(arrayList)).booleanValue()) {
            return null;
        }
        return (TextLayoutResult) arrayList.get(0);
    }

    public static final boolean isHidden(SemanticsNode semanticsNode) {
        if (semanticsNode.isTransparent$ui_release()) {
            return true;
        }
        SemanticsConfiguration unmergedConfig$ui_release = semanticsNode.getUnmergedConfig$ui_release();
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        return unmergedConfig$ui_release.contains(semanticsProperties.getHideFromAccessibility()) || semanticsNode.getUnmergedConfig$ui_release().contains(semanticsProperties.getInvisibleToUser());
    }

    public static final boolean isImportantForAccessibility(SemanticsNode semanticsNode) {
        if (isHidden(semanticsNode)) {
            return false;
        }
        return semanticsNode.getUnmergedConfig$ui_release().isMergingSemanticsOfDescendants() || semanticsNode.getUnmergedConfig$ui_release().containsImportantForAccessibility$ui_release();
    }

    public static final View semanticsIdToView(AndroidViewsHandler androidViewsHandler, int i) {
        Object next;
        Iterator it = androidViewsHandler.getLayoutNodeToHolder().entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((LayoutNode) ((Map.Entry) next).getKey()).getSemanticsId() == i) {
                break;
            }
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(entry.getValue());
        }
        return null;
    }

    /* JADX INFO: renamed from: toLegacyClassName-V4PA4sw, reason: not valid java name */
    public static final String m1478toLegacyClassNameV4PA4sw(int i) {
        Role.Companion companion = Role.Companion;
        if (Role.m1485equalsimpl0(i, companion.m1489getButtono7Vup1c())) {
            return "android.widget.Button";
        }
        if (Role.m1485equalsimpl0(i, companion.m1491getCheckboxo7Vup1c())) {
            return "android.widget.CheckBox";
        }
        if (Role.m1485equalsimpl0(i, companion.m1494getRadioButtono7Vup1c())) {
            return "android.widget.RadioButton";
        }
        if (Role.m1485equalsimpl0(i, companion.m1493getImageo7Vup1c())) {
            return "android.widget.ImageView";
        }
        if (Role.m1485equalsimpl0(i, companion.m1492getDropdownListo7Vup1c())) {
            return "android.widget.Spinner";
        }
        if (Role.m1485equalsimpl0(i, companion.m1497getValuePickero7Vup1c())) {
            return "android.widget.NumberPicker";
        }
        return null;
    }
}
