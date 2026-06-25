package com.android.systemui.qs.tileimpl;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import java.util.Arrays;

/* JADX INFO: compiled from: QSTileViewImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class QSTileViewImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final PropertyValuesHolder colorValuesHolder(String str, int... iArr) {
        PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, Arrays.copyOf(iArr, iArr.length));
        propertyValuesHolderOfInt.setEvaluator(ArgbEvaluator.getInstance());
        return propertyValuesHolderOfInt;
    }

    public static final float constrainSquishiness(float f) {
        return (f * 0.9f) + 0.1f;
    }
}
