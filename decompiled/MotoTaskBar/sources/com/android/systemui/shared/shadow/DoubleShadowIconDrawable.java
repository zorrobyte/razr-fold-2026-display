package com.android.systemui.shared.shadow;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RecordingCanvas;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;

/* JADX INFO: compiled from: DoubleShadowIconDrawable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DoubleShadowIconDrawable extends Drawable {
    private final int iconInsetSize;
    private final DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    private final int mCanvasSize;
    private final RenderNode mDoubleShadowNode;
    private final InsetDrawable mIconDrawable;
    private final DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;

    public DoubleShadowIconDrawable(DoubleShadowTextHelper.ShadowInfo shadowInfo, DoubleShadowTextHelper.ShadowInfo shadowInfo2, Drawable drawable, int i, int i2) {
        shadowInfo.getClass();
        shadowInfo2.getClass();
        drawable.getClass();
        this.iconInsetSize = i2;
        int i3 = i + (i2 * 2);
        this.mCanvasSize = i3;
        this.mKeyShadowInfo = shadowInfo;
        this.mAmbientShadowInfo = shadowInfo2;
        setBounds(0, 0, i3, i3);
        InsetDrawable insetDrawable = new InsetDrawable(drawable, i2);
        this.mIconDrawable = insetDrawable;
        insetDrawable.setBounds(0, 0, i3, i3);
        this.mDoubleShadowNode = createShadowRenderNode();
    }

    private final RenderEffect createShadowRenderEffect(float f, float f2, float f3, float f4) {
        RenderEffect renderEffectCreateColorFilterEffect = RenderEffect.createColorFilterEffect(new PorterDuffColorFilter(Color.argb(f4, 0.0f, 0.0f, 0.0f), PorterDuff.Mode.MULTIPLY), RenderEffect.createOffsetEffect(f2, f3, RenderEffect.createBlurEffect(f, f, Shader.TileMode.CLAMP)));
        renderEffectCreateColorFilterEffect.getClass();
        return renderEffectCreateColorFilterEffect;
    }

    private final RenderNode createShadowRenderNode() {
        RenderNode renderNode = new RenderNode("DoubleShadowNode");
        int i = this.mCanvasSize;
        renderNode.setPosition(0, 0, i, i);
        RenderEffect renderEffectCreateBlendModeEffect = RenderEffect.createBlendModeEffect(createShadowRenderEffect(this.mAmbientShadowInfo.getBlur(), this.mAmbientShadowInfo.getOffsetX(), this.mAmbientShadowInfo.getOffsetY(), this.mAmbientShadowInfo.getAlpha()), createShadowRenderEffect(this.mKeyShadowInfo.getBlur(), this.mKeyShadowInfo.getOffsetX(), this.mKeyShadowInfo.getOffsetY(), this.mKeyShadowInfo.getAlpha()), BlendMode.DST_ATOP);
        renderEffectCreateBlendModeEffect.getClass();
        renderNode.setRenderEffect(renderEffectCreateBlendModeEffect);
        return renderNode;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.getClass();
        if (canvas.isHardwareAccelerated()) {
            if (!this.mDoubleShadowNode.hasDisplayList()) {
                RecordingCanvas recordingCanvasBeginRecording = this.mDoubleShadowNode.beginRecording();
                recordingCanvasBeginRecording.getClass();
                this.mIconDrawable.draw(recordingCanvasBeginRecording);
                this.mDoubleShadowNode.endRecording();
            }
            canvas.drawRenderNode(this.mDoubleShadowNode);
        }
        this.mIconDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mCanvasSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mCanvasSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mIconDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mIconDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(int i) {
        this.mIconDrawable.setTint(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mIconDrawable.setTintList(colorStateList);
    }
}
