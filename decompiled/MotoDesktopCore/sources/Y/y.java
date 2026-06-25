package Y;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.SearchView;
import com.motorola.mobiledesktop.core.audio.MediaOutputRouteFragment;

/* JADX INFO: loaded from: classes.dex */
public final class y implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f457a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f458b;

    public /* synthetic */ y(int i2, Object obj) {
        this.f457a = i2;
        this.f458b = obj;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        switch (this.f457a) {
            case 0:
                MediaOutputRouteFragment mediaOutputRouteFragment = (MediaOutputRouteFragment) this.f458b;
                mediaOutputRouteFragment.mListAdapter.changeSelected(i2, true);
                i iVar = mediaOutputRouteFragment.mAudioOutputRouteControl;
                if (iVar.f399l) {
                    iVar.n(i2, false);
                } else {
                    iVar.o(i2, false);
                }
                mediaOutputRouteFragment.mCurrentSelect = i2;
                break;
            case 1:
                androidx.appcompat.widget.A a2 = (androidx.appcompat.widget.A) this.f458b;
                a2.f860F.setSelection(i2);
                if (a2.f860F.getOnItemClickListener() != null) {
                    a2.f860F.performItemClick(view, i2, a2.f858D.getItemId(i2));
                }
                a2.dismiss();
                break;
            default:
                ((SearchView) this.f458b).m(i2);
                break;
        }
    }
}
