package com.motorola.taskbar.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.motorola.taskbar.guide.R$id;
import com.motorola.taskbar.guide.R$layout;
import com.motorola.taskbar.settings.SizeChangedAwareLinearLayout;
import com.motorola.taskbar.settings.utils.Utils;
import com.motorola.taskbar.settings.wallpaper.asset.IAsset;
import com.motorola.taskbar.settings.wallpaper.model.WallpaperInfo;
import com.motorola.taskbar.settings.wallpaper.module.MyPhotosStarter;
import com.motorola.taskbar.settings.wallpaper.utils.TileSizeCalculator;
import com.motorola.taskbar.settings.wallpaper.utils.WallpaperChangedNotifier;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: WallpaperFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WallpaperFragment extends Fragment implements MyPhotosStarter, WallpaperChangedNotifier.Listener {
    public static final Companion Companion = new Companion(null);
    private ImageView currentSetWallpaperView;
    private final WallpaperFragment$differCallback$1 differCallback;
    private final MyPhotosStarter myPhotosStarter;
    private Configuration oldConfiguration;
    private SizeChangedAwareLinearLayout sizeChangedAwareView;
    private WallpaperAdapter wallpaperAdapter;
    private final WallpaperFragment$wallpaperHostCallback$1 wallpaperHostCallback;
    private RecyclerView wallpaperList;
    private final Lazy wallpaperViewModel$delegate;

    /* JADX INFO: compiled from: WallpaperFragment.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WallpaperFragment newInstance() {
            return new WallpaperFragment();
        }
    }

    /* JADX INFO: compiled from: WallpaperFragment.kt */
    final class WallpaperAdapter extends ListAdapter {
        private final WallpaperHostCallback hostCallback;
        final /* synthetic */ WallpaperFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WallpaperAdapter(WallpaperFragment wallpaperFragment, WallpaperHostCallback wallpaperHostCallback) {
            super(wallpaperFragment.differCallback);
            wallpaperHostCallback.getClass();
            this.this$0 = wallpaperFragment;
            this.hostCallback = wallpaperHostCallback;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(WallpaperViewHolder wallpaperViewHolder, int i) {
            wallpaperViewHolder.getClass();
            Object item = getItem(i);
            item.getClass();
            wallpaperViewHolder.bind((WallpaperInfo) item, this.hostCallback);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public WallpaperViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            viewGroup.getClass();
            WallpaperFragment wallpaperFragment = this.this$0;
            View viewInflate = wallpaperFragment.getLayoutInflater().inflate(R$layout.wallpaper_item, viewGroup, false);
            viewInflate.getClass();
            return new WallpaperViewHolder(wallpaperFragment, viewInflate);
        }
    }

    /* JADX INFO: compiled from: WallpaperFragment.kt */
    public interface WallpaperHostCallback {
        void setWallpaper(View view, WallpaperInfo wallpaperInfo);
    }

    /* JADX INFO: compiled from: WallpaperFragment.kt */
    final class WallpaperViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        final /* synthetic */ WallpaperFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public WallpaperViewHolder(WallpaperFragment wallpaperFragment, View view) {
            super(view);
            view.getClass();
            this.this$0 = wallpaperFragment;
            View viewRequireViewById = this.itemView.requireViewById(R$id.wallpaper_image);
            viewRequireViewById.getClass();
            this.icon = (ImageView) viewRequireViewById;
        }

        public final void bind(final WallpaperInfo wallpaperInfo, final WallpaperHostCallback wallpaperHostCallback) {
            wallpaperInfo.getClass();
            wallpaperHostCallback.getClass();
            Context applicationContext = this.icon.getContext().getApplicationContext();
            applicationContext.getClass();
            wallpaperInfo.getThumbAsset(applicationContext).loadDrawable(applicationContext, this.icon, -1);
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.WallpaperFragment$WallpaperViewHolder$bind$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    wallpaperHostCallback.setWallpaper(this.itemView, wallpaperInfo);
                }
            });
            this.icon.setOnHoverListener(new View.OnHoverListener() { // from class: com.motorola.taskbar.settings.WallpaperFragment$WallpaperViewHolder$bind$2
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked == 9) {
                        this.this$0.icon.setHovered(true);
                    } else if (actionMasked == 10) {
                        this.this$0.icon.setHovered(false);
                    }
                    return false;
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.motorola.taskbar.settings.WallpaperFragment$wallpaperHostCallback$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.motorola.taskbar.settings.WallpaperFragment$differCallback$1] */
    public WallpaperFragment() {
        super(R$layout.wallpaper_layout);
        Function0 function0 = new Function0() { // from class: com.motorola.taskbar.settings.WallpaperFragment$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return WallpaperFragment.wallpaperViewModel_delegate$lambda$0(this.f$0);
            }
        };
        final Function0 function02 = new Function0() { // from class: com.motorola.taskbar.settings.WallpaperFragment$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Fragment mo2224invoke() {
                return this;
            }
        };
        final Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: com.motorola.taskbar.settings.WallpaperFragment$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final ViewModelStoreOwner mo2224invoke() {
                return (ViewModelStoreOwner) function02.mo2224invoke();
            }
        });
        final Function0 function03 = null;
        this.wallpaperViewModel$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(WallpaperViewModel.class), new Function0() { // from class: com.motorola.taskbar.settings.WallpaperFragment$special$$inlined$viewModels$default$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final ViewModelStore mo2224invoke() {
                return FragmentViewModelLazyKt.m1066viewModels$lambda1(lazy).getViewModelStore();
            }
        }, new Function0() { // from class: com.motorola.taskbar.settings.WallpaperFragment$special$$inlined$viewModels$default$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final CreationExtras mo2224invoke() {
                CreationExtras creationExtras;
                Function0 function04 = function03;
                if (function04 != null && (creationExtras = (CreationExtras) function04.mo2224invoke()) != null) {
                    return creationExtras;
                }
                ViewModelStoreOwner viewModelStoreOwnerM1066viewModels$lambda1 = FragmentViewModelLazyKt.m1066viewModels$lambda1(lazy);
                HasDefaultViewModelProviderFactory hasDefaultViewModelProviderFactory = viewModelStoreOwnerM1066viewModels$lambda1 instanceof HasDefaultViewModelProviderFactory ? (HasDefaultViewModelProviderFactory) viewModelStoreOwnerM1066viewModels$lambda1 : null;
                return hasDefaultViewModelProviderFactory != null ? hasDefaultViewModelProviderFactory.getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            }
        }, function0);
        this.wallpaperHostCallback = new WallpaperHostCallback() { // from class: com.motorola.taskbar.settings.WallpaperFragment$wallpaperHostCallback$1
            @Override // com.motorola.taskbar.settings.WallpaperFragment.WallpaperHostCallback
            public void setWallpaper(View view, WallpaperInfo wallpaperInfo) {
                wallpaperInfo.getClass();
                WallpaperViewModel wallpaperViewModel = this.this$0.getWallpaperViewModel();
                FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
                fragmentActivityRequireActivity.getClass();
                wallpaperViewModel.setWallpaper(fragmentActivityRequireActivity, view, wallpaperInfo);
            }
        };
        this.differCallback = new DiffUtil.ItemCallback() { // from class: com.motorola.taskbar.settings.WallpaperFragment$differCallback$1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2) {
                wallpaperInfo.getClass();
                wallpaperInfo2.getClass();
                Context contextRequireContext = this.this$0.requireContext();
                contextRequireContext.getClass();
                return Objects.equals(wallpaperInfo.getThumbAsset(contextRequireContext), wallpaperInfo2.getThumbAsset(contextRequireContext)) && Objects.equals(wallpaperInfo.getAsset(contextRequireContext), wallpaperInfo2.getAsset(contextRequireContext));
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2) {
                wallpaperInfo.getClass();
                wallpaperInfo2.getClass();
                return Intrinsics.areEqual(wallpaperInfo, wallpaperInfo2);
            }
        };
        this.myPhotosStarter = this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final WallpaperViewModel getWallpaperViewModel() {
        return (WallpaperViewModel) this.wallpaperViewModel$delegate.getValue();
    }

    public static final WallpaperFragment newInstance() {
        return Companion.newInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewCreated$lambda$1(WallpaperFragment wallpaperFragment, List list) {
        WallpaperAdapter wallpaperAdapter = wallpaperFragment.wallpaperAdapter;
        if (wallpaperAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wallpaperAdapter");
            wallpaperAdapter = null;
        }
        wallpaperAdapter.submitList(list);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onViewCreated$lambda$2(WallpaperFragment wallpaperFragment, WallpaperInfo wallpaperInfo) {
        Context applicationContext = wallpaperFragment.requireContext().getApplicationContext();
        applicationContext.getClass();
        IAsset thumbAsset = wallpaperInfo.getThumbAsset(applicationContext);
        Context contextRequireContext = wallpaperFragment.requireContext();
        contextRequireContext.getClass();
        ImageView imageView = wallpaperFragment.currentSetWallpaperView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentSetWallpaperView");
            imageView = null;
        }
        thumbAsset.loadDrawable(contextRequireContext, imageView, -1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateWallpaperGrid() {
        TileSizeCalculator.Companion companion = TileSizeCalculator.Companion;
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        fragmentActivityRequireActivity.getClass();
        View viewRequireView = requireView();
        viewRequireView.getClass();
        int numIndividualColumns = companion.getNumIndividualColumns(fragmentActivityRequireActivity, viewRequireView);
        if (Utils.isMobileUIAndPortrait(getContext())) {
            numIndividualColumns = 3;
        }
        RecyclerView recyclerView = this.wallpaperList;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wallpaperList");
            recyclerView = null;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        layoutManager.getClass();
        ((GridLayoutManager) layoutManager).setSpanCount(numIndividualColumns);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ViewModelProvider.Factory wallpaperViewModel_delegate$lambda$0(final WallpaperFragment wallpaperFragment) {
        return new ViewModelProvider.Factory() { // from class: com.motorola.taskbar.settings.WallpaperFragment$wallpaperViewModel$2$1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public ViewModel create(Class cls) {
                cls.getClass();
                return new WallpaperViewModel(this.this$0);
            }
        };
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        WallpaperViewModel wallpaperViewModel = getWallpaperViewModel();
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        fragmentActivityRequireActivity.getClass();
        wallpaperViewModel.onActivityResult(fragmentActivityRequireActivity, i, i2, intent);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        context.getClass();
        super.onAttach(context);
        WallpaperChangedNotifier.INSTANCE.registerListener(this);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        fragmentActivityRequireActivity.getClass();
        ((SettingsActivity) fragmentActivityRequireActivity).mSettingsComponent.inject(this);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        configuration.getClass();
        super.onConfigurationChanged(configuration);
        Configuration configuration2 = this.oldConfiguration;
        Configuration configuration3 = null;
        if (configuration2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("oldConfiguration");
            configuration2 = null;
        }
        if (configuration.diff(configuration2) != 0) {
            Configuration configuration4 = this.oldConfiguration;
            if (configuration4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("oldConfiguration");
            } else {
                configuration3 = configuration4;
            }
            configuration3.setTo(configuration);
            updateWallpaperGrid();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        WallpaperChangedNotifier.INSTANCE.unregisterListener(this);
        super.onDetach();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        strArr.getClass();
        iArr.getClass();
        WallpaperViewModel wallpaperViewModel = getWallpaperViewModel();
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        fragmentActivityRequireActivity.getClass();
        wallpaperViewModel.onRequestPermissionsResult(fragmentActivityRequireActivity, i, strArr, iArr);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Glide.get(requireActivity()).setMemoryCategory(MemoryCategory.NORMAL);
        WallpaperViewModel wallpaperViewModel = getWallpaperViewModel();
        Context contextRequireContext = requireContext();
        contextRequireContext.getClass();
        wallpaperViewModel.updateCurrentlySetWallpaper(contextRequireContext, true);
        updateWallpaperGrid();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        view.getClass();
        super.onViewCreated(view, bundle);
        this.oldConfiguration = new Configuration(getResources().getConfiguration());
        this.sizeChangedAwareView = (SizeChangedAwareLinearLayout) view.requireViewById(R$id.wallpaper_root);
        this.currentSetWallpaperView = (ImageView) view.requireViewById(R$id.image_current_wallpaper);
        this.wallpaperList = (RecyclerView) view.requireViewById(R$id.wallpaper_list);
        this.wallpaperAdapter = new WallpaperAdapter(this, this.wallpaperHostCallback);
        RecyclerView recyclerView = this.wallpaperList;
        SizeChangedAwareLinearLayout sizeChangedAwareLinearLayout = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wallpaperList");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RecyclerView recyclerView2 = this.wallpaperList;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wallpaperList");
            recyclerView2 = null;
        }
        WallpaperAdapter wallpaperAdapter = this.wallpaperAdapter;
        if (wallpaperAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wallpaperAdapter");
            wallpaperAdapter = null;
        }
        recyclerView2.setAdapter(wallpaperAdapter);
        getWallpaperViewModel().getAllWallpapers().observe(getViewLifecycleOwner(), new WallpaperFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.motorola.taskbar.settings.WallpaperFragment$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return WallpaperFragment.onViewCreated$lambda$1(this.f$0, (List) obj);
            }
        }));
        view.requireViewById(R$id.browser_more_wallpaper).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.settings.WallpaperFragment.onViewCreated.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                WallpaperFragment.this.getWallpaperViewModel().requestCustomPhotoPicker(WallpaperFragment.this);
            }
        });
        SizeChangedAwareLinearLayout sizeChangedAwareLinearLayout2 = this.sizeChangedAwareView;
        if (sizeChangedAwareLinearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sizeChangedAwareView");
        } else {
            sizeChangedAwareLinearLayout = sizeChangedAwareLinearLayout2;
        }
        sizeChangedAwareLinearLayout.setOnSizeChangedListener(new SizeChangedAwareLinearLayout.OnSizeChangedListener() { // from class: com.motorola.taskbar.settings.WallpaperFragment.onViewCreated.3
            @Override // com.motorola.taskbar.settings.SizeChangedAwareLinearLayout.OnSizeChangedListener
            public final void onSizeChanged(int i, int i2, int i3, int i4) {
                if (i3 == i && i4 == i2) {
                    return;
                }
                WallpaperFragment.this.updateWallpaperGrid();
            }
        });
        getWallpaperViewModel().getCurrentWallpaper().observe(getViewLifecycleOwner(), new WallpaperFragment$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.motorola.taskbar.settings.WallpaperFragment$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return WallpaperFragment.onViewCreated$lambda$2(this.f$0, (WallpaperInfo) obj);
            }
        }));
        getWallpaperViewModel().requestExternalStoragePermission(this);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.utils.WallpaperChangedNotifier.Listener
    public void onWallpaperChanged() {
        WallpaperViewModel wallpaperViewModel = getWallpaperViewModel();
        Context contextRequireContext = requireContext();
        contextRequireContext.getClass();
        wallpaperViewModel.updateCurrentlySetWallpaper(contextRequireContext, true);
    }

    @Override // com.motorola.taskbar.settings.wallpaper.module.MyPhotosStarter
    public void requestCustomPhotoPicker() {
        WallpaperViewModel wallpaperViewModel = getWallpaperViewModel();
        Context contextRequireContext = requireContext();
        contextRequireContext.getClass();
        if (wallpaperViewModel.isReadExternalStoragePermissionGranted(contextRequireContext)) {
            getWallpaperViewModel().showCustomPhotoPicker(this);
        } else {
            getWallpaperViewModel().requestExternalStoragePermission(this);
        }
    }
}
