package androidx.compose.ui.platform;

import androidx.collection.IntObjectMap;
import androidx.collection.MutableIntSet;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.List;

/* JADX INFO: compiled from: SemanticsUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsNodeCopy {
    private final MutableIntSet children;
    private final SemanticsConfiguration unmergedConfig;

    public SemanticsNodeCopy(SemanticsNode semanticsNode, IntObjectMap intObjectMap) {
        this.unmergedConfig = semanticsNode.getUnmergedConfig$ui_release();
        this.children = new MutableIntSet(semanticsNode.getReplacedChildren$ui_release().size());
        List replacedChildren$ui_release = semanticsNode.getReplacedChildren$ui_release();
        int size = replacedChildren$ui_release.size();
        for (int i = 0; i < size; i++) {
            SemanticsNode semanticsNode2 = (SemanticsNode) replacedChildren$ui_release.get(i);
            if (intObjectMap.containsKey(semanticsNode2.getId())) {
                this.children.add(semanticsNode2.getId());
            }
        }
    }

    public final MutableIntSet getChildren() {
        return this.children;
    }

    public final SemanticsConfiguration getUnmergedConfig() {
        return this.unmergedConfig;
    }
}
