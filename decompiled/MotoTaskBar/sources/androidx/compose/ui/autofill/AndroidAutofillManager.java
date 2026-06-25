package androidx.compose.ui.autofill;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import androidx.collection.MutableIntSet;
import androidx.collection.MutableObjectList;
import androidx.collection.ObjectListKt;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.focus.FocusListener;
import androidx.compose.ui.focus.FocusTargetModifierNode;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.platform.coreshims.AutofillIdCompat;
import androidx.compose.ui.platform.coreshims.ViewCompatShims;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsInfo;
import androidx.compose.ui.semantics.SemanticsListener;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.spatial.RectManager;
import androidx.compose.ui.text.AnnotatedString;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AndroidAutofillManager.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AndroidAutofillManager extends AutofillManager implements SemanticsListener, FocusListener {
    private MutableIntSet currentlyDisplayedIDs;
    private final String packageName;
    private boolean pendingChangesToDisplayedIds;
    private PlatformAutofillManager platformAutofillManager;
    private MutableIntSet previouslyDisplayedIDs;
    private final RectManager rectManager;
    private Rect reusableRect = new Rect();
    private AutofillId rootAutofillId;
    private final SemanticsOwner semanticsOwner;
    private final View view;

    public AndroidAutofillManager(PlatformAutofillManager platformAutofillManager, SemanticsOwner semanticsOwner, View view, RectManager rectManager, String str) {
        this.platformAutofillManager = platformAutofillManager;
        this.semanticsOwner = semanticsOwner;
        this.view = view;
        this.rectManager = rectManager;
        this.packageName = str;
        view.setImportantForAutofill(1);
        AutofillIdCompat autofillId = ViewCompatShims.getAutofillId(view);
        AutofillId autofillId2 = autofillId != null ? autofillId.toAutofillId() : null;
        if (autofillId2 == null) {
            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("Required value was null.");
            throw new KotlinNothingValueException();
        }
        this.rootAutofillId = autofillId2;
        this.currentlyDisplayedIDs = new MutableIntSet(0, 1, null);
        this.previouslyDisplayedIDs = new MutableIntSet(0, 1, null);
    }

    private final void executeAutoCommit() {
        if (!AutofillUtils_androidKt.containsAll(this.currentlyDisplayedIDs, this.previouslyDisplayedIDs)) {
            this.platformAutofillManager.commit();
        }
        AutofillUtils_androidKt.copyFrom(this.previouslyDisplayedIDs, this.currentlyDisplayedIDs);
    }

    public final PlatformAutofillManager getPlatformAutofillManager() {
        return this.platformAutofillManager;
    }

    public final void onDetach$ui_release(SemanticsInfo semanticsInfo) {
        if (this.currentlyDisplayedIDs.remove(semanticsInfo.getSemanticsId())) {
            this.pendingChangesToDisplayedIds = true;
            this.platformAutofillManager.notifyViewVisibilityChanged(this.view, semanticsInfo.getSemanticsId(), false);
        }
    }

    public final void onEndApplyChanges$ui_release() {
        if (this.pendingChangesToDisplayedIds) {
            executeAutoCommit();
            this.pendingChangesToDisplayedIds = false;
        }
    }

    @Override // androidx.compose.ui.focus.FocusListener
    public void onFocusChanged(FocusTargetModifierNode focusTargetModifierNode, FocusTargetModifierNode focusTargetModifierNode2) {
        SemanticsInfo semanticsInfoRequireSemanticsInfo;
        SemanticsConfiguration semanticsConfiguration;
        SemanticsInfo semanticsInfoRequireSemanticsInfo2;
        SemanticsConfiguration semanticsConfiguration2;
        if (focusTargetModifierNode != null && (semanticsInfoRequireSemanticsInfo2 = DelegatableNodeKt.requireSemanticsInfo(focusTargetModifierNode)) != null && (semanticsConfiguration2 = semanticsInfoRequireSemanticsInfo2.getSemanticsConfiguration()) != null && AndroidAutofillManager_androidKt.isAutofillable(semanticsConfiguration2)) {
            this.platformAutofillManager.notifyViewExited(this.view, semanticsInfoRequireSemanticsInfo2.getSemanticsId());
        }
        if (focusTargetModifierNode2 == null || (semanticsInfoRequireSemanticsInfo = DelegatableNodeKt.requireSemanticsInfo(focusTargetModifierNode2)) == null || (semanticsConfiguration = semanticsInfoRequireSemanticsInfo.getSemanticsConfiguration()) == null || !AndroidAutofillManager_androidKt.isAutofillable(semanticsConfiguration)) {
            return;
        }
        final int semanticsId = semanticsInfoRequireSemanticsInfo.getSemanticsId();
        this.rectManager.getRects().withRect(semanticsId, new Function4() { // from class: androidx.compose.ui.autofill.AndroidAutofillManager$onFocusChanged$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                invoke(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i, int i2, int i3, int i4) {
                this.this$0.getPlatformAutofillManager().notifyViewEntered(this.this$0.view, semanticsId, new Rect(i, i2, i3, i4));
            }
        });
    }

    public final void onLayoutNodeDeactivated$ui_release(SemanticsInfo semanticsInfo) {
        if (this.currentlyDisplayedIDs.remove(semanticsInfo.getSemanticsId())) {
            this.pendingChangesToDisplayedIds = true;
            this.platformAutofillManager.notifyViewVisibilityChanged(this.view, semanticsInfo.getSemanticsId(), false);
        }
    }

    public final void onPostAttach$ui_release(SemanticsInfo semanticsInfo) {
        SemanticsConfiguration semanticsConfiguration = semanticsInfo.getSemanticsConfiguration();
        if (semanticsConfiguration == null || !AndroidAutofillManager_androidKt.isRelatedToAutoCommit(semanticsConfiguration)) {
            return;
        }
        this.currentlyDisplayedIDs.add(semanticsInfo.getSemanticsId());
        this.pendingChangesToDisplayedIds = true;
        this.platformAutofillManager.notifyViewVisibilityChanged(this.view, semanticsInfo.getSemanticsId(), true);
    }

    @Override // androidx.compose.ui.semantics.SemanticsListener
    public void onSemanticsChanged(SemanticsInfo semanticsInfo, SemanticsConfiguration semanticsConfiguration) {
        AnnotatedString annotatedString;
        AnnotatedString annotatedString2;
        SemanticsConfiguration semanticsConfiguration2 = semanticsInfo.getSemanticsConfiguration();
        final int semanticsId = semanticsInfo.getSemanticsId();
        String text = (semanticsConfiguration == null || (annotatedString2 = (AnnotatedString) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.INSTANCE.getEditableText())) == null) ? null : annotatedString2.getText();
        String text2 = (semanticsConfiguration2 == null || (annotatedString = (AnnotatedString) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.INSTANCE.getEditableText())) == null) ? null : annotatedString.getText();
        if (text != null && text.length() != 0 && !Intrinsics.areEqual(text, text2) && text2 != null && text2.length() != 0 && Intrinsics.areEqual((ContentDataType) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.INSTANCE.getContentDataType()), ContentDataType.Companion.getText())) {
            this.platformAutofillManager.notifyValueChanged(this.view, semanticsId, AutofillApi26Helper.INSTANCE.getAutofillTextValue(text2.toString()));
        }
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            Boolean bool = semanticsConfiguration != null ? (Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.INSTANCE.getFocused()) : null;
            Boolean bool2 = semanticsConfiguration2 != null ? (Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.INSTANCE.getFocused()) : null;
            Boolean bool3 = Boolean.TRUE;
            if (!Intrinsics.areEqual(bool, bool3) && Intrinsics.areEqual(bool2, bool3) && AndroidAutofillManager_androidKt.isAutofillable(semanticsConfiguration2)) {
                this.rectManager.getRects().withRect(semanticsId, new Function4() { // from class: androidx.compose.ui.autofill.AndroidAutofillManager.onSemanticsChanged.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                        invoke(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i, int i2, int i3, int i4) {
                        AndroidAutofillManager.this.getPlatformAutofillManager().notifyViewEntered(AndroidAutofillManager.this.view, semanticsId, new Rect(i, i2, i3, i4));
                    }
                });
            }
            if (Intrinsics.areEqual(bool, bool3) && !Intrinsics.areEqual(bool2, bool3) && AndroidAutofillManager_androidKt.isAutofillable(semanticsConfiguration)) {
                this.platformAutofillManager.notifyViewExited(this.view, semanticsId);
            }
        }
        boolean z = false;
        boolean z2 = semanticsConfiguration != null && AndroidAutofillManager_androidKt.isRelatedToAutoCommit(semanticsConfiguration);
        if (semanticsConfiguration2 != null && AndroidAutofillManager_androidKt.isRelatedToAutoCommit(semanticsConfiguration2)) {
            z = true;
        }
        if (z2 != z) {
            if (z) {
                this.currentlyDisplayedIDs.add(semanticsId);
            } else {
                this.currentlyDisplayedIDs.remove(semanticsId);
            }
            this.pendingChangesToDisplayedIds = true;
        }
    }

    public final void performAutofill(SparseArray sparseArray) {
        SemanticsConfiguration semanticsConfiguration;
        AccessibilityAction accessibilityAction;
        Function1 function1;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseArray.keyAt(i);
            AutofillValue autofillValue = (AutofillValue) sparseArray.get(iKeyAt);
            AutofillApi26Helper autofillApi26Helper = AutofillApi26Helper.INSTANCE;
            if (autofillApi26Helper.isText(autofillValue)) {
                SemanticsInfo semanticsInfo = this.semanticsOwner.get$ui_release(iKeyAt);
                if (semanticsInfo != null && (semanticsConfiguration = semanticsInfo.getSemanticsConfiguration()) != null && (accessibilityAction = (AccessibilityAction) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsActions.INSTANCE.getOnAutofillText())) != null && (function1 = (Function1) accessibilityAction.getAction()) != null) {
                }
            } else if (autofillApi26Helper.isDate(autofillValue)) {
                Log.w("ComposeAutofillManager", "Auto filling Date fields is not yet supported.");
            } else if (autofillApi26Helper.isList(autofillValue)) {
                Log.w("ComposeAutofillManager", "Auto filling dropdown lists is not yet supported.");
            } else if (autofillApi26Helper.isToggle(autofillValue)) {
                Log.w("ComposeAutofillManager", "Auto filling toggle fields are not yet supported.");
            }
        }
    }

    public final void populateViewStructure(ViewStructure viewStructure) {
        AutofillApi26Helper autofillApi26Helper = AutofillApi26Helper.INSTANCE;
        SemanticsInfo rootInfo$ui_release = this.semanticsOwner.getRootInfo$ui_release();
        PopulateViewStructure_androidKt.populate(viewStructure, rootInfo$ui_release, this.rootAutofillId, this.packageName, this.rectManager);
        MutableObjectList mutableObjectListMutableObjectListOf = ObjectListKt.mutableObjectListOf(rootInfo$ui_release, viewStructure);
        while (mutableObjectListMutableObjectListOf.isNotEmpty()) {
            Object objRemoveAt = mutableObjectListMutableObjectListOf.removeAt(mutableObjectListMutableObjectListOf._size - 1);
            objRemoveAt.getClass();
            ViewStructure viewStructure2 = (ViewStructure) objRemoveAt;
            Object objRemoveAt2 = mutableObjectListMutableObjectListOf.removeAt(mutableObjectListMutableObjectListOf._size - 1);
            objRemoveAt2.getClass();
            List childrenInfo = ((SemanticsInfo) objRemoveAt2).getChildrenInfo();
            int size = childrenInfo.size();
            for (int i = 0; i < size; i++) {
                SemanticsInfo semanticsInfo = (SemanticsInfo) childrenInfo.get(i);
                if (!semanticsInfo.isDeactivated() && semanticsInfo.isAttached() && semanticsInfo.isPlaced()) {
                    SemanticsConfiguration semanticsConfiguration = semanticsInfo.getSemanticsConfiguration();
                    if (semanticsConfiguration == null || !AndroidAutofillManager_androidKt.isRelatedToAutofill(semanticsConfiguration)) {
                        mutableObjectListMutableObjectListOf.add(semanticsInfo);
                        mutableObjectListMutableObjectListOf.add(viewStructure2);
                    } else {
                        ViewStructure viewStructureNewChild = autofillApi26Helper.newChild(viewStructure2, autofillApi26Helper.addChildCount(viewStructure2, 1));
                        PopulateViewStructure_androidKt.populate(viewStructureNewChild, semanticsInfo, this.rootAutofillId, this.packageName, this.rectManager);
                        mutableObjectListMutableObjectListOf.add(semanticsInfo);
                        mutableObjectListMutableObjectListOf.add(viewStructureNewChild);
                    }
                }
            }
        }
    }
}
