package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;

/* JADX INFO: loaded from: classes.dex */
abstract class ContentModelParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of("ty", "d");

    static ContentModel parse(JsonReader jsonReader, LottieComposition lottieComposition) {
        ContentModel contentModel;
        String strNextString;
        jsonReader.beginObject();
        int iNextInt = 2;
        while (true) {
            contentModel = null;
            if (!jsonReader.hasNext()) {
                strNextString = null;
                break;
            }
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                strNextString = jsonReader.nextString();
                break;
            }
            if (iSelectName != 1) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                iNextInt = jsonReader.nextInt();
            }
        }
        if (strNextString == null) {
            return null;
        }
        switch (strNextString) {
            case "el":
                contentModel = CircleShapeParser.parse(jsonReader, lottieComposition, iNextInt);
                break;
            case "fl":
                contentModel = ShapeFillParser.parse(jsonReader, lottieComposition);
                break;
            case "gf":
                contentModel = GradientFillParser.parse(jsonReader, lottieComposition);
                break;
            case "gr":
                contentModel = ShapeGroupParser.parse(jsonReader, lottieComposition);
                break;
            case "gs":
                contentModel = GradientStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case "mm":
                contentModel = MergePathsParser.parse(jsonReader);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                break;
            case "rc":
                contentModel = RectangleShapeParser.parse(jsonReader, lottieComposition);
                break;
            case "rd":
                contentModel = RoundedCornersParser.parse(jsonReader, lottieComposition);
                break;
            case "rp":
                contentModel = RepeaterParser.parse(jsonReader, lottieComposition);
                break;
            case "sh":
                contentModel = ShapePathParser.parse(jsonReader, lottieComposition);
                break;
            case "sr":
                contentModel = PolystarShapeParser.parse(jsonReader, lottieComposition, iNextInt);
                break;
            case "st":
                contentModel = ShapeStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case "tm":
                contentModel = ShapeTrimPathParser.parse(jsonReader, lottieComposition);
                break;
            case "tr":
                contentModel = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                break;
            default:
                Logger.warning("Unknown shape type " + strNextString);
                break;
        }
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return contentModel;
    }
}
