package com.motorola.taskbar.settings.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.motorola.taskbar.guide.R$drawable;

/* JADX INFO: loaded from: classes2.dex */
public class AndroidHelperFragment extends KeyboardShortcutHelperFragment {
    public AndroidHelperFragment() {
        this.mKeyboardType = 0;
    }

    private void loadResources(Context context) {
        this.mModifierDrawables.put(65536, context.getDrawable(R$drawable.ic_ksh_key_meta));
    }

    public static AndroidHelperFragment newInstance() {
        AndroidHelperFragment androidHelperFragment = new AndroidHelperFragment();
        androidHelperFragment.setArguments(new Bundle());
        return androidHelperFragment;
    }

    @Override // com.motorola.taskbar.settings.helper.KeyboardShortcutHelperFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        loadResources(getContext());
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
}
