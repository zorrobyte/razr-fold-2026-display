package com.motorola.plugin.core.rule;

import android.content.Context;
import android.content.res.XmlResourceParser;
import com.motorola.plugin.core.components.DisplayContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: IRule.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface IRule {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAX_VALUE_LENGTH = 1000;
    public static final int PRIORITY_1 = 1;
    public static final int PRIORITY_2 = 2;
    public static final int PRIORITY_3 = 3;
    public static final int PRIORITY_4 = 4;
    public static final int PRIORITY_5 = 5;

    /* JADX INFO: compiled from: IRule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int MAX_VALUE_LENGTH = 1000;
        public static final int PRIORITY_1 = 1;
        public static final int PRIORITY_2 = 2;
        public static final int PRIORITY_3 = 3;
        public static final int PRIORITY_4 = 4;
        public static final int PRIORITY_5 = 5;

        private Companion() {
        }

        public static /* synthetic */ void getMAX_VALUE_LENGTH$annotations() {
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final IRule parseRule(String str, String str2) {
            switch (str.hashCode()) {
                case -1809644472:
                    if (str.equals("match_display")) {
                        return DisplayIdRule.Companion.parse(str2);
                    }
                    return null;
                case 6496204:
                    if (str.equals("match_package")) {
                        return PackageNameRule.Companion.parse(str2);
                    }
                    return null;
                case 1164879865:
                    if (str.equals("match_system_prop")) {
                        return SystemPropRule.Companion.parse(str2);
                    }
                    return null;
                case 1769385033:
                    if (str.equals("match_metrics")) {
                        return DisplayMetricsRule.Companion.parse(str2);
                    }
                    return null;
                case 1865293317:
                    if (str.equals("match_user")) {
                        return UserRule.Companion.parse(str2);
                    }
                    return null;
                default:
                    return null;
            }
        }

        private final void parseSingleRule(String str, String str2, Map map, List list) {
            IRule rule = parseRule(str, StringsKt.take(str2, 1000));
            if (rule == null) {
                return;
            }
            list.add(rule);
            if (!map.containsKey(str)) {
                map.put(str, 1);
                return;
            }
            throw new IllegalArgumentException("duplicate attribute " + str + " is provided");
        }

        public final List forTest(List list) {
            list.getClass();
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                $$INSTANCE.parseSingleRule((String) pair.component1(), (String) pair.component2(), linkedHashMap, arrayList);
            }
            if (arrayList.size() > 1) {
                CollectionsKt.sortWith(arrayList, new Comparator() { // from class: com.motorola.plugin.core.rule.IRule$Companion$forTest$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((IRule) obj).getPriority()), Integer.valueOf(((IRule) obj2).getPriority()));
                    }
                });
            }
            return arrayList;
        }

        public final List parse(XmlResourceParser xmlResourceParser) {
            xmlResourceParser.getClass();
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int attributeCount = xmlResourceParser.getAttributeCount();
            if (attributeCount > 0) {
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    String attributeName = xmlResourceParser.getAttributeName(i);
                    attributeName.getClass();
                    if (StringsKt.startsWith$default(attributeName, "match_", false, 2, (Object) null)) {
                        String attributeValue = xmlResourceParser.getAttributeValue(null, attributeName);
                        attributeValue.getClass();
                        parseSingleRule(attributeName, attributeValue, linkedHashMap, arrayList);
                    }
                    if (i2 >= attributeCount) {
                        break;
                    }
                    i = i2;
                }
            }
            if (arrayList.size() > 1) {
                CollectionsKt.sortWith(arrayList, new Comparator() { // from class: com.motorola.plugin.core.rule.IRule$Companion$parse$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((IRule) obj).getPriority()), Integer.valueOf(((IRule) obj2).getPriority()));
                    }
                });
            }
            return arrayList;
        }
    }

    int getPriority();

    boolean match(@DisplayContext Context context);
}
