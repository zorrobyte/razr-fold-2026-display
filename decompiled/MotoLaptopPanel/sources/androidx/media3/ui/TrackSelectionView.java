package androidx.media3.ui;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import androidx.media3.common.util.Assertions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TrackSelectionView extends LinearLayout {
    private final ComponentListener componentListener;
    private final CheckedTextView defaultView;
    private final CheckedTextView disableView;
    private final LayoutInflater inflater;
    private boolean isDisabled;
    private final Map overrides;
    private final int selectableItemBackgroundResourceId;
    private final List trackGroups;
    private TrackNameProvider trackNameProvider;
    private CheckedTextView[][] trackViews;

    class ComponentListener implements View.OnClickListener {
        private ComponentListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TrackSelectionView.this.onClick(view);
        }
    }

    public TrackSelectionView(Context context) {
        this(context, null);
    }

    public TrackSelectionView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TrackSelectionView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(1);
        setSaveFromParentEnabled(false);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        this.selectableItemBackgroundResourceId = resourceId;
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.inflater = layoutInflaterFrom;
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.trackGroups = new ArrayList();
        this.overrides = new HashMap();
        CheckedTextView checkedTextView = (CheckedTextView) layoutInflaterFrom.inflate(R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.disableView = checkedTextView;
        checkedTextView.setBackgroundResource(resourceId);
        checkedTextView.setText(R$string.exo_track_selection_none);
        checkedTextView.setEnabled(false);
        checkedTextView.setFocusable(true);
        checkedTextView.setOnClickListener(componentListener);
        checkedTextView.setVisibility(8);
        addView(checkedTextView);
        addView(layoutInflaterFrom.inflate(R$layout.exo_list_divider, (ViewGroup) this, false));
        CheckedTextView checkedTextView2 = (CheckedTextView) layoutInflaterFrom.inflate(R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.defaultView = checkedTextView2;
        checkedTextView2.setBackgroundResource(resourceId);
        checkedTextView2.setText(R$string.exo_track_selection_auto);
        checkedTextView2.setEnabled(false);
        checkedTextView2.setFocusable(true);
        checkedTextView2.setOnClickListener(componentListener);
        addView(checkedTextView2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(View view) {
        if (view == this.disableView) {
            onDisableViewClicked();
        } else if (view == this.defaultView) {
            onDefaultViewClicked();
        } else {
            onTrackViewClicked(view);
        }
        updateViewStates();
    }

    private void onDefaultViewClicked() {
        this.isDisabled = false;
        this.overrides.clear();
    }

    private void onDisableViewClicked() {
        this.isDisabled = true;
        this.overrides.clear();
    }

    private void onTrackViewClicked(View view) {
        this.isDisabled = false;
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(Assertions.checkNotNull(view.getTag()));
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005d, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateViewStates() {
        /*
            r6 = this;
            android.widget.CheckedTextView r0 = r6.disableView
            boolean r1 = r6.isDisabled
            r0.setChecked(r1)
            android.widget.CheckedTextView r0 = r6.defaultView
            boolean r1 = r6.isDisabled
            r2 = 0
            if (r1 != 0) goto L18
            java.util.Map r1 = r6.overrides
            int r1 = r1.size()
            if (r1 != 0) goto L18
            r1 = 1
            goto L19
        L18:
            r1 = r2
        L19:
            r0.setChecked(r1)
            r0 = r2
        L1d:
            android.widget.CheckedTextView[][] r1 = r6.trackViews
            int r1 = r1.length
            if (r0 >= r1) goto L60
            java.util.Map r1 = r6.overrides
            java.util.List r3 = r6.trackGroups
            java.lang.Object r3 = r3.get(r0)
            androidx.media3.common.Tracks$Group r3 = (androidx.media3.common.Tracks.Group) r3
            androidx.media3.common.TrackGroup r3 = r3.getMediaTrackGroup()
            java.lang.Object r1 = r1.get(r3)
            androidx.media3.common.TrackSelectionOverride r1 = (androidx.media3.common.TrackSelectionOverride) r1
            r3 = r2
        L37:
            android.widget.CheckedTextView[][] r4 = r6.trackViews
            r4 = r4[r0]
            int r5 = r4.length
            if (r3 >= r5) goto L5d
            if (r1 != 0) goto L48
            r4 = r4[r3]
            r4.setChecked(r2)
            int r3 = r3 + 1
            goto L37
        L48:
            r1 = r4[r3]
            java.lang.Object r1 = r1.getTag()
            java.lang.Object r1 = androidx.media3.common.util.Assertions.checkNotNull(r1)
            android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(r1)
            android.widget.CheckedTextView[][] r6 = r6.trackViews
            r6 = r6[r0]
            r6 = r6[r3]
            r6 = 0
            throw r6
        L5d:
            int r0 = r0 + 1
            goto L1d
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.ui.TrackSelectionView.updateViewStates():void");
    }
}
