package com.google.android.material.color.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public final class TemperatureCache {
    private final Hct input;
    private List precomputedHctsByHue;
    private List precomputedHctsByTemp;
    private Map precomputedTempsByHct;

    public static /* synthetic */ Double $r8$lambda$VKoHiU00a9ybz_sbHTTerV8DYaI(TemperatureCache temperatureCache, Hct hct) {
        return (Double) temperatureCache.getTempsByHct().get(hct);
    }

    public TemperatureCache(Hct hct) {
        this.input = hct;
    }

    private Hct getColdest() {
        return (Hct) getHctsByTemp().get(0);
    }

    private List getHctsByHue() {
        List list = this.precomputedHctsByHue;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (double d = 0.0d; d <= 360.0d; d += 1.0d) {
            arrayList.add(Hct.from(d, this.input.getChroma(), this.input.getTone()));
        }
        List listUnmodifiableList = Collections.unmodifiableList(arrayList);
        this.precomputedHctsByHue = listUnmodifiableList;
        return listUnmodifiableList;
    }

    private List getHctsByTemp() {
        List list = this.precomputedHctsByTemp;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        Collections.sort(arrayList, Comparator.comparing(new Function() { // from class: com.google.android.material.color.utilities.TemperatureCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TemperatureCache.$r8$lambda$VKoHiU00a9ybz_sbHTTerV8DYaI(this.f$0, (Hct) obj);
            }
        }, new Comparator() { // from class: com.google.android.material.color.utilities.TemperatureCache$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Double) obj).compareTo((Double) obj2);
            }
        }));
        this.precomputedHctsByTemp = arrayList;
        return arrayList;
    }

    private Map getTempsByHct() {
        Map map = this.precomputedTempsByHct;
        if (map != null) {
            return map;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        HashMap map2 = new HashMap();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Hct hct = (Hct) obj;
            map2.put(hct, Double.valueOf(rawTemperature(hct)));
        }
        this.precomputedTempsByHct = map2;
        return map2;
    }

    private Hct getWarmest() {
        return (Hct) getHctsByTemp().get(getHctsByTemp().size() - 1);
    }

    public static double rawTemperature(Hct hct) {
        double[] dArrLabFromArgb = ColorUtils.labFromArgb(hct.toInt());
        return ((Math.pow(Math.hypot(dArrLabFromArgb[1], dArrLabFromArgb[2]), 1.07d) * 0.02d) * Math.cos(Math.toRadians(MathUtils.sanitizeDegreesDouble(MathUtils.sanitizeDegreesDouble(Math.toDegrees(Math.atan2(dArrLabFromArgb[2], dArrLabFromArgb[1]))) - 50.0d)))) - 0.5d;
    }

    public List getAnalogousColors(int i, int i2) {
        int iRound = (int) Math.round(this.input.getHue());
        Hct hct = (Hct) getHctsByHue().get(iRound);
        double relativeTemperature = getRelativeTemperature(hct);
        ArrayList arrayList = new ArrayList();
        arrayList.add(hct);
        double dAbs = 0.0d;
        double dAbs2 = 0.0d;
        int i3 = 0;
        while (i3 < 360) {
            double relativeTemperature2 = getRelativeTemperature((Hct) getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i3)));
            dAbs2 += Math.abs(relativeTemperature2 - relativeTemperature);
            i3++;
            relativeTemperature = relativeTemperature2;
        }
        double d = dAbs2 / ((double) i2);
        double relativeTemperature3 = getRelativeTemperature(hct);
        int i4 = 1;
        while (true) {
            if (arrayList.size() >= i2) {
                break;
            }
            Hct hct2 = (Hct) getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i4));
            double relativeTemperature4 = getRelativeTemperature(hct2);
            dAbs += Math.abs(relativeTemperature4 - relativeTemperature3);
            boolean z = dAbs >= ((double) arrayList.size()) * d;
            int i5 = 1;
            while (z && arrayList.size() < i2) {
                arrayList.add(hct2);
                int i6 = i4;
                z = dAbs >= ((double) (arrayList.size() + i5)) * d;
                i5++;
                i4 = i6;
            }
            i4++;
            if (i4 > 360) {
                while (arrayList.size() < i2) {
                    arrayList.add(hct2);
                }
            } else {
                relativeTemperature3 = relativeTemperature4;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.input);
        int iFloor = (int) Math.floor((((double) i) - 1.0d) / 2.0d);
        for (int i7 = 1; i7 < iFloor + 1; i7++) {
            int size = 0 - i7;
            while (size < 0) {
                size += arrayList.size();
            }
            if (size >= arrayList.size()) {
                size %= arrayList.size();
            }
            arrayList2.add(0, (Hct) arrayList.get(size));
        }
        int i8 = i - iFloor;
        for (int i9 = 1; i9 < i8; i9++) {
            int size2 = i9;
            while (size2 < 0) {
                size2 += arrayList.size();
            }
            if (size2 >= arrayList.size()) {
                size2 %= arrayList.size();
            }
            arrayList2.add((Hct) arrayList.get(size2));
        }
        return arrayList2;
    }

    public double getRelativeTemperature(Hct hct) {
        double dDoubleValue = ((Double) getTempsByHct().get(getWarmest())).doubleValue() - ((Double) getTempsByHct().get(getColdest())).doubleValue();
        double dDoubleValue2 = ((Double) getTempsByHct().get(hct)).doubleValue() - ((Double) getTempsByHct().get(getColdest())).doubleValue();
        if (dDoubleValue == 0.0d) {
            return 0.5d;
        }
        return dDoubleValue2 / dDoubleValue;
    }
}
