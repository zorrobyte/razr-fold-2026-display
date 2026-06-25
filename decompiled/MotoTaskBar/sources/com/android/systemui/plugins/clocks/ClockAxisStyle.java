package com.android.systemui.plugins.clocks;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ClockSettings.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockAxisStyle {
    private final Map settings;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final String KEY_AXIS_KEY = "key";
    private static final String KEY_AXIS_VALUE = "value";

    /* JADX INFO: compiled from: ClockSettings.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final ClockAxisStyle fromJson(JSONArray jSONArray) throws JSONException {
            jSONArray.getClass();
            ClockAxisStyle clockAxisStyle = new ClockAxisStyle(null, 1, 0 == true ? 1 : 0);
            int length = jSONArray.length() - 1;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject != null) {
                        String string = jSONObject.getString(ClockAxisStyle.KEY_AXIS_KEY);
                        string.getClass();
                        clockAxisStyle.put(string, (float) jSONObject.getDouble(ClockAxisStyle.KEY_AXIS_VALUE));
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            return clockAxisStyle;
        }

        public final JSONArray toJson(ClockAxisStyle clockAxisStyle) throws JSONException {
            clockAxisStyle.getClass();
            JSONArray jSONArray = new JSONArray();
            for (Map.Entry entry : clockAxisStyle.settings.entrySet()) {
                String str = (String) entry.getKey();
                float fFloatValue = ((Number) entry.getValue()).floatValue();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(ClockAxisStyle.KEY_AXIS_KEY, str);
                jSONObject.put(ClockAxisStyle.KEY_AXIS_VALUE, Float.valueOf(fFloatValue));
                jSONArray.put(jSONObject);
            }
            return jSONArray;
        }
    }

    public ClockAxisStyle(ClockAxisStyle clockAxisStyle) {
        clockAxisStyle.getClass();
        this.settings = MapsKt.toMutableMap(clockAxisStyle.settings);
    }

    public ClockAxisStyle(String str, float f) {
        str.getClass();
        this.settings = MapsKt.mutableMapOf(TuplesKt.to(str, Float.valueOf(f)));
    }

    public ClockAxisStyle(List list) {
        list.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ClockFontAxis clockFontAxis = (ClockFontAxis) it.next();
            Pair pair = TuplesKt.to(clockFontAxis.getKey(), Float.valueOf(clockFontAxis.getCurrentValue()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.settings = MapsKt.toMutableMap(linkedHashMap);
    }

    public ClockAxisStyle(Map map) {
        map.getClass();
        this.settings = MapsKt.toMutableMap(map);
    }

    public ClockAxisStyle(Function1 function1) {
        function1.getClass();
        this.settings = new LinkedHashMap();
        function1.invoke(this);
    }

    public /* synthetic */ ClockAxisStyle(Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1() { // from class: com.android.systemui.plugins.clocks.ClockAxisStyle$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockAxisStyle._init_$lambda$0((ClockAxisStyle) obj);
            }
        } : function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit _init_$lambda$0(ClockAxisStyle clockAxisStyle) {
        clockAxisStyle.getClass();
        return Unit.INSTANCE;
    }

    public final ClockAxisStyle copy(Function1 function1) {
        function1.getClass();
        ClockAxisStyle clockAxisStyle = new ClockAxisStyle(this);
        function1.invoke(clockAxisStyle);
        return clockAxisStyle;
    }

    public final ClockAxisStyle copyWith(ClockAxisStyle clockAxisStyle) {
        clockAxisStyle.getClass();
        ClockAxisStyle clockAxisStyle2 = new ClockAxisStyle(this);
        for (Map.Entry entry : clockAxisStyle.settings.entrySet()) {
            clockAxisStyle2.set((String) entry.getKey(), ((Number) entry.getValue()).floatValue());
        }
        return clockAxisStyle2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ClockAxisStyle) {
            return Intrinsics.areEqual(this.settings, ((ClockAxisStyle) obj).settings);
        }
        return false;
    }

    public final Float get(String str) {
        str.getClass();
        return (Float) this.settings.get(str);
    }

    public final Iterable getItems() {
        return this.settings.entrySet();
    }

    public final boolean isEmpty() {
        return this.settings.isEmpty();
    }

    public final void put(String str, float f) {
        str.getClass();
        this.settings.put(str, Float.valueOf(f));
    }

    public final void set(String str, float f) {
        str.getClass();
        put(str, f);
    }

    public final String toFVar() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : this.settings.entrySet()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("'" + entry.getKey() + "' " + ((int) ((Number) entry.getValue()).floatValue()));
        }
        String string = sb.toString();
        string.getClass();
        return string;
    }
}
