package com.motorola.settings.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.widget.theme.R$id;
import com.android.settingslib.widget.theme.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class MotoButtonBarLayout extends LinearLayout {
    private boolean mAllowStacking;
    private int mLastWidthSize;
    private boolean mStacked;

    public MotoButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MotoButtonBarLayout);
        this.mAllowStacking = typedArrayObtainStyledAttributes.getBoolean(R$styleable.MotoButtonBarLayout_allowStacking, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static Activity findActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private int getNextVisibleChildIndex(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            if (getChildAt(i).getVisibility() == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean isStacked() {
        return this.mStacked;
    }

    private void setStacked(boolean z) {
        if (this.mStacked != z) {
            if (!z || this.mAllowStacking) {
                this.mStacked = z;
                setOrientation(z ? 1 : 0);
                setGravity(z ? 8388613 : 80);
                View viewFindViewById = findViewById(R$id.spacer);
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(z ? 8 : 4);
                }
                for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
                    bringChildToFront(getChildAt(childCount));
                }
            }
        }
    }

    private boolean shouldButtonsBeStacked() {
        if (isStacked()) {
            return true;
        }
        int childCount = getChildCount();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 <= childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                CharSequence text = textView.getText();
                if (!TextUtils.isEmpty(text)) {
                    i++;
                    float fMeasureText = textView.getPaint().measureText(text.toString());
                    float measuredWidth = textView.getMeasuredWidth();
                    if (measuredWidth != 0.0f && measuredWidth <= fMeasureText) {
                        z = true;
                    }
                }
            }
        }
        return i >= childCount || z;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int iMakeMeasureSpec;
        boolean z;
        int size = View.MeasureSpec.getSize(i);
        boolean zShouldButtonsBeStacked = shouldButtonsBeStacked();
        if (zShouldButtonsBeStacked && !isStacked()) {
            setStacked(true);
        }
        int paddingBottom = 0;
        if (this.mAllowStacking) {
            if (size > this.mLastWidthSize && isStacked() && !zShouldButtonsBeStacked) {
                setStacked(false);
            }
            this.mLastWidthSize = size;
        }
        if (isStacked() || View.MeasureSpec.getMode(i) != 1073741824) {
            iMakeMeasureSpec = i;
            z = false;
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z = true;
        }
        super.onMeasure(iMakeMeasureSpec, i2);
        if (this.mAllowStacking && !isStacked() && (getMeasuredWidthAndState() & (-16777216)) == 16777216) {
            setStacked(true);
            z = true;
        }
        if (z) {
            super.onMeasure(i, i2);
        }
        int nextVisibleChildIndex = getNextVisibleChildIndex(0);
        if (nextVisibleChildIndex >= 0) {
            View childAt = getChildAt(nextVisibleChildIndex);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            int paddingTop = getPaddingTop() + childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (isStacked()) {
                boolean z2 = getResources().getConfiguration().orientation == 2;
                Activity activityFindActivity = findActivity(getContext());
                if (activityFindActivity != null && activityFindActivity.isInMultiWindowMode()) {
                    paddingBottom = 1;
                }
                if (z2 || paddingBottom != 0) {
                    if (getNextVisibleChildIndex(nextVisibleChildIndex + 1) >= 0) {
                        paddingTop = (int) (paddingTop + getChildAt(r6).getPaddingTop() + (getResources().getDisplayMetrics().density * 16.0f));
                    }
                } else {
                    int i3 = nextVisibleChildIndex + 1;
                    while (true) {
                        int nextVisibleChildIndex2 = getNextVisibleChildIndex(i3);
                        if (nextVisibleChildIndex2 == -1) {
                            break;
                        }
                        View childAt2 = getChildAt(nextVisibleChildIndex2);
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt2.getLayoutParams();
                        paddingTop += childAt2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                        i3 = nextVisibleChildIndex2 + 1;
                    }
                    paddingTop += getPaddingBottom();
                }
                paddingBottom = paddingTop;
            } else {
                paddingBottom = paddingTop + getPaddingBottom();
            }
        }
        if (getMinimumHeight() != paddingBottom) {
            setMinimumHeight(paddingBottom);
        }
    }
}
