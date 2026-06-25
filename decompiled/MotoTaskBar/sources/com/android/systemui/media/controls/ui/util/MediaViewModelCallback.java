package com.android.systemui.media.controls.ui.util;

import androidx.recyclerview.widget.DiffUtil;
import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaViewModelCallback.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaViewModelCallback extends DiffUtil.Callback {

    /* JADX INFO: renamed from: new, reason: not valid java name */
    private final List f5new;
    private final List old;

    public MediaViewModelCallback(List list, List list2) {
        list.getClass();
        list2.getClass();
        this.old = list;
        this.f5new = list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        MediaCommonViewModel mediaCommonViewModel = (MediaCommonViewModel) this.old.get(i);
        MediaCommonViewModel mediaCommonViewModel2 = (MediaCommonViewModel) this.f5new.get(i2);
        return (mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl) && (mediaCommonViewModel2 instanceof MediaCommonViewModel.MediaControl) && ((MediaCommonViewModel.MediaControl) mediaCommonViewModel).getImmediatelyUpdateUi() == ((MediaCommonViewModel.MediaControl) mediaCommonViewModel2).getImmediatelyUpdateUi();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        MediaCommonViewModel mediaCommonViewModel = (MediaCommonViewModel) this.old.get(i);
        MediaCommonViewModel mediaCommonViewModel2 = (MediaCommonViewModel) this.f5new.get(i2);
        if ((mediaCommonViewModel instanceof MediaCommonViewModel.MediaControl) && (mediaCommonViewModel2 instanceof MediaCommonViewModel.MediaControl)) {
            return Intrinsics.areEqual(((MediaCommonViewModel.MediaControl) mediaCommonViewModel).getInstanceId(), ((MediaCommonViewModel.MediaControl) mediaCommonViewModel2).getInstanceId());
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.f5new.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.old.size();
    }
}
