package com.android.systemui.statusbar.notification.footer.ui.view;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.row.FooterViewButton;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.motorola.taskbar.R$id;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class FooterView extends StackScrollerDecorView {
    private FooterViewButton mClearAllButton;
    private View.OnClickListener mClearAllButtonClickListener;
    private int mClearAllButtonDescriptionId;
    private int mClearAllButtonTextId;
    private String mManageNotificationHistoryText;
    private String mManageNotificationText;
    private FooterViewButton mManageOrHistoryButton;
    private Drawable mSeenNotifsFilteredIcon;
    private String mSeenNotifsFilteredText;
    private TextView mSeenNotifsFooterTextView;
    private boolean mShouldBeHidden;
    private boolean mShowHistory;

    public class FooterViewState extends ExpandableViewState {
        public boolean hideContent;
        public boolean resetY = false;

        public FooterViewState(FooterView footerView) {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void animateTo(View view, AnimationProperties animationProperties) {
            if ((view instanceof FooterView) && this.resetY) {
                animationProperties.getAnimationFilter().animateY = false;
                this.resetY = false;
            }
            super.animateTo(view, animationProperties);
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof FooterView) {
                ((FooterView) view).setContentVisibleAnimated(!this.hideContent);
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void copyFrom(ViewState viewState) {
            super.copyFrom(viewState);
            if (viewState instanceof FooterViewState) {
                this.hideContent = ((FooterViewState) viewState).hideContent;
            }
        }
    }

    public FooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void updateClearAllButtonDescription() {
        if (this.mClearAllButtonDescriptionId == 0) {
            return;
        }
        this.mClearAllButton.setContentDescription(getContext().getString(this.mClearAllButtonDescriptionId));
    }

    private void updateClearAllButtonText() {
        if (this.mClearAllButtonTextId == 0) {
            return;
        }
        this.mClearAllButton.setText(getContext().getString(this.mClearAllButtonTextId));
    }

    private void updateContent() {
        if (FooterViewRefactor.isEnabled()) {
            updateClearAllButtonText();
            updateClearAllButtonDescription();
            return;
        }
        if (this.mShowHistory) {
            this.mManageOrHistoryButton.setText(this.mManageNotificationHistoryText);
            this.mManageOrHistoryButton.setContentDescription(this.mManageNotificationHistoryText);
        } else {
            this.mManageOrHistoryButton.setText(this.mManageNotificationText);
            this.mManageOrHistoryButton.setContentDescription(this.mManageNotificationText);
        }
        this.mClearAllButton.setText(R$string.clear_all_notifications_text);
        this.mClearAllButton.setContentDescription(((FrameLayout) this).mContext.getString(R$string.accessibility_clear_all));
        this.mSeenNotifsFooterTextView.setText(this.mSeenNotifsFilteredText);
        this.mSeenNotifsFooterTextView.setCompoundDrawablesRelative(this.mSeenNotifsFilteredIcon, null, null, null);
    }

    private void updateResources() {
        FooterViewRefactor.assertInLegacyMode();
        this.mManageNotificationText = getContext().getString(R$string.manage_notifications_text);
        this.mManageNotificationHistoryText = getContext().getString(R$string.manage_notifications_history_text);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.notifications_unseen_footer_icon_size);
        this.mSeenNotifsFilteredText = getContext().getString(R$string.unlock_to_see_notif_text);
        Drawable drawable = getContext().getDrawable(R$drawable.ic_friction_lock_closed);
        this.mSeenNotifsFilteredIcon = drawable;
        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public ExpandableViewState createExpandableViewState() {
        return new FooterViewState(this);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    protected View findContentView() {
        return findViewById(R$id.content);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    protected View findSecondaryView() {
        return findViewById(R$id.dismiss_text);
    }

    public boolean isHistoryShown() {
        FooterViewRefactor.assertInLegacyMode();
        return this.mShowHistory;
    }

    public boolean isOnEmptySpace(float f, float f2) {
        return f < this.mContent.getX() || f > this.mContent.getX() + ((float) this.mContent.getWidth()) || f2 < this.mContent.getY() || f2 > this.mContent.getY() + ((float) this.mContent.getHeight());
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        ColorUpdateLogger colorUpdateLogger = ColorUpdateLogger.getInstance();
        if (colorUpdateLogger != null) {
            colorUpdateLogger.logTriggerEvent("Footer.onConfigurationChanged()");
        }
        super.onConfigurationChanged(configuration);
        updateColors();
        if (!FooterViewRefactor.isEnabled()) {
            updateResources();
        }
        updateContent();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    protected void onFinishInflate() {
        ColorUpdateLogger colorUpdateLogger = ColorUpdateLogger.getInstance();
        if (colorUpdateLogger != null) {
            colorUpdateLogger.logTriggerEvent("Footer.onFinishInflate()");
        }
        super.onFinishInflate();
        this.mClearAllButton = (FooterViewButton) findSecondaryView();
        this.mManageOrHistoryButton = (FooterViewButton) findViewById(R$id.manage_text);
        this.mSeenNotifsFooterTextView = (TextView) findViewById(R$id.unlock_prompt_footer);
        if (!FooterViewRefactor.isEnabled()) {
            updateResources();
        }
        updateContent();
        updateColors();
    }

    public void setClearAllButtonClickListener(View.OnClickListener onClickListener) {
        if (FooterViewRefactor.isEnabled()) {
            if (this.mClearAllButtonClickListener == onClickListener) {
                return;
            } else {
                this.mClearAllButtonClickListener = onClickListener;
            }
        }
        this.mClearAllButton.setOnClickListener(onClickListener);
    }

    public void setClearAllButtonDescription(int i) {
        if (FooterViewRefactor.isUnexpectedlyInLegacyMode() || this.mClearAllButtonDescriptionId == i) {
            return;
        }
        this.mClearAllButtonDescriptionId = i;
        updateClearAllButtonDescription();
    }

    public void setClearAllButtonText(int i) {
        if (FooterViewRefactor.isUnexpectedlyInLegacyMode() || this.mClearAllButtonTextId == i) {
            return;
        }
        this.mClearAllButtonTextId = i;
        updateClearAllButtonText();
    }

    public void setClearAllButtonVisible(boolean z, boolean z2) {
        setClearAllButtonVisible(z, z2, null);
    }

    public void setClearAllButtonVisible(boolean z, boolean z2, Consumer consumer) {
        setSecondaryVisible(z, z2, consumer);
    }

    public void setFooterLabelVisible(boolean z) {
        if (FooterViewRefactor.isEnabled()) {
            if (z) {
                this.mSeenNotifsFooterTextView.setVisibility(0);
                return;
            } else {
                this.mSeenNotifsFooterTextView.setVisibility(8);
                return;
            }
        }
        if (z) {
            this.mManageOrHistoryButton.setVisibility(8);
            this.mClearAllButton.setVisibility(8);
            this.mSeenNotifsFooterTextView.setVisibility(0);
        } else {
            this.mManageOrHistoryButton.setVisibility(0);
            this.mClearAllButton.setVisibility(0);
            this.mSeenNotifsFooterTextView.setVisibility(8);
        }
    }

    public void setManageButtonClickListener(View.OnClickListener onClickListener) {
        this.mManageOrHistoryButton.setOnClickListener(onClickListener);
    }

    public void setManageOrHistoryButtonVisible(boolean z) {
        this.mManageOrHistoryButton.setVisibility(z ? 0 : 8);
    }

    public void setShouldBeHidden(boolean z) {
        this.mShouldBeHidden = z;
    }

    public boolean shouldBeHidden() {
        return this.mShouldBeHidden;
    }

    public void showHistory(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        if (this.mShowHistory == z) {
            return;
        }
        this.mShowHistory = z;
        updateContent();
    }

    public void updateColors() {
        int color;
        Resources.Theme theme = ((FrameLayout) this).mContext.getTheme();
        int color2 = ((FrameLayout) this).mContext.getColor(R.color.materialColorPrimaryFixed);
        int i = R$drawable.notif_footer_btn_background;
        Drawable drawable = theme.getDrawable(i);
        Drawable drawable2 = theme.getDrawable(i);
        if (!FlagsFake.notificationBackgroundTintOptimization() && (color = ((FrameLayout) this).mContext.getColor(R.color.material_blue_grey_200)) != 0) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            drawable.setColorFilter(porterDuffColorFilter);
            drawable2.setColorFilter(porterDuffColorFilter);
        }
        this.mClearAllButton.setBackground(drawable);
        this.mClearAllButton.setTextColor(color2);
        this.mManageOrHistoryButton.setBackground(drawable2);
        this.mManageOrHistoryButton.setTextColor(color2);
        this.mSeenNotifsFooterTextView.setTextColor(color2);
        this.mSeenNotifsFooterTextView.setCompoundDrawableTintList(ColorStateList.valueOf(color2));
    }
}
