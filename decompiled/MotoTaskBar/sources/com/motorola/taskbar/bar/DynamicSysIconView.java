package com.motorola.taskbar.bar;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;
import com.android.systemui.statusbar.policy.KeyButtonView;

/* JADX INFO: loaded from: classes2.dex */
public class DynamicSysIconView extends KeyButtonView {
    private StatusBarIcon mIcon;
    private PendingIntent mPendingIntent;

    public DynamicSysIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DynamicSysIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DynamicSysIconView(Context context, AttributeSet attributeSet, int i, InputManager inputManager) {
        super(context, attributeSet, i, inputManager);
    }

    public static Drawable getIcon(Context context, StatusBarIcon statusBarIcon) {
        int identifier = statusBarIcon.user.getIdentifier();
        if (identifier == -1) {
            identifier = 0;
        }
        return statusBarIcon.icon.loadDrawableAsUser(context, identifier);
    }

    private Drawable getIcon(StatusBarIcon statusBarIcon) {
        return getIcon(getContext(), statusBarIcon);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStatusBarIcon$0(View view) {
        onClick();
    }

    public Drawable getIcon() {
        return getIcon(this.mIcon);
    }

    public void onClick() {
        if (this.mPendingIntent != null) {
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused) {
            }
            try {
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchDisplayId(((ImageView) this).mContext.getDisplayId());
                PendingIntent pendingIntent = this.mPendingIntent;
                Context context = ((ImageView) this).mContext;
                Log.d("DynamicSysIconView", "onClick launchResult = " + pendingIntent.sendAndReturnResult(context, context.getDisplayId(), null, null, null, null, activityOptionsMakeBasic.toBundle()));
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStatusBarIcon(StatusBarIcon statusBarIcon, PendingIntent pendingIntent) {
        if (statusBarIcon == null) {
            return;
        }
        this.mPendingIntent = pendingIntent;
        StatusBarIcon statusBarIconClone = statusBarIcon.clone();
        this.mIcon = statusBarIconClone;
        try {
            Drawable icon = getIcon(statusBarIconClone);
            if (icon == null) {
                Log.w("DynamicSysIconView", "No icon ; " + this.mIcon.icon);
                return;
            }
            setImageDrawable(KeyButtonDrawable.create(((ImageView) this).mContext, icon, false));
            setImageLevel(statusBarIcon.iconLevel);
            CharSequence charSequence = statusBarIcon.contentDescription;
            setToolTipText(charSequence != null ? charSequence.toString() : "");
            setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.DynamicSysIconView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setStatusBarIcon$0(view);
                }
            });
        } catch (OutOfMemoryError unused) {
            Log.w("DynamicSysIconView", "OOM while inflating " + this.mIcon.icon);
        }
    }
}
