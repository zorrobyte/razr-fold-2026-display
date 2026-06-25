package com.motorola.plugin.core.discovery;

import android.content.ComponentName;
import android.content.res.XmlResourceParser;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: PluginInfoParser.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginInfoParserKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final com.motorola.plugin.core.discovery.PluginInfo parsePluginInfo(android.content.ComponentName r30, final android.content.res.XmlResourceParser r31, java.lang.ClassLoader r32, java.lang.String r33) {
        /*
            Method dump skipped, instruction units count: 380
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.discovery.PluginInfoParserKt.parsePluginInfo(android.content.ComponentName, android.content.res.XmlResourceParser, java.lang.ClassLoader, java.lang.String):com.motorola.plugin.core.discovery.PluginInfo");
    }

    public static final List parsePluginInfoFromXml(final ComponentName componentName, XmlResourceParser xmlResourceParser, ClassLoader classLoader, String str) {
        Object objM2707constructorimpl;
        componentName.getClass();
        xmlResourceParser.getClass();
        classLoader.getClass();
        str.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.discovery.PluginInfoParserKt.parsePluginInfoFromXml.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "start parse plugin info for [" + componentName.flattenToShortString() + ']';
            }
        }, 60, null);
        try {
            Result.Companion companion = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(parsePluginInfoList(componentName, xmlResourceParser, classLoader, str));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.discovery.PluginInfoParserKt$parsePluginInfoFromXml$3$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "XML parsing failed for plugin provider [" + componentName.flattenToShortString() + ']';
                }
            }, 52, null);
        }
        if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
            objM2707constructorimpl = null;
        }
        return (List) objM2707constructorimpl;
    }

    private static final List parsePluginInfoList(final ComponentName componentName, XmlResourceParser xmlResourceParser, ClassLoader classLoader, String str) throws XmlPullParserException, IOException {
        ExtensionsKt.beginDocumentOrThrow(xmlResourceParser, "plugins");
        ArrayList arrayList = new ArrayList();
        while (true) {
            ExtensionsKt.nextElement(xmlResourceParser);
            if (xmlResourceParser.getEventType() == 1) {
                break;
            }
            if (Intrinsics.areEqual(xmlResourceParser.getName(), "plugin")) {
                arrayList.add(parsePluginInfo(componentName, xmlResourceParser, classLoader, str));
            }
        }
        xmlResourceParser.close();
        if (arrayList.isEmpty()) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_PLUGIN_DISCOVERY, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.discovery.PluginInfoParserKt.parsePluginInfoList.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "XML parsing success but no any plugin tags defined here for [" + componentName.flattenToShortString() + ']';
                }
            }, 60, null);
        }
        return arrayList;
    }
}
