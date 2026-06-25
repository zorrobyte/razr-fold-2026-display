package com.android.systemui.qs.tileimpl;

/* JADX INFO: compiled from: QSLongPressProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public final class QSLongPressProperties {
    private int backgroundColor;
    private int chevronColor;
    private float cornerRadius;
    private int iconColor;
    private int labelColor;
    private int overlayColor;
    private int secondaryLabelColor;
    private float xScale;
    private float yScale;

    public QSLongPressProperties(float f, float f2, float f3, int i, int i2, int i3, int i4, int i5, int i6) {
        this.xScale = f;
        this.yScale = f2;
        this.cornerRadius = f3;
        this.backgroundColor = i;
        this.labelColor = i2;
        this.secondaryLabelColor = i3;
        this.chevronColor = i4;
        this.overlayColor = i5;
        this.iconColor = i6;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSLongPressProperties)) {
            return false;
        }
        QSLongPressProperties qSLongPressProperties = (QSLongPressProperties) obj;
        return Float.compare(this.xScale, qSLongPressProperties.xScale) == 0 && Float.compare(this.yScale, qSLongPressProperties.yScale) == 0 && Float.compare(this.cornerRadius, qSLongPressProperties.cornerRadius) == 0 && this.backgroundColor == qSLongPressProperties.backgroundColor && this.labelColor == qSLongPressProperties.labelColor && this.secondaryLabelColor == qSLongPressProperties.secondaryLabelColor && this.chevronColor == qSLongPressProperties.chevronColor && this.overlayColor == qSLongPressProperties.overlayColor && this.iconColor == qSLongPressProperties.iconColor;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    public final int getChevronColor() {
        return this.chevronColor;
    }

    public final float getCornerRadius() {
        return this.cornerRadius;
    }

    public final int getIconColor() {
        return this.iconColor;
    }

    public final int getLabelColor() {
        return this.labelColor;
    }

    public final int getOverlayColor() {
        return this.overlayColor;
    }

    public final int getSecondaryLabelColor() {
        return this.secondaryLabelColor;
    }

    public final float getXScale() {
        return this.xScale;
    }

    public int hashCode() {
        return (((((((((((((((Float.hashCode(this.xScale) * 31) + Float.hashCode(this.yScale)) * 31) + Float.hashCode(this.cornerRadius)) * 31) + Integer.hashCode(this.backgroundColor)) * 31) + Integer.hashCode(this.labelColor)) * 31) + Integer.hashCode(this.secondaryLabelColor)) * 31) + Integer.hashCode(this.chevronColor)) * 31) + Integer.hashCode(this.overlayColor)) * 31) + Integer.hashCode(this.iconColor);
    }

    public String toString() {
        return "QSLongPressProperties(xScale=" + this.xScale + ", yScale=" + this.yScale + ", cornerRadius=" + this.cornerRadius + ", backgroundColor=" + this.backgroundColor + ", labelColor=" + this.labelColor + ", secondaryLabelColor=" + this.secondaryLabelColor + ", chevronColor=" + this.chevronColor + ", overlayColor=" + this.overlayColor + ", iconColor=" + this.iconColor + ")";
    }
}
