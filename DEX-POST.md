# Comparison point for the DeX community: a Razr Fold's desktop mode runs an external monitor at 5120×2160 — AND above 60Hz (3440×1440@100)

DeX has been 60Hz-locked on external displays basically forever. So as a **comparison data point**, here's what I got out of a *different* Android desktop mode — Motorola's, on a Razr Fold 2026 (Snapdragon 8 Gen 5) — driving an LG 45GX950A (5K2K ultrawide). Not trying to tell anyone what DeX should do; just putting real numbers from another phone next to it.

## What the Razr's desktop mode runs externally

- **5120×2160 (5K2K) @ 60Hz**
- 3840×2160 (4K) @ 60Hz
- **3440×1440 @ 100Hz** ← *above 60*
- 2560×1080 @ 120Hz / 240Hz, 1920×1080 @ 120Hz

**Headline: 5K2K works, and the external refresh goes above 60Hz on current Snapdragon hardware.** If you've ever been told a phone desktop mode physically can't do high-refresh external, here's one that does.

## How

- The **>60Hz modes came straight from Moto's desktop mode** — no hacking, it just offers them.
- The **4K/5K2K *resolution* needed root.** Android has a hidden flag (`enable_mode_limit_for_external_display`) that caps external resolution to your phone's *own screen's* pixel count; I patched the compiled framework to remove it. (It's the same AOSP flag that holds stock Pixels around 1440p.)
- Honesty note on the Razr: the desktop *renders* adaptively — it idles around 60 and boosts to the panel's mode when you interact (mouse/scroll). So the panel runs a 100Hz mode, but TestUFO reads 60 unless you're moving the cursor. That's an AOSP frame-pacing thing; mentioning it so nobody thinks it's a magic constant-100.

## Where even the Razr stops — and this part is universal to phones

The hard ceiling is the **USB-C DisplayPort version**. Same monitor, **same exact cable and input**: plugged into a Mac it ran **5120×2160 @ 165Hz**; the phone on that identical cable maxed at 5K2K@60. Reading the DisplayPort AUX channel, the monitor handed the **phone DP 1.2** but the **Mac DP 2.1**. It's the source's USB-C controller.

Phones top out at **DisplayPort 1.4**, and **as of 2026 no phone ships DisplayPort 2.1** — UHBR (40/54/80 Gbps) needs a USB4/Thunderbolt-class USB-C controller, and no phone has one (they're all USB 3.2). So the really heavy modes (5K2K@120, 8K) aren't happening on *any* phone — that needs a laptop/PC port.

## One practical thing I measured: direct cable beats a dock

Through a Thunderbolt dock the Razr did **3440×1440@60**; on a **direct USB-C→DP cable** the *same phone + monitor* jumped to **5K2K@60 / 3440×1440@100**. A dock runs DisplayPort in 2-lane mode (keeping 2 lanes for USB), roughly halving your DP bandwidth. If you ever want max resolution/refresh out of a phone, a direct cable gets you more than a hub.

---

**Why I'm posting it here:** purely as a comparison. The Razr's desktop mode does **5K2K and above-60Hz external** on this generation of Snapdragon silicon. Make of that what you will. Genuinely curious — has One UI / DeX *ever* output above 60Hz to an external monitor? From the hardware side I saw, there's no reason a phone couldn't.
