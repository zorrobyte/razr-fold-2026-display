package com.android.systemui.monet;

import android.app.WallpaperColors;
import com.android.systemui.monet.ColorScheme;
import com.google.android.material.color.utilities.DynamicScheme;
import com.google.android.material.color.utilities.Hct;
import com.google.android.material.color.utilities.SchemeContent;
import com.google.android.material.color.utilities.SchemeExpressive;
import com.google.android.material.color.utilities.SchemeFruitSalad;
import com.google.android.material.color.utilities.SchemeMonochrome;
import com.google.android.material.color.utilities.SchemeNeutral;
import com.google.android.material.color.utilities.SchemeRainbow;
import com.google.android.material.color.utilities.SchemeTonalSpot;
import com.google.android.material.color.utilities.SchemeVibrant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ColorScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ColorScheme {
    public static final Companion Companion = new Companion(null);
    private final TonalPalette accent1;
    private final TonalPalette accent2;
    private final TonalPalette accent3;
    private final double contrastLevel;
    private final boolean isDark;
    private DynamicScheme materialScheme;
    private final TonalPalette neutral1;
    private final TonalPalette neutral2;
    private final Hct proposedSeedHct;
    private final int seed;
    private final Hct seedHct;
    private final Style style;

    /* JADX INFO: compiled from: ColorScheme.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final double hueDiff(double d, double d2) {
            double d3 = 180.0f;
            return d3 - Math.abs(Math.abs(d - d2) - d3);
        }

        private final List huePopulations(Map map, Map map2, boolean z) {
            ArrayList arrayList = new ArrayList(360);
            for (int i = 0; i < 360; i++) {
                arrayList.add(Double.valueOf(0.0d));
            }
            List mutableList = CollectionsKt.toMutableList((Collection) arrayList);
            for (Map.Entry entry : map2.entrySet()) {
                Object obj = map2.get(entry.getKey());
                obj.getClass();
                double dDoubleValue = ((Number) obj).doubleValue();
                Object obj2 = map.get(entry.getKey());
                obj2.getClass();
                Hct hct = (Hct) obj2;
                int iRoundToInt = MathKt.roundToInt(hct.getHue()) % 360;
                if (!z || hct.getChroma() > 5.0d) {
                    mutableList.set(iRoundToInt, Double.valueOf(((Number) mutableList.get(iRoundToInt)).doubleValue() + dDoubleValue));
                }
            }
            return mutableList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String humanReadable(String str, List list) {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(ColorScheme.Companion.stringForColor(((Number) it.next()).intValue()));
            }
            return str + "\n" + CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, new Function1() { // from class: com.android.systemui.monet.ColorScheme$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ColorScheme.Companion.humanReadable$lambda$11((String) obj);
                }
            }, 30, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final CharSequence humanReadable$lambda$11(String str) {
            str.getClass();
            return str;
        }

        private final double score(Hct hct, double d) {
            double chroma;
            double d2;
            double d3 = d * 70.0d;
            if (hct.getChroma() < 48.0d) {
                chroma = hct.getChroma() - ((double) 48.0f);
                d2 = 0.1d;
            } else {
                chroma = hct.getChroma() - ((double) 48.0f);
                d2 = 0.3d;
            }
            return (chroma * d2) + d3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String stringForColor(int i) {
            Hct hctFromInt = Hct.fromInt(i);
            String str = "H" + StringsKt.padEnd$default(String.valueOf(MathKt.roundToInt(hctFromInt.getHue())), 4, (char) 0, 2, null);
            String str2 = "C" + StringsKt.padEnd$default(String.valueOf(MathKt.roundToInt(hctFromInt.getChroma())), 4, (char) 0, 2, null);
            String str3 = "T" + StringsKt.padEnd$default(String.valueOf(MathKt.roundToInt(hctFromInt.getTone())), 4, (char) 0, 2, null);
            String hexString = Integer.toHexString(i & 16777215);
            hexString.getClass();
            String upperCase = StringsKt.padStart(hexString, 6, '0').toUpperCase(Locale.ROOT);
            upperCase.getClass();
            return str + str2 + str3 + " = #" + upperCase;
        }

        private final int wrapDegrees(int i) {
            return i < 0 ? (i % 360) + 360 : i >= 360 ? i % 360 : i;
        }

        public final int getSeedColor(WallpaperColors wallpaperColors, boolean z) {
            wallpaperColors.getClass();
            return ((Number) CollectionsKt.first(getSeedColors(wallpaperColors, z))).intValue();
        }

        /* JADX WARN: Code restructure failed: missing block: B:82:0x02ed, code lost:
        
            if (r3 == 15) goto L105;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.util.List getSeedColors(android.app.WallpaperColors r22, boolean r23) {
            /*
                Method dump skipped, instruction units count: 776
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.monet.ColorScheme.Companion.getSeedColors(android.app.WallpaperColors, boolean):java.util.List");
        }
    }

    /* JADX INFO: compiled from: ColorScheme.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Style.values().length];
            try {
                iArr[Style.SPRITZ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Style.TONAL_SPOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Style.VIBRANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Style.EXPRESSIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Style.RAINBOW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Style.FRUIT_SALAD.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Style.CONTENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Style.MONOCHROMATIC.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorScheme(int i, boolean z, Style style) {
        this(i, z, style, 0.5d);
        style.getClass();
    }

    public ColorScheme(int i, boolean z, Style style, double d) {
        DynamicScheme schemeNeutral;
        style.getClass();
        this.seed = i;
        this.isDark = z;
        this.style = style;
        this.contrastLevel = d;
        Hct hctFromInt = Hct.fromInt(i);
        hctFromInt.getClass();
        this.proposedSeedHct = hctFromInt;
        if (i == 0 || (style != Style.CONTENT && hctFromInt.getChroma() < 5.0d)) {
            i = -14979341;
        }
        Hct hctFromInt2 = Hct.fromInt(i);
        hctFromInt2.getClass();
        this.seedHct = hctFromInt2;
        switch (WhenMappings.$EnumSwitchMapping$0[style.ordinal()]) {
            case 1:
                schemeNeutral = new SchemeNeutral(hctFromInt2, z, d);
                break;
            case 2:
                schemeNeutral = new SchemeTonalSpot(hctFromInt2, z, d);
                break;
            case 3:
                schemeNeutral = new SchemeVibrant(hctFromInt2, z, d);
                break;
            case 4:
                schemeNeutral = new SchemeExpressive(hctFromInt2, z, d);
                break;
            case 5:
                schemeNeutral = new SchemeRainbow(hctFromInt2, z, d);
                break;
            case 6:
                schemeNeutral = new SchemeFruitSalad(hctFromInt2, z, d);
                break;
            case 7:
                schemeNeutral = new SchemeContent(hctFromInt2, z, d);
                break;
            case 8:
                schemeNeutral = new SchemeMonochrome(hctFromInt2, z, d);
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.materialScheme = schemeNeutral;
        com.google.android.material.color.utilities.TonalPalette tonalPalette = this.materialScheme.primaryPalette;
        tonalPalette.getClass();
        this.accent1 = new TonalPalette(tonalPalette);
        com.google.android.material.color.utilities.TonalPalette tonalPalette2 = this.materialScheme.secondaryPalette;
        tonalPalette2.getClass();
        this.accent2 = new TonalPalette(tonalPalette2);
        com.google.android.material.color.utilities.TonalPalette tonalPalette3 = this.materialScheme.tertiaryPalette;
        tonalPalette3.getClass();
        this.accent3 = new TonalPalette(tonalPalette3);
        com.google.android.material.color.utilities.TonalPalette tonalPalette4 = this.materialScheme.neutralPalette;
        tonalPalette4.getClass();
        this.neutral1 = new TonalPalette(tonalPalette4);
        com.google.android.material.color.utilities.TonalPalette tonalPalette5 = this.materialScheme.neutralVariantPalette;
        tonalPalette5.getClass();
        this.neutral2 = new TonalPalette(tonalPalette5);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style) {
        this(Companion.getSeedColor(wallpaperColors, style != Style.CONTENT), z, style);
        wallpaperColors.getClass();
        style.getClass();
    }

    public final TonalPalette getAccent1() {
        return this.accent1;
    }

    public final TonalPalette getAccent2() {
        return this.accent2;
    }

    public final TonalPalette getNeutral1() {
        return this.neutral1;
    }

    public final TonalPalette getNeutral2() {
        return this.neutral2;
    }

    public String toString() {
        Companion companion = Companion;
        return "ColorScheme {\n  seed color: " + companion.stringForColor(this.seed) + "\n  style: " + this.style + "\n  palettes: \n  " + companion.humanReadable("PRIMARY", this.accent1.getAllShades()) + "\n  " + companion.humanReadable("SECONDARY", this.accent2.getAllShades()) + "\n  " + companion.humanReadable("TERTIARY", this.accent3.getAllShades()) + "\n  " + companion.humanReadable("NEUTRAL", this.neutral1.getAllShades()) + "\n  " + companion.humanReadable("NEUTRAL VARIANT", this.neutral2.getAllShades()) + "\n}";
    }
}
