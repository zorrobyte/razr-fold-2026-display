package com.android.settingslib.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.widget.preference.barchart.R$id;
import com.android.settingslib.widget.preference.barchart.R$layout;
import com.android.settingslib.widget.preference.barchart.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class BarView extends LinearLayout {
    private TextView mBarSummary;
    private TextView mBarTitle;
    private View mBarView;
    private ImageView mIcon;

    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
        int color = context.obtainStyledAttributes(new int[]{R.attr.colorAccent}).getColor(0, 0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SettingsBarView);
        int color2 = typedArrayObtainStyledAttributes.getColor(R$styleable.SettingsBarView_barColor, color);
        typedArrayObtainStyledAttributes.recycle();
        this.mBarView.setBackgroundColor(color2);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R$layout.settings_bar_view, this);
        setOrientation(1);
        setGravity(81);
        this.mBarView = findViewById(R$id.bar_view);
        this.mIcon = (ImageView) findViewById(R$id.icon_view);
        this.mBarTitle = (TextView) findViewById(R$id.bar_title);
        this.mBarSummary = (TextView) findViewById(R$id.bar_summary);
    }

    CharSequence getSummary() {
        return this.mBarSummary.getText();
    }

    CharSequence getTitle() {
        return this.mBarTitle.getText();
    }
}
