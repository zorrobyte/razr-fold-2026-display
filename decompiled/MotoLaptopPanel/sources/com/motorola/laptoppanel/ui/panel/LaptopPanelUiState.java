package com.motorola.laptoppanel.ui.panel;

import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LaptopPanelModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaptopPanelUiState {
    private final LaptopPanelModel.PanelType activePanel;
    private final int brightnessIcon;
    private final boolean isGuideUIShown;
    private final boolean isMediaPlaying;
    private final boolean isModernStyle;
    private final MediaMetadata mediaMetadata;
    private final String packageName;
    private final PlaybackState playbackState;
    private final LaptopPanelModel.PanelType previousPanel;
    private final int volumeIcon;

    public LaptopPanelUiState(LaptopPanelModel.PanelType panelType, LaptopPanelModel.PanelType panelType2, String str, boolean z, boolean z2, boolean z3, int i, int i2, MediaMetadata mediaMetadata, PlaybackState playbackState) {
        panelType.getClass();
        panelType2.getClass();
        str.getClass();
        this.activePanel = panelType;
        this.previousPanel = panelType2;
        this.packageName = str;
        this.isMediaPlaying = z;
        this.isGuideUIShown = z2;
        this.isModernStyle = z3;
        this.brightnessIcon = i;
        this.volumeIcon = i2;
        this.mediaMetadata = mediaMetadata;
        this.playbackState = playbackState;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ LaptopPanelUiState(com.motorola.laptoppanel.ui.panel.LaptopPanelModel.PanelType r2, com.motorola.laptoppanel.ui.panel.LaptopPanelModel.PanelType r3, java.lang.String r4, boolean r5, boolean r6, boolean r7, int r8, int r9, android.media.MediaMetadata r10, android.media.session.PlaybackState r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r1 = this;
            r13 = r12 & 1
            if (r13 == 0) goto L6
            com.motorola.laptoppanel.ui.panel.LaptopPanelModel$PanelType r2 = com.motorola.laptoppanel.ui.panel.LaptopPanelModel.PanelType.TOUCHPAD
        L6:
            r13 = r12 & 2
            if (r13 == 0) goto Lc
            com.motorola.laptoppanel.ui.panel.LaptopPanelModel$PanelType r3 = com.motorola.laptoppanel.ui.panel.LaptopPanelModel.PanelType.NONE
        Lc:
            r13 = r12 & 4
            if (r13 == 0) goto L12
            java.lang.String r4 = ""
        L12:
            r13 = r12 & 8
            if (r13 == 0) goto L17
            r5 = 0
        L17:
            r13 = r12 & 16
            r0 = 1
            if (r13 == 0) goto L1d
            r6 = r0
        L1d:
            r13 = r12 & 32
            if (r13 == 0) goto L22
            r7 = r0
        L22:
            r13 = r12 & 64
            if (r13 == 0) goto L29
            r8 = 2131231542(0x7f080336, float:1.8079168E38)
        L29:
            r13 = r12 & 128(0x80, float:1.794E-43)
            if (r13 == 0) goto L30
            r9 = 2131231562(0x7f08034a, float:1.8079209E38)
        L30:
            r13 = r12 & 256(0x100, float:3.59E-43)
            r0 = 0
            if (r13 == 0) goto L36
            r10 = r0
        L36:
            r12 = r12 & 512(0x200, float:7.175E-43)
            if (r12 == 0) goto L46
            r13 = r0
            r11 = r9
            r12 = r10
            r9 = r7
            r10 = r8
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            goto L51
        L46:
            r13 = r11
            r12 = r10
            r10 = r8
            r11 = r9
            r8 = r6
            r9 = r7
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
        L51:
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelUiState.<init>(com.motorola.laptoppanel.ui.panel.LaptopPanelModel$PanelType, com.motorola.laptoppanel.ui.panel.LaptopPanelModel$PanelType, java.lang.String, boolean, boolean, boolean, int, int, android.media.MediaMetadata, android.media.session.PlaybackState, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public static /* synthetic */ LaptopPanelUiState copy$default(LaptopPanelUiState laptopPanelUiState, LaptopPanelModel.PanelType panelType, LaptopPanelModel.PanelType panelType2, String str, boolean z, boolean z2, boolean z3, int i, int i2, MediaMetadata mediaMetadata, PlaybackState playbackState, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            panelType = laptopPanelUiState.activePanel;
        }
        if ((i3 & 2) != 0) {
            panelType2 = laptopPanelUiState.previousPanel;
        }
        if ((i3 & 4) != 0) {
            str = laptopPanelUiState.packageName;
        }
        if ((i3 & 8) != 0) {
            z = laptopPanelUiState.isMediaPlaying;
        }
        if ((i3 & 16) != 0) {
            z2 = laptopPanelUiState.isGuideUIShown;
        }
        if ((i3 & 32) != 0) {
            z3 = laptopPanelUiState.isModernStyle;
        }
        if ((i3 & 64) != 0) {
            i = laptopPanelUiState.brightnessIcon;
        }
        if ((i3 & 128) != 0) {
            i2 = laptopPanelUiState.volumeIcon;
        }
        if ((i3 & 256) != 0) {
            mediaMetadata = laptopPanelUiState.mediaMetadata;
        }
        if ((i3 & 512) != 0) {
            playbackState = laptopPanelUiState.playbackState;
        }
        MediaMetadata mediaMetadata2 = mediaMetadata;
        PlaybackState playbackState2 = playbackState;
        int i4 = i;
        int i5 = i2;
        boolean z4 = z2;
        boolean z5 = z3;
        return laptopPanelUiState.copy(panelType, panelType2, str, z, z4, z5, i4, i5, mediaMetadata2, playbackState2);
    }

    public final LaptopPanelUiState copy(LaptopPanelModel.PanelType panelType, LaptopPanelModel.PanelType panelType2, String str, boolean z, boolean z2, boolean z3, int i, int i2, MediaMetadata mediaMetadata, PlaybackState playbackState) {
        panelType.getClass();
        panelType2.getClass();
        str.getClass();
        return new LaptopPanelUiState(panelType, panelType2, str, z, z2, z3, i, i2, mediaMetadata, playbackState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LaptopPanelUiState)) {
            return false;
        }
        LaptopPanelUiState laptopPanelUiState = (LaptopPanelUiState) obj;
        return this.activePanel == laptopPanelUiState.activePanel && this.previousPanel == laptopPanelUiState.previousPanel && Intrinsics.areEqual(this.packageName, laptopPanelUiState.packageName) && this.isMediaPlaying == laptopPanelUiState.isMediaPlaying && this.isGuideUIShown == laptopPanelUiState.isGuideUIShown && this.isModernStyle == laptopPanelUiState.isModernStyle && this.brightnessIcon == laptopPanelUiState.brightnessIcon && this.volumeIcon == laptopPanelUiState.volumeIcon && Intrinsics.areEqual(this.mediaMetadata, laptopPanelUiState.mediaMetadata) && Intrinsics.areEqual(this.playbackState, laptopPanelUiState.playbackState);
    }

    public final LaptopPanelModel.PanelType getActivePanel() {
        return this.activePanel;
    }

    public final int getBrightnessIcon() {
        return this.brightnessIcon;
    }

    public final MediaMetadata getMediaMetadata() {
        return this.mediaMetadata;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final PlaybackState getPlaybackState() {
        return this.playbackState;
    }

    public final LaptopPanelModel.PanelType getPreviousPanel() {
        return this.previousPanel;
    }

    public final int getVolumeIcon() {
        return this.volumeIcon;
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((this.activePanel.hashCode() * 31) + this.previousPanel.hashCode()) * 31) + this.packageName.hashCode()) * 31) + Boolean.hashCode(this.isMediaPlaying)) * 31) + Boolean.hashCode(this.isGuideUIShown)) * 31) + Boolean.hashCode(this.isModernStyle)) * 31) + Integer.hashCode(this.brightnessIcon)) * 31) + Integer.hashCode(this.volumeIcon)) * 31;
        MediaMetadata mediaMetadata = this.mediaMetadata;
        int iHashCode2 = (iHashCode + (mediaMetadata == null ? 0 : mediaMetadata.hashCode())) * 31;
        PlaybackState playbackState = this.playbackState;
        return iHashCode2 + (playbackState != null ? playbackState.hashCode() : 0);
    }

    public final boolean isGuideUIShown() {
        return this.isGuideUIShown;
    }

    public final boolean isMediaPlaying() {
        return this.isMediaPlaying;
    }

    public final boolean isModernStyle() {
        return this.isModernStyle;
    }

    public String toString() {
        return "LaptopPanelUiState(activePanel=" + this.activePanel + ", previousPanel=" + this.previousPanel + ", packageName=" + this.packageName + ", isMediaPlaying=" + this.isMediaPlaying + ", isGuideUIShown=" + this.isGuideUIShown + ", isModernStyle=" + this.isModernStyle + ", brightnessIcon=" + this.brightnessIcon + ", volumeIcon=" + this.volumeIcon + ", mediaMetadata=" + this.mediaMetadata + ", playbackState=" + this.playbackState + ")";
    }
}
