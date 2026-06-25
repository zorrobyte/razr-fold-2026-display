package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TextKeyframeAnimation extends KeyframeAnimation {
    public TextKeyframeAnimation(List list) {
        super(list);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public DocumentData getValue(Keyframe keyframe, float f) {
        Object obj;
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback == null) {
            return (f != 1.0f || (obj = keyframe.endValue) == null) ? (DocumentData) keyframe.startValue : (DocumentData) obj;
        }
        float f2 = keyframe.startFrame;
        Float f3 = keyframe.endFrame;
        float fFloatValue = f3 == null ? Float.MAX_VALUE : f3.floatValue();
        Object obj2 = keyframe.startValue;
        DocumentData documentData = (DocumentData) obj2;
        Object obj3 = keyframe.endValue;
        return (DocumentData) lottieValueCallback.getValueInternal(f2, fFloatValue, documentData, obj3 == null ? (DocumentData) obj2 : (DocumentData) obj3, f, getInterpolatedCurrentKeyframeProgress(), getProgress());
    }

    public void setStringValueCallback(final LottieValueCallback lottieValueCallback) {
        final LottieFrameInfo lottieFrameInfo = new LottieFrameInfo();
        final DocumentData documentData = new DocumentData();
        super.setValueCallback(new LottieValueCallback() { // from class: com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation.1
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public DocumentData getValue(LottieFrameInfo lottieFrameInfo2) {
                lottieFrameInfo.set(lottieFrameInfo2.getStartFrame(), lottieFrameInfo2.getEndFrame(), ((DocumentData) lottieFrameInfo2.getStartValue()).text, ((DocumentData) lottieFrameInfo2.getEndValue()).text, lottieFrameInfo2.getLinearKeyframeProgress(), lottieFrameInfo2.getInterpolatedKeyframeProgress(), lottieFrameInfo2.getOverallProgress());
                String str = (String) lottieValueCallback.getValue(lottieFrameInfo);
                DocumentData documentData2 = (DocumentData) (lottieFrameInfo2.getInterpolatedKeyframeProgress() == 1.0f ? lottieFrameInfo2.getEndValue() : lottieFrameInfo2.getStartValue());
                documentData.set(str, documentData2.fontName, documentData2.size, documentData2.justification, documentData2.tracking, documentData2.lineHeight, documentData2.baselineShift, documentData2.color, documentData2.strokeColor, documentData2.strokeWidth, documentData2.strokeOverFill, documentData2.boxPosition, documentData2.boxSize);
                return documentData;
            }
        });
    }
}
