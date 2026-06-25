package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class TonalPalette {
    Map cache = new HashMap();
    double chroma;
    double hue;
    Hct keyColor;

    final class KeyColor {
        private final Map chromaCache = new HashMap();
        private final double hue;
        private final double requestedChroma;

        public KeyColor(double d, double d2) {
            this.hue = d;
            this.requestedChroma = d2;
        }

        private double maxChroma(int i) {
            if (this.chromaCache.get(Integer.valueOf(i)) == null) {
                this.chromaCache.put(Integer.valueOf(i), Double.valueOf(Hct.from(this.hue, 200.0d, i).getChroma()));
            }
            return ((Double) this.chromaCache.get(Integer.valueOf(i))).doubleValue();
        }

        public Hct create() {
            int i = 100;
            int i2 = 0;
            while (i2 < i) {
                int i3 = (i2 + i) / 2;
                int i4 = i3 + 1;
                boolean z = maxChroma(i3) < maxChroma(i4);
                if (maxChroma(i3) >= this.requestedChroma - 0.01d) {
                    if (Math.abs(i2 - 50) < Math.abs(i - 50)) {
                        i = i3;
                    } else {
                        if (i2 == i3) {
                            return Hct.from(this.hue, this.requestedChroma, i2);
                        }
                        i2 = i3;
                    }
                } else if (z) {
                    i2 = i4;
                } else {
                    i = i3;
                }
            }
            return Hct.from(this.hue, this.requestedChroma, i2);
        }
    }

    private TonalPalette(double d, double d2, Hct hct) {
        this.hue = d;
        this.chroma = d2;
        this.keyColor = hct;
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.getHue(), hct.getChroma(), hct);
    }

    public static TonalPalette fromHueAndChroma(double d, double d2) {
        return new TonalPalette(d, d2, new KeyColor(d, d2).create());
    }

    public int tone(int i) {
        Integer numValueOf = (Integer) this.cache.get(Integer.valueOf(i));
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(Hct.from(this.hue, this.chroma, i).toInt());
            this.cache.put(Integer.valueOf(i), numValueOf);
        }
        return numValueOf.intValue();
    }
}
