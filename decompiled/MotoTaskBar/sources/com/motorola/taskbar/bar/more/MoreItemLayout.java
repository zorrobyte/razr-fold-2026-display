package com.motorola.taskbar.bar.more;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.bar.DynamicSysIconView;
import com.motorola.taskbar.bar.TaskBarIconData;
import com.motorola.taskbar.bar.TaskBarView;

/* JADX INFO: loaded from: classes2.dex */
public class MoreItemLayout extends LinearLayout implements View.OnClickListener {
    private MorePanelImageView mImageView;
    private OnItemClickedCallback mOnItemClickedCallback;
    private FrameLayout mProviderDrawableLayout;
    private FrameLayout mProviderTitleLayout;
    private TaskBarIconData mTaskBarIconData;
    private TextView mTipTextView;

    public interface OnItemClickedCallback {
        void OnItemClicked();
    }

    public MoreItemLayout(Context context) {
        super(context);
    }

    public MoreItemLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MoreItemLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MoreItemLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void inflateView(TaskBarIconData taskBarIconData, OnItemClickedCallback onItemClickedCallback) {
        DynamicSysIconView dynamicIconView;
        Drawable icon;
        if (taskBarIconData == null) {
            return;
        }
        this.mTaskBarIconData = taskBarIconData;
        this.mOnItemClickedCallback = onItemClickedCallback;
        float f = getContext().getResources().getFloat(R$dimen.taskbar_icon_dark_intensity);
        TaskBarView.DesktopStatusBarIconInfo desktopStatusBarIconInfo = taskBarIconData.statusBarIconInfo;
        if (desktopStatusBarIconInfo != null) {
            StatusBarIcon statusBarIcon = desktopStatusBarIconInfo.getStatusBarIcon();
            if (statusBarIcon == null || (dynamicIconView = taskBarIconData.statusBarIconInfo.getDynamicIconView()) == null || (icon = dynamicIconView.getIcon()) == null) {
                return;
            }
            KeyButtonDrawable keyButtonDrawableCreate = KeyButtonDrawable.create(getContext(), icon, false);
            this.mImageView.setImageLevel(statusBarIcon.iconLevel);
            this.mImageView.setImageDrawable(keyButtonDrawableCreate);
            this.mImageView.setDarkIntensity(f);
            this.mImageView.setVisibility(0);
            TextView textView = this.mTipTextView;
            CharSequence charSequence = statusBarIcon.contentDescription;
            textView.setText(charSequence != null ? charSequence.toString() : "");
            this.mTipTextView.setVisibility(0);
            this.mProviderTitleLayout.setVisibility(8);
            this.mProviderDrawableLayout.setVisibility(8);
        } else {
            TaskBarIconData.TaskBarIconProvider taskBarIconProvider = taskBarIconData.provider;
            if (taskBarIconProvider != null) {
                View drawableView = taskBarIconProvider.getDrawableView();
                View titleView = taskBarIconData.provider.getTitleView();
                if (drawableView != null) {
                    ViewGroup viewGroup = (ViewGroup) drawableView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(drawableView);
                    }
                    this.mProviderDrawableLayout.addView(drawableView);
                    this.mProviderDrawableLayout.setVisibility(0);
                    taskBarIconData.provider.setDarkIntensity(f);
                    this.mImageView.setVisibility(8);
                } else if (taskBarIconData.drawableId > 0) {
                    this.mImageView.setImageDrawable(KeyButtonDrawable.create(getContext(), taskBarIconData.drawableId, false));
                    this.mImageView.setDarkIntensity(f);
                    this.mProviderDrawableLayout.setVisibility(8);
                    this.mImageView.setVisibility(0);
                }
                if (titleView != null) {
                    ViewGroup viewGroup2 = (ViewGroup) titleView.getParent();
                    if (viewGroup2 != null) {
                        viewGroup2.removeView(titleView);
                    }
                    this.mProviderTitleLayout.addView(titleView);
                    this.mProviderTitleLayout.setVisibility(0);
                    this.mTipTextView.setVisibility(8);
                } else if (taskBarIconData.tipId > 0) {
                    this.mTipTextView.setText(getResources().getString(taskBarIconData.tipId));
                    this.mProviderTitleLayout.setVisibility(8);
                    this.mTipTextView.setVisibility(0);
                }
            } else {
                this.mProviderDrawableLayout.setVisibility(8);
                this.mProviderTitleLayout.setVisibility(8);
                if (taskBarIconData.drawableId > 0) {
                    this.mImageView.setImageDrawable(KeyButtonDrawable.create(getContext(), taskBarIconData.drawableId, false));
                    this.mImageView.setDarkIntensity(f);
                    this.mImageView.setVisibility(0);
                }
                if (taskBarIconData.tipId > 0) {
                    this.mTipTextView.setText(getResources().getString(taskBarIconData.tipId));
                    this.mTipTextView.setVisibility(0);
                }
            }
        }
        setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        TaskBarIconData taskBarIconData = this.mTaskBarIconData;
        if (taskBarIconData != null) {
            TaskBarView.DesktopStatusBarIconInfo desktopStatusBarIconInfo = taskBarIconData.statusBarIconInfo;
            if (desktopStatusBarIconInfo != null) {
                final DynamicSysIconView dynamicIconView = desktopStatusBarIconInfo.getDynamicIconView();
                if (dynamicIconView == null) {
                    return;
                } else {
                    post(new Runnable() { // from class: com.motorola.taskbar.bar.more.MoreItemLayout$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            dynamicIconView.onClick();
                        }
                    });
                }
            } else {
                TaskBarIconData.TaskBarIconProvider taskBarIconProvider = taskBarIconData.provider;
                if (taskBarIconProvider == null) {
                    post(taskBarIconData.runnable);
                } else if (!taskBarIconProvider.onIconClick()) {
                    post(this.mTaskBarIconData.runnable);
                }
            }
            OnItemClickedCallback onItemClickedCallback = this.mOnItemClickedCallback;
            if (onItemClickedCallback != null) {
                onItemClickedCallback.OnItemClicked();
            }
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (MorePanelImageView) findViewById(R$id.image);
        this.mTipTextView = (TextView) findViewById(R$id.tip);
        this.mProviderDrawableLayout = (FrameLayout) findViewById(R$id.provider_drawable);
        this.mProviderTitleLayout = (FrameLayout) findViewById(R$id.provider_tip);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        MorePanelImageView morePanelImageView = this.mImageView;
        if (morePanelImageView != null) {
            morePanelImageView.setDarkIntensity(z ? getResources().getFloat(R$dimen.taskbar_icon_dark_intensity) : 0.7f);
            this.mImageView.invalidate();
        }
        TextView textView = this.mTipTextView;
        if (textView != null) {
            textView.setTextColor(z ? getResources().getColor(R$color.taskbar_text_color) : getResources().getColor(R$color.taskbar_text_color_dis));
            this.mTipTextView.invalidate();
        }
    }
}
