package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.TransformState;
import com.motorola.taskbar.R$id;

/* JADX INFO: loaded from: classes.dex */
public class HybridNotificationView extends AlphaOptimizedLinearLayout implements TransformableView, NotificationFadeAware {
    protected int mPrimaryTextColor;
    protected int mSecondaryTextColor;
    protected TextView mTextView;
    protected TextView mTitleView;
    protected final ViewTransformationHelper mTransformationHelper;

    public class FadeOutAndDownWithTitleTransformation extends ViewTransformationHelper.CustomTransformation {
        private final View mView;

        public FadeOutAndDownWithTitleTransformation(View view) {
            this.mView = view;
        }

        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
            TransformState currentState = transformableView.getCurrentState(1);
            CrossFadeHelper.fadeIn(this.mView, f, true);
            if (currentState != null) {
                transformState.transformViewVerticalFrom(currentState, f);
                currentState.recycle();
            }
            return true;
        }

        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
            TransformState currentState = transformableView.getCurrentState(1);
            CrossFadeHelper.fadeOut(this.mView, f);
            if (currentState != null) {
                transformState.transformViewVerticalTo(currentState, f);
                currentState.recycle();
            }
            return true;
        }
    }

    public HybridNotificationView(Context context) {
        this(context, null);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTransformationHelper = new ViewTransformationHelper();
        this.mPrimaryTextColor = 1;
        this.mSecondaryTextColor = 1;
    }

    private void resolveThemeTextColors() {
        this.mPrimaryTextColor = ((LinearLayout) this).mContext.getColor(R.color.materialColorPrimaryFixed);
        this.mSecondaryTextColor = ((LinearLayout) this).mContext.getColor(R.color.materialColorScrim);
    }

    protected void applyTextColor(TextView textView, int i) {
        if (i != 1) {
            textView.setTextColor(i);
        }
    }

    public void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        this.mTitleView.setText(charSequence != null ? charSequence.toString() : charSequence);
        this.mTitleView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        if (TextUtils.isEmpty(charSequence2)) {
            this.mTextView.setVisibility(8);
            this.mTextView.setText((CharSequence) null);
        } else {
            this.mTextView.setVisibility(0);
            this.mTextView.setText(charSequence2.toString());
        }
        requestLayout();
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public TransformState getCurrentState(int i) {
        return this.mTransformationHelper.getCurrentState(i);
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        resolveThemeTextColors();
        this.mTitleView = (TextView) findViewById(R$id.notification_title);
        this.mTextView = (TextView) findViewById(R$id.notification_text);
        applyTextColor(this.mTitleView, this.mPrimaryTextColor);
        applyTextColor(this.mTextView, this.mSecondaryTextColor);
        this.mTransformationHelper.setCustomTransformation(new FadeOutAndDownWithTitleTransformation(this.mTextView), 2);
        this.mTransformationHelper.addTransformedView(1, this.mTitleView);
        this.mTransformationHelper.addTransformedView(2, this.mTextView);
    }

    public void setNotificationFaded(boolean z) {
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean z) {
        setVisibility(z ? 0 : 4);
        this.mTransformationHelper.setVisible(z);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(transformableView);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView transformableView, float f) {
        this.mTransformationHelper.transformFrom(transformableView, f);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView transformableView, float f) {
        this.mTransformationHelper.transformTo(transformableView, f);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView transformableView, Runnable runnable) {
        this.mTransformationHelper.transformTo(transformableView, runnable);
    }
}
