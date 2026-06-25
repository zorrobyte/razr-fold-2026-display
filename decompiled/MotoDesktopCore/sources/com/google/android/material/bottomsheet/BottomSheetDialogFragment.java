package com.google.android.material.bottomsheet;

import K.e;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import e0.k;

/* JADX INFO: loaded from: classes.dex */
public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        int theme = getTheme();
        if (theme == 0) {
            TypedValue typedValue = new TypedValue();
            theme = context.getTheme().resolveAttribute(R$attr.bottomSheetDialogTheme, typedValue, true) ? typedValue.resourceId : R$style.Theme_Design_Light_BottomSheetDialog;
        }
        e eVar = new e(context, theme);
        eVar.f197d = true;
        eVar.f198e = true;
        eVar.f200g = new k(eVar);
        eVar.a().t(1);
        return eVar;
    }
}
