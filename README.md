# 📸 Universal Watermark

![App Icon](app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp) <!-- You can replace this with your actual badge/logo link -->

**Universal Watermark** is a professional-grade Android camera application designed specifically for field workers, inspectors, and professionals who need to document their work with precise metadata. The application seamlessly imprints critical information—such as GPS coordinates, timestamps, altitude, and custom notes—directly onto photographs in real-time.

---

## 🌟 Key Features

### 1. 💧 Advanced Real-Time Watermarking
- **Customizable Layouts:** Users can toggle and reorder exactly what information appears on the photo.
- **Rich Metadata Extraction:** Automatically pulls EXIF data, GPS location (Latitude/Longitude), Altitude, Speed, and Compass Azimuth.
- **Project & Inspector Details:** Add specific workflow info like "Project Name," "Inspector Name," "Tags," and custom "Notes."
- **Typography & Styling:** Change text colors, fonts (Roboto, Oswald, etc.), sizes, background opacity, and text stroke (outlines) for maximum readability in any lighting condition.

### 2. 🗂️ Smart File Organization
- **Dynamic Folders:** Photos are automatically saved into intelligently named subfolders based on the active "Note" or "Project Name".
- **Dynamic Filenames:** The file name itself automatically adapts to the context, appending the Note and a readable timestamp (e.g., `SiteA_20260621_143000.jpg`).
- **MediaStore Integration:** Fully compliant with Android 10+ Scoped Storage, saving efficiently into the `Pictures/UniversalWatermark` directory.

### 3. 🎯 AssistiveTouch (Floating Widget)
- **Always-on-Top Widget:** A floating `Service`-based widget that hovers over the camera viewfinder.
- **Quick Settings:** Allows users to change the "Note" or "Tags" on the fly without diving deep into the settings menu.
- **Saved Notes History:** Includes a quick-picker (Chips) for recently used notes.

### 4. 🎨 Premium "Dark Tech" UI (Monogram Stamp Concept)
- **Sleek Aesthetics:** Entirely built on a custom Jetpack Compose `UniversalWatermarkTheme`.
- **Deep Dark Mode:** Utilizes `#121212` backgrounds with striking **Vibrant Orange** and **Teal** accents for a highly professional and modern feel.

### 5. 📷 Professional Camera Capabilities
- **CameraX API:** Built on Android's robust CameraX library ensuring device compatibility.
- **Controls:** Toggle Grid lines (3x3, Golden Ratio), Shutter Sounds, Flip Front Camera, and various resolutions/aspect ratios.
- **Hardware Integration:** Use Volume Keys as a physical shutter button.

---

## 🛠️ Technology Stack

The app is built using modern Android development practices and the latest libraries:

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (100% Declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture principles.
- **Dependency Injection:** [Hilt / Dagger](https://dagger.dev/hilt/)
- **Camera Framework:** [CameraX](https://developer.android.com/training/camerax) (Lifecycle-aware camera integration)
- **Asynchronous Processing:** Kotlin Coroutines & Flow
- **Background Processing:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) (for heavy watermark rendering off the main thread)
- **Local Storage:** [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Preferences)
- **Location Services:** FusedLocationProviderClient (Google Play Services)

---

## 📂 Project Structure

```text
com.universalwatermark/
│
├── data/           # Repositories, DataStore logic, and Models (CameraSettings)
├── di/             # Hilt Dependency Injection Modules
├── domain/         # Use cases and core business logic
├── engine/         # Watermark rendering engine, Bitmap processing, Exif extraction
├── service/        # Floating Widget Service (WindowManager), ContentObservers
├── ui/             # Jetpack Compose UI (Screens, Components, Theme, Navigation)
├── util/           # Helper functions, Permissions, Location logic
└── worker/         # WorkManager classes (WatermarkWorker) for background image saving
```

---

## 🔐 Required Permissions

For the app to function properly, it requests the following permissions:
- `CAMERA`: To capture photos.
- `ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION`: To embed GPS data into the watermark and EXIF.
- `SYSTEM_ALERT_WINDOW`: To draw the AssistiveTouch floating widget over the screen.
- `READ/WRITE_EXTERNAL_STORAGE` or `READ_MEDIA_IMAGES`: To save and manage photos in the Gallery.
- `FOREGROUND_SERVICE`: To keep the floating widget alive in the background.

---

## 🚀 Setup & Installation (For Developers)

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/universal-watermark.git
   ```
2. **Open in Android Studio**
   - Ensure you are using Android Studio Iguana (or newer).
   - Sync the Gradle project.
3. **Build & Run**
   - Connect a physical Android device or start an Emulator (API 26+ recommended).
   - Press **Run** (`Shift + F10`).

---

## 📜 License

Copyright (c) 2026 WATCHARA MANADEE. All Rights Reserved.

**PERSONAL / PROPRIETARY LICENSE**

This software and its associated documentation files are the proprietary property of the author. You may NOT use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software without explicit written permission.
