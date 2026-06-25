package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* JADX INFO: loaded from: classes.dex */
public class PointFParser implements ValueParser {
    public static final PointFParser INSTANCE = new PointFParser();

    private PointFParser() {
    }

    @Override // com.airbnb.lottie.parser.ValueParser
    public PointF parse(JsonReader jsonReader, float f) {
        JsonReader.Token tokenPeek = jsonReader.peek();
        if (tokenPeek != JsonReader.Token.BEGIN_ARRAY && tokenPeek != JsonReader.Token.BEGIN_OBJECT) {
            if (tokenPeek == JsonReader.Token.NUMBER) {
                PointF pointF = new PointF(((float) jsonReader.nextDouble()) * f, ((float) jsonReader.nextDouble()) * f);
                while (jsonReader.hasNext()) {
                    jsonReader.skipValue();
                }
                return pointF;
            }
            throw new IllegalArgumentException("Cannot convert json to point. Next token is " + tokenPeek);
        }
        return JsonUtils.jsonToPoint(jsonReader, f);
    }
}
