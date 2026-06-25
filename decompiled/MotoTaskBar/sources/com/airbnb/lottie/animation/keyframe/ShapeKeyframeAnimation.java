package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.animation.content.ShapeModifierContent;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ShapeKeyframeAnimation extends BaseKeyframeAnimation {
    private List shapeModifiers;
    private final Path tempPath;
    private final ShapeData tempShapeData;
    private Path valueCallbackEndPath;
    private Path valueCallbackStartPath;

    public ShapeKeyframeAnimation(List list) {
        super(list);
        this.tempShapeData = new ShapeData();
        this.tempPath = new Path();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public Path getValue(Keyframe keyframe, float f) {
        ShapeData shapeData = (ShapeData) keyframe.startValue;
        ShapeData shapeData2 = (ShapeData) keyframe.endValue;
        this.tempShapeData.interpolateBetween(shapeData, shapeData2 == null ? shapeData : shapeData2, f);
        ShapeData shapeDataModifyShape = this.tempShapeData;
        List list = this.shapeModifiers;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                shapeDataModifyShape = ((ShapeModifierContent) this.shapeModifiers.get(size)).modifyShape(shapeDataModifyShape);
            }
        }
        MiscUtils.getPathFromData(shapeDataModifyShape, this.tempPath);
        if (this.valueCallback == null) {
            return this.tempPath;
        }
        if (this.valueCallbackStartPath == null) {
            this.valueCallbackStartPath = new Path();
            this.valueCallbackEndPath = new Path();
        }
        MiscUtils.getPathFromData(shapeData, this.valueCallbackStartPath);
        if (shapeData2 != null) {
            MiscUtils.getPathFromData(shapeData2, this.valueCallbackEndPath);
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        float f2 = keyframe.startFrame;
        float fFloatValue = keyframe.endFrame.floatValue();
        Path path = this.valueCallbackStartPath;
        return (Path) lottieValueCallback.getValueInternal(f2, fFloatValue, path, shapeData2 == null ? path : this.valueCallbackEndPath, f, getLinearCurrentKeyframeProgress(), getProgress());
    }

    public void setShapeModifiers(List list) {
        this.shapeModifiers = list;
    }
}
