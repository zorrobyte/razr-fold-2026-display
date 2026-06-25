package com.motorola.taskbar.settings.di;

import com.motorola.taskbar.settings.SettingsActivity;
import com.motorola.taskbar.settings.di.SettingsComponent;

/* JADX INFO: compiled from: SettingsComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SettingsComponentFactory implements SettingsComponent.Factory {
    @Override // com.motorola.taskbar.settings.di.SettingsComponent.Factory
    public SettingsComponent create(SettingsActivity settingsActivity) {
        settingsActivity.getClass();
        return new SettingsComponentImpl(settingsActivity);
    }
}
