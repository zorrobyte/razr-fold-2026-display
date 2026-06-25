package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import com.android.launcher3.icons.mono.MonoThemedBitmap;
import com.android.launcher3.util.FlagOp;

/* JADX INFO: loaded from: classes.dex */
public class BitmapInfo {
    public static final Bitmap LOW_RES_ICON;
    public static final BitmapInfo LOW_RES_INFO;
    private static ArrayMap sDrawables;
    private BitmapInfo badgeInfo;
    public final int color;
    public int flags;
    public final Bitmap icon;
    protected ThemedBitmap mThemedBitmap;
    private UserHandle mUserHandle;

    public interface Extender {
        void drawForPersistence(Canvas canvas);

        BitmapInfo getExtendedInfo(Bitmap bitmap, int i, BaseIconFactory baseIconFactory, float f);
    }

    class FixedSizeEmptyDrawable extends ColorDrawable {
        private final int mSize;

        public FixedSizeEmptyDrawable(int i) {
            super(0);
            this.mSize = i;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mSize;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mSize;
        }
    }

    static {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        LOW_RES_ICON = bitmapCreateBitmap;
        LOW_RES_INFO = fromBitmap(bitmapCreateBitmap);
        sDrawables = new ArrayMap();
    }

    public BitmapInfo(Bitmap bitmap, int i) {
        this.icon = bitmap;
        this.color = i;
    }

    public static BitmapInfo fromBitmap(Bitmap bitmap) {
        return of(bitmap, 0);
    }

    public static BitmapInfo of(Bitmap bitmap, int i) {
        return new BitmapInfo(bitmap, i);
    }

    protected void applyFlags(Context context, FastBitmapDrawable fastBitmapDrawable, int i) {
        fastBitmapDrawable.mDisabledAlpha = GraphicsUtils.getFloat(context, R$attr.disabledIconAlpha, 1.0f);
        fastBitmapDrawable.mCreationFlags = i;
        if ((i & 2) == 0) {
            Drawable badgeDrawable = getBadgeDrawable(context, (i & 1) != 0, (i & 4) != 0, fastBitmapDrawable.getIntrinsicWidth());
            if (badgeDrawable != null) {
                if ((this.flags & 4) != 0) {
                    fastBitmapDrawable.setCloneBadge(badgeDrawable);
                } else {
                    fastBitmapDrawable.setBadge(badgeDrawable);
                }
            }
        }
    }

    @Override // 
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public BitmapInfo mo1131clone() {
        return copyInternalsTo(new BitmapInfo(this.icon, this.color));
    }

    protected BitmapInfo copyInternalsTo(BitmapInfo bitmapInfo) {
        bitmapInfo.mThemedBitmap = this.mThemedBitmap;
        bitmapInfo.flags = this.flags;
        bitmapInfo.badgeInfo = this.badgeInfo;
        return bitmapInfo;
    }

    public Drawable getBadgeDrawable(Context context, boolean z) {
        return getBadgeDrawable(context, z, false, 256);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public Drawable getBadgeDrawable(Context context, boolean z, boolean z2, int i) {
        BitmapInfo bitmapInfo = this.badgeInfo;
        if (bitmapInfo != null) {
            int i2 = z;
            if (bitmapInfo.icon == null) {
                return bitmapInfo.getBadgeDrawable(context, z);
            }
            if (z2) {
                i2 = (z ? 1 : 0) | 4;
            }
            bitmapInfo.setUserHandle(this.mUserHandle);
            return this.badgeInfo.newIcon(context, i2);
        }
        if (z2) {
            return null;
        }
        int i3 = this.flags;
        if ((i3 & 2) != 0) {
            return new UserBadgeDrawable(context, R$drawable.ic_instant_app_badge, R$color.badge_tint_instant, z);
        }
        if ((i3 & 1) != 0) {
            return new UserBadgeDrawable(context, R$drawable.ic_work_app_badge, R$color.badge_tint_work, z);
        }
        if ((i3 & 4) != 0) {
            if (isCloneUserBadgeLoaded()) {
                if (!(sDrawables.get(this.mUserHandle) instanceof BitmapDrawable)) {
                    return (Drawable) sDrawables.get(this.mUserHandle);
                }
                return new BitmapDrawable(context.getResources(), ((BitmapDrawable) sDrawables.get(this.mUserHandle)).getBitmap());
            }
            try {
                Drawable userBadgedIcon = context.getPackageManager().getUserBadgedIcon(new FixedSizeEmptyDrawable(i), this.mUserHandle);
                sDrawables.put(this.mUserHandle, userBadgedIcon);
                return userBadgedIcon;
            } catch (Exception e) {
                Log.w("BitmapInfo", "Failed to get clone badged icon", e);
            }
        } else {
            if ((i3 & 8) != 0) {
                return new UserBadgeDrawable(context, R$drawable.ic_private_profile_app_badge, R$color.badge_tint_private, z);
            }
            if ((i3 & 16) != 0) {
                return new UserBadgeDrawable(context, R$drawable.ic_vault_app_badge, R$color.badge_tint_vault, z);
            }
        }
        return null;
    }

    public boolean isCloneUserBadgeLoaded() {
        UserHandle userHandle = this.mUserHandle;
        return (userHandle == null || sDrawables.get(userHandle) == null) ? false : true;
    }

    public final boolean isLowRes() {
        return LOW_RES_ICON == this.icon;
    }

    public FastBitmapDrawable newIcon(Context context) {
        return newIcon(context, 0);
    }

    public FastBitmapDrawable newIcon(Context context, int i) {
        ThemedBitmap themedBitmap;
        FastBitmapDrawable placeHolderIconDrawable = isLowRes() ? new PlaceHolderIconDrawable(this, context) : ((i & 1) == 0 || (themedBitmap = this.mThemedBitmap) == null) ? new FastBitmapDrawable(this) : themedBitmap.newDrawable(this, context);
        applyFlags(context, placeHolderIconDrawable, i);
        return placeHolderIconDrawable;
    }

    public void setMonoIcon(Bitmap bitmap, Bitmap bitmap2) {
        this.mThemedBitmap = new MonoThemedBitmap(bitmap, bitmap2, null);
    }

    public void setUserHandle(UserHandle userHandle) {
        this.mUserHandle = userHandle;
    }

    public BitmapInfo withFlags(FlagOp flagOp) {
        if (flagOp == FlagOp.NO_OP) {
            return this;
        }
        BitmapInfo bitmapInfoMo1131clone = mo1131clone();
        bitmapInfoMo1131clone.flags = flagOp.apply(bitmapInfoMo1131clone.flags);
        return bitmapInfoMo1131clone;
    }
}
