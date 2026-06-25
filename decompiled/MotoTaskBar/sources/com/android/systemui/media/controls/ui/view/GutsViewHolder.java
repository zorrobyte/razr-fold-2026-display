package com.android.systemui.media.controls.ui.view;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.media.controls.ui.animation.MediaColorSchemesKt;
import com.android.systemui.monet.ColorScheme;
import com.motorola.taskbar.R$id;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: GutsViewHolder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GutsViewHolder {
    public static final Companion Companion = new Companion(null);
    private static final Set ids = SetsKt.setOf((Object[]) new Integer[]{Integer.valueOf(R$id.remove_text), Integer.valueOf(R$id.cancel), Integer.valueOf(R$id.dismiss), Integer.valueOf(R$id.settings)});
    private final View cancel;
    private final TextView cancelText;
    private ColorScheme colorScheme;
    private final ViewGroup dismiss;
    private final TextView dismissText;
    private final TextView gutsText;
    private boolean isDismissible;
    private final ImageButton settings;

    /* JADX INFO: compiled from: GutsViewHolder.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Set getIds() {
            return GutsViewHolder.ids;
        }
    }

    public GutsViewHolder(View view) {
        view.getClass();
        View viewRequireViewById = view.requireViewById(R$id.remove_text);
        viewRequireViewById.getClass();
        this.gutsText = (TextView) viewRequireViewById;
        View viewRequireViewById2 = view.requireViewById(R$id.cancel);
        viewRequireViewById2.getClass();
        this.cancel = viewRequireViewById2;
        View viewRequireViewById3 = view.requireViewById(R$id.cancel_text);
        viewRequireViewById3.getClass();
        this.cancelText = (TextView) viewRequireViewById3;
        View viewRequireViewById4 = view.requireViewById(R$id.dismiss);
        viewRequireViewById4.getClass();
        this.dismiss = (ViewGroup) viewRequireViewById4;
        View viewRequireViewById5 = view.requireViewById(R$id.dismiss_text);
        viewRequireViewById5.getClass();
        this.dismissText = (TextView) viewRequireViewById5;
        View viewRequireViewById6 = view.requireViewById(R$id.settings);
        viewRequireViewById6.getClass();
        this.settings = (ImageButton) viewRequireViewById6;
        this.isDismissible = true;
    }

    public final View getCancel() {
        return this.cancel;
    }

    public final TextView getCancelText() {
        return this.cancelText;
    }

    public final ViewGroup getDismiss() {
        return this.dismiss;
    }

    public final TextView getDismissText() {
        return this.dismissText;
    }

    public final TextView getGutsText() {
        return this.gutsText;
    }

    public final ImageButton getSettings() {
        return this.settings;
    }

    public final void marquee(final boolean z, long j, String str) {
        str.getClass();
        Handler handler = this.gutsText.getHandler();
        if (handler == null) {
            Log.d(str, "marquee while longPressText.getHandler() is null", new Exception());
        } else {
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.media.controls.ui.view.GutsViewHolder.marquee.1
                @Override // java.lang.Runnable
                public final void run() {
                    GutsViewHolder.this.getGutsText().setSelected(z);
                }
            }, j);
        }
    }

    public final void setAccentPrimaryColor(int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        this.settings.setImageTintList(colorStateListValueOf);
        this.cancelText.setBackgroundTintList(colorStateListValueOf);
        this.dismissText.setBackgroundTintList(colorStateListValueOf);
    }

    public final void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    public final void setColors(ColorScheme colorScheme) {
        colorScheme.getClass();
        this.colorScheme = colorScheme;
        setSurfaceColor(MediaColorSchemesKt.surfaceFromScheme(colorScheme));
        setTextPrimaryColor(MediaColorSchemesKt.textPrimaryFromScheme(colorScheme));
        setAccentPrimaryColor(MediaColorSchemesKt.accentPrimaryFromScheme(colorScheme));
    }

    public final void setDismissible(boolean z) {
        if (this.isDismissible == z) {
            return;
        }
        this.isDismissible = z;
        ColorScheme colorScheme = this.colorScheme;
        if (colorScheme != null) {
            setColors(colorScheme);
        }
    }

    public final void setSurfaceColor(int i) {
        this.dismissText.setTextColor(i);
        if (this.isDismissible) {
            return;
        }
        this.cancelText.setTextColor(i);
    }

    public final void setTextPrimaryColor(int i) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i);
        colorStateListValueOf.getClass();
        this.gutsText.setTextColor(colorStateListValueOf);
        if (this.isDismissible) {
            this.cancelText.setTextColor(colorStateListValueOf);
        }
    }
}
