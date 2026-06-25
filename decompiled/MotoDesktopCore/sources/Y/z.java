package Y;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.I;
import androidx.appcompat.widget.Q;
import androidx.appcompat.widget.SearchView;
import com.motorola.mobiledesktop.core.audio.MediaOutputRouteFragment;

/* JADX INFO: loaded from: classes.dex */
public final class z implements AdapterView.OnItemSelectedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f459a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f460b;

    public /* synthetic */ z(int i2, Object obj) {
        this.f459a = i2;
        this.f460b = obj;
    }

    private final void a(AdapterView adapterView) {
    }

    private final void b(AdapterView adapterView) {
    }

    private final void c(AdapterView adapterView) {
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i2, long j2) {
        I i3;
        switch (this.f459a) {
            case 0:
                MediaOutputRouteFragment mediaOutputRouteFragment = (MediaOutputRouteFragment) this.f460b;
                mediaOutputRouteFragment.mListAdapter.changeSelected(i2, true);
                mediaOutputRouteFragment.mCurrentSelect = i2;
                break;
            case 1:
                if (i2 != -1 && (i3 = ((Q) this.f460b).f1049c) != null) {
                    i3.setListSelectionHidden(false);
                    break;
                }
                break;
            default:
                ((SearchView) this.f460b).n(i2);
                break;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
        int i2 = this.f459a;
    }
}
