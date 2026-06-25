package com.motorola.plugins;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes2.dex */
public class ClockFaceAttributes {
    public final Drawable background;
    public final boolean lightNavBar;
    public final boolean lightStatusBar;
    public final boolean showNavBar;
    public final boolean showStatusBar;
    public final int style;
    public final String template;

    public class Builder {
        private Drawable background;
        private boolean lightNavBar;
        private boolean lightStatusBar;
        private boolean showNavBar;
        private boolean showStatusBar;
        private int style;
        private String template;

        public Builder(ClockFaceAttributes clockFaceAttributes) {
            this.showStatusBar = true;
            this.lightStatusBar = true;
            this.showNavBar = true;
            this.lightNavBar = true;
            this.background = new ColorDrawable(0);
            this.template = clockFaceAttributes.template;
            this.style = clockFaceAttributes.style;
            this.showStatusBar = clockFaceAttributes.showStatusBar;
            this.lightStatusBar = clockFaceAttributes.lightStatusBar;
            this.showNavBar = clockFaceAttributes.showNavBar;
            this.lightNavBar = clockFaceAttributes.lightNavBar;
            Drawable drawable = clockFaceAttributes.background;
            this.background = drawable == null ? new ColorDrawable(0) : drawable;
        }

        public Builder(String str) {
            this.showStatusBar = true;
            this.lightStatusBar = true;
            this.showNavBar = true;
            this.lightNavBar = true;
            this.background = new ColorDrawable(0);
            this.template = str;
        }

        public ClockFaceAttributes build() {
            return new ClockFaceAttributes(this.template, this.style, this.showStatusBar, this.lightStatusBar, this.showNavBar, this.lightNavBar, this.background);
        }

        public Builder setBackground(Drawable drawable) {
            if (drawable == null) {
                drawable = new ColorDrawable(0);
            }
            this.background = drawable;
            return this;
        }

        public Builder setLightNavBar(boolean z) {
            this.lightNavBar = z;
            return this;
        }

        public Builder setLightStatusBar(boolean z) {
            this.lightStatusBar = z;
            return this;
        }

        public Builder setShowNavBar(boolean z) {
            this.showNavBar = z;
            return this;
        }

        public Builder setShowStatusBar(boolean z) {
            this.showStatusBar = z;
            return this;
        }

        public Builder setStyle(int i) {
            this.style = i;
            return this;
        }

        public Builder setTemplate(String str) {
            this.template = str;
            return this;
        }
    }

    private ClockFaceAttributes(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, Drawable drawable) {
        this.template = str;
        this.style = i;
        this.showStatusBar = z;
        this.lightStatusBar = z2;
        this.showNavBar = z3;
        this.lightNavBar = z4;
        this.background = drawable;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }
}
