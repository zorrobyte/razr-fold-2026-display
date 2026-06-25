package androidx.fragment.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.BackEventCompat;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.util.Consumer;
import androidx.core.view.MenuProvider;
import androidx.fragment.R$id;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStore;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentManager {
    private static boolean DEBUG = false;
    static boolean USE_PREDICTIVE_BACK = true;
    private FragmentContainer mContainer;
    private ArrayList mCreatedMenus;
    private boolean mDestroyed;
    private boolean mExecutingActions;
    private boolean mHavePendingDeferredStart;
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    Fragment mPrimaryNav;
    private ActivityResultLauncher mRequestPermissions;
    private ActivityResultLauncher mStartActivityForResult;
    private ActivityResultLauncher mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private FragmentStrictMode.Policy mStrictModePolicy;
    private ArrayList mTmpAddedFragments;
    private ArrayList mTmpIsPop;
    private ArrayList mTmpRecords;
    private final ArrayList mPendingActions = new ArrayList();
    private final FragmentStore mFragmentStore = new FragmentStore();
    ArrayList mBackStack = new ArrayList();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    BackStackRecord mTransitioningOp = null;
    boolean mHandlingTransitioningOp = false;
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackCancelled() {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "handleOnBackCancelled. PREDICTIVE_BACK = " + FragmentManager.USE_PREDICTIVE_BACK + " fragment manager " + FragmentManager.this);
            }
            if (FragmentManager.USE_PREDICTIVE_BACK) {
                FragmentManager.this.cancelBackStackTransition();
            }
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "handleOnBackPressed. PREDICTIVE_BACK = " + FragmentManager.USE_PREDICTIVE_BACK + " fragment manager " + FragmentManager.this);
            }
            FragmentManager.this.handleOnBackPressed();
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackProgressed(BackEventCompat backEventCompat) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "handleOnBackProgressed. PREDICTIVE_BACK = " + FragmentManager.USE_PREDICTIVE_BACK + " fragment manager " + FragmentManager.this);
            }
            FragmentManager fragmentManager = FragmentManager.this;
            if (fragmentManager.mTransitioningOp != null) {
                Iterator it = fragmentManager.collectChangedControllers(new ArrayList(Collections.singletonList(FragmentManager.this.mTransitioningOp)), 0, 1).iterator();
                while (it.hasNext()) {
                    ((SpecialEffectsController) it.next()).processProgress(backEventCompat);
                }
                Iterator it2 = FragmentManager.this.mBackStackChangeListeners.iterator();
                if (it2.hasNext()) {
                    MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
                    throw null;
                }
            }
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackStarted(BackEventCompat backEventCompat) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "handleOnBackStarted. PREDICTIVE_BACK = " + FragmentManager.USE_PREDICTIVE_BACK + " fragment manager " + FragmentManager.this);
            }
            if (FragmentManager.USE_PREDICTIVE_BACK) {
                FragmentManager.this.endAnimatingAwayFragments();
                FragmentManager.this.prepareBackStackTransition();
            }
        }
    };
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private final Map mBackStackStates = Collections.synchronizedMap(new HashMap());
    private final Map mResults = Collections.synchronizedMap(new HashMap());
    private final Map mResultListeners = Collections.synchronizedMap(new HashMap());
    ArrayList mBackStackChangeListeners = new ArrayList();
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private final CopyOnWriteArrayList mOnAttachListeners = new CopyOnWriteArrayList();
    private final Consumer mOnConfigurationChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.$r8$lambda$yvqmBMGn9GAGgLS3kX3Pb1Mp5KA(this.f$0, (Configuration) obj);
        }
    };
    private final Consumer mOnTrimMemoryListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.$r8$lambda$7VI8nQONq1ttVaacOnJY0SOCn_A(this.f$0, (Integer) obj);
        }
    };
    private final Consumer mOnMultiWindowModeChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda2
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.$r8$lambda$sjgpDn2MJatSnY7YFGNxTaNF_8E(this.f$0, (MultiWindowModeChangedInfo) obj);
        }
    };
    private final Consumer mOnPictureInPictureModeChangedListener = new Consumer() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda3
        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            FragmentManager.m1973$r8$lambda$lO3Nl4juk4ObZbb6MTFkJMWEjY(this.f$0, (PictureInPictureModeChangedInfo) obj);
        }
    };
    private final MenuProvider mMenuProvider = new MenuProvider() { // from class: androidx.fragment.app.FragmentManager.2
        @Override // androidx.core.view.MenuProvider
        public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
            FragmentManager.this.dispatchCreateOptionsMenu(menu, menuInflater);
        }

        @Override // androidx.core.view.MenuProvider
        public void onMenuClosed(Menu menu) {
            FragmentManager.this.dispatchOptionsMenuClosed(menu);
        }

        @Override // androidx.core.view.MenuProvider
        public boolean onMenuItemSelected(MenuItem menuItem) {
            return FragmentManager.this.dispatchOptionsItemSelected(menuItem);
        }

        @Override // androidx.core.view.MenuProvider
        public void onPrepareMenu(Menu menu) {
            FragmentManager.this.dispatchPrepareOptionsMenu(menu);
        }
    };
    int mCurState = -1;
    private FragmentFactory mFragmentFactory = null;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
        @Override // androidx.fragment.app.FragmentFactory
        public Fragment instantiate(ClassLoader classLoader, String str) {
            FragmentManager.this.getHost();
            FragmentManager.this.getHost();
            throw null;
        }
    };
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() { // from class: androidx.fragment.app.FragmentManager.4
        @Override // androidx.fragment.app.SpecialEffectsControllerFactory
        public SpecialEffectsController createController(ViewGroup viewGroup) {
            return new DefaultSpecialEffectsController(viewGroup);
        }
    };
    ArrayDeque mLaunchedFragments = new ArrayDeque();
    private Runnable mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
        @Override // java.lang.Runnable
        public void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };

    public abstract class FragmentLifecycleCallbacks {
        public abstract void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle);

        public abstract void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle);

        public abstract void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment);

        public abstract void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment);

        public abstract void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle);

        public abstract void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment);

        public abstract void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle);

        public abstract void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment);

        public abstract void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment);

        public abstract void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle);

        public abstract void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment);
    }

    class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: androidx.fragment.app.FragmentManager.LaunchedFragmentInfo.1
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        };
        int mRequestCode;
        String mWho;

        LaunchedFragmentInfo(Parcel parcel) {
            this.mWho = parcel.readString();
            this.mRequestCode = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mWho);
            parcel.writeInt(this.mRequestCode);
        }
    }

    interface OpGenerator {
        boolean generateOps(ArrayList arrayList, ArrayList arrayList2);
    }

    class PopBackStackState implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
            Fragment fragment = FragmentManager.this.mPrimaryNav;
            if (fragment == null || this.mId >= 0 || this.mName != null || !fragment.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            }
            return false;
        }
    }

    class PrepareBackStackTransitionState implements OpGenerator {
        PrepareBackStackTransitionState() {
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
            boolean zPrepareBackStackState = FragmentManager.this.prepareBackStackState(arrayList, arrayList2);
            if (!FragmentManager.this.mBackStackChangeListeners.isEmpty() && arrayList.size() > 0) {
                ((Boolean) arrayList2.get(arrayList.size() - 1)).booleanValue();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    linkedHashSet.addAll(FragmentManager.this.fragmentsFromRecord((BackStackRecord) obj));
                }
                ArrayList arrayList3 = FragmentManager.this.mBackStackChangeListeners;
                int size2 = arrayList3.size();
                while (i < size2) {
                    Object obj2 = arrayList3.get(i);
                    i++;
                    MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(obj2);
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        throw null;
                    }
                }
            }
            return zPrepareBackStackState;
        }
    }

    public static /* synthetic */ void $r8$lambda$7VI8nQONq1ttVaacOnJY0SOCn_A(FragmentManager fragmentManager, Integer num) {
        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
            fragmentManager.dispatchLowMemory(false);
        }
    }

    public static /* synthetic */ void $r8$lambda$bPin5ccQodW2WrqGfSgyKuPsaCA(FragmentManager fragmentManager) {
        Iterator it = fragmentManager.mBackStackChangeListeners.iterator();
        if (it.hasNext()) {
            MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$lO3Nl4juk4ObZbb6MT-FkJMWEjY, reason: not valid java name */
    public static /* synthetic */ void m1973$r8$lambda$lO3Nl4juk4ObZbb6MTFkJMWEjY(FragmentManager fragmentManager, PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.isInPictureInPictureMode(), false);
        }
    }

    public static /* synthetic */ void $r8$lambda$sjgpDn2MJatSnY7YFGNxTaNF_8E(FragmentManager fragmentManager, MultiWindowModeChangedInfo multiWindowModeChangedInfo) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.isInMultiWindowMode(), false);
        }
    }

    public static /* synthetic */ void $r8$lambda$yvqmBMGn9GAGgLS3kX3Pb1Mp5KA(FragmentManager fragmentManager, Configuration configuration) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchConfigurationChanged(configuration, false);
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void clearBackStackStateViewModels() {
        throw null;
    }

    private Set collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        Iterator it = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
        while (it.hasNext()) {
            ViewGroup viewGroup = ((FragmentStateManager) it.next()).getFragment().mContainer;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, getSpecialEffectsControllerFactory()));
            }
        }
        return hashSet;
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(Fragment fragment) {
        if (fragment == null || !fragment.equals(findActiveFragment(fragment.mWho))) {
            return;
        }
        fragment.performPrimaryNavigationFragmentChanged();
    }

    private void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.dispatchStateChange(i);
            moveToState(i, false);
            Iterator it = collectAllSpecialEffectsController().iterator();
            while (it.hasNext()) {
                ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endAnimatingAwayFragments() {
        Iterator it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (!this.mDestroyed) {
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        }
        throw new IllegalStateException("FragmentManager has been destroyed");
    }

    private static void executeOps(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i);
            if (((Boolean) arrayList2.get(i)).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1);
                backStackRecord.executePopOps();
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            i++;
        }
    }

    private void executeOpsTogether(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        boolean z = ((BackStackRecord) arrayList.get(i)).mReorderingAllowed;
        ArrayList arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList3.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z2 = false;
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i3);
            primaryNavigationFragment = !((Boolean) arrayList2.get(i3)).booleanValue() ? backStackRecord.expandOps(this.mTmpAddedFragments, primaryNavigationFragment) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, primaryNavigationFragment);
            z2 = z2 || backStackRecord.mAddToBackStack;
        }
        this.mTmpAddedFragments.clear();
        if (!z && this.mCurState >= 1) {
            for (int i4 = i; i4 < i2; i4++) {
                ArrayList arrayList4 = ((BackStackRecord) arrayList.get(i4)).mOps;
                int size = arrayList4.size();
                int i5 = 0;
                while (i5 < size) {
                    Object obj = arrayList4.get(i5);
                    i5++;
                    Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                    if (fragment != null && fragment.mFragmentManager != null) {
                        this.mFragmentStore.makeActive(createOrGetFragmentStateManager(fragment));
                    }
                }
            }
        }
        executeOps(arrayList, arrayList2, i, i2);
        boolean zBooleanValue = ((Boolean) arrayList2.get(i2 - 1)).booleanValue();
        if (z2 && !this.mBackStackChangeListeners.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int size2 = arrayList.size();
            int i6 = 0;
            while (i6 < size2) {
                Object obj2 = arrayList.get(i6);
                i6++;
                linkedHashSet.addAll(fragmentsFromRecord((BackStackRecord) obj2));
            }
            if (this.mTransitioningOp == null) {
                ArrayList arrayList5 = this.mBackStackChangeListeners;
                int size3 = arrayList5.size();
                int i7 = 0;
                while (i7 < size3) {
                    Object obj3 = arrayList5.get(i7);
                    i7++;
                    MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(obj3);
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        throw null;
                    }
                }
                ArrayList arrayList6 = this.mBackStackChangeListeners;
                int size4 = arrayList6.size();
                int i8 = 0;
                while (i8 < size4) {
                    Object obj4 = arrayList6.get(i8);
                    i8++;
                    MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(obj4);
                    Iterator it2 = linkedHashSet.iterator();
                    if (it2.hasNext()) {
                        throw null;
                    }
                }
            }
        }
        for (int i9 = i; i9 < i2; i9++) {
            BackStackRecord backStackRecord2 = (BackStackRecord) arrayList.get(i9);
            if (zBooleanValue) {
                for (int size5 = backStackRecord2.mOps.size() - 1; size5 >= 0; size5--) {
                    Fragment fragment2 = ((FragmentTransaction.Op) backStackRecord2.mOps.get(size5)).mFragment;
                    if (fragment2 != null) {
                        createOrGetFragmentStateManager(fragment2).moveToExpectedState();
                    }
                }
            } else {
                ArrayList arrayList7 = backStackRecord2.mOps;
                int size6 = arrayList7.size();
                int i10 = 0;
                while (i10 < size6) {
                    Object obj5 = arrayList7.get(i10);
                    i10++;
                    Fragment fragment3 = ((FragmentTransaction.Op) obj5).mFragment;
                    if (fragment3 != null) {
                        createOrGetFragmentStateManager(fragment3).moveToExpectedState();
                    }
                }
            }
        }
        moveToState(this.mCurState, true);
        for (SpecialEffectsController specialEffectsController : collectChangedControllers(arrayList, i, i2)) {
            specialEffectsController.updateOperationDirection(zBooleanValue);
            specialEffectsController.markPostponedState();
            specialEffectsController.executePendingOperations();
        }
        while (i < i2) {
            BackStackRecord backStackRecord3 = (BackStackRecord) arrayList.get(i);
            if (((Boolean) arrayList2.get(i)).booleanValue() && backStackRecord3.mIndex >= 0) {
                backStackRecord3.mIndex = -1;
            }
            backStackRecord3.runOnCommitRunnables();
            i++;
        }
        if (z2) {
            reportBackStackChanged();
        }
    }

    private int findBackStackIndex(String str, int i, boolean z) {
        if (this.mBackStack.isEmpty()) {
            return -1;
        }
        if (str == null && i < 0) {
            if (z) {
                return 0;
            }
            return this.mBackStack.size() - 1;
        }
        int size = this.mBackStack.size() - 1;
        while (size >= 0) {
            BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(size);
            if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                break;
            }
            size--;
        }
        if (size < 0) {
            return size;
        }
        if (!z) {
            if (size == this.mBackStack.size() - 1) {
                return -1;
            }
            return size + 1;
        }
        while (size > 0) {
            BackStackRecord backStackRecord2 = (BackStackRecord) this.mBackStack.get(size - 1);
            if ((str == null || !str.equals(backStackRecord2.getName())) && (i < 0 || i != backStackRecord2.mIndex)) {
                break;
            }
            size--;
        }
        return size;
    }

    static Fragment findViewFragment(View view) {
        while (view != null) {
            Fragment viewFragment = getViewFragment(view);
            if (viewFragment != null) {
                return viewFragment;
            }
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        Iterator it = collectAllSpecialEffectsController().iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forcePostponedExecutePendingOperations();
        }
    }

    private boolean generateOpsForPendingActions(ArrayList arrayList, ArrayList arrayList2) {
        synchronized (this.mPendingActions) {
            if (!this.mPendingActions.isEmpty()) {
                int size = this.mPendingActions.size();
                for (int i = 0; i < size; i++) {
                    ((OpGenerator) this.mPendingActions.get(i)).generateOps(arrayList, arrayList2);
                }
                this.mPendingActions.clear();
                throw null;
            }
        }
        return false;
    }

    private FragmentManagerViewModel getChildNonConfig(Fragment fragment) {
        return this.mNonConfig.getChildNonConfig(fragment);
    }

    private ViewGroup getFragmentContainer(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId > 0 && this.mContainer.onHasView()) {
            View viewOnFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
            if (viewOnFindViewById instanceof ViewGroup) {
                return (ViewGroup) viewOnFindViewById;
            }
        }
        return null;
    }

    static Fragment getViewFragment(View view) {
        Object tag = view.getTag(R$id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    public static boolean isLoggingEnabled(int i) {
        return DEBUG || Log.isLoggable("FragmentManager", i);
    }

    private boolean isMenuAvailable(Fragment fragment) {
        return (fragment.mHasMenu && fragment.mMenuVisible) || fragment.mChildFragmentManager.checkForMenus();
    }

    private boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.isAdded() && this.mParent.getParentFragmentManager().isParentAdded();
    }

    private boolean popBackStackImmediate(String str, int i, int i2) {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && i < 0 && str == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean zPopBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
        if (zPopBackStackState) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return zPopBackStackState;
    }

    private void removeRedundantOperationsAndExecute(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!((BackStackRecord) arrayList.get(i)).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (((Boolean) arrayList2.get(i)).booleanValue()) {
                    while (i2 < size && ((Boolean) arrayList2.get(i2)).booleanValue() && !((BackStackRecord) arrayList.get(i2)).mReorderingAllowed) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners.size() <= 0) {
            return;
        }
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mBackStackChangeListeners.get(0));
        throw null;
    }

    static int reverseTransit(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i == 8194) {
            return 4097;
        }
        if (i == 8197) {
            return 4100;
        }
        if (i != 4099) {
            return i != 4100 ? 0 : 8197;
        }
        return 4099;
    }

    private void setVisibleRemovingFragment(Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || fragment.getEnterAnim() + fragment.getExitAnim() + fragment.getPopEnterAnim() + fragment.getPopExitAnim() <= 0) {
            return;
        }
        int i = R$id.visible_removing_fragment_view_tag;
        if (fragmentContainer.getTag(i) == null) {
            fragmentContainer.setTag(i, fragment);
        }
        ((Fragment) fragmentContainer.getTag(i)).setPopDirection(fragment.getPopDirection());
    }

    private void startPendingDeferredFragments() {
        Iterator it = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
        while (it.hasNext()) {
            performPendingDeferredStart((FragmentStateManager) it.next());
        }
    }

    private void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            try {
                if (!this.mPendingActions.isEmpty()) {
                    this.mOnBackPressedCallback.setEnabled(true);
                    if (isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "FragmentManager " + this + " enabling OnBackPressedCallback, caused by non-empty pending actions");
                    }
                    return;
                }
                boolean z = getBackStackEntryCount() > 0 && isPrimaryNavigation(this.mParent);
                if (isLoggingEnabled(3)) {
                    Log.d("FragmentManager", "OnBackPressedCallback for FragmentManager " + this + " enabled state is " + z);
                }
                this.mOnBackPressedCallback.setEnabled(z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        this.mBackStack.add(backStackRecord);
    }

    FragmentStateManager addFragment(Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        FragmentStateManager fragmentStateManagerCreateOrGetFragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        this.mFragmentStore.makeActive(fragmentStateManagerCreateOrGetFragmentStateManager);
        if (!fragment.mDetached) {
            this.mFragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return fragmentStateManagerCreateOrGetFragmentStateManager;
    }

    public void addFragmentOnAttachListener(FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.add(fragmentOnAttachListener);
    }

    int allocBackStackIndex() {
        return this.mBackStackIndex.getAndIncrement();
    }

    void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, final Fragment fragment) {
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        if (fragment != null) {
            addFragmentOnAttachListener(new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.7
            });
        }
        if (this.mParent != null) {
            updateOnBackPressedCallbackEnabled();
        }
        if (fragment != null) {
            this.mNonConfig = fragment.mFragmentManager.getChildNonConfig(fragment);
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        this.mNonConfig.setIsStateSaved(isStateSaved());
        this.mFragmentStore.setNonConfig(this.mNonConfig);
    }

    void attachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (fragment.mAdded) {
                return;
            }
            this.mFragmentStore.addFragment(fragment);
            if (isLoggingEnabled(2)) {
                Log.v("FragmentManager", "add from attach: " + fragment);
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    void cancelBackStackTransition() {
        if (isLoggingEnabled(3)) {
            Log.d("FragmentManager", "cancelBackStackTransition for transition " + this.mTransitioningOp);
        }
        BackStackRecord backStackRecord = this.mTransitioningOp;
        if (backStackRecord != null) {
            backStackRecord.mCommitted = false;
            backStackRecord.collapseOps();
            this.mTransitioningOp.runOnCommitInternal(true, new Runnable() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    FragmentManager.$r8$lambda$bPin5ccQodW2WrqGfSgyKuPsaCA(this.f$0);
                }
            });
            this.mTransitioningOp.commit();
            this.mHandlingTransitioningOp = true;
            executePendingTransactions();
            this.mHandlingTransitioningOp = false;
            this.mTransitioningOp = null;
        }
    }

    boolean checkForMenus() {
        boolean zIsMenuAvailable = false;
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                zIsMenuAvailable = isMenuAvailable(fragment);
            }
            if (zIsMenuAvailable) {
                return true;
            }
        }
        return false;
    }

    Set collectChangedControllers(ArrayList arrayList, int i, int i2) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            ArrayList arrayList2 = ((BackStackRecord) arrayList.get(i)).mOps;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                if (fragment != null && (viewGroup = fragment.mContainer) != null) {
                    hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, this));
                }
            }
            i++;
        }
        return hashSet;
    }

    FragmentStateManager createOrGetFragmentStateManager(Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager != null) {
            return fragmentStateManager;
        }
        new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
        throw null;
    }

    void detachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (fragment.mDetached) {
            return;
        }
        fragment.mDetached = true;
        if (fragment.mAdded) {
            if (isLoggingEnabled(2)) {
                Log.v("FragmentManager", "remove from detach: " + fragment);
            }
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(4);
    }

    void dispatchConfigurationChanged(Configuration configuration, boolean z) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration);
                if (z) {
                    fragment.mChildFragmentManager.dispatchConfigurationChanged(configuration, true);
                }
            }
        }
    }

    void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(1);
    }

    boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList arrayList = null;
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment) && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(fragment);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment2 = (Fragment) this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions(true);
        endAnimatingAwayFragments();
        clearBackStackStateViewModels();
        dispatchStateChange(-1);
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    void dispatchLowMemory(boolean z) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performLowMemory();
                if (z) {
                    fragment.mChildFragmentManager.dispatchLowMemory(true);
                }
            }
        }
    }

    void dispatchMultiWindowModeChanged(boolean z, boolean z2) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z);
                if (z2) {
                    fragment.mChildFragmentManager.dispatchMultiWindowModeChanged(z, true);
                }
            }
        }
    }

    void dispatchOnHiddenChanged() {
        for (Fragment fragment : this.mFragmentStore.getActiveFragments()) {
            if (fragment != null) {
                fragment.onHiddenChanged(fragment.isHidden());
                fragment.mChildFragmentManager.dispatchOnHiddenChanged();
            }
        }
    }

    boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performOptionsMenuClosed(menu);
            }
        }
    }

    void dispatchPause() {
        dispatchStateChange(5);
    }

    void dispatchPictureInPictureModeChanged(boolean z, boolean z2) {
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z);
                if (z2) {
                    fragment.mChildFragmentManager.dispatchPictureInPictureModeChanged(z, true);
                }
            }
        }
    }

    boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment) && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    void dispatchPrimaryNavigationFragmentChanged() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(7);
    }

    void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(5);
    }

    void dispatchStop() {
        this.mStopped = true;
        this.mNonConfig.setIsStateSaved(true);
        dispatchStateChange(4);
    }

    void dispatchViewCreated() {
        dispatchStateChange(2);
    }

    void enqueueAction(OpGenerator opGenerator, boolean z) {
        if (!z) {
            if (!this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        synchronized (this.mPendingActions) {
            try {
                if (!z) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    boolean execPendingActions(boolean z) {
        BackStackRecord backStackRecord;
        ensureExecReady(z);
        boolean z2 = false;
        if (!this.mHandlingTransitioningOp && (backStackRecord = this.mTransitioningOp) != null) {
            backStackRecord.mCommitted = false;
            backStackRecord.collapseOps();
            if (isLoggingEnabled(3)) {
                Log.d("FragmentManager", "Reversing mTransitioningOp " + this.mTransitioningOp + " as part of execPendingActions for actions " + this.mPendingActions);
            }
            this.mTransitioningOp.commitInternal(false, false);
            this.mPendingActions.add(0, this.mTransitioningOp);
            ArrayList arrayList = this.mTransitioningOp.mOps;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                if (fragment != null) {
                    fragment.mTransitioning = false;
                }
            }
            this.mTransitioningOp = null;
        }
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            z2 = true;
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return z2;
    }

    void execSingleAction(OpGenerator opGenerator, boolean z) {
        if (z) {
            return;
        }
        ensureExecReady(z);
        BackStackRecord backStackRecord = this.mTransitioningOp;
        boolean z2 = false;
        if (backStackRecord != null) {
            backStackRecord.mCommitted = false;
            backStackRecord.collapseOps();
            if (isLoggingEnabled(3)) {
                Log.d("FragmentManager", "Reversing mTransitioningOp " + this.mTransitioningOp + " as part of execSingleAction for action " + opGenerator);
            }
            this.mTransitioningOp.commitInternal(false, false);
            boolean zGenerateOps = this.mTransitioningOp.generateOps(this.mTmpRecords, this.mTmpIsPop);
            ArrayList arrayList = this.mTransitioningOp.mOps;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                if (fragment != null) {
                    fragment.mTransitioning = false;
                }
            }
            this.mTransitioningOp = null;
            z2 = zGenerateOps;
        }
        boolean zGenerateOps2 = opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop);
        if (z2 || zGenerateOps2) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.burpActive();
    }

    public boolean executePendingTransactions() {
        boolean zExecPendingActions = execPendingActions(true);
        forcePostponedTransactions();
        return zExecPendingActions;
    }

    Fragment findActiveFragment(String str) {
        return this.mFragmentStore.findActiveFragment(str);
    }

    public Fragment findFragmentById(int i) {
        return this.mFragmentStore.findFragmentById(i);
    }

    public Fragment findFragmentByTag(String str) {
        return this.mFragmentStore.findFragmentByTag(str);
    }

    Set fragmentsFromRecord(BackStackRecord backStackRecord) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < backStackRecord.mOps.size(); i++) {
            Fragment fragment = ((FragmentTransaction.Op) backStackRecord.mOps.get(i)).mFragment;
            if (fragment != null && backStackRecord.mAddToBackStack) {
                hashSet.add(fragment);
            }
        }
        return hashSet;
    }

    public int getBackStackEntryCount() {
        return this.mBackStack.size() + (this.mTransitioningOp != null ? 1 : 0);
    }

    FragmentContainer getContainer() {
        return this.mContainer;
    }

    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getFragmentFactory() : this.mHostFragmentFactory;
    }

    public FragmentHostCallback getHost() {
        return null;
    }

    FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    Fragment getParent() {
        return this.mParent;
    }

    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        SpecialEffectsControllerFactory specialEffectsControllerFactory = this.mSpecialEffectsControllerFactory;
        if (specialEffectsControllerFactory != null) {
            return specialEffectsControllerFactory;
        }
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getSpecialEffectsControllerFactory() : this.mDefaultSpecialEffectsControllerFactory;
    }

    public FragmentStrictMode.Policy getStrictModePolicy() {
        return this.mStrictModePolicy;
    }

    ViewModelStore getViewModelStore(Fragment fragment) {
        return this.mNonConfig.getViewModelStore(fragment);
    }

    void handleOnBackPressed() {
        this.mHandlingTransitioningOp = true;
        execPendingActions(true);
        int i = 0;
        this.mHandlingTransitioningOp = false;
        if (!USE_PREDICTIVE_BACK || this.mTransitioningOp == null) {
            if (this.mOnBackPressedCallback.isEnabled()) {
                if (isLoggingEnabled(3)) {
                    Log.d("FragmentManager", "Calling popBackStackImmediate via onBackPressed callback");
                }
                popBackStackImmediate();
                return;
            } else {
                if (isLoggingEnabled(3)) {
                    Log.d("FragmentManager", "Calling onBackPressed via onBackPressed callback");
                }
                this.mOnBackPressedDispatcher.onBackPressed();
                return;
            }
        }
        if (!this.mBackStackChangeListeners.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(fragmentsFromRecord(this.mTransitioningOp));
            ArrayList arrayList = this.mBackStackChangeListeners;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    throw null;
                }
            }
        }
        ArrayList arrayList2 = this.mTransitioningOp.mOps;
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            Fragment fragment = ((FragmentTransaction.Op) obj2).mFragment;
            if (fragment != null) {
                fragment.mTransitioning = false;
            }
        }
        Iterator it2 = collectChangedControllers(new ArrayList(Collections.singletonList(this.mTransitioningOp)), 0, 1).iterator();
        while (it2.hasNext()) {
            ((SpecialEffectsController) it2.next()).completeBack();
        }
        ArrayList arrayList3 = this.mTransitioningOp.mOps;
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj3 = arrayList3.get(i);
            i++;
            Fragment fragment2 = ((FragmentTransaction.Op) obj3).mFragment;
            if (fragment2 != null && fragment2.mContainer == null) {
                createOrGetFragmentStateManager(fragment2).moveToExpectedState();
            }
        }
        this.mTransitioningOp = null;
        updateOnBackPressedCallbackEnabled();
        if (isLoggingEnabled(3)) {
            Log.d("FragmentManager", "Op is being set to null");
            Log.d("FragmentManager", "OnBackPressedCallback enabled=" + this.mOnBackPressedCallback.isEnabled() + " for  FragmentManager " + this);
        }
    }

    void hideFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (fragment.mHidden) {
            return;
        }
        fragment.mHidden = true;
        fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        setVisibleRemovingFragment(fragment);
    }

    void invalidateMenuForFragment(Fragment fragment) {
        if (fragment.mAdded && isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isParentHidden(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        return fragment.isHidden();
    }

    boolean isParentMenuVisible(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.isMenuVisible();
    }

    boolean isPrimaryNavigation(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.getPrimaryNavigationFragment()) && isPrimaryNavigation(fragmentManager.mParent);
    }

    boolean isStateAtLeast(int i) {
        return this.mCurState >= i;
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    void moveToState(int i, boolean z) {
        if (i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            this.mFragmentStore.moveToExpectedState();
            startPendingDeferredFragments();
        }
    }

    void noteStateNotSaved() {
    }

    public final void onContainerAvailable(FragmentContainerView fragmentContainerView) {
        View view;
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.getActiveFragmentStateManagers()) {
            Fragment fragment = fragmentStateManager.getFragment();
            if (fragment.mContainerId == fragmentContainerView.getId() && (view = fragment.mView) != null && view.getParent() == null) {
                fragment.mContainer = fragmentContainerView;
                fragmentStateManager.addViewToContainer();
                fragmentStateManager.moveToExpectedState();
            }
        }
    }

    void performPendingDeferredStart(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
            } else {
                fragment.mDeferStart = false;
                fragmentStateManager.moveToExpectedState();
            }
        }
    }

    void popBackStack(int i, int i2, boolean z) {
        if (i >= 0) {
            enqueueAction(new PopBackStackState(null, i, i2), z);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public boolean popBackStackImmediate() {
        return popBackStackImmediate(null, -1, 0);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        if (i >= 0) {
            return popBackStackImmediate(null, i, i2);
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    boolean popBackStackState(ArrayList arrayList, ArrayList arrayList2, String str, int i, int i2) {
        int iFindBackStackIndex = findBackStackIndex(str, i, (i2 & 1) != 0);
        if (iFindBackStackIndex < 0) {
            return false;
        }
        for (int size = this.mBackStack.size() - 1; size >= iFindBackStackIndex; size--) {
            arrayList.add((BackStackRecord) this.mBackStack.remove(size));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    boolean prepareBackStackState(ArrayList arrayList, ArrayList arrayList2) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "FragmentManager has the following pending actions inside of prepareBackStackState: " + this.mPendingActions);
        }
        int i = 0;
        if (this.mBackStack.isEmpty()) {
            Log.i("FragmentManager", "Ignoring call to start back stack pop because the back stack is empty.");
            return false;
        }
        ArrayList arrayList3 = this.mBackStack;
        BackStackRecord backStackRecord = (BackStackRecord) arrayList3.get(arrayList3.size() - 1);
        this.mTransitioningOp = backStackRecord;
        ArrayList arrayList4 = backStackRecord.mOps;
        int size = arrayList4.size();
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
            if (fragment != null) {
                fragment.mTransitioning = true;
            }
        }
        return popBackStackState(arrayList, arrayList2, null, -1, 0);
    }

    void prepareBackStackTransition() {
        enqueueAction(new PrepareBackStackTransitionState(), false);
    }

    void removeFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean zIsInBackStack = fragment.isInBackStack();
        if (fragment.mDetached && zIsInBackStack) {
            return;
        }
        this.mFragmentStore.removeFragment(fragment);
        if (isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mRemoving = true;
        setVisibleRemovingFragment(fragment);
    }

    void restoreSaveStateInternal(Parcelable parcelable) {
        if (parcelable == null) {
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        for (String str : bundle.keySet()) {
            if (str.startsWith("result_") && bundle.getBundle(str) != null) {
                throw null;
            }
        }
        HashMap map = new HashMap();
        for (String str2 : bundle.keySet()) {
            if (str2.startsWith("fragment_") && bundle.getBundle(str2) != null) {
                throw null;
            }
        }
        this.mFragmentStore.restoreSaveState(map);
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        this.mFragmentStore.resetActiveFragments();
        ArrayList arrayList = fragmentManagerState.mActive;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Bundle savedState = this.mFragmentStore.setSavedState((String) obj, null);
            if (savedState != null) {
                Fragment fragmentFindRetainedFragmentByWho = this.mNonConfig.findRetainedFragmentByWho(((FragmentState) savedState.getParcelable("state")).mWho);
                fragmentFindRetainedFragmentByWho.getClass();
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + fragmentFindRetainedFragmentByWho);
                }
                Fragment fragment = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragmentFindRetainedFragmentByWho, savedState).getFragment();
                fragment.mSavedFragmentState = savedState;
                fragment.mFragmentManager = this;
                if (!isLoggingEnabled(2)) {
                    throw null;
                }
                Log.v("FragmentManager", "restoreSaveState: active (" + fragment.mWho + "): " + fragment);
                throw null;
            }
        }
        for (Fragment fragment2 : this.mNonConfig.getRetainedFragments()) {
            if (!this.mFragmentStore.containsActiveFragment(fragment2.mWho)) {
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Discarding retained Fragment " + fragment2 + " that was not found in the set of active Fragments " + fragmentManagerState.mActive);
                }
                this.mNonConfig.removeRetainedFragment(fragment2);
                fragment2.mFragmentManager = this;
                FragmentStateManager fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment2);
                fragmentStateManager.setFragmentManagerState(1);
                fragmentStateManager.moveToExpectedState();
                fragment2.mRemoving = true;
                fragmentStateManager.moveToExpectedState();
            }
        }
        this.mFragmentStore.restoreAddedFragments(fragmentManagerState.mAdded);
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            int i2 = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                if (i2 >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecord backStackRecordInstantiate = backStackRecordStateArr[i2].instantiate(this);
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + i2 + " (index " + backStackRecordInstantiate.mIndex + "): " + backStackRecordInstantiate);
                    PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                    backStackRecordInstantiate.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add(backStackRecordInstantiate);
                i2++;
            }
        } else {
            this.mBackStack = new ArrayList();
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        String str3 = fragmentManagerState.mPrimaryNavActiveWho;
        if (str3 != null) {
            Fragment fragmentFindActiveFragment = findActiveFragment(str3);
            this.mPrimaryNav = fragmentFindActiveFragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragmentFindActiveFragment);
        }
        ArrayList arrayList2 = fragmentManagerState.mBackStackStateKeys;
        if (arrayList2 != null) {
            for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                this.mBackStackStates.put((String) arrayList2.get(i3), (BackStackState) fragmentManagerState.mBackStackStates.get(i3));
            }
        }
        this.mLaunchedFragments = new ArrayDeque(fragmentManagerState.mLaunchedFragments);
    }

    Bundle saveAllStateInternal() {
        BackStackRecordState[] backStackRecordStateArr;
        Bundle bundle = new Bundle();
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.setIsStateSaved(true);
        ArrayList arrayListSaveActiveFragments = this.mFragmentStore.saveActiveFragments();
        HashMap allSavedState = this.mFragmentStore.getAllSavedState();
        if (!allSavedState.isEmpty()) {
            ArrayList arrayListSaveAddedFragments = this.mFragmentStore.saveAddedFragments();
            int size = this.mBackStack.size();
            if (size > 0) {
                backStackRecordStateArr = new BackStackRecordState[size];
                for (int i = 0; i < size; i++) {
                    backStackRecordStateArr[i] = new BackStackRecordState((BackStackRecord) this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
                    }
                }
            } else {
                backStackRecordStateArr = null;
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = arrayListSaveActiveFragments;
            fragmentManagerState.mAdded = arrayListSaveAddedFragments;
            fragmentManagerState.mBackStack = backStackRecordStateArr;
            fragmentManagerState.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment = this.mPrimaryNav;
            if (fragment != null) {
                fragmentManagerState.mPrimaryNavActiveWho = fragment.mWho;
            }
            fragmentManagerState.mBackStackStateKeys.addAll(this.mBackStackStates.keySet());
            fragmentManagerState.mBackStackStates.addAll(this.mBackStackStates.values());
            fragmentManagerState.mLaunchedFragments = new ArrayList(this.mLaunchedFragments);
            bundle.putParcelable("state", fragmentManagerState);
            for (String str : this.mResults.keySet()) {
                bundle.putBundle("result_" + str, (Bundle) this.mResults.get(str));
            }
            for (String str2 : allSavedState.keySet()) {
                bundle.putBundle("fragment_" + str2, (Bundle) allSavedState.get(str2));
            }
        } else if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "saveAllState: no fragments!");
            return bundle;
        }
        return bundle;
    }

    void setExitAnimationOrder(Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).setDrawDisappearingViewsLast(!z);
    }

    void setMaxLifecycle(Fragment fragment, Lifecycle.State state) {
        if (fragment.equals(findActiveFragment(fragment.mWho))) {
            fragment.mMaxState = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment == null || fragment.equals(findActiveFragment(fragment.mWho))) {
            Fragment fragment2 = this.mPrimaryNav;
            this.mPrimaryNav = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    void showFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            sb.append("null");
        }
        sb.append("}}");
        return sb.toString();
    }
}
