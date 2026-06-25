package com.android.systemui.qs;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import com.android.systemui.qs.QSTile;

/* JADX INFO: loaded from: classes.dex */
public abstract class QSTileView extends LinearLayout {
    public QSTileView(Context context) {
        super(context);
    }

    public int getLongPressEffectDuration() {
        return ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout();
    }

    public abstract void init(QSTile qSTile);

    public abstract void onStateChanged(QSTile.State state);

    public abstract void setPosition(int i);

    public abstract View updateAccessibilityOrder(View view);
}
