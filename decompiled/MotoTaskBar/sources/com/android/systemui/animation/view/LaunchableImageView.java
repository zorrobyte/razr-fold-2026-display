package com.android.systemui.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: LaunchableImageView.kt */
/* JADX INFO: loaded from: classes.dex */
public class LaunchableImageView extends ImageView {
    private final LaunchableViewDelegate delegate;

    public LaunchableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.delegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.animation.view.LaunchableImageView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LaunchableImageView.delegate$lambda$0(this.f$0, ((Integer) obj).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit delegate$lambda$0(LaunchableImageView launchableImageView, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int i) {
        this.delegate.setVisibility(i);
    }
}
