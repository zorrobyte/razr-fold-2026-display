package androidx.compose.ui.semantics;

import androidx.compose.ui.layout.LayoutInfo;
import java.util.List;

/* JADX INFO: compiled from: SemanticsInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SemanticsInfo extends LayoutInfo {
    List getChildrenInfo();

    SemanticsInfo getParentInfo();

    SemanticsConfiguration getSemanticsConfiguration();

    boolean isTransparent();
}
