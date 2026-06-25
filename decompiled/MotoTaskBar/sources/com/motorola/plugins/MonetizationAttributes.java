package com.motorola.plugins;

import android.content.Intent;
import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes2.dex */
public class MonetizationAttributes {
    public final Intent leftAction;
    public final Bitmap leftBackground;
    public final Bitmap lockscreenWallpaper;
    public final Intent rightAction;
    public final Bitmap rightBackground;

    public class Builder {
        private Intent leftAction;
        private Bitmap leftBackground;
        private Bitmap lockscreenWallpaper;
        private Intent rightAction;
        private Bitmap rightBackground;

        public Builder() {
            this.lockscreenWallpaper = null;
            this.leftAction = null;
            this.rightAction = null;
            this.leftBackground = null;
            this.rightBackground = null;
        }

        public Builder(MonetizationAttributes monetizationAttributes) {
            this.lockscreenWallpaper = null;
            this.leftAction = null;
            this.rightAction = null;
            this.leftBackground = null;
            this.rightBackground = null;
            this.lockscreenWallpaper = monetizationAttributes.lockscreenWallpaper;
            this.leftAction = monetizationAttributes.leftAction;
            this.rightAction = monetizationAttributes.rightAction;
            this.leftBackground = monetizationAttributes.leftBackground;
            this.rightBackground = monetizationAttributes.rightBackground;
        }

        public MonetizationAttributes build() {
            return new MonetizationAttributes(this.lockscreenWallpaper, this.leftAction, this.rightAction, this.leftBackground, this.rightBackground);
        }

        public Builder setLeftAction(Intent intent) {
            this.leftAction = intent;
            return this;
        }

        public Builder setLeftBackground(Bitmap bitmap) {
            this.leftBackground = bitmap;
            return this;
        }

        public Builder setLockscreenWallpaper(Bitmap bitmap) {
            this.lockscreenWallpaper = bitmap;
            return this;
        }

        public Builder setRightAction(Intent intent) {
            this.rightAction = intent;
            return this;
        }

        public Builder setRightBackground(Bitmap bitmap) {
            this.rightBackground = bitmap;
            return this;
        }
    }

    private MonetizationAttributes(Bitmap bitmap, Intent intent, Intent intent2, Bitmap bitmap2, Bitmap bitmap3) {
        this.lockscreenWallpaper = bitmap;
        this.leftAction = intent;
        this.rightAction = intent2;
        this.leftBackground = bitmap2;
        this.rightBackground = bitmap3;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }
}
