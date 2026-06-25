package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.util.PluralMessageFormaterKt;
import com.motorola.taskbar.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class HybridGroupManager {
    private final Context mContext;
    private int mOverflowNumberColor;
    private int mOverflowNumberPadding;
    private float mOverflowNumberSize;

    public HybridGroupManager(Context context) {
        this.mContext = context;
        initDimens();
    }

    private HybridNotificationView inflateHybridView(View view, ViewGroup viewGroup) {
        Trace.beginSection("HybridGroupManager#inflateHybridView");
        HybridNotificationView hybridNotificationView = (HybridNotificationView) LayoutInflater.from(this.mContext).inflate(view instanceof ConversationLayout ? R$layout.hybrid_conversation_notification : R$layout.hybrid_notification, viewGroup, false);
        viewGroup.addView(hybridNotificationView);
        Trace.endSection();
        return hybridNotificationView;
    }

    private TextView inflateOverflowNumber(ViewGroup viewGroup) {
        TextView textView = (TextView) ((LayoutInflater) this.mContext.getSystemService(LayoutInflater.class)).inflate(R$layout.hybrid_overflow_number, viewGroup, false);
        viewGroup.addView(textView);
        updateOverFlowNumberColor(textView);
        return textView;
    }

    public static CharSequence resolveText(Notification notification) {
        CharSequence charSequence = notification.extras.getCharSequence("android.text");
        return charSequence == null ? notification.extras.getCharSequence("android.bigText") : charSequence;
    }

    public static CharSequence resolveTitle(Notification notification) {
        CharSequence charSequence = notification.extras.getCharSequence("android.title");
        return charSequence == null ? notification.extras.getCharSequence("android.title.big") : charSequence;
    }

    private void updateOverFlowNumberColor(TextView textView) {
        textView.setTextColor(this.mOverflowNumberColor);
    }

    public HybridNotificationView bindFromNotification(HybridNotificationView hybridNotificationView, View view, StatusBarNotification statusBarNotification, ViewGroup viewGroup) {
        boolean z;
        AsyncHybridViewInflation.assertInLegacyMode();
        if (hybridNotificationView == null) {
            Trace.beginSection("HybridGroupManager#bindFromNotification");
            hybridNotificationView = inflateHybridView(view, viewGroup);
            z = true;
        } else {
            z = false;
        }
        updateReusableView(hybridNotificationView, statusBarNotification, view);
        if (z) {
            Trace.endSection();
        }
        return hybridNotificationView;
    }

    public TextView bindOverflowNumber(TextView textView, int i, ViewGroup viewGroup) {
        if (textView == null) {
            textView = inflateOverflowNumber(viewGroup);
        }
        String string = this.mContext.getResources().getString(R$string.notification_group_overflow_indicator, Integer.valueOf(i));
        if (!string.equals(textView.getText())) {
            textView.setText(string);
        }
        textView.setContentDescription(PluralMessageFormaterKt.icuMessageFormat(this.mContext.getResources(), R$string.notification_group_overflow_description, i));
        textView.setTextSize(0, this.mOverflowNumberSize);
        textView.setPaddingRelative(textView.getPaddingStart(), textView.getPaddingTop(), this.mOverflowNumberPadding, textView.getPaddingBottom());
        updateOverFlowNumberColor(textView);
        return textView;
    }

    public void initDimens() {
        Resources resources = this.mContext.getResources();
        this.mOverflowNumberSize = resources.getDimensionPixelSize(R$dimen.group_overflow_number_size);
        this.mOverflowNumberPadding = resources.getDimensionPixelSize(R$dimen.group_overflow_number_padding);
    }

    public void setOverflowNumberColor(TextView textView, int i) {
        this.mOverflowNumberColor = i;
        if (textView != null) {
            updateOverFlowNumberColor(textView);
        }
    }

    public void updateReusableView(HybridNotificationView hybridNotificationView, StatusBarNotification statusBarNotification, View view) {
        AsyncHybridViewInflation.assertInLegacyMode();
        CharSequence charSequenceResolveTitle = resolveTitle(statusBarNotification.getNotification());
        CharSequence charSequenceResolveText = resolveText(statusBarNotification.getNotification());
        if (hybridNotificationView != null) {
            hybridNotificationView.bind(charSequenceResolveTitle, charSequenceResolveText, view);
        }
    }
}
