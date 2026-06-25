package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaDeviceManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDeviceManager implements MediaDataManager.Listener {
    private final Executor bgExecutor;
    private final Context context;
    private MediaDeviceData current;
    private final MediaDeviceManager$desktopFocusAudioOutputListener$1 desktopFocusAudioOutputListener;
    private final DesktopFocusAudioOutputMonitor desktopFocusAudioOutputMonitor;
    private final Map entries;
    private final Executor fgExecutor;
    private final Set listeners;

    /* JADX INFO: compiled from: MediaDeviceManager.kt */
    final class Entry {
        private final String key;
        private final String oldKey;
        final /* synthetic */ MediaDeviceManager this$0;

        public Entry(MediaDeviceManager mediaDeviceManager, String str, String str2) {
            str.getClass();
            this.this$0 = mediaDeviceManager;
            this.key = str;
            this.oldKey = str2;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getOldKey() {
            return this.oldKey;
        }
    }

    /* JADX INFO: compiled from: MediaDeviceManager.kt */
    public interface Listener {
        void onKeyRemoved(String str);

        void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$desktopFocusAudioOutputListener$1] */
    public MediaDeviceManager(Context context, Executor executor, Executor executor2, DesktopFocusAudioOutputMonitor desktopFocusAudioOutputMonitor) {
        context.getClass();
        executor.getClass();
        executor2.getClass();
        desktopFocusAudioOutputMonitor.getClass();
        this.context = context;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        this.desktopFocusAudioOutputMonitor = desktopFocusAudioOutputMonitor;
        this.listeners = new LinkedHashSet();
        this.entries = new LinkedHashMap();
        this.desktopFocusAudioOutputListener = new DesktopFocusAudioOutputMonitor.DesktopFocusAudioOutputListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$desktopFocusAudioOutputListener$1
            @Override // com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor.DesktopFocusAudioOutputListener
            public void onFocusAudioOutputChanged(String str, Drawable drawable) {
                this.this$0.setCurrent(new MediaDeviceData(true, drawable, str, null, null, false, 24, null));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processDevice(MediaDeviceData mediaDeviceData) {
        for (Entry entry : this.entries.values()) {
            processDevice(entry.getKey(), entry.getOldKey(), mediaDeviceData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processDevice(String str, String str2, MediaDeviceData mediaDeviceData) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onMediaDeviceChanged(str, str2, mediaDeviceData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setCurrent(final MediaDeviceData mediaDeviceData) {
        if (mediaDeviceData == null || !mediaDeviceData.equalsWithoutIcon(this.current)) {
            this.current = mediaDeviceData;
            this.fgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$current$1
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.processDevice(mediaDeviceData);
                }
            });
        }
    }

    public final boolean addListener(Listener listener) {
        listener.getClass();
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataLoaded(final String str, final String str2, MediaData mediaData, boolean z, boolean z2) {
        str.getClass();
        mediaData.getClass();
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.entries.remove(str2);
        }
        if (((Entry) this.entries.get(str)) == null) {
            this.entries.put(str, new Entry(this, str, str2));
        }
        this.fgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager.onMediaDataLoaded.1
            @Override // java.lang.Runnable
            public final void run() {
                if (MediaDeviceManager.this.current != null) {
                    MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
                    mediaDeviceManager.processDevice(str, str2, mediaDeviceManager.current);
                }
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener, com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor.Listener
    public void onMediaDataRemoved(String str) {
        str.getClass();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onKeyRemoved(str);
        }
    }

    public final void start() {
        this.desktopFocusAudioOutputMonitor.start(this.desktopFocusAudioOutputListener);
    }
}
