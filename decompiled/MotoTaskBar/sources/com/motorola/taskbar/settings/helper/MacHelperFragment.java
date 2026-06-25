package com.motorola.taskbar.settings.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.motorola.taskbar.guide.R$drawable;

/* JADX INFO: loaded from: classes2.dex */
public class MacHelperFragment extends KeyboardShortcutHelperFragment {
    public MacHelperFragment() {
        this.mKeyboardType = 1;
    }

    private void loadResources(Context context) {
        this.mModifierDrawables.put(65536, context.getDrawable(R$drawable.ic_ksh_key_meta_mac));
    }

    public static MacHelperFragment newInstance() {
        MacHelperFragment macHelperFragment = new MacHelperFragment();
        macHelperFragment.setArguments(new Bundle());
        return macHelperFragment;
    }

    @Override // com.motorola.taskbar.settings.helper.KeyboardShortcutHelperFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        loadResources(getContext());
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
}
