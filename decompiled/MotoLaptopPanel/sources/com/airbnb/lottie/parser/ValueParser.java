package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* JADX INFO: loaded from: classes.dex */
interface ValueParser {
    Object parse(JsonReader jsonReader, float f);
}
