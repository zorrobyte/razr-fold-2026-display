package com.google.android.setupdesign.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.setupdesign.R$attr;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$style;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBar extends LinearLayout implements View.OnClickListener {
    private Button backButton;
    private Button moreButton;
    private Button nextButton;

    public NavigationBar(Context context) {
        super(getThemedContext(context));
        init();
    }

    public NavigationBar(Context context, AttributeSet attributeSet) {
        super(getThemedContext(context), attributeSet);
        init();
    }

    public NavigationBar(Context context, AttributeSet attributeSet, int i) {
        super(getThemedContext(context), attributeSet, i);
        init();
    }

    private static int getNavbarTheme(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R$attr.sudNavBarTheme, R.attr.colorForeground, R.attr.colorBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId == 0) {
            float[] fArr = new float[3];
            float[] fArr2 = new float[3];
            Color.colorToHSV(typedArrayObtainStyledAttributes.getColor(1, 0), fArr);
            Color.colorToHSV(typedArrayObtainStyledAttributes.getColor(2, 0), fArr2);
            resourceId = fArr[2] > fArr2[2] ? R$style.SudNavBarThemeDark : R$style.SudNavBarThemeLight;
        }
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    private static Context getThemedContext(Context context) {
        return new ContextThemeWrapper(context, getNavbarTheme(context));
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }
        View.inflate(getContext(), R$layout.sud_navbar_view, this);
        this.nextButton = (Button) findViewById(R$id.sud_navbar_next);
        this.backButton = (Button) findViewById(R$id.sud_navbar_back);
        this.moreButton = (Button) findViewById(R$id.sud_navbar_more);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }
}
