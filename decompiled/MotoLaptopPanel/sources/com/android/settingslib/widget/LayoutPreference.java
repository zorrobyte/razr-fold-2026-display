package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.R$styleable;
import com.android.settingslib.widget.preference.layout.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class LayoutPreference extends Preference {
    private boolean mAllowDividerAbove;
    private boolean mAllowDividerBelow;
    private final View.OnClickListener mClickListener;
    private View mRootView;

    public LayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        };
        init(context, attributeSet, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        int[] iArr = R$styleable.Preference;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        int i2 = R$styleable.Preference_allowDividerAbove;
        this.mAllowDividerAbove = TypedArrayUtils.getBoolean(typedArrayObtainStyledAttributes, i2, i2, false);
        int i3 = R$styleable.Preference_allowDividerBelow;
        this.mAllowDividerBelow = TypedArrayUtils.getBoolean(typedArrayObtainStyledAttributes, i3, i3, false);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        int resourceId = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.Preference_android_layout, 0);
        if (resourceId == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        typedArrayObtainStyledAttributes2.recycle();
        setView(LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) null, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        performClick(view);
    }

    private void setView(View view) {
        setLayoutResource(R$layout.layout_preference_frame);
        this.mRootView = view;
        setShouldDisableView(false);
    }
}
