package com.motorola.laptoppanel.ui.compose;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.compose.ui.graphics.AndroidImageBitmap_androidKt;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import androidx.lifecycle.ViewModel;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: SliderModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SliderModel extends ViewModel {
    private final Application application;
    private MutableStateFlow currentSliderValue;
    private float maxSliderValue;
    private float minSliderValue;
    private final LaptopPanelModel panelModel;
    private int sliderSteps;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final String TAG = "SliderModel";

    /* JADX INFO: compiled from: SliderModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BitmapPainter vectorResToBitmapPainter(int i, Context context) {
            context.getClass();
            Drawable drawable = AppCompatResources.getDrawable(context, i);
            VectorDrawable vectorDrawable = drawable instanceof VectorDrawable ? (VectorDrawable) drawable : null;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(vectorDrawable != null ? vectorDrawable.getIntrinsicWidth() : 100, vectorDrawable != null ? vectorDrawable.getIntrinsicHeight() : 100, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.getClass();
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            if (vectorDrawable != null) {
                vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            }
            if (vectorDrawable != null) {
                vectorDrawable.draw(canvas);
            }
            return new BitmapPainter(AndroidImageBitmap_androidKt.asImageBitmap(bitmapCreateBitmap), 0L, 0L, 6, null);
        }
    }

    /* JADX INFO: compiled from: SliderModel.kt */
    public interface Drag {

        /* JADX INFO: compiled from: SliderModel.kt */
        public final class Dragging implements Drag {
            private final float dragValue;

            private /* synthetic */ Dragging(float f) {
                this.dragValue = f;
            }

            /* JADX INFO: renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Dragging m2157boximpl(float f) {
                return new Dragging(f);
            }

            /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
            public static float m2158constructorimpl(float f) {
                return f;
            }

            /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
            public static boolean m2159equalsimpl(float f, Object obj) {
                return (obj instanceof Dragging) && Float.compare(f, ((Dragging) obj).m2162unboximpl()) == 0;
            }

            /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
            public static int m2160hashCodeimpl(float f) {
                return Float.hashCode(f);
            }

            /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
            public static String m2161toStringimpl(float f) {
                return "Dragging(dragValue=" + f + ")";
            }

            public boolean equals(Object obj) {
                return m2159equalsimpl(this.dragValue, obj);
            }

            @Override // com.motorola.laptoppanel.ui.compose.SliderModel.Drag
            public float getDragValue() {
                return this.dragValue;
            }

            public int hashCode() {
                return m2160hashCodeimpl(this.dragValue);
            }

            public String toString() {
                return m2161toStringimpl(this.dragValue);
            }

            /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
            public final /* synthetic */ float m2162unboximpl() {
                return this.dragValue;
            }
        }

        /* JADX INFO: compiled from: SliderModel.kt */
        public final class Stopped implements Drag {
            private final float dragValue;

            private /* synthetic */ Stopped(float f) {
                this.dragValue = f;
            }

            /* JADX INFO: renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Stopped m2163boximpl(float f) {
                return new Stopped(f);
            }

            /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
            public static float m2164constructorimpl(float f) {
                return f;
            }

            /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
            public static boolean m2165equalsimpl(float f, Object obj) {
                return (obj instanceof Stopped) && Float.compare(f, ((Stopped) obj).m2168unboximpl()) == 0;
            }

            /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
            public static int m2166hashCodeimpl(float f) {
                return Float.hashCode(f);
            }

            /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
            public static String m2167toStringimpl(float f) {
                return "Stopped(dragValue=" + f + ")";
            }

            public boolean equals(Object obj) {
                return m2165equalsimpl(this.dragValue, obj);
            }

            @Override // com.motorola.laptoppanel.ui.compose.SliderModel.Drag
            public float getDragValue() {
                return this.dragValue;
            }

            public int hashCode() {
                return m2166hashCodeimpl(this.dragValue);
            }

            public String toString() {
                return m2167toStringimpl(this.dragValue);
            }

            /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
            public final /* synthetic */ float m2168unboximpl() {
                return this.dragValue;
            }
        }

        float getDragValue();
    }

    public SliderModel(Application application, LaptopPanelModel laptopPanelModel) {
        application.getClass();
        laptopPanelModel.getClass();
        this.application = application;
        this.panelModel = laptopPanelModel;
        this.currentSliderValue = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this.maxSliderValue = 1.0f;
    }

    static /* synthetic */ Object onDrag$suspendImpl(SliderModel sliderModel, Drag drag, Continuation continuation) {
        sliderModel.panelModel.onUserInteraction();
        return Unit.INSTANCE;
    }

    public final Application getApplication() {
        return this.application;
    }

    public final MutableStateFlow getCurrentSliderValue() {
        return this.currentSliderValue;
    }

    /* JADX INFO: renamed from: getCurrentSliderValue, reason: collision with other method in class */
    public abstract void mo2156getCurrentSliderValue();

    public abstract int getIconForPercentage(float f);

    public final float getMaxSliderValue() {
        return this.maxSliderValue;
    }

    public final float getMinSliderValue() {
        return this.minSliderValue;
    }

    public final int getSliderSteps() {
        return this.sliderSteps;
    }

    public Object onDrag(Drag drag, Continuation continuation) {
        return onDrag$suspendImpl(this, drag, continuation);
    }

    public final void refreshModel() {
        Log.d(TAG, "Refresh Model");
        mo2156getCurrentSliderValue();
    }

    public final void setMaxSliderValue(float f) {
        this.maxSliderValue = f;
    }

    public final void setMinSliderValue(float f) {
        this.minSliderValue = f;
    }

    public final void setSliderSteps(int i) {
        this.sliderSteps = i;
    }
}
