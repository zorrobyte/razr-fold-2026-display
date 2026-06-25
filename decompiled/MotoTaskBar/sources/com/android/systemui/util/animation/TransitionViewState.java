package com.android.systemui.util.animation;

import android.graphics.PointF;
import android.view.View;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: TransitionLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TransitionViewState {
    private int height;
    private int measureHeight;
    private int measureWidth;
    private int width;
    private Map widgetStates = new LinkedHashMap();
    private float alpha = 1.0f;
    private final PointF translation = new PointF();
    private final PointF contentTranslation = new PointF();

    public static /* synthetic */ TransitionViewState copy$default(TransitionViewState transitionViewState, TransitionViewState transitionViewState2, int i, Object obj) {
        if ((i & 1) != 0) {
            transitionViewState2 = null;
        }
        return transitionViewState.copy(transitionViewState2);
    }

    public final TransitionViewState copy(TransitionViewState transitionViewState) {
        TransitionViewState transitionViewState2 = transitionViewState == null ? new TransitionViewState() : transitionViewState;
        transitionViewState2.width = this.width;
        transitionViewState2.height = this.height;
        transitionViewState2.measureHeight = this.measureHeight;
        transitionViewState2.measureWidth = this.measureWidth;
        transitionViewState2.alpha = this.alpha;
        PointF pointF = transitionViewState2.translation;
        PointF pointF2 = this.translation;
        pointF.set(pointF2.x, pointF2.y);
        PointF pointF3 = transitionViewState2.contentTranslation;
        PointF pointF4 = this.contentTranslation;
        pointF3.set(pointF4.x, pointF4.y);
        for (Map.Entry entry : this.widgetStates.entrySet()) {
            transitionViewState2.widgetStates.put(entry.getKey(), WidgetState.copy$default((WidgetState) entry.getValue(), 0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 511, null));
        }
        return transitionViewState2;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final PointF getContentTranslation() {
        return this.contentTranslation;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getMeasureHeight() {
        return this.measureHeight;
    }

    public final int getMeasureWidth() {
        return this.measureWidth;
    }

    public final PointF getTranslation() {
        return this.translation;
    }

    public final Map getWidgetStates() {
        return this.widgetStates;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void initFromLayout(TransitionLayout transitionLayout) {
        transitionLayout.getClass();
        int childCount = transitionLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = transitionLayout.getChildAt(i);
            Map map = this.widgetStates;
            Integer numValueOf = Integer.valueOf(childAt.getId());
            Object obj = map.get(numValueOf);
            if (obj == null) {
                WidgetState widgetState = new WidgetState(0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 384, null);
                map.put(numValueOf, widgetState);
                obj = widgetState;
            }
            ((WidgetState) obj).initFromLayout(childAt);
        }
        this.width = transitionLayout.getMeasuredWidth();
        int measuredHeight = transitionLayout.getMeasuredHeight();
        this.height = measuredHeight;
        this.measureWidth = this.width;
        this.measureHeight = measuredHeight;
        this.translation.set(0.0f, 0.0f);
        this.contentTranslation.set(0.0f, 0.0f);
        this.alpha = 1.0f;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final void setHeight(int i) {
        this.height = i;
    }

    public final void setMeasureHeight(int i) {
        this.measureHeight = i;
    }

    public final void setMeasureWidth(int i) {
        this.measureWidth = i;
    }

    public final void setWidth(int i) {
        this.width = i;
    }
}
