package com.android.systemui.media.controls.util;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: MediaUiEventLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaUiEvent implements UiEventLogger.UiEventEnum {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ MediaUiEvent[] $VALUES;
    private final int metricId;
    public static final MediaUiEvent LOCAL_MEDIA_ADDED = new MediaUiEvent("LOCAL_MEDIA_ADDED", 0, 1029);
    public static final MediaUiEvent CAST_MEDIA_ADDED = new MediaUiEvent("CAST_MEDIA_ADDED", 1, 1030);
    public static final MediaUiEvent REMOTE_MEDIA_ADDED = new MediaUiEvent("REMOTE_MEDIA_ADDED", 2, 1031);
    public static final MediaUiEvent TRANSFER_TO_LOCAL = new MediaUiEvent("TRANSFER_TO_LOCAL", 3, 1032);
    public static final MediaUiEvent TRANSFER_TO_CAST = new MediaUiEvent("TRANSFER_TO_CAST", 4, 1033);
    public static final MediaUiEvent TRANSFER_TO_REMOTE = new MediaUiEvent("TRANSFER_TO_REMOTE", 5, 1034);
    public static final MediaUiEvent RESUME_MEDIA_ADDED = new MediaUiEvent("RESUME_MEDIA_ADDED", 6, 1013);
    public static final MediaUiEvent ACTIVE_TO_RESUME = new MediaUiEvent("ACTIVE_TO_RESUME", 7, 1014);
    public static final MediaUiEvent MEDIA_TIMEOUT = new MediaUiEvent("MEDIA_TIMEOUT", 8, 1015);
    public static final MediaUiEvent MEDIA_REMOVED = new MediaUiEvent("MEDIA_REMOVED", 9, 1016);
    public static final MediaUiEvent CAROUSEL_PAGE = new MediaUiEvent("CAROUSEL_PAGE", 10, 1017);
    public static final MediaUiEvent DISMISS_SWIPE = new MediaUiEvent("DISMISS_SWIPE", 11, 1018);
    public static final MediaUiEvent OPEN_LONG_PRESS = new MediaUiEvent("OPEN_LONG_PRESS", 12, 1019);
    public static final MediaUiEvent DISMISS_LONG_PRESS = new MediaUiEvent("DISMISS_LONG_PRESS", 13, 1020);
    public static final MediaUiEvent OPEN_SETTINGS_LONG_PRESS = new MediaUiEvent("OPEN_SETTINGS_LONG_PRESS", 14, 1021);
    public static final MediaUiEvent OPEN_SETTINGS_CAROUSEL = new MediaUiEvent("OPEN_SETTINGS_CAROUSEL", 15, 1022);
    public static final MediaUiEvent TAP_ACTION_PLAY_PAUSE = new MediaUiEvent("TAP_ACTION_PLAY_PAUSE", 16, 1023);
    public static final MediaUiEvent TAP_ACTION_PREV = new MediaUiEvent("TAP_ACTION_PREV", 17, 1024);
    public static final MediaUiEvent TAP_ACTION_NEXT = new MediaUiEvent("TAP_ACTION_NEXT", 18, 1025);
    public static final MediaUiEvent TAP_ACTION_OTHER = new MediaUiEvent("TAP_ACTION_OTHER", 19, 1026);
    public static final MediaUiEvent ACTION_SEEK = new MediaUiEvent("ACTION_SEEK", 20, 1027);
    public static final MediaUiEvent OPEN_OUTPUT_SWITCHER = new MediaUiEvent("OPEN_OUTPUT_SWITCHER", 21, 1028);
    public static final MediaUiEvent MEDIA_TAP_CONTENT_VIEW = new MediaUiEvent("MEDIA_TAP_CONTENT_VIEW", 22, 1036);
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_QQS = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_QQS", 23, 1037);
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_QS = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_QS", 24, 1038);
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_LOCKSCREEN = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_LOCKSCREEN", 25, 1039);
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_DREAM = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_DREAM", 26, 1040);
    public static final MediaUiEvent MEDIA_CAROUSEL_LOCATION_COMMUNAL = new MediaUiEvent("MEDIA_CAROUSEL_LOCATION_COMMUNAL", 27, 1520);
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ADDED = new MediaUiEvent("MEDIA_RECOMMENDATION_ADDED", 28, 1041);
    public static final MediaUiEvent MEDIA_RECOMMENDATION_REMOVED = new MediaUiEvent("MEDIA_RECOMMENDATION_REMOVED", 29, 1042);
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ACTIVATED = new MediaUiEvent("MEDIA_RECOMMENDATION_ACTIVATED", 30, 1043);
    public static final MediaUiEvent MEDIA_RECOMMENDATION_ITEM_TAP = new MediaUiEvent("MEDIA_RECOMMENDATION_ITEM_TAP", 31, 1044);
    public static final MediaUiEvent MEDIA_RECOMMENDATION_CARD_TAP = new MediaUiEvent("MEDIA_RECOMMENDATION_CARD_TAP", 32, 1045);
    public static final MediaUiEvent MEDIA_OPEN_BROADCAST_DIALOG = new MediaUiEvent("MEDIA_OPEN_BROADCAST_DIALOG", 33, 1079);
    public static final MediaUiEvent MEDIA_CAROUSEL_SINGLE_PLAYER = new MediaUiEvent("MEDIA_CAROUSEL_SINGLE_PLAYER", 34, 1244);
    public static final MediaUiEvent MEDIA_CAROUSEL_MULTIPLE_PLAYERS = new MediaUiEvent("MEDIA_CAROUSEL_MULTIPLE_PLAYERS", 35, 1245);

    private static final /* synthetic */ MediaUiEvent[] $values() {
        return new MediaUiEvent[]{LOCAL_MEDIA_ADDED, CAST_MEDIA_ADDED, REMOTE_MEDIA_ADDED, TRANSFER_TO_LOCAL, TRANSFER_TO_CAST, TRANSFER_TO_REMOTE, RESUME_MEDIA_ADDED, ACTIVE_TO_RESUME, MEDIA_TIMEOUT, MEDIA_REMOVED, CAROUSEL_PAGE, DISMISS_SWIPE, OPEN_LONG_PRESS, DISMISS_LONG_PRESS, OPEN_SETTINGS_LONG_PRESS, OPEN_SETTINGS_CAROUSEL, TAP_ACTION_PLAY_PAUSE, TAP_ACTION_PREV, TAP_ACTION_NEXT, TAP_ACTION_OTHER, ACTION_SEEK, OPEN_OUTPUT_SWITCHER, MEDIA_TAP_CONTENT_VIEW, MEDIA_CAROUSEL_LOCATION_QQS, MEDIA_CAROUSEL_LOCATION_QS, MEDIA_CAROUSEL_LOCATION_LOCKSCREEN, MEDIA_CAROUSEL_LOCATION_DREAM, MEDIA_CAROUSEL_LOCATION_COMMUNAL, MEDIA_RECOMMENDATION_ADDED, MEDIA_RECOMMENDATION_REMOVED, MEDIA_RECOMMENDATION_ACTIVATED, MEDIA_RECOMMENDATION_ITEM_TAP, MEDIA_RECOMMENDATION_CARD_TAP, MEDIA_OPEN_BROADCAST_DIALOG, MEDIA_CAROUSEL_SINGLE_PLAYER, MEDIA_CAROUSEL_MULTIPLE_PLAYERS};
    }

    static {
        MediaUiEvent[] mediaUiEventArr$values = $values();
        $VALUES = mediaUiEventArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(mediaUiEventArr$values);
    }

    private MediaUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static MediaUiEvent valueOf(String str) {
        return (MediaUiEvent) Enum.valueOf(MediaUiEvent.class, str);
    }

    public static MediaUiEvent[] values() {
        return (MediaUiEvent[]) $VALUES.clone();
    }

    public int getId() {
        return this.metricId;
    }
}
