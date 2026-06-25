package com.android.systemui.media.controls.ui.controller;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.util.animation.MeasurementOutput;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;

/* JADX INFO: compiled from: MediaHostStatesManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaHostStatesManager {
    private final Set callbacks = new LinkedHashSet();
    private final Set controllers = new LinkedHashSet();
    private final Map carouselSizes = new LinkedHashMap();
    private final Map mediaHostStates = new LinkedHashMap();

    /* JADX INFO: compiled from: MediaHostStatesManager.kt */
    public interface Callback {
        void onHostStateChanged(int i, MediaHostState mediaHostState);
    }

    public final void addCallback(Callback callback) {
        callback.getClass();
        this.callbacks.add(callback);
    }

    public final void addController(MediaViewController mediaViewController) {
        mediaViewController.getClass();
        this.controllers.add(mediaViewController);
    }

    public final Map getCarouselSizes() {
        return this.carouselSizes;
    }

    public final Map getMediaHostStates() {
        return this.mediaHostStates;
    }

    public final void removeController(MediaViewController mediaViewController) {
        mediaViewController.getClass();
        this.controllers.remove(mediaViewController);
    }

    public final MeasurementOutput updateCarouselDimensions(int i, MediaHostState mediaHostState) {
        mediaHostState.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaHostStatesManager#updateCarouselDimensions");
        }
        try {
            MeasurementOutput measurementOutput = new MeasurementOutput(0, 0);
            Iterator it = this.controllers.iterator();
            while (it.hasNext()) {
                MeasurementOutput measurementsForState = ((MediaViewController) it.next()).getMeasurementsForState(mediaHostState);
                if (measurementsForState != null) {
                    if (measurementsForState.getMeasuredHeight() > measurementOutput.getMeasuredHeight()) {
                        measurementOutput.setMeasuredHeight(measurementsForState.getMeasuredHeight());
                    }
                    if (measurementsForState.getMeasuredWidth() > measurementOutput.getMeasuredWidth()) {
                        measurementOutput.setMeasuredWidth(measurementsForState.getMeasuredWidth());
                    }
                }
            }
            this.carouselSizes.put(Integer.valueOf(i), measurementOutput);
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return measurementOutput;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void updateHostState(int i, MediaHostState mediaHostState) {
        mediaHostState.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaHostStatesManager#updateHostState");
        }
        try {
            if (!mediaHostState.equals((MediaHostState) this.mediaHostStates.get(Integer.valueOf(i)))) {
                MediaHostState mediaHostStateCopy = mediaHostState.copy();
                this.mediaHostStates.put(Integer.valueOf(i), mediaHostStateCopy);
                updateCarouselDimensions(i, mediaHostState);
                Iterator it = this.controllers.iterator();
                while (it.hasNext()) {
                    ((MediaViewController) it.next()).getStateCallback().onHostStateChanged(i, mediaHostStateCopy);
                }
                Iterator it2 = this.callbacks.iterator();
                while (it2.hasNext()) {
                    ((Callback) it2.next()).onHostStateChanged(i, mediaHostStateCopy);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
