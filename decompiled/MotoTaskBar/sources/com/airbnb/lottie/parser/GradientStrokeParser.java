package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import com.motorola.plugin.core.R;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
abstract class GradientStrokeParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "g", "o", "t", "s", "e", "w", "lc", "lj", "ml", "hd", "d");
    private static final JsonReader.Options GRADIENT_NAMES = JsonReader.Options.of("p", "k");
    private static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.of("n", "v");

    static GradientStroke parse(JsonReader jsonReader, LottieComposition lottieComposition) {
        GradientType gradientType;
        AnimatableIntegerValue animatableIntegerValue;
        ArrayList arrayList = new ArrayList();
        GradientType gradientType2 = null;
        String strNextString = null;
        AnimatableGradientColorValue gradientColor = null;
        AnimatablePointValue point = null;
        AnimatablePointValue point2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        float fNextDouble = 0.0f;
        boolean zNextBoolean = false;
        AnimatableIntegerValue integer = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    strNextString = jsonReader.nextString();
                    break;
                case 1:
                    gradientType = gradientType2;
                    animatableIntegerValue = integer;
                    jsonReader.beginObject();
                    int iNextInt = -1;
                    while (jsonReader.hasNext()) {
                        int iSelectName = jsonReader.selectName(GRADIENT_NAMES);
                        if (iSelectName == 0) {
                            iNextInt = jsonReader.nextInt();
                        } else if (iSelectName != 1) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            gradientColor = AnimatableValueParser.parseGradientColor(jsonReader, lottieComposition, iNextInt);
                        }
                    }
                    jsonReader.endObject();
                    integer = animatableIntegerValue;
                    gradientType2 = gradientType;
                    break;
                case 2:
                    integer = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    break;
                case 3:
                    AnimatableIntegerValue animatableIntegerValue2 = integer;
                    gradientType2 = jsonReader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                    integer = animatableIntegerValue2;
                    break;
                case 4:
                    point = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    break;
                case 5:
                    point2 = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
                    break;
                case 6:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                    break;
                case 7:
                    gradientType = gradientType2;
                    animatableIntegerValue = integer;
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    integer = animatableIntegerValue;
                    gradientType2 = gradientType;
                    break;
                case 8:
                    gradientType = gradientType2;
                    animatableIntegerValue = integer;
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    integer = animatableIntegerValue;
                    gradientType2 = gradientType;
                    break;
                case 9:
                    gradientType = gradientType2;
                    animatableIntegerValue = integer;
                    fNextDouble = (float) jsonReader.nextDouble();
                    integer = animatableIntegerValue;
                    gradientType2 = gradientType;
                    break;
                case R.styleable.GradientColor_android_endX /* 10 */:
                    gradientType = gradientType2;
                    zNextBoolean = jsonReader.nextBoolean();
                    gradientType2 = gradientType;
                    break;
                case R.styleable.GradientColor_android_endY /* 11 */:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String strNextString2 = null;
                        AnimatableFloatValue animatableFloatValue3 = null;
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(DASH_PATTERN_NAMES);
                            if (iSelectName2 != 0) {
                                GradientType gradientType3 = gradientType2;
                                if (iSelectName2 != 1) {
                                    jsonReader.skipName();
                                    jsonReader.skipValue();
                                } else {
                                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                                }
                                gradientType2 = gradientType3;
                            } else {
                                strNextString2 = jsonReader.nextString();
                            }
                        }
                        GradientType gradientType4 = gradientType2;
                        jsonReader.endObject();
                        if (strNextString2.equals("o")) {
                            animatableFloatValue2 = animatableFloatValue3;
                        } else {
                            if (strNextString2.equals("d") || strNextString2.equals("g")) {
                                lottieComposition.setHasDashPattern(true);
                                arrayList.add(animatableFloatValue3);
                            }
                            gradientType2 = gradientType4;
                        }
                        gradientType2 = gradientType4;
                    }
                    gradientType = gradientType2;
                    jsonReader.endArray();
                    if (arrayList.size() == 1) {
                        arrayList.add((AnimatableFloatValue) arrayList.get(0));
                    }
                    gradientType2 = gradientType;
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        GradientType gradientType5 = gradientType2;
        AnimatableIntegerValue animatableIntegerValue3 = integer;
        if (animatableIntegerValue3 == null) {
            animatableIntegerValue3 = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
        }
        return new GradientStroke(strNextString, gradientType5, gradientColor, animatableIntegerValue3, point, point2, animatableFloatValue, lineCapType, lineJoinType, fNextDouble, arrayList, animatableFloatValue2, zNextBoolean);
    }
}
