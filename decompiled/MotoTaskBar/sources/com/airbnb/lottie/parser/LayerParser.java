package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.LBlendMode;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.motorola.plugin.core.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class LayerParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd", "ao", "bm");
    private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", "a");
    private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("ty", "nm");

    /* JADX INFO: renamed from: com.airbnb.lottie.parser.LayerParser$1, reason: invalid class name */
    abstract /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType;

        static {
            int[] iArr = new int[Layer.MatteType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = iArr;
            try {
                iArr[Layer.MatteType.LUMA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[Layer.MatteType.LUMA_INVERTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static Layer parse(LottieComposition lottieComposition) {
        Rect bounds = lottieComposition.getBounds();
        List list = Collections.EMPTY_LIST;
        return new Layer(list, lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, list, new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, list, Layer.MatteType.NONE, null, false, null, null, LBlendMode.NORMAL);
    }

    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) {
        Float f;
        ArrayList arrayList;
        boolean z;
        float f2;
        ArrayList arrayList2;
        float f3;
        float f4;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        LBlendMode lBlendMode = LBlendMode.NORMAL;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        jsonReader.beginObject();
        boolean z2 = false;
        float f5 = 0.0f;
        Float fValueOf = Float.valueOf(0.0f);
        float fNextDouble = 1.0f;
        Float fValueOf2 = Float.valueOf(1.0f);
        LBlendMode lBlendMode2 = lBlendMode;
        AnimatableTextFrame documentData = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        BlurEffect blurEffect = null;
        DropShadowEffect dropShadowEffect = null;
        boolean z3 = false;
        int iNextInt = 0;
        int iNextInt2 = 0;
        int color = 0;
        boolean zNextBoolean = false;
        long jNextInt = 0;
        float fNextDouble2 = 0.0f;
        float endFrame = 0.0f;
        float fNextDouble3 = 0.0f;
        float fNextDouble4 = 0.0f;
        Layer.MatteType matteType2 = matteType;
        long jNextInt2 = -1;
        String strNextString = "UNSET";
        String strNextString2 = null;
        AnimatableTransform animatableTransform = null;
        Layer.LayerType layerType = null;
        String strNextString3 = null;
        float fNextDouble5 = 0.0f;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString = jsonReader.nextString();
                    z2 = false;
                    break;
                case 1:
                    jNextInt = jsonReader.nextInt();
                    z2 = false;
                    break;
                case 2:
                    strNextString3 = jsonReader.nextString();
                    z2 = false;
                    break;
                case 3:
                    f2 = f5;
                    int iNextInt3 = jsonReader.nextInt();
                    layerType = Layer.LayerType.UNKNOWN;
                    if (iNextInt3 < layerType.ordinal()) {
                        layerType = Layer.LayerType.values()[iNextInt3];
                    }
                    f5 = f2;
                    z2 = false;
                    break;
                case 4:
                    jNextInt2 = jsonReader.nextInt();
                    z2 = false;
                    break;
                case 5:
                    iNextInt = (int) (jsonReader.nextInt() * Utils.dpScale());
                    z2 = false;
                    break;
                case 6:
                    iNextInt2 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    z2 = false;
                    break;
                case 7:
                    color = Color.parseColor(jsonReader.nextString());
                    z2 = false;
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    z2 = false;
                    break;
                case 9:
                    f2 = f5;
                    int iNextInt4 = jsonReader.nextInt();
                    if (iNextInt4 >= Layer.MatteType.values().length) {
                        lottieComposition.addWarning("Unsupported matte type: " + iNextInt4);
                    } else {
                        matteType2 = Layer.MatteType.values()[iNextInt4];
                        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        if (i == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.incrementMatteOrMaskCount(1);
                    }
                    f5 = f2;
                    z2 = false;
                    break;
                case R.styleable.GradientColor_android_endX /* 10 */:
                    f2 = f5;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList3.add(MaskParser.parse(jsonReader, lottieComposition));
                    }
                    lottieComposition.incrementMatteOrMaskCount(arrayList3.size());
                    jsonReader.endArray();
                    f5 = f2;
                    z2 = false;
                    break;
                case R.styleable.GradientColor_android_endY /* 11 */:
                    ArrayList arrayList5 = arrayList4;
                    f2 = f5;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel contentModel = ContentModelParser.parse(jsonReader, lottieComposition);
                        ArrayList arrayList6 = arrayList5;
                        if (contentModel != null) {
                            arrayList6.add(contentModel);
                        }
                        arrayList5 = arrayList6;
                    }
                    arrayList4 = arrayList5;
                    jsonReader.endArray();
                    f5 = f2;
                    z2 = false;
                    break;
                case 12:
                    arrayList2 = arrayList4;
                    f3 = f5;
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(TEXT_NAMES);
                        if (iSelectName == 0) {
                            documentData = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                        } else if (iSelectName != 1) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    f5 = f3;
                    arrayList4 = arrayList2;
                    z2 = false;
                    break;
                case 13:
                    arrayList2 = arrayList4;
                    f3 = f5;
                    jsonReader.beginArray();
                    ArrayList arrayList7 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(EFFECTS_NAMES);
                            if (iSelectName2 == 0) {
                                int iNextInt5 = jsonReader.nextInt();
                                if (iNextInt5 == 29) {
                                    blurEffect = BlurEffectParser.parse(jsonReader, lottieComposition);
                                } else if (iNextInt5 == 25) {
                                    dropShadowEffect = new DropShadowEffectParser().parse(jsonReader, lottieComposition);
                                }
                            } else if (iSelectName2 != 1) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                arrayList7.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList7);
                    f5 = f3;
                    arrayList4 = arrayList2;
                    z2 = false;
                    break;
                case 14:
                    fNextDouble = (float) jsonReader.nextDouble();
                    z2 = false;
                    break;
                case 15:
                    fNextDouble5 = (float) jsonReader.nextDouble();
                    z2 = false;
                    break;
                case 16:
                    arrayList2 = arrayList4;
                    fNextDouble3 = (float) (jsonReader.nextDouble() * ((double) Utils.dpScale()));
                    arrayList4 = arrayList2;
                    z2 = false;
                    break;
                case 17:
                    f3 = f5;
                    arrayList2 = arrayList4;
                    fNextDouble4 = (float) (jsonReader.nextDouble() * ((double) Utils.dpScale()));
                    f5 = f3;
                    arrayList4 = arrayList2;
                    z2 = false;
                    break;
                case 18:
                    fNextDouble2 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    endFrame = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z2);
                    break;
                case 21:
                    strNextString2 = jsonReader.nextString();
                    break;
                case 22:
                    zNextBoolean = jsonReader.nextBoolean();
                    break;
                case 23:
                    f4 = f5;
                    z3 = jsonReader.nextInt() == 1 ? true : z2;
                    f5 = f4;
                    break;
                case 24:
                    int iNextInt6 = jsonReader.nextInt();
                    if (iNextInt6 < LBlendMode.values().length) {
                        lBlendMode2 = LBlendMode.values()[iNextInt6];
                    } else {
                        StringBuilder sb = new StringBuilder();
                        f4 = f5;
                        sb.append("Unsupported Blend Mode: ");
                        sb.append(iNextInt6);
                        lottieComposition.addWarning(sb.toString());
                        lBlendMode2 = LBlendMode.NORMAL;
                        f5 = f4;
                    }
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    f2 = f5;
                    f5 = f2;
                    z2 = false;
                    break;
            }
        }
        float f6 = f5;
        jsonReader.endObject();
        ArrayList arrayList8 = new ArrayList();
        if (fNextDouble2 > f6) {
            arrayList = arrayList4;
            z = z3;
            f = fValueOf;
            arrayList8.add(new Keyframe(lottieComposition, fValueOf, fValueOf, null, 0.0f, Float.valueOf(fNextDouble2)));
        } else {
            f = fValueOf;
            arrayList = arrayList4;
            z = z3;
        }
        if (endFrame <= f6) {
            endFrame = lottieComposition.getEndFrame();
        }
        arrayList8.add(new Keyframe(lottieComposition, fValueOf2, fValueOf2, null, fNextDouble2, Float.valueOf(endFrame)));
        arrayList8.add(new Keyframe(lottieComposition, f, f, null, endFrame, Float.valueOf(Float.MAX_VALUE)));
        if (strNextString.endsWith(".ai") || "ai".equals(strNextString2)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        if (z) {
            if (animatableTransform == null) {
                animatableTransform = new AnimatableTransform();
            }
            animatableTransform.setAutoOrient(z);
        }
        return new Layer(arrayList, lottieComposition, strNextString, jNextInt, layerType, jNextInt2, strNextString3, arrayList3, animatableTransform, iNextInt, iNextInt2, color, fNextDouble, fNextDouble5, fNextDouble3, fNextDouble4, documentData, animatableTextProperties, arrayList8, matteType2, animatableFloatValue, zNextBoolean, blurEffect, dropShadowEffect, lBlendMode2);
    }
}
