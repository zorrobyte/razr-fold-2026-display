package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.preference.Preference;
import com.android.settingslib.widget.preference.actionbuttons.R$array;
import com.android.settingslib.widget.preference.actionbuttons.R$layout;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ActionButtonsPreference extends Preference {
    private static final boolean mIsAtLeastS = true;
    private final List mBtnBackgroundStyle1;
    private final List mBtnBackgroundStyle2;
    private final List mBtnBackgroundStyle3;
    private final List mBtnBackgroundStyle4;
    private final ButtonInfo mButton1Info;
    private final ButtonInfo mButton2Info;
    private final ButtonInfo mButton3Info;
    private final ButtonInfo mButton4Info;
    private View mDivider1;
    private View mDivider2;
    private View mDivider3;
    private final List mVisibleButtonInfos;

    class ButtonInfo {
        private Button mButton;
        private boolean mIsEnabled = true;
        private boolean mIsVisible = true;
        private boolean mIsExpressive = false;

        ButtonInfo() {
        }

        boolean isVisible() {
            return this.mButton.getVisibility() == 0;
        }
    }

    public ActionButtonsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        this.mButton3Info = new ButtonInfo();
        this.mButton4Info = new ButtonInfo();
        this.mVisibleButtonInfos = new ArrayList(4);
        this.mBtnBackgroundStyle1 = new ArrayList(1);
        this.mBtnBackgroundStyle2 = new ArrayList(2);
        this.mBtnBackgroundStyle3 = new ArrayList(3);
        this.mBtnBackgroundStyle4 = new ArrayList(4);
        init();
    }

    private void fetchDrawableArray(List list, TypedArray typedArray) {
        for (int i = 0; i < typedArray.length(); i++) {
            list.add(getContext().getDrawable(typedArray.getResourceId(i, 0)));
        }
    }

    private void init() {
        setLayoutResource(SettingsThemeHelper.isExpressiveTheme(getContext()) ? R$layout.settingslib_expressive_action_buttons : R$layout.settingslib_action_buttons);
        setSelectable(false);
        Resources resources = getContext().getResources();
        fetchDrawableArray(this.mBtnBackgroundStyle1, resources.obtainTypedArray(R$array.background_style1));
        fetchDrawableArray(this.mBtnBackgroundStyle2, resources.obtainTypedArray(R$array.background_style2));
        fetchDrawableArray(this.mBtnBackgroundStyle3, resources.obtainTypedArray(R$array.background_style3));
        fetchDrawableArray(this.mBtnBackgroundStyle4, resources.obtainTypedArray(R$array.background_style4));
    }

    private void setupBackgrounds(List list, List list2) {
        for (int i = 0; i < list2.size(); i++) {
            ((ButtonInfo) list.get(i)).mButton.setBackground((Drawable) list2.get(i));
        }
    }

    private void setupDivider1() {
        if (this.mDivider1 != null && this.mButton1Info.isVisible() && this.mButton2Info.isVisible()) {
            this.mDivider1.setVisibility(0);
        }
    }

    private void setupDivider2() {
        if (this.mDivider2 == null || !this.mButton3Info.isVisible()) {
            return;
        }
        if (this.mButton1Info.isVisible() || this.mButton2Info.isVisible()) {
            this.mDivider2.setVisibility(0);
        }
    }

    private void setupDivider3() {
        if (this.mDivider3 == null || this.mVisibleButtonInfos.size() <= 1 || !this.mButton4Info.isVisible()) {
            return;
        }
        this.mDivider3.setVisibility(0);
    }

    private void setupRtlBackgrounds(List list, List list2) {
        for (int size = list2.size() - 1; size >= 0; size--) {
            ((ButtonInfo) list.get((list2.size() - 1) - size)).mButton.setBackground((Drawable) list2.get(size));
        }
    }

    private void updateLayout() {
        if (this.mButton1Info.isVisible() && mIsAtLeastS) {
            this.mVisibleButtonInfos.add(this.mButton1Info);
        }
        if (this.mButton2Info.isVisible() && mIsAtLeastS) {
            this.mVisibleButtonInfos.add(this.mButton2Info);
        }
        if (this.mButton3Info.isVisible() && mIsAtLeastS) {
            this.mVisibleButtonInfos.add(this.mButton3Info);
        }
        if (this.mButton4Info.isVisible() && mIsAtLeastS) {
            this.mVisibleButtonInfos.add(this.mButton4Info);
        }
        if (SettingsThemeHelper.isExpressiveTheme(getContext())) {
            return;
        }
        boolean z = getContext().getResources().getConfiguration().getLayoutDirection() == 1;
        int size = this.mVisibleButtonInfos.size();
        if (size != 1) {
            if (size != 2) {
                if (size != 3) {
                    if (size != 4) {
                        Log.e("ActionButtonPreference", "No visible buttons info, skip background settings.");
                    } else if (z) {
                        setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    } else {
                        setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle4);
                    }
                } else if (z) {
                    setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                } else {
                    setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle3);
                }
            } else if (z) {
                setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            } else {
                setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle2);
            }
        } else if (z) {
            setupRtlBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        } else {
            setupBackgrounds(this.mVisibleButtonInfos, this.mBtnBackgroundStyle1);
        }
        setupDivider1();
        setupDivider2();
        setupDivider3();
    }

    @Override // androidx.preference.Preference
    protected void notifyChanged() {
        super.notifyChanged();
        if (this.mVisibleButtonInfos.isEmpty()) {
            return;
        }
        this.mVisibleButtonInfos.clear();
        updateLayout();
    }
}
