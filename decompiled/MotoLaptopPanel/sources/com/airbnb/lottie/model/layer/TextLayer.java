package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTextRangeSelector;
import com.airbnb.lottie.model.animatable.AnimatableTextStyle;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.content.TextRangeUnits;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextLayer extends BaseLayer {
    private final LongSparseArray codePointCache;
    private BaseKeyframeAnimation colorAnimation;
    private BaseKeyframeAnimation colorCallbackAnimation;
    private final LottieComposition composition;
    private final Map contentsForCharacter;
    private final Paint fillPaint;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private BaseKeyframeAnimation opacityAnimation;
    private final RectF rectF;
    private final StringBuilder stringBuilder;
    private BaseKeyframeAnimation strokeColorAnimation;
    private BaseKeyframeAnimation strokeColorCallbackAnimation;
    private final Paint strokePaint;
    private BaseKeyframeAnimation strokeWidthAnimation;
    private BaseKeyframeAnimation strokeWidthCallbackAnimation;
    private final TextKeyframeAnimation textAnimation;
    private BaseKeyframeAnimation textRangeEndAnimation;
    private BaseKeyframeAnimation textRangeOffsetAnimation;
    private BaseKeyframeAnimation textRangeStartAnimation;
    private TextRangeUnits textRangeUnits;
    private BaseKeyframeAnimation textSizeCallbackAnimation;
    private final List textSubLines;
    private BaseKeyframeAnimation trackingAnimation;
    private BaseKeyframeAnimation trackingCallbackAnimation;
    private BaseKeyframeAnimation typefaceCallbackAnimation;

    /* JADX INFO: renamed from: com.airbnb.lottie.model.layer.TextLayer$3, reason: invalid class name */
    abstract /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification;

        static {
            int[] iArr = new int[DocumentData.Justification.values().length];
            $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification = iArr;
            try {
                iArr[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    class TextSubLine {
        private String text;
        private float width;

        private TextSubLine() {
            this.text = "";
            this.width = 0.0f;
        }

        void set(String str, float f) {
            this.text = str;
            this.width = f;
        }
    }

    TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        AnimatableTextRangeSelector animatableTextRangeSelector;
        AnimatableTextRangeSelector animatableTextRangeSelector2;
        AnimatableIntegerValue animatableIntegerValue;
        AnimatableTextRangeSelector animatableTextRangeSelector3;
        AnimatableIntegerValue animatableIntegerValue2;
        AnimatableTextRangeSelector animatableTextRangeSelector4;
        AnimatableIntegerValue animatableIntegerValue3;
        AnimatableTextStyle animatableTextStyle;
        AnimatableIntegerValue animatableIntegerValue4;
        AnimatableTextStyle animatableTextStyle2;
        AnimatableFloatValue animatableFloatValue;
        AnimatableTextStyle animatableTextStyle3;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableTextStyle animatableTextStyle4;
        AnimatableColorValue animatableColorValue;
        AnimatableTextStyle animatableTextStyle5;
        AnimatableColorValue animatableColorValue2;
        super(lottieDrawable, layer);
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        int i = 1;
        this.fillPaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray();
        this.textSubLines = new ArrayList();
        this.textRangeUnits = TextRangeUnits.INDEX;
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        TextKeyframeAnimation textKeyframeAnimationCreateAnimation = layer.getText().createAnimation();
        this.textAnimation = textKeyframeAnimationCreateAnimation;
        textKeyframeAnimationCreateAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimationCreateAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && (animatableTextStyle5 = textProperties.textStyle) != null && (animatableColorValue2 = animatableTextStyle5.color) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = baseKeyframeAnimationCreateAnimation;
            baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && (animatableTextStyle4 = textProperties.textStyle) != null && (animatableColorValue = animatableTextStyle4.stroke) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = baseKeyframeAnimationCreateAnimation2;
            baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && (animatableTextStyle3 = textProperties.textStyle) != null && (animatableFloatValue2 = animatableTextStyle3.strokeWidth) != null) {
            FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = floatKeyframeAnimationCreateAnimation;
            floatKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties != null && (animatableTextStyle2 = textProperties.textStyle) != null && (animatableFloatValue = animatableTextStyle2.tracking) != null) {
            FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation2 = animatableFloatValue.createAnimation();
            this.trackingAnimation = floatKeyframeAnimationCreateAnimation2;
            floatKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.trackingAnimation);
        }
        if (textProperties != null && (animatableTextStyle = textProperties.textStyle) != null && (animatableIntegerValue4 = animatableTextStyle.opacity) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = animatableIntegerValue4.createAnimation();
            this.opacityAnimation = baseKeyframeAnimationCreateAnimation3;
            baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
            addAnimation(this.opacityAnimation);
        }
        if (textProperties != null && (animatableTextRangeSelector4 = textProperties.rangeSelector) != null && (animatableIntegerValue3 = animatableTextRangeSelector4.start) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = animatableIntegerValue3.createAnimation();
            this.textRangeStartAnimation = baseKeyframeAnimationCreateAnimation4;
            baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
            addAnimation(this.textRangeStartAnimation);
        }
        if (textProperties != null && (animatableTextRangeSelector3 = textProperties.rangeSelector) != null && (animatableIntegerValue2 = animatableTextRangeSelector3.end) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation5 = animatableIntegerValue2.createAnimation();
            this.textRangeEndAnimation = baseKeyframeAnimationCreateAnimation5;
            baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
            addAnimation(this.textRangeEndAnimation);
        }
        if (textProperties != null && (animatableTextRangeSelector2 = textProperties.rangeSelector) != null && (animatableIntegerValue = animatableTextRangeSelector2.offset) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation6 = animatableIntegerValue.createAnimation();
            this.textRangeOffsetAnimation = baseKeyframeAnimationCreateAnimation6;
            baseKeyframeAnimationCreateAnimation6.addUpdateListener(this);
            addAnimation(this.textRangeOffsetAnimation);
        }
        if (textProperties == null || (animatableTextRangeSelector = textProperties.rangeSelector) == null) {
            return;
        }
        this.textRangeUnits = animatableTextRangeSelector.units;
    }

    private String codePointToString(String str, int i) {
        int iCodePointAt = str.codePointAt(i);
        int iCharCount = Character.charCount(iCodePointAt) + i;
        while (iCharCount < str.length()) {
            int iCodePointAt2 = str.codePointAt(iCharCount);
            if (!isModifier(iCodePointAt2)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt2);
            iCodePointAt = (iCodePointAt * 31) + iCodePointAt2;
        }
        long j = iCodePointAt;
        if (this.codePointCache.containsKey(j)) {
            return (String) this.codePointCache.get(j);
        }
        this.stringBuilder.setLength(0);
        while (i < iCharCount) {
            int iCodePointAt3 = str.codePointAt(i);
            this.stringBuilder.appendCodePoint(iCodePointAt3);
            i += Character.charCount(iCodePointAt3);
        }
        String string = this.stringBuilder.toString();
        this.codePointCache.put(j, string);
        return string;
    }

    private void configurePaint(DocumentData documentData, int i, int i2) {
        BaseKeyframeAnimation baseKeyframeAnimation = this.colorCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            this.fillPaint.setColor(((Integer) baseKeyframeAnimation.getValue()).intValue());
        } else if (this.colorAnimation == null || !isIndexInRangeSelection(i2)) {
            this.fillPaint.setColor(documentData.color);
        } else {
            this.fillPaint.setColor(((Integer) this.colorAnimation.getValue()).intValue());
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
        if (baseKeyframeAnimation2 != null) {
            this.strokePaint.setColor(((Integer) baseKeyframeAnimation2.getValue()).intValue());
        } else if (this.strokeColorAnimation == null || !isIndexInRangeSelection(i2)) {
            this.strokePaint.setColor(documentData.strokeColor);
        } else {
            this.strokePaint.setColor(((Integer) this.strokeColorAnimation.getValue()).intValue());
        }
        int iIntValue = 100;
        int iIntValue2 = this.transform.getOpacity() == null ? 100 : ((Integer) this.transform.getOpacity().getValue()).intValue();
        if (this.opacityAnimation != null && isIndexInRangeSelection(i2)) {
            iIntValue = ((Integer) this.opacityAnimation.getValue()).intValue();
        }
        int iRound = Math.round(((((iIntValue2 * 255.0f) / 100.0f) * (iIntValue / 100.0f)) * i) / 255.0f);
        this.fillPaint.setAlpha(iRound);
        this.strokePaint.setAlpha(iRound);
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
        if (baseKeyframeAnimation3 != null) {
            this.strokePaint.setStrokeWidth(((Float) baseKeyframeAnimation3.getValue()).floatValue());
        } else if (this.strokeWidthAnimation == null || !isIndexInRangeSelection(i2)) {
            this.strokePaint.setStrokeWidth(documentData.strokeWidth * Utils.dpScale());
        } else {
            this.strokePaint.setStrokeWidth(((Float) this.strokeWidthAnimation.getValue()).floatValue());
        }
    }

    private void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    private void drawCharacterAsGlyph(FontCharacter fontCharacter, float f, DocumentData documentData, Canvas canvas, int i, int i2) {
        configurePaint(documentData, i2, i);
        List contentsForCharacter = getContentsForCharacter(fontCharacter);
        for (int i3 = 0; i3 < contentsForCharacter.size(); i3++) {
            Path path = ((ContentGroup) contentsForCharacter.get(i3)).getPath();
            path.computeBounds(this.rectF, false);
            this.matrix.reset();
            this.matrix.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
            this.matrix.preScale(f, f);
            path.transform(this.matrix);
            if (documentData.strokeOverFill) {
                drawGlyph(path, this.fillPaint, canvas);
                drawGlyph(path, this.strokePaint, canvas);
            } else {
                drawGlyph(path, this.strokePaint, canvas);
                drawGlyph(path, this.fillPaint, canvas);
            }
        }
    }

    private void drawCharacterFromFont(String str, DocumentData documentData, Canvas canvas, int i, int i2) {
        configurePaint(documentData, i2, i);
        if (documentData.strokeOverFill) {
            drawCharacter(str, this.fillPaint, canvas);
            drawCharacter(str, this.strokePaint, canvas);
        } else {
            drawCharacter(str, this.strokePaint, canvas);
            drawCharacter(str, this.fillPaint, canvas);
        }
    }

    private void drawFontTextLine(String str, DocumentData documentData, Canvas canvas, float f, int i, int i2) {
        int length = 0;
        while (length < str.length()) {
            String strCodePointToString = this.codePointToString(str, length);
            TextLayer textLayer = this;
            DocumentData documentData2 = documentData;
            textLayer.drawCharacterFromFont(strCodePointToString, documentData2, canvas, i + length, i2);
            canvas.translate(textLayer.fillPaint.measureText(strCodePointToString) + f, 0.0f);
            length += strCodePointToString.length();
            this = textLayer;
            documentData = documentData2;
        }
    }

    private void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void drawGlyphTextLine(String str, DocumentData documentData, Font font, Canvas canvas, float f, float f2, float f3, int i) {
        TextLayer textLayer;
        DocumentData documentData2;
        Canvas canvas2;
        float f4;
        int i2;
        int i3 = 0;
        while (i3 < str.length()) {
            FontCharacter fontCharacter = (FontCharacter) this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i3), font.getFamily(), font.getStyle()));
            if (fontCharacter == null) {
                textLayer = this;
                documentData2 = documentData;
                canvas2 = canvas;
                f4 = f2;
                i2 = i;
            } else {
                textLayer = this;
                documentData2 = documentData;
                canvas2 = canvas;
                f4 = f2;
                i2 = i;
                textLayer.drawCharacterAsGlyph(fontCharacter, f4, documentData2, canvas2, i3, i2);
                canvas2.translate((((float) fontCharacter.getWidth()) * f4 * Utils.dpScale()) + f3, 0.0f);
            }
            i3++;
            this = textLayer;
            f2 = f4;
            documentData = documentData2;
            canvas = canvas2;
            i = i2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawTextWithFont(com.airbnb.lottie.model.DocumentData r18, com.airbnb.lottie.model.Font r19, android.graphics.Canvas r20, int r21) {
        /*
            Method dump skipped, instruction units count: 238
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.TextLayer.drawTextWithFont(com.airbnb.lottie.model.DocumentData, com.airbnb.lottie.model.Font, android.graphics.Canvas, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawTextWithGlyphs(com.airbnb.lottie.model.DocumentData r17, android.graphics.Matrix r18, com.airbnb.lottie.model.Font r19, android.graphics.Canvas r20, int r21) {
        /*
            r16 = this;
            r0 = r16
            r7 = r17
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r1 = r0.textSizeCallbackAnimation
            if (r1 == 0) goto L13
            java.lang.Object r1 = r1.getValue()
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            goto L15
        L13:
            float r1 = r7.size
        L15:
            r2 = 1120403456(0x42c80000, float:100.0)
            float r4 = r1 / r2
            float r8 = com.airbnb.lottie.utils.Utils.getScale(r18)
            java.lang.String r1 = r7.text
            java.util.List r9 = r0.getTextLines(r1)
            int r10 = r9.size()
            int r1 = r7.tracking
            float r1 = (float) r1
            r2 = 1092616192(0x41200000, float:10.0)
            float r1 = r1 / r2
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r2 = r0.trackingCallbackAnimation
            if (r2 == 0) goto L3e
            java.lang.Object r2 = r2.getValue()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
        L3b:
            float r1 = r1 + r2
        L3c:
            r5 = r1
            goto L4d
        L3e:
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r2 = r0.trackingAnimation
            if (r2 == 0) goto L3c
            java.lang.Object r2 = r2.getValue()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            goto L3b
        L4d:
            r11 = 0
            r1 = -1
            r12 = r1
            r13 = r11
        L51:
            if (r13 >= r10) goto Lb0
            java.lang.Object r1 = r9.get(r13)
            java.lang.String r1 = (java.lang.String) r1
            android.graphics.PointF r2 = r7.boxSize
            if (r2 != 0) goto L5f
            r2 = 0
            goto L61
        L5f:
            float r2 = r2.x
        L61:
            r6 = 1
            r3 = r19
            java.util.List r14 = r0.splitGlyphTextIntoLines(r1, r2, r3, r4, r5, r6)
            r15 = r11
        L69:
            int r1 = r14.size()
            if (r15 >= r1) goto La6
            java.lang.Object r1 = r14.get(r15)
            com.airbnb.lottie.model.layer.TextLayer$TextSubLine r1 = (com.airbnb.lottie.model.layer.TextLayer.TextSubLine) r1
            int r12 = r12 + 1
            r20.save()
            float r2 = com.airbnb.lottie.model.layer.TextLayer.TextSubLine.access$000(r1)
            r3 = r20
            boolean r2 = r0.offsetCanvas(r3, r7, r12, r2)
            if (r2 == 0) goto L98
            java.lang.String r1 = com.airbnb.lottie.model.layer.TextLayer.TextSubLine.access$100(r1)
            r6 = r4
            r2 = r7
            r4 = r3
            r7 = r5
            r5 = r8
            r3 = r19
            r8 = r21
            r0.drawGlyphTextLine(r1, r2, r3, r4, r5, r6, r7, r8)
            r4 = r6
            goto L9a
        L98:
            r7 = r5
            r5 = r8
        L9a:
            r20.restore()
            int r15 = r15 + 1
            r0 = r16
            r8 = r5
            r5 = r7
            r7 = r17
            goto L69
        La6:
            r7 = r5
            r5 = r8
            int r13 = r13 + 1
            r0 = r16
            r5 = r7
            r7 = r17
            goto L51
        Lb0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.TextLayer.drawTextWithGlyphs(com.airbnb.lottie.model.DocumentData, android.graphics.Matrix, com.airbnb.lottie.model.Font, android.graphics.Canvas, int):void");
    }

    private TextSubLine ensureEnoughSubLines(int i) {
        for (int size = this.textSubLines.size(); size < i; size++) {
            this.textSubLines.add(new TextSubLine());
        }
        return (TextSubLine) this.textSubLines.get(i - 1);
    }

    private List getContentsForCharacter(FontCharacter fontCharacter) {
        if (this.contentsForCharacter.containsKey(fontCharacter)) {
            return (List) this.contentsForCharacter.get(fontCharacter);
        }
        List shapes = fontCharacter.getShapes();
        int size = shapes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new ContentGroup(this.lottieDrawable, this, (ShapeGroup) shapes.get(i), this.composition));
        }
        this.contentsForCharacter.put(fontCharacter, arrayList);
        return arrayList;
    }

    private List getTextLines(String str) {
        return Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
    }

    private Typeface getTypeface(Font font) {
        Typeface typeface;
        BaseKeyframeAnimation baseKeyframeAnimation = this.typefaceCallbackAnimation;
        if (baseKeyframeAnimation != null && (typeface = (Typeface) baseKeyframeAnimation.getValue()) != null) {
            return typeface;
        }
        Typeface typeface2 = this.lottieDrawable.getTypeface(font);
        return typeface2 != null ? typeface2 : font.getTypeface();
    }

    private boolean isIndexInRangeSelection(int i) {
        int length = ((DocumentData) this.textAnimation.getValue()).text.length();
        BaseKeyframeAnimation baseKeyframeAnimation = this.textRangeStartAnimation;
        if (baseKeyframeAnimation == null || this.textRangeEndAnimation == null) {
            return true;
        }
        int iMin = Math.min(((Integer) baseKeyframeAnimation.getValue()).intValue(), ((Integer) this.textRangeEndAnimation.getValue()).intValue());
        int iMax = Math.max(((Integer) this.textRangeStartAnimation.getValue()).intValue(), ((Integer) this.textRangeEndAnimation.getValue()).intValue());
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.textRangeOffsetAnimation;
        if (baseKeyframeAnimation2 != null) {
            int iIntValue = ((Integer) baseKeyframeAnimation2.getValue()).intValue();
            iMin += iIntValue;
            iMax += iIntValue;
        }
        if (this.textRangeUnits == TextRangeUnits.INDEX) {
            return i >= iMin && i < iMax;
        }
        float f = (i / length) * 100.0f;
        return f >= ((float) iMin) && f < ((float) iMax);
    }

    private boolean isModifier(int i) {
        return Character.getType(i) == 16 || Character.getType(i) == 27 || Character.getType(i) == 6 || Character.getType(i) == 28 || Character.getType(i) == 8 || Character.getType(i) == 19;
    }

    private boolean offsetCanvas(Canvas canvas, DocumentData documentData, int i, float f) {
        PointF pointF = documentData.boxPosition;
        PointF pointF2 = documentData.boxSize;
        float fDpScale = Utils.dpScale();
        float f2 = (i * documentData.lineHeight * fDpScale) + (pointF == null ? 0.0f : (documentData.lineHeight * fDpScale) + pointF.y);
        if (this.lottieDrawable.getClipTextToBoundingBox() && pointF2 != null && pointF != null && f2 >= pointF.y + pointF2.y + documentData.size) {
            return false;
        }
        float f3 = pointF == null ? 0.0f : pointF.x;
        float f4 = pointF2 != null ? pointF2.x : 0.0f;
        int i2 = AnonymousClass3.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[documentData.justification.ordinal()];
        if (i2 == 1) {
            canvas.translate(f3, f2);
        } else if (i2 == 2) {
            canvas.translate((f3 + f4) - f, f2);
        } else if (i2 == 3) {
            canvas.translate((f3 + (f4 / 2.0f)) - (f / 2.0f), f2);
        }
        return true;
    }

    private List splitGlyphTextIntoLines(String str, float f, Font font, float f2, float f3, boolean z) {
        float fMeasureText;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i4 = 0; i4 < str.length(); i4++) {
            char cCharAt = str.charAt(i4);
            if (z) {
                FontCharacter fontCharacter = (FontCharacter) this.composition.getCharacters().get(FontCharacter.hashFor(cCharAt, font.getFamily(), font.getStyle()));
                if (fontCharacter != null) {
                    fMeasureText = ((float) fontCharacter.getWidth()) * f2 * Utils.dpScale();
                }
            } else {
                fMeasureText = this.fillPaint.measureText(str.substring(i4, i4 + 1));
            }
            float f7 = fMeasureText + f3;
            if (cCharAt == ' ') {
                z2 = true;
                f6 = f7;
            } else if (z2) {
                z2 = false;
                i3 = i4;
                f5 = f7;
            } else {
                f5 += f7;
            }
            f4 += f7;
            if (f > 0.0f && f4 >= f && cCharAt != ' ') {
                i++;
                TextSubLine textSubLineEnsureEnoughSubLines = ensureEnoughSubLines(i);
                if (i3 == i2) {
                    textSubLineEnsureEnoughSubLines.set(str.substring(i2, i4).trim(), (f4 - f7) - ((r9.length() - r7.length()) * f6));
                    i2 = i4;
                    i3 = i2;
                    f4 = f7;
                    f5 = f4;
                } else {
                    textSubLineEnsureEnoughSubLines.set(str.substring(i2, i3 - 1).trim(), ((f4 - f5) - ((r7.length() - r13.length()) * f6)) - f6);
                    f4 = f5;
                    i2 = i3;
                }
            }
        }
        if (f4 > 0.0f) {
            i++;
            ensureEnoughSubLines(i).set(str.substring(i2), f4);
        }
        return this.textSubLines.subList(0, i);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(Object obj, LottieValueCallback lottieValueCallback) {
        super.addValueCallback(obj, lottieValueCallback);
        if (obj == LottieProperty.COLOR) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.colorCallbackAnimation;
            if (baseKeyframeAnimation != null) {
                removeAnimation(baseKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_COLOR) {
            BaseKeyframeAnimation baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
            if (baseKeyframeAnimation2 != null) {
                removeAnimation(baseKeyframeAnimation2);
            }
            if (lottieValueCallback == null) {
                this.strokeColorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeColorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_WIDTH) {
            BaseKeyframeAnimation baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
            if (baseKeyframeAnimation3 != null) {
                removeAnimation(baseKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.strokeWidthCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeWidthCallbackAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_TRACKING) {
            BaseKeyframeAnimation baseKeyframeAnimation4 = this.trackingCallbackAnimation;
            if (baseKeyframeAnimation4 != null) {
                removeAnimation(baseKeyframeAnimation4);
            }
            if (lottieValueCallback == null) {
                this.trackingCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.trackingCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            addAnimation(this.trackingCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_SIZE) {
            BaseKeyframeAnimation baseKeyframeAnimation5 = this.textSizeCallbackAnimation;
            if (baseKeyframeAnimation5 != null) {
                removeAnimation(baseKeyframeAnimation5);
            }
            if (lottieValueCallback == null) {
                this.textSizeCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation5;
            valueCallbackKeyframeAnimation5.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
            return;
        }
        if (obj != LottieProperty.TYPEFACE) {
            if (obj == LottieProperty.TEXT) {
                this.textAnimation.setStringValueCallback(lottieValueCallback);
                return;
            }
            return;
        }
        BaseKeyframeAnimation baseKeyframeAnimation6 = this.typefaceCallbackAnimation;
        if (baseKeyframeAnimation6 != null) {
            removeAnimation(baseKeyframeAnimation6);
        }
        if (lottieValueCallback == null) {
            this.typefaceCallbackAnimation = null;
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
        this.typefaceCallbackAnimation = valueCallbackKeyframeAnimation6;
        valueCallbackKeyframeAnimation6.addUpdateListener(this);
        addAnimation(this.typefaceCallbackAnimation);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Canvas canvas2;
        DocumentData documentData = (DocumentData) this.textAnimation.getValue();
        Font font = (Font) this.composition.getFonts().get(documentData.fontName);
        if (font == null) {
            return;
        }
        canvas.save();
        canvas.concat(matrix);
        configurePaint(documentData, i, 0);
        if (this.lottieDrawable.useTextGlyphs()) {
            canvas2 = canvas;
            drawTextWithGlyphs(documentData, matrix, font, canvas2, i);
        } else {
            canvas2 = canvas;
            drawTextWithFont(documentData, font, canvas2, i);
        }
        canvas2.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        rectF.set(0.0f, 0.0f, this.composition.getBounds().width(), this.composition.getBounds().height());
    }
}
