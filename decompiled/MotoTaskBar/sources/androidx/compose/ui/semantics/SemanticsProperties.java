package androidx.compose.ui.semantics;

import androidx.compose.ui.autofill.ContentDataType;
import androidx.compose.ui.autofill.ContentType;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsProperties {
    public static final SemanticsProperties INSTANCE = new SemanticsProperties();
    private static final SemanticsPropertyKey ContentDescription = new SemanticsPropertyKey("ContentDescription", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentDescription$1
        @Override // kotlin.jvm.functions.Function2
        public final List invoke(List list, List list2) {
            List mutableList;
            if (list == null || (mutableList = CollectionsKt.toMutableList((Collection) list)) == null) {
                return list2;
            }
            mutableList.addAll(list2);
            return mutableList;
        }
    });
    private static final SemanticsPropertyKey StateDescription = new SemanticsPropertyKey("StateDescription", true);
    private static final SemanticsPropertyKey ProgressBarRangeInfo = new SemanticsPropertyKey("ProgressBarRangeInfo", true);
    private static final SemanticsPropertyKey PaneTitle = new SemanticsPropertyKey("PaneTitle", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$PaneTitle$1
        @Override // kotlin.jvm.functions.Function2
        public final String invoke(String str, String str2) {
            throw new IllegalStateException("merge function called on unmergeable property PaneTitle.");
        }
    });
    private static final SemanticsPropertyKey SelectableGroup = new SemanticsPropertyKey("SelectableGroup", true);
    private static final SemanticsPropertyKey CollectionInfo = new SemanticsPropertyKey("CollectionInfo", true);
    private static final SemanticsPropertyKey CollectionItemInfo = new SemanticsPropertyKey("CollectionItemInfo", true);
    private static final SemanticsPropertyKey Heading = new SemanticsPropertyKey("Heading", true);
    private static final SemanticsPropertyKey Disabled = new SemanticsPropertyKey("Disabled", true);
    private static final SemanticsPropertyKey LiveRegion = new SemanticsPropertyKey("LiveRegion", true);
    private static final SemanticsPropertyKey Focused = new SemanticsPropertyKey("Focused", true);
    private static final SemanticsPropertyKey IsContainer = new SemanticsPropertyKey("IsContainer", true);
    private static final SemanticsPropertyKey IsTraversalGroup = new SemanticsPropertyKey("IsTraversalGroup", null, 2, null);
    private static final SemanticsPropertyKey InvisibleToUser = new SemanticsPropertyKey("InvisibleToUser", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$InvisibleToUser$1
        @Override // kotlin.jvm.functions.Function2
        public final Unit invoke(Unit unit, Unit unit2) {
            return unit;
        }
    });
    private static final SemanticsPropertyKey HideFromAccessibility = new SemanticsPropertyKey("HideFromAccessibility", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$HideFromAccessibility$1
        @Override // kotlin.jvm.functions.Function2
        public final Unit invoke(Unit unit, Unit unit2) {
            return unit;
        }
    });
    private static final SemanticsPropertyKey ContentType = new SemanticsPropertyKey("ContentType", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentType$1
        @Override // kotlin.jvm.functions.Function2
        public final ContentType invoke(ContentType contentType, ContentType contentType2) {
            return contentType;
        }
    });
    private static final SemanticsPropertyKey ContentDataType = new SemanticsPropertyKey("ContentDataType", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentDataType$1
        @Override // kotlin.jvm.functions.Function2
        public final ContentDataType invoke(ContentDataType contentDataType, ContentDataType contentDataType2) {
            return contentDataType;
        }
    });
    private static final SemanticsPropertyKey TraversalIndex = new SemanticsPropertyKey("TraversalIndex", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$TraversalIndex$1
        public final Float invoke(Float f, float f2) {
            return f;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return invoke((Float) obj, ((Number) obj2).floatValue());
        }
    });
    private static final SemanticsPropertyKey HorizontalScrollAxisRange = new SemanticsPropertyKey("HorizontalScrollAxisRange", true);
    private static final SemanticsPropertyKey VerticalScrollAxisRange = new SemanticsPropertyKey("VerticalScrollAxisRange", true);
    private static final SemanticsPropertyKey IsPopup = new SemanticsPropertyKey("IsPopup", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$IsPopup$1
        @Override // kotlin.jvm.functions.Function2
        public final Unit invoke(Unit unit, Unit unit2) {
            throw new IllegalStateException("merge function called on unmergeable property IsPopup. A popup should not be a child of a clickable/focusable node.");
        }
    });
    private static final SemanticsPropertyKey IsDialog = new SemanticsPropertyKey("IsDialog", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$IsDialog$1
        @Override // kotlin.jvm.functions.Function2
        public final Unit invoke(Unit unit, Unit unit2) {
            throw new IllegalStateException("merge function called on unmergeable property IsDialog. A dialog should not be a child of a clickable/focusable node.");
        }
    });
    private static final SemanticsPropertyKey Role = new SemanticsPropertyKey("Role", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$Role$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return m752invokeqtAw6s((Role) obj, ((Role) obj2).m739unboximpl());
        }

        /* JADX INFO: renamed from: invoke-qtA-w6s, reason: not valid java name */
        public final Role m752invokeqtAw6s(Role role, int i) {
            return role;
        }
    });
    private static final SemanticsPropertyKey TestTag = new SemanticsPropertyKey("TestTag", false, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$TestTag$1
        @Override // kotlin.jvm.functions.Function2
        public final String invoke(String str, String str2) {
            return str;
        }
    });
    private static final SemanticsPropertyKey LinkTestMarker = new SemanticsPropertyKey("LinkTestMarker", false, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$LinkTestMarker$1
        @Override // kotlin.jvm.functions.Function2
        public final Unit invoke(Unit unit, Unit unit2) {
            return unit;
        }
    });
    private static final SemanticsPropertyKey Text = new SemanticsPropertyKey("Text", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$Text$1
        @Override // kotlin.jvm.functions.Function2
        public final List invoke(List list, List list2) {
            List mutableList;
            if (list == null || (mutableList = CollectionsKt.toMutableList((Collection) list)) == null) {
                return list2;
            }
            mutableList.addAll(list2);
            return mutableList;
        }
    });
    private static final SemanticsPropertyKey TextSubstitution = new SemanticsPropertyKey("TextSubstitution", null, 2, null);
    private static final SemanticsPropertyKey IsShowingTextSubstitution = new SemanticsPropertyKey("IsShowingTextSubstitution", null, 2, null);
    private static final SemanticsPropertyKey EditableText = new SemanticsPropertyKey("EditableText", true);
    private static final SemanticsPropertyKey TextSelectionRange = new SemanticsPropertyKey("TextSelectionRange", true);
    private static final SemanticsPropertyKey ImeAction = new SemanticsPropertyKey("ImeAction", true);
    private static final SemanticsPropertyKey Selected = new SemanticsPropertyKey("Selected", true);
    private static final SemanticsPropertyKey ToggleableState = new SemanticsPropertyKey("ToggleableState", true);
    private static final SemanticsPropertyKey Password = new SemanticsPropertyKey("Password", true);
    private static final SemanticsPropertyKey Error = new SemanticsPropertyKey("Error", true);
    private static final SemanticsPropertyKey IndexForKey = new SemanticsPropertyKey("IndexForKey", null, 2, null);
    private static final SemanticsPropertyKey IsEditable = new SemanticsPropertyKey("IsEditable", null, 2, null);
    private static final SemanticsPropertyKey MaxTextLength = new SemanticsPropertyKey("MaxTextLength", null, 2, null);
    public static final int $stable = 8;

    private SemanticsProperties() {
    }

    public final SemanticsPropertyKey getCollectionInfo() {
        return CollectionInfo;
    }

    public final SemanticsPropertyKey getCollectionItemInfo() {
        return CollectionItemInfo;
    }

    public final SemanticsPropertyKey getContentDataType() {
        return ContentDataType;
    }

    public final SemanticsPropertyKey getContentDescription() {
        return ContentDescription;
    }

    public final SemanticsPropertyKey getContentType() {
        return ContentType;
    }

    public final SemanticsPropertyKey getDisabled() {
        return Disabled;
    }

    public final SemanticsPropertyKey getEditableText() {
        return EditableText;
    }

    public final SemanticsPropertyKey getError() {
        return Error;
    }

    public final SemanticsPropertyKey getFocused() {
        return Focused;
    }

    public final SemanticsPropertyKey getHeading() {
        return Heading;
    }

    public final SemanticsPropertyKey getHideFromAccessibility() {
        return HideFromAccessibility;
    }

    public final SemanticsPropertyKey getHorizontalScrollAxisRange() {
        return HorizontalScrollAxisRange;
    }

    public final SemanticsPropertyKey getImeAction() {
        return ImeAction;
    }

    public final SemanticsPropertyKey getInvisibleToUser() {
        return InvisibleToUser;
    }

    public final SemanticsPropertyKey getIsContainer() {
        return IsContainer;
    }

    public final SemanticsPropertyKey getIsEditable() {
        return IsEditable;
    }

    public final SemanticsPropertyKey getIsShowingTextSubstitution() {
        return IsShowingTextSubstitution;
    }

    public final SemanticsPropertyKey getIsTraversalGroup() {
        return IsTraversalGroup;
    }

    public final SemanticsPropertyKey getLinkTestMarker() {
        return LinkTestMarker;
    }

    public final SemanticsPropertyKey getLiveRegion() {
        return LiveRegion;
    }

    public final SemanticsPropertyKey getMaxTextLength() {
        return MaxTextLength;
    }

    public final SemanticsPropertyKey getPaneTitle() {
        return PaneTitle;
    }

    public final SemanticsPropertyKey getPassword() {
        return Password;
    }

    public final SemanticsPropertyKey getProgressBarRangeInfo() {
        return ProgressBarRangeInfo;
    }

    public final SemanticsPropertyKey getRole() {
        return Role;
    }

    public final SemanticsPropertyKey getSelectableGroup() {
        return SelectableGroup;
    }

    public final SemanticsPropertyKey getSelected() {
        return Selected;
    }

    public final SemanticsPropertyKey getStateDescription() {
        return StateDescription;
    }

    public final SemanticsPropertyKey getTestTag() {
        return TestTag;
    }

    public final SemanticsPropertyKey getText() {
        return Text;
    }

    public final SemanticsPropertyKey getTextSelectionRange() {
        return TextSelectionRange;
    }

    public final SemanticsPropertyKey getTextSubstitution() {
        return TextSubstitution;
    }

    public final SemanticsPropertyKey getToggleableState() {
        return ToggleableState;
    }

    public final SemanticsPropertyKey getTraversalIndex() {
        return TraversalIndex;
    }

    public final SemanticsPropertyKey getVerticalScrollAxisRange() {
        return VerticalScrollAxisRange;
    }
}
