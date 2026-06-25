package androidx.transition;

import android.view.ViewGroup;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;

/* JADX INFO: loaded from: classes.dex */
public abstract class Scene {
    public static Scene getCurrentScene(ViewGroup viewGroup) {
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(viewGroup.getTag(R$id.transition_current_scene));
        return null;
    }

    static void setCurrentScene(ViewGroup viewGroup, Scene scene) {
        viewGroup.setTag(R$id.transition_current_scene, scene);
    }
}
