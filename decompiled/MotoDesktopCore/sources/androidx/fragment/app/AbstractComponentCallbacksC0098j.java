package androidx.fragment.app;

import X.w0;
import android.animation.Animator;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import l.AbstractC0149c;

/* JADX INFO: renamed from: androidx.fragment.app.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractComponentCallbacksC0098j implements ComponentCallbacks, View.OnCreateContextMenuListener, androidx.lifecycle.d, androidx.lifecycle.k {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    boolean mAdded;
    C0094f mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    z mChildFragmentManager;
    A mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    z mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    AbstractC0102n mHost;
    boolean mInLayout;
    View mInnerView;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    LayoutInflater mLayoutInflater;
    AbstractComponentCallbacksC0098j mParentFragment;
    boolean mPerformedCreateView;
    float mPostponedAlpha;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    Boolean mSavedUserVisibleHint;
    SparseArray<Parcelable> mSavedViewState;
    String mTag;
    AbstractComponentCallbacksC0098j mTarget;
    int mTargetRequestCode;
    View mView;
    androidx.lifecycle.d mViewLifecycleOwner;
    androidx.lifecycle.f mViewLifecycleRegistry;
    androidx.lifecycle.j mViewModelStore;
    String mWho;
    private static final h.k sClassMap = new h.k();
    static final Object USE_DEFAULT_TRANSITION = new Object();
    int mState = 0;
    int mIndex = -1;
    int mTargetIndex = -1;
    boolean mMenuVisible = true;
    boolean mUserVisibleHint = true;
    androidx.lifecycle.f mLifecycleRegistry = new androidx.lifecycle.f(this);
    androidx.lifecycle.h mViewLifecycleOwnerLiveData = new androidx.lifecycle.h();

    public static AbstractComponentCallbacksC0098j instantiate(Context context, String str) {
        return instantiate(context, str, null);
    }

    public static AbstractComponentCallbacksC0098j instantiate(Context context, String str, Bundle bundle) {
        try {
            h.k kVar = sClassMap;
            Class<?> clsLoadClass = (Class) kVar.get(str);
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str);
                kVar.put(str, clsLoadClass);
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) clsLoadClass.getConstructor(null).newInstance(null);
            if (bundle != null) {
                bundle.setClassLoader(abstractComponentCallbacksC0098j.getClass().getClassLoader());
                abstractComponentCallbacksC0098j.setArguments(bundle);
            }
            return abstractComponentCallbacksC0098j;
        } catch (ClassNotFoundException e2) {
            throw new C0095g("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (IllegalAccessException e3) {
            throw new C0095g("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        } catch (InstantiationException e4) {
            throw new C0095g("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e4);
        } catch (NoSuchMethodException e5) {
            throw new C0095g("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e5);
        } catch (InvocationTargetException e6) {
            throw new C0095g("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e6);
        }
    }

    public static boolean isSupportFragmentClass(Context context, String str) {
        try {
            h.k kVar = sClassMap;
            Class<?> clsLoadClass = (Class) kVar.get(str);
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str);
                kVar.put(str, clsLoadClass);
            }
            return AbstractComponentCallbacksC0098j.class.isAssignableFrom(clsLoadClass);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public final C0094f a() {
        if (this.mAnimationInfo == null) {
            C0094f c0094f = new C0094f();
            c0094f.f1623g = null;
            Object obj = USE_DEFAULT_TRANSITION;
            c0094f.f1624h = obj;
            c0094f.f1625i = null;
            c0094f.f1626j = obj;
            c0094f.f1627k = null;
            c0094f.f1628l = obj;
            this.mAnimationInfo = c0094f;
        }
        return this.mAnimationInfo;
    }

    public void callStartTransitionListener() {
        C0094f c0094f = this.mAnimationInfo;
        InterfaceC0096h interfaceC0096h = null;
        if (c0094f != null) {
            c0094f.f1631o = false;
            InterfaceC0096h interfaceC0096h2 = c0094f.f1632p;
            c0094f.f1632p = null;
            interfaceC0096h = interfaceC0096h2;
        }
        if (interfaceC0096h != null) {
            y yVar = (y) interfaceC0096h;
            int i2 = yVar.f1665c - 1;
            yVar.f1665c = i2;
            if (i2 != 0) {
                return;
            }
            yVar.f1664b.f1596a.b0();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (getNextAnim() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.mView);
        }
        if (this.mInnerView != null) {
            printWriter.print(str);
            printWriter.print("mInnerView=");
            printWriter.println(this.mView);
        }
        if (getAnimatingAway() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(getAnimatingAway());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(getStateAfterAnimating());
        }
        if (getContext() != null) {
            androidx.lifecycle.j viewModelStore = getViewModelStore();
            String canonicalName = A.b.class.getCanonicalName();
            if (canonicalName == null) {
                throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
            }
            String strConcat = "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName);
            A.b bVar = (A.b) viewModelStore.f1716a.get(strConcat);
            if (!A.b.class.isInstance(bVar)) {
                bVar = new A.b();
                A.b bVar2 = (A.b) viewModelStore.f1716a.put(strConcat, bVar);
                if (bVar2 != null) {
                    bVar2.a();
                }
            }
            h.l lVar = bVar.f0a;
            if (lVar.f() > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                if (lVar.f() > 0) {
                    w0.c(lVar.g(0));
                    printWriter.print(str);
                    printWriter.print("  #");
                    if (lVar.f2613a) {
                        lVar.c();
                    }
                    printWriter.print(lVar.f2614b[0]);
                    printWriter.print(": ");
                    throw null;
                }
            }
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(str);
            printWriter.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.D(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public AbstractComponentCallbacksC0098j findFragmentByWho(String str) {
        if (str.equals(this.mWho)) {
            return this;
        }
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            return zVar.K(str);
        }
        return null;
    }

    public final FragmentActivity getActivity() {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            return null;
        }
        return (FragmentActivity) abstractC0102n.f1638b;
    }

    public boolean getAllowEnterTransitionOverlap() {
        Boolean bool;
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null || (bool = c0094f.f1630n) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public boolean getAllowReturnTransitionOverlap() {
        Boolean bool;
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null || (bool = c0094f.f1629m) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public View getAnimatingAway() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        return c0094f.f1617a;
    }

    public Animator getAnimator() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        return c0094f.f1618b;
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public final AbstractC0103o getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            int i2 = this.mState;
            if (i2 >= 4) {
                z zVar = this.mChildFragmentManager;
                zVar.f1687q = false;
                zVar.r = false;
                zVar.C(4);
            } else if (i2 >= 3) {
                z zVar2 = this.mChildFragmentManager;
                zVar2.f1687q = false;
                zVar2.r = false;
                zVar2.C(3);
            } else if (i2 >= 2) {
                z zVar3 = this.mChildFragmentManager;
                zVar3.f1687q = false;
                zVar3.r = false;
                zVar3.C(2);
            } else if (i2 >= 1) {
                z zVar4 = this.mChildFragmentManager;
                zVar4.f1687q = false;
                zVar4.r = false;
                zVar4.C(1);
            }
        }
        return this.mChildFragmentManager;
    }

    public Context getContext() {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            return null;
        }
        return abstractC0102n.f1639c;
    }

    public Object getEnterTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        return c0094f.f1623g;
    }

    public AbstractC0149c getEnterTransitionCallback() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        c0094f.getClass();
        return null;
    }

    public Object getExitTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        return c0094f.f1625i;
    }

    public AbstractC0149c getExitTransitionCallback() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        c0094f.getClass();
        return null;
    }

    public final AbstractC0103o getFragmentManager() {
        return this.mFragmentManager;
    }

    public final Object getHost() {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            return null;
        }
        return ((C0099k) abstractC0102n).f1634f;
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = this.mLayoutInflater;
        return layoutInflater == null ? performGetLayoutInflater(null) : layoutInflater;
    }

    @Deprecated
    public LayoutInflater getLayoutInflater(Bundle bundle) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        FragmentActivity fragmentActivity = ((C0099k) abstractC0102n).f1634f;
        LayoutInflater layoutInflaterCloneInContext = fragmentActivity.getLayoutInflater().cloneInContext(fragmentActivity);
        getChildFragmentManager();
        z zVar = this.mChildFragmentManager;
        zVar.getClass();
        layoutInflaterCloneInContext.setFactory2(zVar);
        return layoutInflaterCloneInContext;
    }

    @Override // androidx.lifecycle.d
    public androidx.lifecycle.c getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Deprecated
    public A.a getLoaderManager() {
        return new A.c(this, getViewModelStore());
    }

    public int getNextAnim() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return 0;
        }
        return c0094f.f1620d;
    }

    public int getNextTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return 0;
        }
        return c0094f.f1621e;
    }

    public int getNextTransitionStyle() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return 0;
        }
        return c0094f.f1622f;
    }

    public final AbstractComponentCallbacksC0098j getParentFragment() {
        return this.mParentFragment;
    }

    public Object getReenterTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        Object obj = c0094f.f1626j;
        return obj == USE_DEFAULT_TRANSITION ? getExitTransition() : obj;
    }

    public final Resources getResources() {
        return requireContext().getResources();
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public Object getReturnTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        Object obj = c0094f.f1624h;
        return obj == USE_DEFAULT_TRANSITION ? getEnterTransition() : obj;
    }

    public Object getSharedElementEnterTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        return c0094f.f1627k;
    }

    public Object getSharedElementReturnTransition() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return null;
        }
        Object obj = c0094f.f1628l;
        return obj == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : obj;
    }

    public int getStateAfterAnimating() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return 0;
        }
        return c0094f.f1619c;
    }

    public final String getString(int i2) {
        return getResources().getString(i2);
    }

    public final String getString(int i2, Object... objArr) {
        return getResources().getString(i2, objArr);
    }

    public final String getTag() {
        return this.mTag;
    }

    public final AbstractComponentCallbacksC0098j getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public final CharSequence getText(int i2) {
        return getResources().getText(i2);
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    public View getView() {
        return this.mView;
    }

    public androidx.lifecycle.d getViewLifecycleOwner() {
        androidx.lifecycle.d dVar = this.mViewLifecycleOwner;
        if (dVar != null) {
            return dVar;
        }
        throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
    }

    public androidx.lifecycle.g getViewLifecycleOwnerLiveData() {
        return this.mViewLifecycleOwnerLiveData;
    }

    @Override // androidx.lifecycle.k
    public androidx.lifecycle.j getViewModelStore() {
        if (getContext() == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.mViewModelStore == null) {
            this.mViewModelStore = new androidx.lifecycle.j();
        }
        return this.mViewModelStore;
    }

    public final boolean hasOptionsMenu() {
        return this.mHasMenu;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
    }

    public void instantiateChildFragmentManager() {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment has not been attached yet.");
        }
        z zVar = new z();
        this.mChildFragmentManager = zVar;
        AbstractC0102n abstractC0102n = this.mHost;
        C0092d c0092d = new C0092d(this);
        if (zVar.f1682l != null) {
            throw new IllegalStateException("Already attached");
        }
        zVar.f1682l = abstractC0102n;
        zVar.f1683m = c0092d;
        zVar.f1684n = this;
    }

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    public boolean isHideReplaced() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return false;
        }
        return c0094f.f1633q;
    }

    public final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    public boolean isPostponed() {
        C0094f c0094f = this.mAnimationInfo;
        if (c0094f == null) {
            return false;
        }
        return c0094f.f1631o;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isResumed() {
        return this.mState >= 4;
    }

    public final boolean isStateSaved() {
        z zVar = this.mFragmentManager;
        if (zVar == null) {
            return false;
        }
        return zVar.f1687q || zVar.r;
    }

    public final boolean isVisible() {
        View view;
        return (!isAdded() || isHidden() || (view = this.mView) == null || view.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
    }

    public void noteStateNotSaved() {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
    }

    @Deprecated
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    public void onAttach(Context context) {
        this.mCalled = true;
        AbstractC0102n abstractC0102n = this.mHost;
        Activity activity = abstractC0102n == null ? null : abstractC0102n.f1638b;
        if (activity != null) {
            this.mCalled = false;
            onAttach(activity);
        }
    }

    public void onAttachFragment(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        this.mCalled = true;
        restoreChildFragmentState(bundle);
        z zVar = this.mChildFragmentManager;
        if (zVar == null || zVar.f1681k >= 1) {
            return;
        }
        zVar.f1687q = false;
        zVar.r = false;
        zVar.C(1);
    }

    public Animation onCreateAnimation(int i2, boolean z2, int i3) {
        return null;
    }

    public Animator onCreateAnimator(int i2, boolean z2, int i3) {
        return null;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void onDestroy() {
        this.mCalled = true;
        FragmentActivity activity = getActivity();
        boolean z2 = activity != null && activity.isChangingConfigurations();
        androidx.lifecycle.j jVar = this.mViewModelStore;
        if (jVar == null || z2) {
            return;
        }
        HashMap map = jVar.f1716a;
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            ((A.b) it.next()).a();
        }
        map.clear();
    }

    public void onDestroyOptionsMenu() {
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public LayoutInflater onGetLayoutInflater(Bundle bundle) {
        return getLayoutInflater(bundle);
    }

    public void onHiddenChanged(boolean z2) {
    }

    @Deprecated
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
        AbstractC0102n abstractC0102n = this.mHost;
        Activity activity = abstractC0102n == null ? null : abstractC0102n.f1638b;
        if (activity != null) {
            this.mCalled = false;
            onInflate(activity, attributeSet, bundle);
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean z2) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    public void onPause() {
        this.mCalled = true;
    }

    public void onPictureInPictureModeChanged(boolean z2) {
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
        this.mCalled = true;
    }

    public void onStop() {
        this.mCalled = true;
    }

    public void onViewCreated(View view, Bundle bundle) {
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    public AbstractC0103o peekChildFragmentManager() {
        return this.mChildFragmentManager;
    }

    public void performActivityCreated(Bundle bundle) {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated(bundle);
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onActivityCreated()"));
        }
        z zVar2 = this.mChildFragmentManager;
        if (zVar2 != null) {
            zVar2.f1687q = false;
            zVar2.r = false;
            zVar2.C(2);
        }
    }

    public void performConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
        z zVar = this.mChildFragmentManager;
        if (zVar == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            ArrayList arrayList = zVar.f1674d;
            if (i2 >= arrayList.size()) {
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performConfigurationChanged(configuration);
            }
            i2++;
        }
    }

    public boolean performContextItemSelected(MenuItem menuItem) {
        if (this.mHidden) {
            return false;
        }
        if (onContextItemSelected(menuItem)) {
            return true;
        }
        z zVar = this.mChildFragmentManager;
        return zVar != null && zVar.i(menuItem);
    }

    public void performCreate(Bundle bundle) {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate(bundle);
        this.mIsCreated = true;
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onCreate()"));
        }
        this.mLifecycleRegistry.a(androidx.lifecycle.a.ON_CREATE);
    }

    public boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z2 = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onCreateOptionsMenu(menu, menuInflater);
            z2 = true;
        }
        z zVar = this.mChildFragmentManager;
        return zVar != null ? z2 | zVar.j(menu, menuInflater) : z2;
    }

    public void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
        }
        this.mPerformedCreateView = true;
        this.mViewLifecycleOwner = new C0093e(this);
        this.mViewLifecycleRegistry = null;
        View viewOnCreateView = onCreateView(layoutInflater, viewGroup, bundle);
        this.mView = viewOnCreateView;
        if (viewOnCreateView != null) {
            this.mViewLifecycleOwner.getLifecycle();
            this.mViewLifecycleOwnerLiveData.a(this.mViewLifecycleOwner);
        } else {
            if (this.mViewLifecycleRegistry != null) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.mViewLifecycleOwner = null;
        }
    }

    public void performDestroy() {
        this.mLifecycleRegistry.a(androidx.lifecycle.a.ON_DESTROY);
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.k();
        }
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        onDestroy();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onDestroy()"));
        }
        this.mChildFragmentManager = null;
    }

    public void performDestroyView() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(androidx.lifecycle.a.ON_DESTROY);
        }
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.C(1);
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onDestroyView()"));
        }
        androidx.lifecycle.j viewModelStore = getViewModelStore();
        String canonicalName = A.b.class.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        String strConcat = "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName);
        A.b bVar = (A.b) viewModelStore.f1716a.get(strConcat);
        if (!A.b.class.isInstance(bVar)) {
            bVar = new A.b();
            A.b bVar2 = (A.b) viewModelStore.f1716a.put(strConcat, bVar);
            if (bVar2 != null) {
                bVar2.a();
            }
        }
        h.l lVar = bVar.f0a;
        if (lVar.f() <= 0) {
            this.mPerformedCreateView = false;
        } else {
            w0.c(lVar.g(0));
            throw null;
        }
    }

    public void performDetach() {
        this.mCalled = false;
        onDetach();
        this.mLayoutInflater = null;
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onDetach()"));
        }
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            if (!this.mRetaining) {
                throw new IllegalStateException(w0.a("Child FragmentManager of ", this, " was not  destroyed and this fragment is not retaining instance"));
            }
            zVar.k();
            this.mChildFragmentManager = null;
        }
    }

    public LayoutInflater performGetLayoutInflater(Bundle bundle) {
        LayoutInflater layoutInflaterOnGetLayoutInflater = onGetLayoutInflater(bundle);
        this.mLayoutInflater = layoutInflaterOnGetLayoutInflater;
        return layoutInflaterOnGetLayoutInflater;
    }

    public void performLowMemory() {
        onLowMemory();
        z zVar = this.mChildFragmentManager;
        if (zVar == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            ArrayList arrayList = zVar.f1674d;
            if (i2 >= arrayList.size()) {
                return;
            }
            AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(i2);
            if (abstractComponentCallbacksC0098j != null) {
                abstractComponentCallbacksC0098j.performLowMemory();
            }
            i2++;
        }
    }

    public void performMultiWindowModeChanged(boolean z2) {
        onMultiWindowModeChanged(z2);
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            ArrayList arrayList = zVar.f1674d;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
                if (abstractComponentCallbacksC0098j != null) {
                    abstractComponentCallbacksC0098j.performMultiWindowModeChanged(z2);
                }
            }
        }
    }

    public boolean performOptionsItemSelected(MenuItem menuItem) {
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(menuItem)) {
            return true;
        }
        z zVar = this.mChildFragmentManager;
        return zVar != null && zVar.z(menuItem);
    }

    public void performOptionsMenuClosed(Menu menu) {
        if (this.mHidden) {
            return;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onOptionsMenuClosed(menu);
        }
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.A(menu);
        }
    }

    public void performPause() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(androidx.lifecycle.a.ON_PAUSE);
        }
        this.mLifecycleRegistry.a(androidx.lifecycle.a.ON_PAUSE);
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.C(3);
        }
        this.mState = 3;
        this.mCalled = false;
        onPause();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onPause()"));
        }
    }

    public void performPictureInPictureModeChanged(boolean z2) {
        onPictureInPictureModeChanged(z2);
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            ArrayList arrayList = zVar.f1674d;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = (AbstractComponentCallbacksC0098j) arrayList.get(size);
                if (abstractComponentCallbacksC0098j != null) {
                    abstractComponentCallbacksC0098j.performPictureInPictureModeChanged(z2);
                }
            }
        }
    }

    public boolean performPrepareOptionsMenu(Menu menu) {
        boolean z2 = false;
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onPrepareOptionsMenu(menu);
            z2 = true;
        }
        z zVar = this.mChildFragmentManager;
        return zVar != null ? z2 | zVar.B(menu) : z2;
    }

    public void performResume() {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
            this.mChildFragmentManager.G();
        }
        this.mState = 4;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onResume()"));
        }
        z zVar2 = this.mChildFragmentManager;
        if (zVar2 != null) {
            zVar2.f1687q = false;
            zVar2.r = false;
            zVar2.C(4);
            this.mChildFragmentManager.G();
        }
        androidx.lifecycle.f fVar = this.mLifecycleRegistry;
        androidx.lifecycle.a aVar = androidx.lifecycle.a.ON_RESUME;
        fVar.a(aVar);
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(aVar);
        }
    }

    public void performSaveInstanceState(Bundle bundle) {
        Parcelable parcelableY;
        onSaveInstanceState(bundle);
        z zVar = this.mChildFragmentManager;
        if (zVar == null || (parcelableY = zVar.Y()) == null) {
            return;
        }
        bundle.putParcelable("android:support:fragments", parcelableY);
    }

    public void performStart() {
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.T();
            this.mChildFragmentManager.G();
        }
        this.mState = 3;
        this.mCalled = false;
        onStart();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onStart()"));
        }
        z zVar2 = this.mChildFragmentManager;
        if (zVar2 != null) {
            zVar2.f1687q = false;
            zVar2.r = false;
            zVar2.C(3);
        }
        androidx.lifecycle.f fVar = this.mLifecycleRegistry;
        androidx.lifecycle.a aVar = androidx.lifecycle.a.ON_START;
        fVar.a(aVar);
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(aVar);
        }
    }

    public void performStop() {
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(androidx.lifecycle.a.ON_STOP);
        }
        this.mLifecycleRegistry.a(androidx.lifecycle.a.ON_STOP);
        z zVar = this.mChildFragmentManager;
        if (zVar != null) {
            zVar.r = true;
            zVar.C(2);
        }
        this.mState = 2;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onStop()"));
        }
    }

    public void postponeEnterTransition() {
        a().f1631o = true;
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public final void requestPermissions(String[] strArr, int i2) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            throw new IllegalStateException(w0.a("Fragment ", this, " not attached to Activity"));
        }
        FragmentActivity fragmentActivity = ((C0099k) abstractC0102n).f1634f;
        fragmentActivity.getClass();
        if (i2 == -1) {
            if (!fragmentActivity.f1532h && i2 != -1) {
                FragmentActivity.d(i2);
            }
            fragmentActivity.requestPermissions(strArr, i2);
            return;
        }
        FragmentActivity.d(i2);
        try {
            fragmentActivity.f1532h = true;
            int iC = ((fragmentActivity.c(this) + 1) << 16) + (i2 & 65535);
            if (!fragmentActivity.f1532h && iC != -1) {
                FragmentActivity.d(iC);
            }
            fragmentActivity.requestPermissions(strArr, iC);
        } finally {
            fragmentActivity.f1532h = false;
        }
    }

    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException(w0.a("Fragment ", this, " not attached to an activity."));
    }

    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException(w0.a("Fragment ", this, " not attached to a context."));
    }

    public final AbstractC0103o requireFragmentManager() {
        AbstractC0103o fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            return fragmentManager;
        }
        throw new IllegalStateException(w0.a("Fragment ", this, " not associated with a fragment manager."));
    }

    public final Object requireHost() {
        Object host = getHost();
        if (host != null) {
            return host;
        }
        throw new IllegalStateException(w0.a("Fragment ", this, " not attached to a host."));
    }

    public void restoreChildFragmentState(Bundle bundle) {
        Parcelable parcelable;
        if (bundle == null || (parcelable = bundle.getParcelable("android:support:fragments")) == null) {
            return;
        }
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
        }
        this.mChildFragmentManager.X(parcelable, this.mChildNonConfig);
        this.mChildNonConfig = null;
        z zVar = this.mChildFragmentManager;
        zVar.f1687q = false;
        zVar.r = false;
        zVar.C(1);
    }

    public final void restoreViewState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = this.mSavedViewState;
        if (sparseArray != null) {
            this.mInnerView.restoreHierarchyState(sparseArray);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored(bundle);
        if (!this.mCalled) {
            throw new T(w0.a("Fragment ", this, " did not call through to super.onViewStateRestored()"));
        }
        if (this.mView != null) {
            this.mViewLifecycleRegistry.a(androidx.lifecycle.a.ON_CREATE);
        }
    }

    public void setAllowEnterTransitionOverlap(boolean z2) {
        a().f1630n = Boolean.valueOf(z2);
    }

    public void setAllowReturnTransitionOverlap(boolean z2) {
        a().f1629m = Boolean.valueOf(z2);
    }

    public void setAnimatingAway(View view) {
        a().f1617a = view;
    }

    public void setAnimator(Animator animator) {
        a().f1618b = animator;
    }

    public void setArguments(Bundle bundle) {
        if (this.mIndex >= 0 && isStateSaved()) {
            throw new IllegalStateException("Fragment already active and state has been saved");
        }
        this.mArguments = bundle;
    }

    public void setEnterSharedElementCallback(AbstractC0149c abstractC0149c) {
        a().getClass();
    }

    public void setEnterTransition(Object obj) {
        a().f1623g = obj;
    }

    public void setExitSharedElementCallback(AbstractC0149c abstractC0149c) {
        a().getClass();
    }

    public void setExitTransition(Object obj) {
        a().f1625i = obj;
    }

    public void setHasOptionsMenu(boolean z2) {
        if (this.mHasMenu != z2) {
            this.mHasMenu = z2;
            if (!isAdded() || isHidden()) {
                return;
            }
            ((C0099k) this.mHost).f1634f.b();
        }
    }

    public void setHideReplaced(boolean z2) {
        a().f1633q = z2;
    }

    public final void setIndex(int i2, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j) {
        this.mIndex = i2;
        if (abstractComponentCallbacksC0098j == null) {
            this.mWho = "android:fragment:" + this.mIndex;
        } else {
            this.mWho = abstractComponentCallbacksC0098j.mWho + ":" + this.mIndex;
        }
    }

    public void setInitialSavedState(Fragment$SavedState fragment$SavedState) {
        Bundle bundle;
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        if (fragment$SavedState == null || (bundle = fragment$SavedState.f1525a) == null) {
            bundle = null;
        }
        this.mSavedFragmentState = bundle;
    }

    public void setMenuVisibility(boolean z2) {
        if (this.mMenuVisible != z2) {
            this.mMenuVisible = z2;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                ((C0099k) this.mHost).f1634f.b();
            }
        }
    }

    public void setNextAnim(int i2) {
        if (this.mAnimationInfo == null && i2 == 0) {
            return;
        }
        a().f1620d = i2;
    }

    public void setNextTransition(int i2, int i3) {
        if (this.mAnimationInfo == null && i2 == 0 && i3 == 0) {
            return;
        }
        a();
        C0094f c0094f = this.mAnimationInfo;
        c0094f.f1621e = i2;
        c0094f.f1622f = i3;
    }

    public void setOnStartEnterTransitionListener(InterfaceC0096h interfaceC0096h) {
        a();
        C0094f c0094f = this.mAnimationInfo;
        InterfaceC0096h interfaceC0096h2 = c0094f.f1632p;
        if (interfaceC0096h == interfaceC0096h2) {
            return;
        }
        if (interfaceC0096h != null && interfaceC0096h2 != null) {
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
        if (c0094f.f1631o) {
            c0094f.f1632p = interfaceC0096h;
        }
        if (interfaceC0096h != null) {
            ((y) interfaceC0096h).f1665c++;
        }
    }

    public void setReenterTransition(Object obj) {
        a().f1626j = obj;
    }

    public void setRetainInstance(boolean z2) {
        this.mRetainInstance = z2;
    }

    public void setReturnTransition(Object obj) {
        a().f1624h = obj;
    }

    public void setSharedElementEnterTransition(Object obj) {
        a().f1627k = obj;
    }

    public void setSharedElementReturnTransition(Object obj) {
        a().f1628l = obj;
    }

    public void setStateAfterAnimating(int i2) {
        a().f1619c = i2;
    }

    public void setTargetFragment(AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, int i2) {
        AbstractC0103o fragmentManager = getFragmentManager();
        AbstractC0103o fragmentManager2 = abstractComponentCallbacksC0098j != null ? abstractComponentCallbacksC0098j.getFragmentManager() : null;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new IllegalArgumentException(w0.a("Fragment ", abstractComponentCallbacksC0098j, " must share the same FragmentManager to be set as a target fragment"));
        }
        for (AbstractComponentCallbacksC0098j targetFragment = abstractComponentCallbacksC0098j; targetFragment != null; targetFragment = targetFragment.getTargetFragment()) {
            if (targetFragment == this) {
                throw new IllegalArgumentException("Setting " + abstractComponentCallbacksC0098j + " as the target of " + this + " would create a target cycle");
            }
        }
        this.mTarget = abstractComponentCallbacksC0098j;
        this.mTargetRequestCode = i2;
    }

    public void setUserVisibleHint(boolean z2) {
        boolean z3 = false;
        if (!this.mUserVisibleHint && z2 && this.mState < 3 && this.mFragmentManager != null && isAdded() && this.mIsCreated) {
            z zVar = this.mFragmentManager;
            zVar.getClass();
            if (this.mDeferStart) {
                if (zVar.f1672b) {
                    zVar.f1689t = true;
                } else {
                    this.mDeferStart = false;
                    zVar.S(this, zVar.f1681k, 0, 0, false);
                }
            }
        }
        this.mUserVisibleHint = z2;
        if (this.mState < 3 && !z2) {
            z3 = true;
        }
        this.mDeferStart = z3;
        if (this.mSavedFragmentState != null) {
            this.mSavedUserVisibleHint = Boolean.valueOf(z2);
        }
    }

    public boolean shouldShowRequestPermissionRationale(String str) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n != null) {
            return ((C0099k) abstractC0102n).f1634f.shouldShowRequestPermissionRationale(str);
        }
        return false;
    }

    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(Intent intent, Bundle bundle) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            throw new IllegalStateException(w0.a("Fragment ", this, " not attached to Activity"));
        }
        abstractC0102n.d(this, intent, -1, bundle);
    }

    public void startActivityForResult(Intent intent, int i2) {
        startActivityForResult(intent, i2, null);
    }

    public void startActivityForResult(Intent intent, int i2, Bundle bundle) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            throw new IllegalStateException(w0.a("Fragment ", this, " not attached to Activity"));
        }
        abstractC0102n.d(this, intent, i2, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i2, Intent intent, int i3, int i4, int i5, Bundle bundle) {
        AbstractC0102n abstractC0102n = this.mHost;
        if (abstractC0102n == null) {
            throw new IllegalStateException(w0.a("Fragment ", this, " not attached to Activity"));
        }
        FragmentActivity fragmentActivity = ((C0099k) abstractC0102n).f1634f;
        fragmentActivity.f1533i = true;
        try {
            if (i2 == -1) {
                fragmentActivity.startIntentSenderForResult(intentSender, i2, intent, i3, i4, i5, bundle);
            } else {
                FragmentActivity.d(i2);
                fragmentActivity.startIntentSenderForResult(intentSender, ((fragmentActivity.c(this) + 1) << 16) + (65535 & i2), intent, i3, i4, i5, bundle);
            }
        } finally {
            fragmentActivity.f1533i = false;
        }
    }

    public void startPostponedEnterTransition() {
        z zVar = this.mFragmentManager;
        if (zVar == null || zVar.f1682l == null) {
            a().f1631o = false;
        } else if (Looper.myLooper() != this.mFragmentManager.f1682l.f1640d.getLooper()) {
            this.mFragmentManager.f1682l.f1640d.postAtFrontOfQueue(new P(1, this));
        } else {
            callStartTransitionListener();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        Y.r.c(this, sb);
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            sb.append(" ");
            sb.append(this.mTag);
        }
        sb.append('}');
        return sb.toString();
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }
}
