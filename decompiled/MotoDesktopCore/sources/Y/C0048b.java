package Y;

import com.motorola.mobiledesktop.core.audio.AudioOutputRouteContentProvider;
import java.util.List;

/* JADX INFO: renamed from: Y.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0048b extends h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AudioOutputRouteContentProvider f355a;

    public C0048b(AudioOutputRouteContentProvider audioOutputRouteContentProvider) {
        this.f355a = audioOutputRouteContentProvider;
    }

    @Override // Y.h
    public final void a(List list, int i2, boolean z2) {
        AudioOutputRouteContentProvider audioOutputRouteContentProvider = this.f355a;
        if (z2) {
            audioOutputRouteContentProvider.f2315b = 1;
        } else {
            audioOutputRouteContentProvider.f2315b = 0;
        }
        audioOutputRouteContentProvider.getContext().getContentResolver().notifyChange(AudioOutputRouteContentProvider.f2307d, null);
        audioOutputRouteContentProvider.getContext().getContentResolver().notifyChange(AudioOutputRouteContentProvider.f2310g, null);
    }

    @Override // Y.h
    public final void b() {
        this.f355a.getContext().getContentResolver().notifyChange(AudioOutputRouteContentProvider.f2311h, null);
    }

    @Override // Y.h
    public final void c() {
        this.f355a.getContext().getContentResolver().notifyChange(AudioOutputRouteContentProvider.f2312i, null);
    }
}
