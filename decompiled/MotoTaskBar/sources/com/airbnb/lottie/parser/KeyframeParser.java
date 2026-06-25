package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
abstract class KeyframeParser {
    private static SparseArrayCompat pathInterpolatorCache;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static JsonReader.Options NAMES = JsonReader.Options.of("t", "s", "e", "o", "i", "h", "to", "ti");
    static JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.of("x", "y");

    private static WeakReference getInterpolator(int i) {
        WeakReference weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = (WeakReference) pathInterpolatorCache().get(i);
        }
        return weakReference;
    }

    private static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        Interpolator interpolatorCreate;
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        float fClamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
        pointF2.y = fClamp;
        int iHashFor = Utils.hashFor(pointF.x, pointF.y, pointF2.x, fClamp);
        WeakReference interpolator = L.getDisablePathInterpolatorCache() ? null : getInterpolator(iHashFor);
        Interpolator interpolator2 = interpolator != null ? (Interpolator) interpolator.get() : null;
        if (interpolator != null && interpolator2 != null) {
            return interpolator2;
        }
        try {
            interpolatorCreate = PathInterpolatorCompat.create(pointF.x, pointF.y, pointF2.x, pointF2.y);
        } catch (IllegalArgumentException e) {
            interpolatorCreate = "The Path cannot loop back on itself.".equals(e.getMessage()) ? PathInterpolatorCompat.create(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y) : new LinearInterpolator();
        }
        if (!L.getDisablePathInterpolatorCache()) {
            try {
                putInterpolator(iHashFor, new WeakReference(interpolatorCreate));
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        return interpolatorCreate;
    }

    static Keyframe parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z, boolean z2) {
        return (z && z2) ? parseMultiDimensionalKeyframe(lottieComposition, jsonReader, f, valueParser) : z ? parseKeyframe(lottieComposition, jsonReader, f, valueParser) : parseStaticValue(jsonReader, f, valueParser);
    }

    private static Keyframe parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser valueParser) {
        Interpolator interpolatorInterpolatorFor;
        Object obj;
        jsonReader.beginObject();
        PointF pointFJsonToPoint = null;
        Object obj2 = null;
        Object obj3 = null;
        PointF pointFJsonToPoint2 = null;
        PointF pointFJsonToPoint3 = null;
        float fNextDouble = 0.0f;
        boolean z = false;
        PointF pointFJsonToPoint4 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    fNextDouble = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    obj3 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    obj2 = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    pointFJsonToPoint = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 4:
                    pointFJsonToPoint4 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 5:
                    z = jsonReader.nextInt() == 1;
                    break;
                case 6:
                    pointFJsonToPoint2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointFJsonToPoint3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolatorInterpolatorFor = LINEAR_INTERPOLATOR;
            obj = obj3;
        } else {
            interpolatorInterpolatorFor = (pointFJsonToPoint == null || pointFJsonToPoint4 == null) ? LINEAR_INTERPOLATOR : interpolatorFor(pointFJsonToPoint, pointFJsonToPoint4);
            obj = obj2;
        }
        Keyframe keyframe = new Keyframe(lottieComposition, obj3, obj, interpolatorInterpolatorFor, fNextDouble, null);
        keyframe.pathCp1 = pointFJsonToPoint2;
        keyframe.pathCp2 = pointFJsonToPoint3;
        return keyframe;
    }

    /* JADX WARN: Removed duplicated region for block: B:93:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.airbnb.lottie.value.Keyframe parseMultiDimensionalKeyframe(com.airbnb.lottie.LottieComposition r21, com.airbnb.lottie.parser.moshi.JsonReader r22, float r23, com.airbnb.lottie.parser.ValueParser r24) {
        /*
            Method dump skipped, instruction units count: 520
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.KeyframeParser.parseMultiDimensionalKeyframe(com.airbnb.lottie.LottieComposition, com.airbnb.lottie.parser.moshi.JsonReader, float, com.airbnb.lottie.parser.ValueParser):com.airbnb.lottie.value.Keyframe");
    }

    private static Keyframe parseStaticValue(JsonReader jsonReader, float f, ValueParser valueParser) {
        return new Keyframe(valueParser.parse(jsonReader, f));
    }

    private static SparseArrayCompat pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat();
        }
        return pathInterpolatorCache;
    }

    private static void putInterpolator(int i, WeakReference weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i, weakReference);
        }
    }
}
