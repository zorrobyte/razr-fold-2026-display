package com.motorola.laptoppanel.ui.panel;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: LaptopPanel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ToolbarAction {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ToolbarAction[] $VALUES;
    public static final ToolbarAction SplitScreen = new ToolbarAction("SplitScreen", 0);
    public static final ToolbarAction Screenshot = new ToolbarAction("Screenshot", 1);
    public static final ToolbarAction ToggleBrightness = new ToolbarAction("ToggleBrightness", 2);
    public static final ToolbarAction ToggleVolume = new ToolbarAction("ToggleVolume", 3);
    public static final ToolbarAction ToggleMedia = new ToolbarAction("ToggleMedia", 4);
    public static final ToolbarAction QuickSettings = new ToolbarAction("QuickSettings", 5);
    public static final ToolbarAction Notification = new ToolbarAction("Notification", 6);

    private static final /* synthetic */ ToolbarAction[] $values() {
        return new ToolbarAction[]{SplitScreen, Screenshot, ToggleBrightness, ToggleVolume, ToggleMedia, QuickSettings, Notification};
    }

    static {
        ToolbarAction[] toolbarActionArr$values = $values();
        $VALUES = toolbarActionArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(toolbarActionArr$values);
    }

    private ToolbarAction(String str, int i) {
    }

    public static ToolbarAction valueOf(String str) {
        return (ToolbarAction) Enum.valueOf(ToolbarAction.class, str);
    }

    public static ToolbarAction[] values() {
        return (ToolbarAction[]) $VALUES.clone();
    }
}
