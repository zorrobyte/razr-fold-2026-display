package com.motorola.plugin.core.misc;

import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import com.motorola.plugin.core.PluginConfigKt;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: TraceHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TraceHelper {
    private static final boolean ENABLED;
    public static final TraceHelper INSTANCE = new TraceHelper();
    private static final long INTERVAL_THRESHOLD;
    private static final boolean SYSTEM_TRACE;
    private static final ArrayMap sUpTimes;

    /* JADX INFO: compiled from: TraceHelper.kt */
    final class SectionInfo {
        private long partitionTimeNanos;
        private long sectionInitTimeNanos;

        public SectionInfo(long j, long j2) {
            this.sectionInitTimeNanos = j;
            this.partitionTimeNanos = j2;
        }

        public /* synthetic */ SectionInfo(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, (i & 2) != 0 ? -1L : j2);
        }

        public final long getPartitionTimeNanos() {
            return this.partitionTimeNanos;
        }

        public final long getSectionInitTimeNanos() {
            return this.sectionInitTimeNanos;
        }

        public final void setPartitionTimeNanos(long j) {
            this.partitionTimeNanos = j;
        }

        public final void setSectionInitTimeNanos(long j) {
            this.sectionInitTimeNanos = j;
        }
    }

    /* JADX INFO: compiled from: TraceHelper.kt */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            iArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            iArr[TimeUnit.MICROSECONDS.ordinal()] = 2;
            iArr[TimeUnit.MILLISECONDS.ordinal()] = 3;
            iArr[TimeUnit.SECONDS.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        boolean z = Log.isLoggable("PLUGIN_TRACE", 2) || PluginConfigKt.getDEBUG();
        ENABLED = z;
        SYSTEM_TRACE = z;
        sUpTimes = z ? new ArrayMap() : null;
        INTERVAL_THRESHOLD = TimeUnit.MILLISECONDS.toNanos(10L);
    }

    private TraceHelper() {
    }

    private final String abbreviate(TimeUnit timeUnit) {
        int i = WhenMappings.$EnumSwitchMapping$0[timeUnit.ordinal()];
        if (i == 1) {
            return "ns";
        }
        if (i == 2) {
            return "μs";
        }
        if (i == 3) {
            return "ms";
        }
        if (i == 4) {
            return "s";
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: beginSection$lambda-1$lambda-0, reason: not valid java name */
    public static final SectionInfo m2221beginSection$lambda1$lambda0(String str, String str2) {
        str.getClass();
        return new SectionInfo(INSTANCE.isSectionLoggable(str) ? 0L : -1L, 0L, 2, null);
    }

    private final TimeUnit chooseUnit(long j) {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        TimeUnit timeUnit2 = TimeUnit.NANOSECONDS;
        if (timeUnit.convert(j, timeUnit2) > 0) {
            return timeUnit;
        }
        TimeUnit timeUnit3 = TimeUnit.MILLISECONDS;
        if (timeUnit3.convert(j, timeUnit2) > 0) {
            return timeUnit3;
        }
        TimeUnit timeUnit4 = TimeUnit.MICROSECONDS;
        return timeUnit4.convert(j, timeUnit2) > 0 ? timeUnit4 : timeUnit2;
    }

    private final boolean isSectionLoggable(String str) {
        return ENABLED || Log.isLoggable(str, 2);
    }

    private final void logTime(String str, long j, CharSequence charSequence) {
        TimeUnit timeUnitChooseUnit = chooseUnit(j);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format(Locale.getDefault(), "%.4g %s", Arrays.copyOf(new Object[]{Double.valueOf(j / TimeUnit.NANOSECONDS.convert(1L, timeUnitChooseUnit)), abbreviate(timeUnitChooseUnit)}, 2));
        if (j >= INTERVAL_THRESHOLD) {
            Log.w(str, ((Object) charSequence) + " : " + str2);
            return;
        }
        Log.d(str, ((Object) charSequence) + " : " + str2);
    }

    public final void beginSection(final String str) {
        str.getClass();
        if (ENABLED) {
            ArrayMap arrayMap = sUpTimes;
            arrayMap.getClass();
            synchronized (arrayMap) {
                try {
                    SectionInfo sectionInfo = (SectionInfo) arrayMap.computeIfAbsent(str, new Function() { // from class: com.motorola.plugin.core.misc.TraceHelper$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return TraceHelper.m2221beginSection$lambda1$lambda0(str, (String) obj);
                        }
                    });
                    if (sectionInfo.getSectionInitTimeNanos() >= 0) {
                        if (SYSTEM_TRACE) {
                            Trace.beginSection(str);
                        }
                        sectionInfo.setPartitionTimeNanos(System.nanoTime());
                        sectionInfo.setSectionInitTimeNanos(sectionInfo.getPartitionTimeNanos());
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void endSection(String str) {
        str.getClass();
        if (ENABLED) {
            endSection(str, "End");
        }
    }

    public final void endSection(String str, CharSequence charSequence) {
        str.getClass();
        charSequence.getClass();
        if (ENABLED) {
            ArrayMap arrayMap = sUpTimes;
            arrayMap.getClass();
            synchronized (arrayMap) {
                try {
                    SectionInfo sectionInfo = (SectionInfo) arrayMap.get(str);
                    if (sectionInfo != null && sectionInfo.getSectionInitTimeNanos() >= 0) {
                        if (SYSTEM_TRACE) {
                            Trace.endSection();
                        }
                        long jNanoTime = System.nanoTime();
                        long sectionInitTimeNanos = jNanoTime - sectionInfo.getSectionInitTimeNanos();
                        sectionInfo.setPartitionTimeNanos(jNanoTime);
                        sectionInfo.setSectionInitTimeNanos(sectionInfo.getPartitionTimeNanos());
                        INSTANCE.logTime(str, sectionInitTimeNanos, charSequence);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void partitionSection(String str, CharSequence charSequence) {
        str.getClass();
        charSequence.getClass();
        if (ENABLED) {
            ArrayMap arrayMap = sUpTimes;
            arrayMap.getClass();
            synchronized (arrayMap) {
                try {
                    SectionInfo sectionInfo = (SectionInfo) arrayMap.get(str);
                    if (sectionInfo != null && sectionInfo.getSectionInitTimeNanos() >= 0) {
                        if (SYSTEM_TRACE) {
                            Trace.endSection();
                            Trace.beginSection(str);
                        }
                        long jNanoTime = System.nanoTime();
                        long partitionTimeNanos = jNanoTime - sectionInfo.getPartitionTimeNanos();
                        sectionInfo.setPartitionTimeNanos(jNanoTime);
                        INSTANCE.logTime(str, partitionTimeNanos, charSequence);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
