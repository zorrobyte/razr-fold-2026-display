# I rooted my Razr Fold 2026 to drive a 5K2K ultrawide off Desktop Mode — here's how far you can actually push a phone's DisplayPort output (and the hard wall you hit)

**TL;DR:** Motorola caps external-display resolution in software *and* the phone's USB-C only does DisplayPort 1.2. With root + a framework patch + a direct DP cable I got **5120×2160 @ 60Hz / 4K@60 / 3440×1440@100** out of a Razr Fold's desktop mode. Higher refresh (5K2K@120) is **physically impossible** on this phone — proven below. Long writeup because the rabbit hole was *deep*.

---

## The setup

- **Phone:** Motorola Razr Fold 2026 (Snapdragon 8 Gen 5, Android 16), Verizon model
- **Monitor:** LG 45GX950A — 45" bendable OLED, **5120×2160 (5K2K) ultrawide, 165Hz**, DisplayPort 2.1
- **Goal:** use Moto's built-in desktop mode ("Smart Connect") to run the monitor as big and smooth as possible

Plugged it in expecting glorious ultrawide. Got **3440×1440@60**. The monitor does 5K2K@165. So... where'd it all go?

---

## Discovery #1: Android secretly caps external displays to your phone's screen size

Digging through `dumpsys` and decompiling the framework, I found the culprit — an AOSP feature flag:

> `enable_mode_limit_for_external_display` — *"Feature limiting external display resolution and refresh rate"*

It's enforced in `DisplayModeDirector` (system_server) and it **drops any external mode whose pixel count exceeds your internal panel's**. The Razr's inner screen is 2232×2484 ≈ **5.54 megapixels**, so:

- 3440×1440 = 4.95 MP → ✅ allowed
- 4K (3840×2160) = 8.3 MP → ❌ **filtered out**
- 5K2K (5120×2160) = 11 MP → ❌ **filtered out**

This is why **Pixels cap external displays at ~1440p too** — it's an AOSP power/thermal policy, not a Moto thing. The flag is read-only and baked into the build, so you can't just toggle it. You have to **change the compiled framework** — which means root.

## Discovery #2: unlocking a Verizon bootloader (the part everyone says is impossible)

Verizon Motorola = famously locked bootloaders. But the OEM-unlock toggle wasn't greyed out, so I tried the official Moto unlock-data flow… and it **went through**. `fastboot oem unlock`, factory wipe, done. (YMMV wildly by model/carrier — this surprised me.)

Then: extracted `init_boot.img` from the stock firmware, patched it with Magisk, `fastboot flash init_boot` (had to use **fastbootd**, not bootloader mode — Moto rejects Magisk-patched images with "Preflash validation failed" otherwise). Root achieved.

## Discovery #3: killing the cap

LSPosed was a dead end (the daemon couldn't load modules on this Android 16 build — a known mount-namespace issue, ReZygisk didn't help either). So I went straight at the framework: **decompiled `services.jar`, patched the one method** `DisplayManagerFlags.isExternalDisplayLimitModeEnabled()` to return `false`, repacked it, and served it as a **Magisk module** (with the stale dexopt files whiteout'd so ART loads the patched jar).

Booted clean. **The governor was gone.** Now the phone would *accept* 4K/5K2K — if the cable could deliver them.

## Discovery #4: the cable matters more than you think

- **Through a Thunderbolt dock:** max 3440×1440@60. The dock runs DisplayPort in **2-lane** mode (keeping 2 lanes for USB data), which halves bandwidth.
- **Direct USB-C→DP cable:** boom — **5120×2160@60, 4K@60, 3440×1440@100** all showed up. 4-lane DP-alt.

So the framework patch + a direct cable = **native 5K2K on a phone.** 🎉 The desktop even adaptively boosts to ~100/120Hz when you move the mouse (idle drops to 60 to save power — that's why TestUFO reads 60 unless you're interacting; it's the same on Samsung DeX).

---

## The wall: why it stops at 60Hz, and why no amount of hacking fixes it

I *really* wanted 5K2K@120. That needs **DSC** (Display Stream Compression). The phone's chip supports DSC (it's used on the internal screen). So I went deep:

- Found the DisplayPort caps live in the **devicetree** (`dtbo`): a 675 MHz pixel-clock limit and an `hbr3-disable` flag (forcing the slower HBR2 link rate). Edited the dtbo, reflashed…
- Raising the pixel-clock cap: **no effect.** Enabling HBR3: **broke the link to 1080p.** Removing the "downgrade" flag: **no effect.**

Then the decisive test: **I plugged the monitor into my Mac using the exact same cable and input.** The Mac drove it at **5120×2160 @ 165Hz**. Same cable. Same port. So the monitor and cable are 100% capable.

The difference is the **source**. Reading the DisplayPort AUX channel, the monitor reports **DPCD revision 1.2** to the phone — but **DP 2.1** to the Mac. That's the whole story:

> **DisplayPort version is negotiated at the USB-C Alt-Mode layer, by the source, before the video link even starts. The phone's USB-C controller advertises DisplayPort 1.2. The Mac's advertises DP 2.1.**

DP 1.2 = HBR2 max, **no DSC, no high-refresh-at-high-res.** And this lives in the **Type-C/USB-PD firmware**, underneath the display driver, underneath the devicetree, underneath even kernel source. **Nothing in software can change the DP version your USB-C silicon advertises.** HBR3 kept failing because the link was DP 1.2 the whole time.

That's the real ceiling. Not Motorola being cheap, not the cap, not the cable — **phones ship DP 1.2/1.4-class USB-C DisplayPort, and this monitor wants a DP 2.1 source to go past 5K2K@60.**

---

## What you can actually take away

- **Your phone caps external resolution to its own screen's pixel count.** That's AOSP, not just Moto/Samsung. Root + a framework patch removes it; without root you're stuck around 1440p-class.
- **Use a direct USB-C→DisplayPort cable, not a dock,** if you want max resolution. Docks split the USB-C lanes and roughly halve your DisplayPort bandwidth. (Dock = USB hub but lower res; direct cable = higher res but no hub. One USB-C port, pick one.)
- **Phone DisplayPort tops out around DP 1.2/1.4.** You will not get DP-2.1 modes (5K2K@120+, etc.) out of a phone, no matter what — it's USB-C controller firmware. A laptop/desktop with a real DP 2.1 / Thunderbolt port will.
- **The "60Hz desktop" feeling** on external displays is adaptive frame-pacing — it boosts on interaction. Browser web pages stay 60 unless you're actively moving the cursor. That's AOSP, same on DeX.

Final achieved state on the Razr Fold: **5120×2160@60 native ultrawide off a phone's desktop mode.** Took a bootloader unlock, root, a hand-patched Android framework, and a lot of devicetree spelunking to learn exactly where the floor is — but it works, and now I know precisely why it can't go further.

*Happy to answer questions on any of the steps.*
