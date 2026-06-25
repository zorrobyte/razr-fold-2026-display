package com.android.systemui.plugins.clocks;

import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: compiled from: ClockEvents.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockEvents {
    boolean isReactiveTouchInteractionEnabled();

    void onAlarmDataChanged(AlarmData alarmData);

    void onLocaleChanged(Locale locale);

    void onTimeFormatChanged(boolean z);

    void onTimeZoneChanged(TimeZone timeZone);

    void onWeatherDataChanged(WeatherData weatherData);

    void onZenDataChanged(ZenData zenData);

    void setReactiveTouchInteractionEnabled(boolean z);
}
