package androidx.compose.ui.autofill;

import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

/* JADX INFO: compiled from: AutofillUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AutofillApi26Helper {
    public static final AutofillApi26Helper INSTANCE = new AutofillApi26Helper();

    private AutofillApi26Helper() {
    }

    public final int addChildCount(ViewStructure viewStructure, int i) {
        return viewStructure.addChildCount(i);
    }

    public final AutofillValue getAutofillTextValue(String str) {
        return AutofillValue.forText(str);
    }

    public final boolean isDate(AutofillValue autofillValue) {
        return autofillValue.isDate();
    }

    public final boolean isList(AutofillValue autofillValue) {
        return autofillValue.isList();
    }

    public final boolean isText(AutofillValue autofillValue) {
        return autofillValue.isText();
    }

    public final boolean isToggle(AutofillValue autofillValue) {
        return autofillValue.isToggle();
    }

    public final ViewStructure newChild(ViewStructure viewStructure, int i) {
        return viewStructure.newChild(i);
    }

    public final void setAutofillHints(ViewStructure viewStructure, String[] strArr) {
        viewStructure.setAutofillHints(strArr);
    }

    public final void setAutofillId(ViewStructure viewStructure, AutofillId autofillId, int i) {
        viewStructure.setAutofillId(autofillId, i);
    }

    public final void setAutofillType(ViewStructure viewStructure, int i) {
        viewStructure.setAutofillType(i);
    }

    public final void setAutofillValue(ViewStructure viewStructure, AutofillValue autofillValue) {
        viewStructure.setAutofillValue(autofillValue);
    }

    public final void setCheckable(ViewStructure viewStructure, boolean z) {
        viewStructure.setCheckable(z);
    }

    public final void setChecked(ViewStructure viewStructure, boolean z) {
        viewStructure.setChecked(z);
    }

    public final void setClassName(ViewStructure viewStructure, String str) {
        viewStructure.setClassName(str);
    }

    public final void setClickable(ViewStructure viewStructure, boolean z) {
        viewStructure.setClickable(z);
    }

    public final void setContentDescription(ViewStructure viewStructure, CharSequence charSequence) {
        viewStructure.setContentDescription(charSequence);
    }

    public final void setDataIsSensitive(ViewStructure viewStructure, boolean z) {
        viewStructure.setDataIsSensitive(z);
    }

    public final void setDimens(ViewStructure viewStructure, int i, int i2, int i3, int i4, int i5, int i6) {
        viewStructure.setDimens(i, i2, i3, i4, i5, i6);
    }

    public final void setEnabled(ViewStructure viewStructure, boolean z) {
        viewStructure.setEnabled(z);
    }

    public final void setFocusable(ViewStructure viewStructure, boolean z) {
        viewStructure.setFocusable(z);
    }

    public final void setFocused(ViewStructure viewStructure, boolean z) {
        viewStructure.setFocused(z);
    }

    public final void setId(ViewStructure viewStructure, int i, String str, String str2, String str3) {
        viewStructure.setId(i, str, str2, str3);
    }

    public final void setInputType(ViewStructure viewStructure, int i) {
        viewStructure.setInputType(i);
    }

    public final void setLongClickable(ViewStructure viewStructure, boolean z) {
        viewStructure.setLongClickable(z);
    }

    public final void setSelected(ViewStructure viewStructure, boolean z) {
        viewStructure.setSelected(z);
    }

    public final void setText(ViewStructure viewStructure, CharSequence charSequence) {
        viewStructure.setText(charSequence);
    }

    public final void setVisibility(ViewStructure viewStructure, int i) {
        viewStructure.setVisibility(i);
    }

    public final CharSequence textValue(AutofillValue autofillValue) {
        return autofillValue.getTextValue();
    }
}
