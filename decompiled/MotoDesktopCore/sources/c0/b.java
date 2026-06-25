package c0;

import android.view.View;
import com.motorola.mobiledesktop.core.desktop.tutorial.TutorialActivity;

/* JADX INFO: loaded from: classes.dex */
public final class b implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2017a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ TutorialActivity f2018b;

    public /* synthetic */ b(TutorialActivity tutorialActivity, int i2) {
        this.f2017a = i2;
        this.f2018b = tutorialActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.f2017a) {
            case 0:
                this.f2018b.finish();
                break;
            case 1:
                TutorialActivity tutorialActivity = this.f2018b;
                int i2 = tutorialActivity.f2335e;
                if (i2 != 0) {
                    tutorialActivity.f2333c.setCurrentItem(i2 - 1);
                }
                break;
            case 2:
                TutorialActivity tutorialActivity2 = this.f2018b;
                if (tutorialActivity2.f2335e != tutorialActivity2.f2332b.size() - 1) {
                    tutorialActivity2.f2333c.setCurrentItem(tutorialActivity2.f2335e + 1);
                }
                break;
            default:
                TutorialActivity tutorialActivity3 = this.f2018b;
                if (tutorialActivity3.f2335e == tutorialActivity3.f2332b.size() - 1) {
                    tutorialActivity3.finish();
                } else if (tutorialActivity3.f2335e != tutorialActivity3.f2332b.size() - 1) {
                    tutorialActivity3.f2333c.setCurrentItem(tutorialActivity3.f2335e + 1);
                }
                break;
        }
    }
}
