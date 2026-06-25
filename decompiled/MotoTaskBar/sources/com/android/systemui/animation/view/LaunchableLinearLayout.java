package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LaunchableLinearLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public class LaunchableLinearLayout extends LinearLayout {
    private final LaunchableViewDelegate delegate;

    public LaunchableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableLinearLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LaunchableLinearLayout.delegate$lambda$0(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit delegate$lambda$0(LaunchableLinearLayout launchableLinearLayout, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }
}
