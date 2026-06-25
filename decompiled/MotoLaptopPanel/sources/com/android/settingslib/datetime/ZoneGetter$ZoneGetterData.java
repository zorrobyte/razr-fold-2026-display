package com.android.settingslib.datetime;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ZoneGetter$ZoneGetterData {
    private static List extractTimeZoneIds(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((CountryTimeZones.TimeZoneMapping) it.next()).getTimeZoneId());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List lookupTimeZoneIdsByCountry(String str) {
        CountryTimeZones countryTimeZonesLookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
        if (countryTimeZonesLookupCountryTimeZones == null) {
            return null;
        }
        return extractTimeZoneIds(countryTimeZonesLookupCountryTimeZones.getTimeZoneMappings());
    }
}
