package com.motorola.taskbar.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.bar.TaskBarIconData;
import com.motorola.taskbar.bar.more.MoreItemLayout;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MorePanelLayout extends FrameLayout {
    private LinearLayout mItemContainer;
    private LayoutInflater mLayoutInflater;

    public MorePanelLayout(Context context) {
        super(context);
    }

    public MorePanelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MorePanelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MorePanelLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void addItemViews(List list, MoreItemLayout.OnItemClickedCallback onItemClickedCallback) {
        this.mItemContainer.removeAllViews();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            TaskBarIconData taskBarIconData = (TaskBarIconData) it.next();
            MoreItemLayout moreItemLayout = (MoreItemLayout) this.mLayoutInflater.inflate(R$layout.more_item_layout, (ViewGroup) this.mItemContainer, false);
            taskBarIconData.view = moreItemLayout;
            moreItemLayout.inflateView(taskBarIconData, onItemClickedCallback);
            this.mItemContainer.addView(moreItemLayout, -1, -2);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mLayoutInflater = LayoutInflater.from(getContext());
        this.mItemContainer = (LinearLayout) findViewById(R$id.container);
    }
}
