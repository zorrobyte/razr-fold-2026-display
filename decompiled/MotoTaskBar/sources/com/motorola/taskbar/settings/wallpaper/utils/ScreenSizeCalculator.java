package com.motorola.taskbar.settings.wallpaper.utils;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

/* JADX INFO: compiled from: ScreenSizeCalculator.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ScreenSizeCalculator {
    public static final ScreenSizeCalculator INSTANCE = new ScreenSizeCalculator();
    private static Point mLandscapeScreenSize;
    private static Point mPortraitScreenSize;

    private ScreenSizeCalculator() {
    }

    private final Point getLandscapeScreenSize(Display display) {
        if (mLandscapeScreenSize == null) {
            mLandscapeScreenSize = new Point();
        }
        Point point = mLandscapeScreenSize;
        point.getClass();
        writeDisplaySizeToPoint(display, point);
        Point point2 = mLandscapeScreenSize;
        point2.getClass();
        return point2;
    }

    private final Point getPortraitScreenSize(Display display) {
        if (mPortraitScreenSize == null) {
            mPortraitScreenSize = new Point();
        }
        Point point = mPortraitScreenSize;
        point.getClass();
        writeDisplaySizeToPoint(display, point);
        Point point2 = mPortraitScreenSize;
        point2.getClass();
        return point2;
    }

    private final void writeDisplaySizeToPoint(Display display, Point point) {
        display.getRealSize(point);
    }

    public final Point getScreenSize(Display display) {
        display.getClass();
        int i = Resources.getSystem().getConfiguration().orientation;
        if (i == 1) {
            return getPortraitScreenSize(display);
        }
        if (i == 2) {
            return getLandscapeScreenSize(display);
        }
        Log.e("ScreenSizeCalculator", "Unknown device orientation: " + Resources.getSystem().getConfiguration().orientation);
        return getPortraitScreenSize(display);
    }
}
