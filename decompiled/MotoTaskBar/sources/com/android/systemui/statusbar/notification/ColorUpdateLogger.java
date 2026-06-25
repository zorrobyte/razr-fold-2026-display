package com.android.systemui.statusbar.notification;

import android.icu.text.SimpleDateFormat;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.Compile;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ColorUpdateLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ColorUpdateLogger implements Dumpable {
    private static final Companion Companion = new Companion(null);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    private static ColorUpdateLogger instance;
    private final List frames;

    /* JADX INFO: compiled from: ColorUpdateLogger.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ColorUpdateLogger getInstance() {
            return ColorUpdateLogger.instance;
        }
    }

    /* JADX INFO: compiled from: ColorUpdateLogger.kt */
    final class Event {
        private final String extraValue;
        private final String notificationKey;
        private final long time;
        private final String type;

        public Event(String str, String str2, String str3) {
            str.getClass();
            this.type = str;
            this.extraValue = str2;
            this.notificationKey = str3;
            this.time = System.currentTimeMillis();
        }

        public /* synthetic */ Event(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3);
        }

        public final long getTime() {
            return this.time;
        }

        public final String getType() {
            return this.type;
        }
    }

    /* JADX INFO: compiled from: ColorUpdateLogger.kt */
    final class Frame {
        private final List events;
        private final long startTime;
        private final SortedSet triggers;
        private int trimmedEvents;

        public Frame(Event event) {
            event.getClass();
            this.startTime = event.getTime();
            this.events = CollectionsKt.mutableListOf(event);
            TreeSet treeSet = new TreeSet();
            treeSet.add(event.getType());
            this.triggers = treeSet;
        }

        public final List getEvents() {
            return this.events;
        }

        public final void trim() {
            if (this.events.size() > 250) {
                this.events.remove(0);
                this.trimmedEvents++;
            }
        }

        public final boolean tryAddTrigger(Event event) {
            event.getClass();
            if (event.getTime() < this.startTime || event.getTime() - this.startTime > TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS || this.triggers.contains(event.getType())) {
                return false;
            }
            this.triggers.add(event.getType());
            this.events.add(event);
            trim();
            return true;
        }
    }

    public ColorUpdateLogger(DumpManager dumpManager) {
        dumpManager.getClass();
        this.frames = new ArrayList();
        dumpManager.registerDumpable(this);
        if (Compile.IS_DEBUG) {
            instance = this;
        }
    }

    public static final ColorUpdateLogger getInstance() {
        return Companion.getInstance();
    }

    public static /* synthetic */ void logEvent$default(ColorUpdateLogger colorUpdateLogger, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        colorUpdateLogger.logEvent(str, str2);
    }

    public static /* synthetic */ void logTriggerEvent$default(ColorUpdateLogger colorUpdateLogger, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        colorUpdateLogger.logTriggerEvent(str, str2);
    }

    public final boolean isEnabled() {
        return Compile.IS_DEBUG;
    }

    public final void logEvent(String str, String str2) {
        Frame frame;
        str.getClass();
        if (Compile.IS_DEBUG && (frame = (Frame) CollectionsKt.lastOrNull(this.frames)) != null) {
            frame.getEvents().add(new Event(str, str2, null, 4, null));
            frame.trim();
        }
    }

    public final void logNotificationEvent(String str, String str2, String str3) {
        Frame frame;
        str.getClass();
        str2.getClass();
        if (Compile.IS_DEBUG && (frame = (Frame) CollectionsKt.lastOrNull(this.frames)) != null) {
            frame.getEvents().add(new Event(str, str3, str2));
            frame.trim();
        }
    }

    public final void logTriggerEvent(String str) {
        str.getClass();
        logTriggerEvent$default(this, str, null, 2, null);
    }

    public final void logTriggerEvent(String str, String str2) {
        str.getClass();
        if (Compile.IS_DEBUG) {
            Event event = new Event(str, str2, null, 4, null);
            Frame frame = (Frame) CollectionsKt.lastOrNull(this.frames);
            if (frame == null || !frame.tryAddTrigger(event)) {
                this.frames.add(new Frame(event));
                if (this.frames.size() > 5) {
                    this.frames.remove(0);
                }
            }
        }
    }
}
