package androidx.compose.ui.autofill;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.util.SparseArray;
import android.view.ViewStructure;
import android.view.autofill.AutofillValue;
import java.util.Iterator;
import java.util.Map;
import kotlin.NotImplementedError;

/* JADX INFO: compiled from: AndroidAutofill.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AndroidAutofill_androidKt {
    public static final void performAutofill(AndroidAutofill androidAutofill, SparseArray sparseArray) {
        if (androidAutofill.getAutofillTree().getChildren().isEmpty()) {
            return;
        }
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseArray.keyAt(i);
            AutofillValue autofillValue = (AutofillValue) sparseArray.get(iKeyAt);
            AutofillApi26Helper autofillApi26Helper = AutofillApi26Helper.INSTANCE;
            if (autofillApi26Helper.isText(autofillValue)) {
                androidAutofill.getAutofillTree().performAutofill(iKeyAt, autofillApi26Helper.textValue(autofillValue).toString());
            } else {
                if (autofillApi26Helper.isDate(autofillValue)) {
                    throw new NotImplementedError("An operation is not implemented: b/138604541: Add onFill() callback for date");
                }
                if (autofillApi26Helper.isList(autofillValue)) {
                    throw new NotImplementedError("An operation is not implemented: b/138604541: Add onFill() callback for list");
                }
                if (autofillApi26Helper.isToggle(autofillValue)) {
                    throw new NotImplementedError("An operation is not implemented: b/138604541:  Add onFill() callback for toggle");
                }
            }
        }
    }

    public static final void populateViewStructure(AndroidAutofill androidAutofill, ViewStructure viewStructure) {
        if (androidAutofill.getAutofillTree().getChildren().isEmpty()) {
            return;
        }
        AutofillApi26Helper autofillApi26Helper = AutofillApi26Helper.INSTANCE;
        int iAddChildCount = autofillApi26Helper.addChildCount(viewStructure, androidAutofill.getAutofillTree().getChildren().size());
        Iterator it = androidAutofill.getAutofillTree().getChildren().entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int iIntValue = ((Number) entry.getKey()).intValue();
            MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(entry.getValue());
            ViewStructure viewStructureNewChild = autofillApi26Helper.newChild(viewStructure, iAddChildCount);
            autofillApi26Helper.setAutofillId(viewStructureNewChild, androidAutofill.getRootAutofillId(), iIntValue);
            autofillApi26Helper.setId(viewStructureNewChild, iIntValue, androidAutofill.getView().getContext().getPackageName(), null, null);
            autofillApi26Helper.setAutofillType(viewStructureNewChild, ContentDataType_androidKt.getDataType(ContentDataType.Companion.getText()));
            throw null;
        }
    }
}
