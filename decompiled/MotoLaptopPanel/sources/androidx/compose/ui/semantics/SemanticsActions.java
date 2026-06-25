package androidx.compose.ui.semantics;

import kotlin.Function;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: SemanticsProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemanticsActions {
    public static final int $stable;
    private static final SemanticsPropertyKey ClearTextSubstitution;
    private static final SemanticsPropertyKey Collapse;
    private static final SemanticsPropertyKey CopyText;
    private static final SemanticsPropertyKey CustomActions;
    private static final SemanticsPropertyKey CutText;
    private static final SemanticsPropertyKey Dismiss;
    private static final SemanticsPropertyKey Expand;
    private static final SemanticsPropertyKey GetScrollViewportLength;
    private static final SemanticsPropertyKey GetTextLayoutResult;
    public static final SemanticsActions INSTANCE = new SemanticsActions();
    private static final SemanticsPropertyKey InsertTextAtCursor;
    private static final SemanticsPropertyKey OnAutofillText;
    private static final SemanticsPropertyKey OnClick;
    private static final SemanticsPropertyKey OnImeAction;
    private static final SemanticsPropertyKey OnLongClick;
    private static final SemanticsPropertyKey PageDown;
    private static final SemanticsPropertyKey PageLeft;
    private static final SemanticsPropertyKey PageRight;
    private static final SemanticsPropertyKey PageUp;
    private static final SemanticsPropertyKey PasteText;
    private static final SemanticsPropertyKey PerformImeAction;
    private static final SemanticsPropertyKey RequestFocus;
    private static final SemanticsPropertyKey ScrollBy;
    private static final SemanticsPropertyKey ScrollByOffset;
    private static final SemanticsPropertyKey ScrollToIndex;
    private static final SemanticsPropertyKey SetProgress;
    private static final SemanticsPropertyKey SetSelection;
    private static final SemanticsPropertyKey SetText;
    private static final SemanticsPropertyKey SetTextSubstitution;
    private static final SemanticsPropertyKey ShowTextSubstitution;

    static {
        SemanticsPropertiesKt$ActionPropertyKey$1 semanticsPropertiesKt$ActionPropertyKey$1 = new Function2() { // from class: androidx.compose.ui.semantics.SemanticsPropertiesKt$ActionPropertyKey$1
            @Override // kotlin.jvm.functions.Function2
            public final AccessibilityAction invoke(AccessibilityAction accessibilityAction, AccessibilityAction accessibilityAction2) {
                String label;
                Function action;
                if (accessibilityAction == null || (label = accessibilityAction.getLabel()) == null) {
                    label = accessibilityAction2.getLabel();
                }
                if (accessibilityAction == null || (action = accessibilityAction.getAction()) == null) {
                    action = accessibilityAction2.getAction();
                }
                return new AccessibilityAction(label, action);
            }
        };
        GetTextLayoutResult = new SemanticsPropertyKey("GetTextLayoutResult", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnClick = new SemanticsPropertyKey("OnClick", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnLongClick = new SemanticsPropertyKey("OnLongClick", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ScrollBy = new SemanticsPropertyKey("ScrollBy", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ScrollByOffset = new SemanticsPropertyKey("ScrollByOffset", null, 2, null);
        ScrollToIndex = new SemanticsPropertyKey("ScrollToIndex", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnAutofillText = new SemanticsPropertyKey("OnAutofillText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetProgress = new SemanticsPropertyKey("SetProgress", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetSelection = new SemanticsPropertyKey("SetSelection", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetText = new SemanticsPropertyKey("SetText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetTextSubstitution = new SemanticsPropertyKey("SetTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ShowTextSubstitution = new SemanticsPropertyKey("ShowTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ClearTextSubstitution = new SemanticsPropertyKey("ClearTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        InsertTextAtCursor = new SemanticsPropertyKey("InsertTextAtCursor", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnImeAction = new SemanticsPropertyKey("PerformImeAction", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PerformImeAction = new SemanticsPropertyKey("PerformImeAction", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CopyText = new SemanticsPropertyKey("CopyText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CutText = new SemanticsPropertyKey("CutText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PasteText = new SemanticsPropertyKey("PasteText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Expand = new SemanticsPropertyKey("Expand", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Collapse = new SemanticsPropertyKey("Collapse", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Dismiss = new SemanticsPropertyKey("Dismiss", true, semanticsPropertiesKt$ActionPropertyKey$1);
        RequestFocus = new SemanticsPropertyKey("RequestFocus", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CustomActions = new SemanticsPropertyKey("CustomActions", true);
        PageUp = new SemanticsPropertyKey("PageUp", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageLeft = new SemanticsPropertyKey("PageLeft", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageDown = new SemanticsPropertyKey("PageDown", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageRight = new SemanticsPropertyKey("PageRight", true, semanticsPropertiesKt$ActionPropertyKey$1);
        GetScrollViewportLength = new SemanticsPropertyKey("GetScrollViewportLength", true, semanticsPropertiesKt$ActionPropertyKey$1);
        $stable = 8;
    }

    private SemanticsActions() {
    }

    public final SemanticsPropertyKey getClearTextSubstitution() {
        return ClearTextSubstitution;
    }

    public final SemanticsPropertyKey getCollapse() {
        return Collapse;
    }

    public final SemanticsPropertyKey getCopyText() {
        return CopyText;
    }

    public final SemanticsPropertyKey getCustomActions() {
        return CustomActions;
    }

    public final SemanticsPropertyKey getCutText() {
        return CutText;
    }

    public final SemanticsPropertyKey getDismiss() {
        return Dismiss;
    }

    public final SemanticsPropertyKey getExpand() {
        return Expand;
    }

    public final SemanticsPropertyKey getGetScrollViewportLength() {
        return GetScrollViewportLength;
    }

    public final SemanticsPropertyKey getGetTextLayoutResult() {
        return GetTextLayoutResult;
    }

    public final SemanticsPropertyKey getOnAutofillText() {
        return OnAutofillText;
    }

    public final SemanticsPropertyKey getOnClick() {
        return OnClick;
    }

    public final SemanticsPropertyKey getOnImeAction() {
        return OnImeAction;
    }

    public final SemanticsPropertyKey getOnLongClick() {
        return OnLongClick;
    }

    public final SemanticsPropertyKey getPageDown() {
        return PageDown;
    }

    public final SemanticsPropertyKey getPageLeft() {
        return PageLeft;
    }

    public final SemanticsPropertyKey getPageRight() {
        return PageRight;
    }

    public final SemanticsPropertyKey getPageUp() {
        return PageUp;
    }

    public final SemanticsPropertyKey getPasteText() {
        return PasteText;
    }

    public final SemanticsPropertyKey getRequestFocus() {
        return RequestFocus;
    }

    public final SemanticsPropertyKey getScrollBy() {
        return ScrollBy;
    }

    public final SemanticsPropertyKey getScrollByOffset() {
        return ScrollByOffset;
    }

    public final SemanticsPropertyKey getSetProgress() {
        return SetProgress;
    }

    public final SemanticsPropertyKey getSetSelection() {
        return SetSelection;
    }

    public final SemanticsPropertyKey getSetText() {
        return SetText;
    }

    public final SemanticsPropertyKey getSetTextSubstitution() {
        return SetTextSubstitution;
    }

    public final SemanticsPropertyKey getShowTextSubstitution() {
        return ShowTextSubstitution;
    }
}
