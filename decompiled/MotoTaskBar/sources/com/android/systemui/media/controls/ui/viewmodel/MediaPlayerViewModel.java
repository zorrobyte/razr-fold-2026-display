package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Icon;
import com.android.systemui.monet.ColorScheme;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaPlayerViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaPlayerViewModel {
    private final List actionButtons;
    private final Icon appIcon;
    private final CharSequence artistName;
    private final Icon backgroundCover;
    private final boolean canShowTime;
    private final ColorScheme colorScheme;
    private final Function1 contentDescription;
    private final GutsViewModel gutsMenu;
    private final boolean isExplicitVisible;
    private final com.android.systemui.common.shared.model.Icon launcherIcon;
    private final Function1 onBindSeekbar;
    private final Function0 onClicked;
    private final Function0 onLongClicked;
    private final Function0 onSeek;
    private final MediaOutputSwitcherViewModel outputSwitcher;
    private final boolean playTurbulenceNoise;
    private final boolean shouldAddGradient;
    private final CharSequence titleName;
    private final boolean useGrayColorFilter;
    private final boolean useSemanticActions;

    public MediaPlayerViewModel(Function1 function1, Icon icon, Icon icon2, com.android.systemui.common.shared.model.Icon icon3, boolean z, CharSequence charSequence, CharSequence charSequence2, boolean z2, boolean z3, ColorScheme colorScheme, boolean z4, boolean z5, boolean z6, List list, MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel, GutsViewModel gutsViewModel, Function0 function0, Function0 function02, Function0 function03, Function1 function12) {
        function1.getClass();
        icon3.getClass();
        charSequence.getClass();
        charSequence2.getClass();
        colorScheme.getClass();
        list.getClass();
        mediaOutputSwitcherViewModel.getClass();
        gutsViewModel.getClass();
        function0.getClass();
        function02.getClass();
        function03.getClass();
        function12.getClass();
        this.contentDescription = function1;
        this.backgroundCover = icon;
        this.appIcon = icon2;
        this.launcherIcon = icon3;
        this.useGrayColorFilter = z;
        this.artistName = charSequence;
        this.titleName = charSequence2;
        this.isExplicitVisible = z2;
        this.shouldAddGradient = z3;
        this.colorScheme = colorScheme;
        this.canShowTime = z4;
        this.playTurbulenceNoise = z5;
        this.useSemanticActions = z6;
        this.actionButtons = list;
        this.outputSwitcher = mediaOutputSwitcherViewModel;
        this.gutsMenu = gutsViewModel;
        this.onClicked = function0;
        this.onLongClicked = function02;
        this.onSeek = function03;
        this.onBindSeekbar = function12;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaPlayerViewModel)) {
            return false;
        }
        MediaPlayerViewModel mediaPlayerViewModel = (MediaPlayerViewModel) obj;
        return Intrinsics.areEqual(this.contentDescription, mediaPlayerViewModel.contentDescription) && Intrinsics.areEqual(this.backgroundCover, mediaPlayerViewModel.backgroundCover) && Intrinsics.areEqual(this.appIcon, mediaPlayerViewModel.appIcon) && Intrinsics.areEqual(this.launcherIcon, mediaPlayerViewModel.launcherIcon) && this.useGrayColorFilter == mediaPlayerViewModel.useGrayColorFilter && Intrinsics.areEqual(this.artistName, mediaPlayerViewModel.artistName) && Intrinsics.areEqual(this.titleName, mediaPlayerViewModel.titleName) && this.isExplicitVisible == mediaPlayerViewModel.isExplicitVisible && this.shouldAddGradient == mediaPlayerViewModel.shouldAddGradient && Intrinsics.areEqual(this.colorScheme, mediaPlayerViewModel.colorScheme) && this.canShowTime == mediaPlayerViewModel.canShowTime && this.playTurbulenceNoise == mediaPlayerViewModel.playTurbulenceNoise && this.useSemanticActions == mediaPlayerViewModel.useSemanticActions && Intrinsics.areEqual(this.actionButtons, mediaPlayerViewModel.actionButtons) && Intrinsics.areEqual(this.outputSwitcher, mediaPlayerViewModel.outputSwitcher) && Intrinsics.areEqual(this.gutsMenu, mediaPlayerViewModel.gutsMenu) && Intrinsics.areEqual(this.onClicked, mediaPlayerViewModel.onClicked) && Intrinsics.areEqual(this.onLongClicked, mediaPlayerViewModel.onLongClicked) && Intrinsics.areEqual(this.onSeek, mediaPlayerViewModel.onSeek) && Intrinsics.areEqual(this.onBindSeekbar, mediaPlayerViewModel.onBindSeekbar);
    }

    public final List getActionButtons() {
        return this.actionButtons;
    }

    public final Icon getAppIcon() {
        return this.appIcon;
    }

    public final CharSequence getArtistName() {
        return this.artistName;
    }

    public final Icon getBackgroundCover() {
        return this.backgroundCover;
    }

    public final boolean getCanShowTime() {
        return this.canShowTime;
    }

    public final ColorScheme getColorScheme() {
        return this.colorScheme;
    }

    public final Function1 getContentDescription() {
        return this.contentDescription;
    }

    public final GutsViewModel getGutsMenu() {
        return this.gutsMenu;
    }

    public final com.android.systemui.common.shared.model.Icon getLauncherIcon() {
        return this.launcherIcon;
    }

    public final Function1 getOnBindSeekbar() {
        return this.onBindSeekbar;
    }

    public final Function0 getOnClicked() {
        return this.onClicked;
    }

    public final Function0 getOnLongClicked() {
        return this.onLongClicked;
    }

    public final Function0 getOnSeek() {
        return this.onSeek;
    }

    public final MediaOutputSwitcherViewModel getOutputSwitcher() {
        return this.outputSwitcher;
    }

    public final boolean getPlayTurbulenceNoise() {
        return this.playTurbulenceNoise;
    }

    public final boolean getShouldAddGradient() {
        return this.shouldAddGradient;
    }

    public final CharSequence getTitleName() {
        return this.titleName;
    }

    public final boolean getUseGrayColorFilter() {
        return this.useGrayColorFilter;
    }

    public final boolean getUseSemanticActions() {
        return this.useSemanticActions;
    }

    public int hashCode() {
        int iHashCode = this.contentDescription.hashCode() * 31;
        Icon icon = this.backgroundCover;
        int iHashCode2 = (iHashCode + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.appIcon;
        return ((((((((((((((((((((((((((((((((((iHashCode2 + (icon2 != null ? icon2.hashCode() : 0)) * 31) + this.launcherIcon.hashCode()) * 31) + Boolean.hashCode(this.useGrayColorFilter)) * 31) + this.artistName.hashCode()) * 31) + this.titleName.hashCode()) * 31) + Boolean.hashCode(this.isExplicitVisible)) * 31) + Boolean.hashCode(this.shouldAddGradient)) * 31) + this.colorScheme.hashCode()) * 31) + Boolean.hashCode(this.canShowTime)) * 31) + Boolean.hashCode(this.playTurbulenceNoise)) * 31) + Boolean.hashCode(this.useSemanticActions)) * 31) + this.actionButtons.hashCode()) * 31) + this.outputSwitcher.hashCode()) * 31) + this.gutsMenu.hashCode()) * 31) + this.onClicked.hashCode()) * 31) + this.onLongClicked.hashCode()) * 31) + this.onSeek.hashCode()) * 31) + this.onBindSeekbar.hashCode();
    }

    public final boolean isExplicitVisible() {
        return this.isExplicitVisible;
    }

    public String toString() {
        Function1 function1 = this.contentDescription;
        Icon icon = this.backgroundCover;
        Icon icon2 = this.appIcon;
        com.android.systemui.common.shared.model.Icon icon3 = this.launcherIcon;
        boolean z = this.useGrayColorFilter;
        CharSequence charSequence = this.artistName;
        CharSequence charSequence2 = this.titleName;
        return "MediaPlayerViewModel(contentDescription=" + function1 + ", backgroundCover=" + icon + ", appIcon=" + icon2 + ", launcherIcon=" + icon3 + ", useGrayColorFilter=" + z + ", artistName=" + ((Object) charSequence) + ", titleName=" + ((Object) charSequence2) + ", isExplicitVisible=" + this.isExplicitVisible + ", shouldAddGradient=" + this.shouldAddGradient + ", colorScheme=" + this.colorScheme + ", canShowTime=" + this.canShowTime + ", playTurbulenceNoise=" + this.playTurbulenceNoise + ", useSemanticActions=" + this.useSemanticActions + ", actionButtons=" + this.actionButtons + ", outputSwitcher=" + this.outputSwitcher + ", gutsMenu=" + this.gutsMenu + ", onClicked=" + this.onClicked + ", onLongClicked=" + this.onLongClicked + ", onSeek=" + this.onSeek + ", onBindSeekbar=" + this.onBindSeekbar + ")";
    }
}
