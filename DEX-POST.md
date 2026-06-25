# Why your DeX desktop "feels like 60Hz," why external resolution gets capped, and the hard DisplayPort ceiling on ALL phones — a deep dive

I just spent way too long reverse-engineering how Android phones drive external displays in desktop mode. I did it on a Motorola foldable (rooted, decompiled the framework, even edited the devicetree), **but almost everything I found is AOSP-level and applies directly to Samsung DeX** — it finally explained a bunch of DeX behavior I'd always wondered about. Sharing in case it helps the DeX crowd.

**TL;DR:**
1. The "60Hz feel" on a high-refresh monitor is **adaptive frame-pacing**, not a real cap — it boosts when you interact and idles down to 60. DeX does exactly this.
2. Android can **cap external resolution to your phone's own screen pixel-count** (an AOSP flag). Samsung tunes around it for DeX, but it's the underlying mechanism.
3. **Every phone's USB-C tops out at DisplayPort 1.4-class.** You will never get DP-2.1 modes (5K2K@120, etc.) out of a phone — it's the USB-C controller, not the OS. Docks make it worse.

---

## 1. "Why does my 144Hz monitor feel like 60Hz in DeX?"

Because it mostly *is* 60 — by design. Android uses **frame-rate-category pacing**: UI that isn't explicitly asking for high frame rate renders at the "normal" category (60Hz on most setups), and the system **boosts to the panel's max only when you're actively interacting** (mouse move, scroll). Idle → drops back to 60 to save power.

I verified this on the framework side: the external display's render rate sat at a 60Hz ceiling when idle and opened up to 120 the instant the cursor moved. **TestUFO / Blur Busters will read 60** on an external display unless you're literally moving the mouse during the test — that's not the monitor or the cable, it's the OS frame pacing. **DeX behaves identically because it's the same AOSP mechanism.** So your desktop is smoother than the "60" you measured — it's just only spending the power when there's motion.

(There's no clean user toggle for this; forcing always-max-refresh needs root-level surface-flinger tweaks, and even then it's a battery hit for little gain.)

## 2. The hidden external-resolution cap

AOSP has a flag — `enable_mode_limit_for_external_display` — that **drops any external mode whose pixel count exceeds your phone's internal panel.** On a stock Pixel this is why external displays often cap around 1080p/1440p. Samsung clearly tunes DeX to allow more (flagships do 4K external), but the AOSP governor is the baseline mechanism, and it's why "my monitor does 4K but my phone only offers 1440p" happens on a lot of devices.

On my phone I had to patch the compiled framework (root) to lift it — but the takeaway for DeX users is just: **if your phone won't offer your monitor's full resolution, that's often a software policy tied to your phone's screen size, not a hardware limit.**

## 3. The real ceiling: phone USB-C is DisplayPort 1.4-class, period

This is the big one, and it's universal. DisplayPort version is negotiated at the **USB-C Alt-Mode layer by the source (your phone)**, before the video link even starts. Phones ship USB-C controllers that advertise **DisplayPort 1.4 at best** (recent Samsung flagships: DP 1.4 = HBR3 + DSC, which is genuinely good — that's how DeX does 4K@120 with DSC over a solid cable). Older/cheaper phones are DP 1.2 (HBR2, no DSC, ~4K@60 max).

But **no phone does DisplayPort 2.1 (UHBR)** — that needs a newer USB-C/Thunderbolt PHY tier that phones don't carry. I proved this the hard way: plugged my monitor into a Mac with the **exact same cable and input** → the Mac drove it at **5120×2160 @ 165Hz** (DP 2.1). The phone on that same cable maxed at 5K2K@60, and reading the DisplayPort AUX channel showed the monitor handing the **phone DP 1.2** but the **Mac DP 2.1**. It's the source's USB-C silicon. Nothing in software — not the OS, not root, not even a custom kernel — changes the DP version your USB-C controller advertises.

**So the hard rule for any phone (DeX included): you get up to DP-1.4 modes (4K@120/DSC on good hardware), and that's the wall. The really heavy modes need a laptop/PC with a DP-2.1 or Thunderbolt port.**

## 4. The most actionable tip: direct cable beats a dock

Your phone has **one USB-C port and 4 high-speed lanes.** A dock/hub usually runs DisplayPort in **2-lane mode** so it can keep 2 lanes for USB data — which **halves your DisplayPort bandwidth.** I measured it: through a Thunderbolt dock I got 3440×1440@60; on a **direct USB-C→DisplayPort cable** the *same phone+monitor* jumped to 5120×2160@60 / 4K@60 / 3440×1440@100.

For DeX: if you want max resolution/refresh, **use a direct USB-C→DP cable.** If you need the USB hub (keyboard/mouse/storage), the dock costs you display bandwidth. You can't have full-bandwidth display *and* the hub on one port — physics of the lane split. (A dock with **DisplayPort 2.1 / dual-lane DSC** handling helps, but you're still bounded by the phone being a DP-1.4 source.)

---

## Takeaways for DeX users
- **"60Hz feel" = adaptive pacing.** It boosts on interaction; idle is 60 to save battery. Same on every Android. Not a defect.
- **Resolution caps** can be an OS policy tied to your phone's screen size, not the monitor.
- **Direct USB-C→DP cable > dock** for max resolution (docks halve DP bandwidth via lane-splitting).
- **Phones are DP-1.4-class max.** Good DeX phones do 4K@120 w/ DSC over a direct cable; nothing phone-side reaches DP-2.1 territory (5K2K@120, 8K). That needs a real PC port.

Did all the gory hardware-level digging on a Moto foldable, but the frame-pacing, the resolution-cap mechanism, the DP-version ceiling, and the dock-vs-cable bandwidth split are all things that shape the DeX experience too. Happy to go deeper on any of it.
