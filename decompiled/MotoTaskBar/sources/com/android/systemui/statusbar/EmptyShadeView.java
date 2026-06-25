package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$string;

/* JADX INFO: loaded from: classes.dex */
public class EmptyShadeView extends StackScrollerDecorView {
    private TextView mEmptyFooterText;
    private TextView mEmptyText;
    private int mFooterIcon;
    private int mFooterText;
    private int mFooterVisibility;
    private int mSize;
    private int mText;

    public class EmptyShadeViewState extends ExpandableViewState {
        public EmptyShadeViewState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof EmptyShadeView) {
                EmptyShadeView emptyShadeView = (EmptyShadeView) view;
                emptyShadeView.setContentVisibleAnimated(((float) this.clipTopAmount) <= ((float) EmptyShadeView.this.mEmptyText.getPaddingTop()) * 0.6f && emptyShadeView.isVisible());
            }
        }
    }

    public EmptyShadeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mText = R$string.empty_shade_text;
        this.mFooterIcon = R$drawable.ic_friction_lock_closed;
        this.mFooterText = com.android.systemui.res.R$string.unlock_to_see_notif_text;
        this.mFooterVisibility = 8;
        this.mSize = getResources().getDimensionPixelSize(R$dimen.notifications_unseen_footer_icon_size);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public ExpandableViewState createExpandableViewState() {
        return new EmptyShadeViewState();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    protected View findContentView() {
        return findViewById(R$id.no_notifications);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    protected View findSecondaryView() {
        return findViewById(R$id.no_notifications_footer);
    }

    public int getFooterIconResource() {
        return this.mFooterIcon;
    }

    public int getFooterTextResource() {
        return this.mFooterText;
    }

    public int getTextResource() {
        return this.mText;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mSize = getResources().getDimensionPixelSize(R$dimen.notifications_unseen_footer_icon_size);
        this.mEmptyText.setText(this.mText);
        this.mEmptyFooterText.setVisibility(this.mFooterVisibility);
        setFooterText(this.mFooterText);
        setFooterIcon(this.mFooterIcon);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mEmptyText = (TextView) findContentView();
        TextView textView = (TextView) findSecondaryView();
        this.mEmptyFooterText = textView;
        textView.setCompoundDrawableTintList(textView.getTextColors());
    }

    public void setFooterIcon(int i) {
        Drawable drawable;
        this.mFooterIcon = i;
        if (i == 0) {
            drawable = null;
        } else {
            drawable = getResources().getDrawable(i);
            int i2 = this.mSize;
            drawable.setBounds(0, 0, i2, i2);
        }
        this.mEmptyFooterText.setCompoundDrawablesRelative(drawable, null, null, null);
    }

    public void setFooterText(int i) {
        this.mFooterText = i;
        if (i != 0) {
            this.mEmptyFooterText.setText(i);
        } else {
            this.mEmptyFooterText.setText((CharSequence) null);
        }
    }

    public void setFooterVisibility(int i) {
        this.mFooterVisibility = i;
        setSecondaryVisible(i == 0, false, null);
    }

    public void setText(int i) {
        this.mText = i;
        this.mEmptyText.setText(i);
    }

    public void setTextColors(int i, int i2) {
        this.mEmptyText.setTextColor(i2);
        this.mEmptyFooterText.setTextColor(i);
        this.mEmptyFooterText.setCompoundDrawableTintList(ColorStateList.valueOf(i));
    }
}
