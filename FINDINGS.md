# Razr Fold 2026 — External Display Cap Investigation & Root Project

Device: `motorola razr_fold_2026` (codename **blanc**), Android 16, Verizon (`ro.carrier=retus`).
SoC: **Qualcomm SM8845 = Snapdragon 8 Gen 5** (platform `canoe`, Adreno 840).
Serial: `ZP2223437N`. Bootloader: **UNLOCKED**. Root: **Magisk v30.7 — CONFIRMED** (`su` → `uid=0`, ctx `u:r:magisk:s0`).

---

## ✅ WHAT YOU NEED TO DO (action items)

**Goal:** lift the external-display resolution cap (stuck at 3440×1440@60) toward 4K@120 / 5K2K@60
by rooting and stubbing Android's `enable_mode_limit_for_external_display` governor.

1. **Finish first-boot setup** on the freshly-wiped phone (skip Google/WiFi to go fast).
2. **Re-enable developer access:** Settings → About → tap **Build number ×7**, then Developer
   Options → **USB debugging** ON. Reconnect WiFi if you want wireless ADB back.
   - (OEM unlocking is already done — no need to redo.)
3. **Source the stock `init_boot.img` matching the exact build** — the one real blocker:
   - Best: **Lenovo Rescue & Smart Assistant (LMSA)** → it downloads full firmware for the
     connected device. ⚠️ **Windows-only** — needs a **Windows PC or VM** (you're on a Mac).
   - Alt: firmware mirror (lolinet) — **not posted yet** for `blanc` (too new); check back later.
   - Once you have firmware, extract `init_boot.img` from it.
4. **Tell me when ADB is back + you have `init_boot.img`** — I'll grab the exact build fingerprint,
   drive the Magisk patch + `fastboot flash init_boot`, then install LSPosed and the display hook.

> Decision needed: **do you have a Windows machine/VM for LMSA?** That's the cleanest firmware route.

---

## 1. Built-in panels (`dumpsys display`)
| Display | Resolution | Refresh rates | Notes |
|---|---|---|---|
| Cover (id 0) | 1080 × 2520 | 24/30/60/90/120/**165** Hz | |
| Inner (id 2) | 2232 × 2484 | 24/30/60/90/**120** Hz | HDR10/10+/HLG/Dolby, ~1500 nits |

---

## 2. External display — LIVE test (confirmed working)
Connected: **LG 45GX950A-B** (45" bendable OLED, native **5120×2160 21:9, 165 Hz**, DP 2.1)
via **Thunderbolt 5 cable → dock → monitor**. Phone link = USB-C **DP Alt-Mode** (phone ≠ TB host).

Negotiated modes the phone exposed (displayId 13, "HDMI Screen", EDID "LG ULTRAGEAR+"):
| Resolution | Refresh | Active |
|---|---|---|
| **3440×1440** | 60 / 50 Hz | ✅ default + active |
| 1920×1080 | **120** / 60 Hz | |
| 1280×1024 | 75 Hz | |
| 1024×768 / 800×600 / 640×480 | 60 Hz | |

The monitor's native 5120×2160 and 3840×2160 were **filtered out** (see §4).

---

## 3. Getting desktop mode to launch (fixed)
- "Won't launch" cause: `com.motorola.mobiledesktop` was in `enabled=3` (disabled-by-user).
  **Fix:** `adb shell pm enable com.motorola.mobiledesktop`. (Now wiped — will need redoing post-reset.)
- Package roles (decompile-classified wired vs wireless):
  | Package | Location | Role |
  |---|---|---|
  | **MotoLaptopPanel** (`com.motorola.laptoppanel`) | /system_ext/priv-app | **Wired external desktop** (88 phys / 17 cast) |
  | MotoDesktopCore (`…mobiledesktop.core`) | /system_ext/priv-app | Cast/Ready-For engine |
  | MotoTaskBar (`com.motorola.systemui.desk`) | /system_ext/priv-app | Desktop taskbar |
  | mobiledesktop (Smart Connect) | /data/app | **Wireless** mirror-to-PC/TV (ignore for wired) |

---

## 4. THE RESOLUTION CAP — root cause (key finding)
External modes are filtered to the **internal panel's pixel budget** via AOSP feature flag:

> **`enable_mode_limit_for_external_display`** = *"Feature limiting external display resolution and
> refresh rate"* (Google Buganizer b/242093547 — internal/not public). On-device: `true (def:true)`.

Consumed by `DisplayModeDirector` (system_server) — drops any external mode whose pixel count exceeds
the internal panel. The math proves it:

| Display | Pixels | Result |
|---|---|---|
| Inner panel 2232×2484 | **5.54 M** | = ceiling |
| 3440×1440 (got it) | 4.95 M | ✅ allowed |
| 3840×2160 (4K) | 8.29 M | ❌ filtered |
| 5120×2160 (native) | 11.1 M | ❌ filtered |

Not bandwidth/dock/cable/SoC — a deliberate governor. Same reason Pixels cap at ~1440p. The flag is
**build-baked & read-only** (absent from `device_config list` and `aflags`) → not toggleable from
userspace. `cmd display set-user-preferred-display-mode … 13` is rejected for this reason.

---

## 5. What the SoC + hardware can do
- **Snapdragon 8 Gen 5 (SM8845)** max external (Qualcomm spec): **4K@120**, up to **8K@30**.
- **Proof it's policy not silicon:** RedMagic (same Snapdragon class) ships DP output at **4K/144** and
  **8K/60** with no root — their ROM doesn't impose the limit. Moto kept the stock conservative default.

---

## 6. Why Android limits it
Mobile display pipeline (DPU + GPU + memory BW + thermal) is sized for the internal panel. 4K/5K
external = 2–4× the pixels/frame on a fanless device → heat/battery/jank. Sibling `ExternalDisplayPolicy`
is literally a thermal kill-switch for external displays. Conservative default; OEMs may raise it.

---

## 7. Bootloader unlock — DONE
- Stock state was locked (`ro.boot.flash.locked=1`, vbmeta locked, verifiedbootstate green). Verizon/US
  Moto are normally non-unlockable, but **OEM unlocking toggle was enabled** and the unlock went through.
- Flow used:
  1. `adb reboot bootloader`
  2. `fastboot oem get_unlock_data` → assembled string (device-specific):
     `3A85837302075843#5A50323232333433374E006D6F746F726F6C0000#0C26C8BE9CACF611735779765B657BD97FB6C6925343B74A9BB2047C074ED83E#51583D96002FD0E10000000000000000`
  3. Submitted at motorola.com unlock portal → emailed code: `BBCPMNUP32K5BZF4SRLG`
  4. `fastboot oem unlock BBCPMNUP32K5BZF4SRLG` → **"Bootloader is unlocked!"**
     (Needed on-screen confirm; first attempt armed the prompt, second attempt after confirming succeeded.)
- Verified: `securestate` went `oem_locked` → **`flashing_unlocked:SDP`**. Device factory-wiped on reboot.
- Note: unlocked-bootloader warning shows ~5s each boot — normal.

---

## 8. Rooting (Magisk) — ✅ DONE
Android 16, **A/B slots** (active `_b`) → patched **`init_boot.img`** (NOT `boot.img`; ramdisk moved
to init_boot in A13+). Firmware: `BLANC_G_W3WBS36.36-48-5-1 ... cid50` (matched build exactly).

Steps that worked:
1. Confirmed build: `W3WBS36.36-48-5-1 / 1c4b8-a3faa` == firmware. ✅
2. Extracted `init_boot.img` from the 15 GB firmware zip (`unzip -j`).
3. `adb push init_boot.img /sdcard/Download/` → Magisk app v30.7 → "Select and Patch a File"
   → produced `magisk_patched-30700_2S4nL.img` (validated: contains `KEEPVERITY`, `/.magisk`, `overlay.d`).
4. `adb pull` patched img.
5. **`fastboot flash init_boot` in BOOTLOADER mode → "Preflash validation failed"** (known Moto issue).
6. **FIX: `fastboot reboot fastboot` (fastbootd) → `fastboot flash init_boot magisk_patched.img` → OKAY.** ✅
7. `fastboot reboot` → booted, Magisk active.

> ⚠️ Key gotcha for next time: Moto rejects Magisk-patched images in bootloader mode
> ("Preflash validation failed") — **flash in fastbootd** (`fastboot reboot fastboot`) instead.

### Then the goal — bypass the mode limit (IN PROGRESS)
**Confirmed with root:** the flag is **read-only** — `aflags disable
com.android.server.display.feature.flags.enable_mode_limit_for_external_display` →
*"it is read-only for the current release configuration."* So **no aconfig/`aflags` override** is possible.

Remaining route = **LSPosed hook** forcing `DisplayManagerFlags.isModeLimitForExternalDisplayEnabled()`
(or the generated `FeatureFlagsImpl.enableModeLimitForExternalDisplay()`) → **false** in system_server.
- Build env present: Android Studio + SDK platforms 36/36.1 + JDK 17 (gradle via wrapper).
- Steps: enable Zygisk → install LSPosed → build + install the 1-method hook module → scope to
  `system_server` → reboot.
- After bypass: `cmd display set-user-preferred-display-mode 3840 2160 120 13` should finally apply,
  and 4K/5K2K modes should appear in the external display's `supportedModes`.

Realistic ceiling unchanged (link-limited): **4K@120 / 5K2K@60**, not native 5K2K@165 (DP-alt vs DP2.1).

### Realistic ceiling (link-limited, not software)
Phone USB-C = **DP Alt-Mode (DP 1.4 / HBR3)**, not DP 2.1 UHBR — even through the TB5 dock.
| Target | Feasible? |
|---|---|
| 3840×2160 @ 120 Hz | ✅ |
| 5120×2160 @ 60 Hz | ✅ likely (DSC) |
| 5120×2160 @ 165 Hz (native) | ❌ needs DP 2.1 UHBR the phone can't source |

Post-root, read `/sys/class/drm/card0-DP-1/` (root) for the real negotiated link rate/lanes.

---

## 9. Access / tooling notes
- **Wireless ADB:** `adb tcpip 5555` then `adb connect <phone-ip>:5555`. Frees the single USB-C port
  for the display. ⚠️ `tcpip` mode does NOT survive reboot — replug USB + re-run after a restart.
  (Wiped by the unlock reset; re-enable USB debugging first.)
- **Decompile:** jadx 1.5.5. Sources in `./decompiled/`, APKs in `./apks/`.
- Key file: `MotoLaptopPanel/resources/res/values/arrays.xml` → `overlay_display_devices_values`
  (Moto extended the AOSP "simulate secondary displays" list to 4K / 4K-upscaled / dual-screen).
- Host: macOS. `fastboot`/`adb` = Android platform-tools. `gh` for publishing.

---

## Bottom line
Everything downstream is capable — SoC does 4K@120, monitor does 5K2K@165, the TB5 link is fat. You're
pinned to **3440×1440@60** by Android's `enable_mode_limit_for_external_display` governor (external res ≤
internal panel pixels), which is framework-level and read-only → no APK patch reaches it. Bootloader is
now **unlocked**; **root + an LSPosed/`DisplayModeDirector` stub** is the lever, realistically buying
**4K@120 or 5K2K@60** — not the panel's native 5K2K@165 (a DP-alt vs DP2.1 link limit).

---

## 10. Root + hook target found; LSPosed module BLOCKED on this device
- Root ✅ (Magisk 30.7). Hook target confirmed via services.jar decompile:
  **`com.android.server.display.feature.DisplayManagerFlags.isExternalDisplayLimitModeEnabled()` → return false**
  short-circuits the limit (DisplayModeDirector line ~1650; flag cached at construction line ~2878).
- Built a correct legacy-Xposed module (`com.dispunlock`, `DispUnlock.apk`) — valid APK (aapt2 + LSPosed
  PackageParser parse fine standalone).
- Installed **Vector (LSPosed fork) v2.0-3021** (Release AND Debug), enabled + scoped to System Framework.
- **BLOCKER:** `lspd` (root) cannot read the module APK at boot:
  `Failed to open APK /data/app/~~.../com.dispunlock-.../base.apk: I/O error` / `ENOENT` / `skip`.
  `lspd`'s mount namespace doesn't contain `/data/app` (even `su` is denied reading `/proc/<lspd>/root`).
  Reproduces every boot, both Vector builds → **Vector/Magisk mount-namespace incompatibility on
  blanc / Android 16**, not our module. So NO Xposed module can load.

### Remaining options
- (A) Debug the Vector namespace/SELinux issue (uncertain).
- (B) Pivot: patch `services.jar` directly via a Magisk module (bypasses LSPosed; heavier, dexopt/bootloop
  risk on A16 ART).
- The hook itself is proven-correct and one line — only the delivery mechanism is blocked.

---

## 11. SOLVED via framework patch (LSPosed abandoned) — software cap removed
LSPosed was a dead end (lspd couldn't read any module APK; ReZygisk didn't help). Pivoted to a direct
`services.jar` patch:
- Decompiled `services.jar` classes.dex (apktool, via wrapper-APK since the smali CLI wasn't available),
  edited `DisplayManagerFlags.isExternalDisplayLimitModeEnabled()` → `const/4 v0,0x0; return v0` (false),
  reassembled.
- **Magisk module** (`framework-patch/services-dispcap.zip`): ships patched `services.jar` +
  `customize.sh` that **whiteouts the stale oat** (`services.odex/vdex/art` + fsv_meta) so ART loads the
  patched dex from the jar (JIT). Booted clean; `ClassLoaderContext mismatch` warnings confirm the new
  jar is live; `services.odex`/`vdex` gone.
- **Result:** the `enable_mode_limit_for_external_display` governor is disabled. A previously-filtered
  mode (2560×1080) now appears.

### BUT the real ceiling here is the LINK, not software
- DRM connector `card0-DP-1/modes` delivers **max 3440×1440** to the phone over the TB5 dock.
- 3440×1440 (4.95 M px) is already **under** the internal-panel limit (5.54 M), so the software cap was
  never the binding constraint for this monitor — 4K/5K2K never reach the phone at all.
- Cause: USB-C **DP Alt-Mode is 2-lane** through the dock (keeps USB3) → ~3440×1440 ceiling. Phone DP-alt
  is **DP 1.4**, so 5K2K@165 is physically impossible regardless.
- **To test higher:** direct USB-C→DP cable (forces 4-lane) → possibly 4K. Software is no longer the gate.

### Net
Root project complete: bootloader unlocked, rooted, **framework governor removed (persistent)**. Remaining
limit is the phone's DP-alt link bandwidth to this specific 5K2K panel, which is hardware, not software.

---

## 12. Why not higher? SoC says 4K@120/8K@30 — we get ~3.3 Mpix worth. The LINK.

The Snapdragon 8 Gen 5's "4K@120 / 8K@30 external" is the **DPU's internal capability assuming a
DP 2.1 UHBR link** (reference/laptop platform). A phone's USB-C cannot present that link. Three stacked
bottlenecks sit between the SoC and the monitor:

### Bottleneck 1 — DP version: DP 1.4 (HBR3), not DP 2.1 (UHBR)
Phones implement DisplayPort Alt Mode over **DP 1.4 / HBR3 = 8.1 Gbps per lane**. DP 2.1 UHBR
(10–20 Gbps/lane) needs Thunderbolt/USB4-class PHY that phones don't ship. This phone is NOT a TB host —
the TB5 cable/dock fall back to DP-alt. So per-lane rate is capped at HBR3.

### Bottleneck 2 — lane count: 2-lane, not 4-lane (shares USB-C with USB3)
USB-C has 4 high-speed lanes. DP-alt can use **4 (DP-only)** or **2 for DP + 2 for USB3**. Through the
dock (which keeps USB data alive), the phone negotiates **2-lane DP**:
- 2-lane HBR3 = 16.2 Gbps raw → ~13 Gbps usable (8b/10b)
- ≈ **~450–500 Mpix/s** ceiling at 24bpp + blanking

### Bottleneck 3 — no DSC negotiated
DP 1.4 + Display Stream Compression (~3:1) could fake 4K@120, but the phone/dock aren't negotiating DSC
for these modes, so we get the raw-bandwidth ceiling.

### The math matches exactly what we observe
| Mode | Mpix/s | Link bits (24bpp×1.2) | Fits 2-lane (~13 Gbps)? |
|---|---|---|---|
| 3440×1440 @ 60 | 297 | 8.6 Gbps | ✅ (was active) |
| 2560×1080 @ 120 | 332 | 9.6 Gbps | ✅ (active now) |
| 1920×1080 @ 120 | 249 | 7.2 Gbps | ✅ |
| 3440×1440 @ 100 | 495 | 14.3 Gbps | ❌ just over |
| **3840×2160 @ 60 (4K)** | 498 | 14.3 Gbps | ❌ just over |
| **5120×2160 @ 60 (5K2K)** | 663 | 19 Gbps | ❌ |
| 5120×2160 @ 165 (native) | 1825 | 53 Gbps | ❌ (needs DP 2.1 UHBR) |

→ The link delivers a **fixed ~3.3 Mpix@60-equivalent budget**, spent on EITHER resolution
(3440×1440@60) OR refresh (2560×1080@120). That's why the dual-mode LG trades one for the other.

### What WOULD raise it
- **Direct USB-C→DP cable (forces 4-lane DP-alt)** → ~26 Gbps → unlocks **4K@60 / 5K2K@60 / 3440×1440@120**.
  This is the single highest-value test left; software is no longer the gate.
- **5K2K@165 is physically impossible** from this phone — needs DP 2.1 UHBR the USB-C PHY can't source.

### Summary of the bottleneck stack
SoC DPU (4K120/8K30) → ❌ DP 1.4 not 2.1 → ❌ 2-lane not 4-lane → ❌ no DSC → **~3.3 Mpix@60 link budget**.
The framework software cap (now removed) sat ABOVE all of this and was never the binding limit for real panels.

---

## 13. "Blur Busters shows 60 Hz at 120 Hz mode" — it's Chrome, not Moto/the display

Tested at 2560×1080@**120** mode. TestUFO/Blur Busters reports 60. Investigated whether Moto/Android caps
the draw rate:
- `dumpsys display`: external display (id 13) `renderFrameRate = 120.00001`; DisplayModeDirector render
  range `(24–120)`; **no frameRateOverride** (`mFrameRateOverrides=[]`).
- `dumpsys SurfaceFlinger`: external display render↔mode table goes to 120 Hz; Chrome's layer
  `requestedFrameRate: {HighHint}` — Chrome is *asking* for high fps and the system grants it (no downclock).

→ The system, SurfaceFlinger, the DP link, and Moto are all serving **120 Hz**. The 60 Hz is **Chrome's
web-content frame rate**: Blink caps `requestAnimationFrame` to 60 fps on Android (especially on
secondary/external displays). TestUFO measures rAF, so it shows 60 even though composition is 120.

### How to confirm / get true 120
- Non-browser test: scroll/animate in a native app on the external display — UI composites at 120.
- Chrome `chrome://flags` high-frame-rate experiments may lift the rAF cap; otherwise it's a known
  Chrome-on-Android limitation, not the device.
- The desktop UI itself (window moves, scrolling) already runs 120 on the external display.

Net: nothing on the device is limiting draw to 60. The cap is inside the browser.

---

## 14. "Whole desktop feels 60 Hz (DeX too)" — AOSP frame-rate-category render cap
Panel scans 120 (HWC `vsyncRate=120`), but the **framework caps the external display's RENDER rate to 60**:
- `dumpsys display` → display 13 `mDesiredDisplayModeSpecs ... render: (0.0 60.0)` (render ceiling 60).
- `frameRateCategoryRate {normal=60.0, high=90.0}` — content voting "NoPreference/normal" renders at 60,
  "high" only 90. Most desktop UI votes normal → **60 Hz render** on a 120 Hz scanout.
- `settings put system min_refresh_rate/peak_refresh_rate 120` did NOT lift it (those steer the *internal*
  default display only).
- This is **AOSP frame-rate-category power-saving**, display-agnostic → **Samsung DeX behaves identically**.
  Not Moto, not the link, not the browser.

Enabled the live overlay to watch it: `service call SurfaceFlinger 1034 i32 1` (shows per-display Hz +
render rate on all screens incl. the monitor; disable with `... i32 0`).

### To actually force 120 render on external (untested)
Would need to defeat the category downclock — e.g., raise the external `frameRateCategory` rates or force
the render floor to 120 in the framework (another services.jar-class patch), or an SF frame-rate-override
property. Diminishing returns; the panel/link/patch already deliver 120 to anything that explicitly votes
for a high frame rate.

---

## 15. RESOLVED/CORRECTED: external desktop is ADAPTIVE 60↔120, not hard-capped
Correction to §14: the `render: (0.0 60.0)` reading was the **idle** snapshot. The external display uses
**adaptive frame pacing** (AOSP interaction/touch boost):
- **Idle → 60 Hz** (battery saving)
- **Mouse move / scroll / interaction → boosts to 120 Hz** (render range opens to `(0.0 120.0)`,
  SF `renderRate=120.00 Hz`; desktop layers vote `FrameRateCategory::HighHint`)
- User-confirmed: "move the mouse → 120, idle → 60."

So there is **no hard 60 cap** on the external desktop — native content renders at the full **120 Hz**
during actual interaction (when motion matters). The panel scans 120, SF composites 120, and the
framework adaptively drives 120 on activity.

### The ONLY remaining 60 is Chrome (browser), not the device
Blur Busters/TestUFO reports 60 because **Chrome caps web-page `requestAnimationFrame` to 60 fps on
secondary/external displays** (§13). That's a Chromium limitation, independent of everything we control.
Native apps / the desktop UI itself get the full 120.

### Final state of the external-display goal
- Software governor removed (framework patch, §11) ✅ persistent
- Link delivers up to 2560×1080@120 / 3440×1440@60 (§12) — DP-alt 2-lane / DP 1.4 ceiling
- System renders external at 120 adaptively (§15) ✅
- Browser web content stuck at 60 = Chromium, not fixable on-device
- Leftover tweaks: `peak_refresh_rate=120` left set (harmless, = panel max); refresh-rate overlay enabled
  (disable: `service call SurfaceFlinger 1034 i32 0`).

**Bottom line: 120 Hz on the external display is achieved for native content. Mission complete.**

---

## 16. CORRECTION to §13/§15: it's interaction-gated, NOT a Chrome cap
User-confirmed: **Blur Busters in Chrome DOES hit 120 when the mouse moves** (drops to 60 when idle).
So §13's "Chrome rAF capped at 60 on secondary displays" was WRONG. The real behavior:

- The external display refresh is **gated on user interaction** (AOSP touch/pointer boost), not on content.
- **Any interaction (mouse move/scroll) → whole display + all content (incl. Chrome) boosts to 120.**
- **Idle (no pointer input) → drops to 60**, even with a continuously-animating page like TestUFO.
- That's why TestUFO read 60 standalone (animation ≠ interaction) but jumps to 120 the moment you move the mouse.

It's uniform across browser and native content — purely the idle-downclock power-saving, not a per-app limit.

### To get SUSTAINED 120 (kill the idle downclock) — candidate levers
- `debug.sf.limit_special_idle_render` (currently `1`) → set `0`
- `ro.surface_flinger.use_content_detection_for_refresh_rate` (currently `true`) → `false`
  (read-only prop → needs Magisk `resetprop` at boot, or a system.prop module)
- These disable SurfaceFlinger's static-content/idle refresh lowering so it holds peak even when idle.
  Trade-off: higher power draw (the exact "battery saving" being defeated).
