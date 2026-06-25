package com.android.systemui.media.controls.domain.resume;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser;

/* JADX INFO: loaded from: classes.dex */
public class ResumeMediaBrowserFactory {
    private final MediaBrowserFactory mBrowserFactory;
    private final Context mContext;
    private final ResumeMediaBrowserLogger mLogger;

    public ResumeMediaBrowserFactory(Context context, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        this.mContext = context;
        this.mBrowserFactory = mediaBrowserFactory;
        this.mLogger = resumeMediaBrowserLogger;
    }

    public ResumeMediaBrowser create(ResumeMediaBrowser.Callback callback, ComponentName componentName, int i) {
        return new ResumeMediaBrowser(this.mContext, callback, componentName, this.mBrowserFactory, this.mLogger, i);
    }
}
