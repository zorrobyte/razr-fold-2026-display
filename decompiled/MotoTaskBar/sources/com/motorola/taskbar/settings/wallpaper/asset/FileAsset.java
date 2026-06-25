package com.motorola.taskbar.settings.wallpaper.asset;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FileAsset.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class FileAsset extends StreamingAsset {
    public static final Companion Companion = new Companion(null);
    private final File file;

    /* JADX INFO: compiled from: FileAsset.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FileAsset(File file) {
        this.file = file;
    }

    @Override // com.motorola.taskbar.settings.wallpaper.asset.StreamingAsset
    protected InputStream openInputStream() {
        File file = this.file;
        if (file == null) {
            return null;
        }
        try {
            return new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.w("FileAsset", "Image file not found", e);
            return null;
        }
    }
}
