package com.android.systemui.monet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ColorScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TonalPalette {
    public static final Companion Companion = new Companion(null);
    private static final List SHADE_KEYS = CollectionsKt.listOf((Object[]) new Integer[]{0, 10, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000});
    private final List allShades;
    private final Map allShadesMapped;
    private final com.google.android.material.color.utilities.TonalPalette materialTonalPalette;

    /* JADX INFO: compiled from: ColorScheme.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TonalPalette(com.google.android.material.color.utilities.TonalPalette tonalPalette) {
        tonalPalette.getClass();
        this.materialTonalPalette = tonalPalette;
        List list = SHADE_KEYS;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(getAtTone(((Number) it.next()).intValue())));
        }
        this.allShades = arrayList;
        this.allShadesMapped = MapsKt.toMap(CollectionsKt.zip(SHADE_KEYS, arrayList));
    }

    public final List getAllShades() {
        return this.allShades;
    }

    public final int getAtTone(float f) {
        return this.materialTonalPalette.tone((int) ((1000.0f - f) / 10.0f));
    }

    public final int getS100() {
        return ((Number) this.allShades.get(3)).intValue();
    }

    public final int getS200() {
        return ((Number) this.allShades.get(4)).intValue();
    }

    public final int getS400() {
        return ((Number) this.allShades.get(6)).intValue();
    }

    public final int getS50() {
        return ((Number) this.allShades.get(2)).intValue();
    }

    public final int getS700() {
        return ((Number) this.allShades.get(9)).intValue();
    }

    public final int getS800() {
        return ((Number) this.allShades.get(10)).intValue();
    }

    public final int getS900() {
        return ((Number) this.allShades.get(11)).intValue();
    }
}
