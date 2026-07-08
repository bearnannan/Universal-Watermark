# Changelog

## [2026-07-08 12:25]

- **Files Modified:** `app/build.gradle.kts`
- **Changes:**
  - อัปเดตหมายเลขเวอร์ชันของแอปพลิเคชันเป็นเวอร์ชัน 3.2.2 (`versionName = "3.2.2"`) และ `versionCode = 322`
- **Reason:** ออก Release ย่อยเพื่ออัปเดตการแก้ไขบั๊กระบบการจัดการแท็ก (Tags) ให้กับผู้ใช้งาน

## [2026-07-08 12:21]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/TagManagementDialog.kt`
- **Changes:**
  - ยกเลิกการใส่เครื่องหมาย `#` (Hashtag) อัตโนมัติเมื่อกดเลือกแท็ก (Tags) เพื่อให้ข้อความแสดงผลตามที่ผู้ใช้พิมพ์มาตรงๆ
  - เพิ่มปุ่มกากบาท (X) หรือปุ่มลบ (Close Icon) ท้ายแท็กแต่ละอันในหน้าต่างจัดการแท็ก เพื่อให้ผู้ใช้สามารถกดลบแท็กเก่าหรือแท็กที่ไม่ต้องการออกจากระบบได้อย่างอิสระ
- **Reason:** ผู้ใช้แจ้งปัญหาว่าระบบแท็กไม่ยอมให้ลบแท็กเก่าออกได้ และไม่ต้องการให้ระบบเติมเครื่องหมาย `#` นำหน้าข้อความเอง

## [2026-07-08 11:56]

- **Files Modified:** `app/build.gradle.kts`
- **Changes:**
  - ปรับปรุงหมายเลขเวอร์ชันของแอปพลิเคชันเป็นเวอร์ชัน 3.2.1 (`versionName = "3.2.1"`) และอัปเดต `versionCode` เป็น 321
- **Reason:** เตรียมความพร้อมสำหรับการออก Release ย่อย (Patch Release) เพื่ออัปเดตการแก้ไขบั๊กและฟีเจอร์ย่อย (การตั้งค่าฟอนต์และเปิดปิด Drop Shadow)

## [2026-07-08 09:47]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/ui/components/LivePreviewCard.kt`
  - `app/src/main/java/com/universalwatermark/engine/renderer/WatermarkRenderer.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/StyleSettingsScreen.kt`
- **Changes:**
  - เพิ่มตัวเลือกฟอนต์ `"Noto Sans Thai Looped"` เข้าไปในเมนู "การปรับแต่งข้อความ -> ฟอนต์ (Font)" ในหน้าจอการตั้งค่าดีไซน์ เพื่อให้ผู้ใช้เลือกได้เอง
  - ยกเลิกการบังคับใช้ฟอนต์ Noto Sans Thai Looped กับทุกลายน้ำ โดยปรับให้ทำงานเฉพาะเมื่อผู้ใช้เลือกฟอนต์นี้จากการตั้งค่าเท่านั้น
- **Reason:** ผู้ใช้ต้องการย้ายฟอนต์ภาษาไทย Noto Sans Thai Looped ไปเป็นตัวเลือกในหน้าการตั้งค่า เพื่อให้สามารถสลับไปใช้ฟอนต์อื่นๆ ได้ตามต้องการ

## [2026-07-08 09:12]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/engine/renderer/WatermarkDrawer.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/StyleSettingsScreen.kt`
- **Changes:**
  - เพิ่มสวิตช์เปิด-ปิด **"เงาตกกระทบ (Drop Shadow)"** ในหน้าต่างการตั้งค่าดีไซน์ (Style & Theme) เพื่อให้ผู้ใช้สามารถควบคุมการแสดงผลเงาของตัวอักษรได้ด้วยตัวเอง
  - ปรับปรุงการวาดเงา (Soft Black Drop Shadow) ในระบบเรนเดอร์ลายน้ำให้ทำงานสอดคล้องกับค่าการตั้งค่าจากผู้ใช้ (เดิมถูกตั้งค่าให้แสดงผลตลอดเวลาโดยไม่มีสวิตช์เปิดปิดใน UI)
- **Reason:** ผู้ใช้แจ้งว่าฟีเจอร์เงาตกกระทบตามที่ระบุในแพตช์ก่อนหน้าไม่สามารถใช้งานหรือมองเห็นได้ชัดเจน จึงเพิ่มตัวควบคุมเพื่อให้ปรับแต่งได้ตามความต้องการ

## [2026-07-08 08:34]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/screen/settings/SettingsScreen.kt`
- **Changes:**
  - อัปเดตไอคอนที่เลิกใช้งาน (Deprecated) อย่าง `Icons.Default.ArrowBack` และ `Icons.Default.KeyboardArrowRight` ไปใช้เวอร์ชัน `AutoMirrored` (`Icons.AutoMirrored.Filled.*`) แทน
  - เปลี่ยนการเรียกใช้คอมโพเนนต์ `Divider()` ที่ถูกยกเลิกไปเป็น `HorizontalDivider()` เพื่อให้เป็นไปตามมาตรฐานล่าสุดของ Jetpack Compose Material 3
- **Reason:** แก้ไขข้อความแจ้งเตือน (Deprecation Warnings) จากคอมไพเลอร์เพื่อรักษามาตรฐานของโค้ด

## [2026-07-08 08:33]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/screen/settings/SettingsScreen.kt`
- **Changes:**
  - แก้ไขข้อผิดพลาดทางไวยากรณ์ (Syntax Error) โดยการเพิ่มบล็อก `item { ... }` ที่หายไปครอบส่วน `SettingsCategoryItem` ของ "ระบบ (System & Output)"
- **Reason:** เพื่อแก้ปัญหา Build ล้มเหลวที่ Task `kspReleaseKotlin` (Expecting a top level declaration)

## [2026-07-03 17:35]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/engine/renderer/OverlayConfig.kt`
  - `app/src/main/java/com/universalwatermark/engine/renderer/WatermarkDrawer.kt`
  - `app/src/main/java/com/universalwatermark/engine/renderer/WatermarkRenderer.kt`
  - `app/src/main/java/com/universalwatermark/ui/components/LivePreviewCard.kt`
- **Changes:**
  - ปรับปรุง Watermark Canvas ให้รองรับฟอนต์ `Noto Sans Thai Looped` โดยการโหลดฟอนต์ผ่าน `ResourcesCompat` แล้วส่งต่อเข้าไปในระบบวาดลายน้ำ
  - บังคับใช้เงาตกกระทบ (Soft Black Drop Shadow) ให้กับลายน้ำเพื่อสร้างมิติให้ตัวอักษรลอยเด่นขึ้นจากภาพพื้นหลัง ตามแนวทางของ Design System อย่างเคร่งครัด
  - ซิงค์ระบบการวาดภาพลายน้ำ (WatermarkDrawer) ให้แสดงผลตรงกันเป๊ะแบบ WYSIWYG ระหว่าง Live Preview บนแอปและรูปภาพผลลัพธ์สุดท้าย
- **Reason:** ผู้ใช้ต้องการเปลี่ยนฟอนต์ของลายน้ำเพื่อให้อ่านภาษาไทยได้ชัดเจนและดูเป็นทางการ พร้อมกับทำลายน้ำให้ดูมีมิติมากขึ้น

## [2026-07-02 14:15]

- **Files Modified:**
  - `docs/privacy_policy.md` (New File)
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/SettingsScreen.kt`
- **Changes:**
  - สร้างไฟล์นโยบายความเป็นส่วนตัว (Privacy Policy) เป็นภาษาอังกฤษไว้ที่ `docs/privacy_policy.md` เพื่อเตรียมเผยแพร่ผ่าน GitHub Pages
  - เพิ่มปุ่มเมนู **"📜 นโยบายความเป็นส่วนตัว (Privacy Policy)"** ลงในหน้าต่างตั้งค่า (Settings) ซึ่งเมื่อกดแล้วจะเปิดเบราว์เซอร์พาไปยังลิงก์ `https://bearnannan.github.io/Universal-Watermark/privacy_policy` โดยอัตโนมัติ
- **Reason:** ผู้ใช้ต้องการเพิ่มหน้า Privacy Policy ให้รองรับการเผยแพร่แอปบนช่องทางที่ต้องการข้อตกลงความเป็นส่วนตัว

## [2026-07-02 12:30]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/ui/screen/dashboard/DashboardScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/dashboard/DashboardViewModel.kt`
  - `app/src/main/java/com/universalwatermark/data/SettingsRepository.kt`
- **Changes:**
  - ลบระบบจัดเก็บ "โปรไฟล์งาน (Workflow Profiles)" ออกจากแอปพลิเคชันทั้งหมด
  - ถอดถอนโค้ด UI ในหน้า Dashboard ที่ใช้เลือกและบันทึกโปรไฟล์
  - ลบ Data Class `WorkflowProfile` และตรรกะที่เกี่ยวข้องใน DataStore/ViewModel
  - ลบส่วนแสดงสถานะปัจจุบัน (Summary Card) ออกจากหน้า Dashboard เพื่อความมินิมอลตามหลัก Dark Tech UI Pro Max
- **Reason:** ผู้ใช้แจ้งว่าไม่มีความจำเป็นต้องใช้งานฟีเจอร์การบันทึกโปรไฟล์อีกต่อไป จึงทำการลบออกเพื่อลดความซับซ้อนของระบบและ UI

## [2026-07-02 12:15]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/ui/components/FloatingWidgetView.kt`
  - `app/src/main/java/com/universalwatermark/service/FloatingWidgetService.kt`
- **Changes:**
  - ย้ายตรรกะการคำนวณพิกัดการลาก (Drag) จากภายใน Compose ไปยัง `WindowManager` ของเซอร์วิส เพื่อป้องกันวิดเจ็ตถูกตัดขอบ (Clipping)
  - เพิ่มระบบ Edge Snapping ให้วิดเจ็ตดีดตัวเข้าขอบจออัตโนมัติเมื่อปล่อยนิ้ว
  - เพิ่มระบบ Auto-Dimming โดยจะลดแสง (Opacity) ของปุ่มเหลือ 40% อัตโนมัติเมื่อไม่มีการใช้งานเป็นเวลา 3 วินาที
  - อัปเกรดดีไซน์ของปุ่มให้เป็นสไตล์ Glassmorphism ตามมาตรฐาน Dark Tech UI Pro Max พร้อมเปลี่ยนไปใช้ไอคอนการตั้งค่า (Settings) แทนตัวอักษร
- **Reason:** ผู้ใช้แจ้งปัญหาการใช้งาน Assistive Touch (ทำงานไม่ดี) จึงได้ทำการออกแบบสถาปัตยกรรมระดับ Window และ UX ใหม่ทั้งหมด

## [2026-07-02 12:00]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/components/LivePreviewCard.kt`
- **Changes:**
  - เพิ่มการ Import `androidx.compose.foundation.clickable`
  - ลบการเรียกใช้งาน Namespace แบบเต็ม (`androidx.compose.foundation.clickable(...)`) บน `Modifier` ออก เพื่อให้ใช้งาน Extension Function ได้ถูกต้อง
- **Reason:** แก้ปัญหา Compile Error (`Unresolved reference 'androidx'`) ที่เกิดจากการเรียกใช้คำสั่ง `clickable` ผิดวิธีบน Jetpack Compose `Modifier`

## [2026-07-02 11:57]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/components/LivePreviewCard.kt`
- **Changes:**
  - เพิ่มการ Import `androidx.compose.foundation.layout.fillMaxHeight` ให้กับไฟล์
- **Reason:** แก้บั๊ก Compile Error (`Unresolved reference 'fillMaxHeight'`) เนื่องจากไม่ได้ทำการ Import ฟังก์ชันสำหรับควบคุม Layout ให้ถูกต้อง

## [2026-07-02 11:50]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/ui/components/LivePreviewCard.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/dashboard/DashboardScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/dashboard/DashboardViewModel.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/StyleSettingsScreen.kt`
- **Changes:**
  - สร้างระบบ Interactive 3x3 Grid ล่องหนทับบนภาพ Live Preview โดยตรง ผู้ใช้สามารถแตะที่มุมต่างๆ ของภาพเพื่อเปลี่ยนตำแหน่งลายน้ำได้ทันที (พร้อม UI Feedback เอฟเฟกต์ Ripple เมื่อแตะ)
  - เพิ่ม Hint แนะนำการใช้งาน "แตะเพื่อเปลี่ยนตำแหน่ง" บนหน้า Dashboard ให้ดูพรีเมียมตามหลัก Dark Tech UI Pro Max
  - ลบเมนูการตั้งค่าตำแหน่งในหน้า Style Settings ออก เพื่อหลีกเลี่ยงความซ้ำซ้อน
- **Reason:** ยกระดับประสบการณ์ผู้ใช้ (UX) ให้เป็นธรรมชาติและตอบสนองได้ทันทีโดยไม่ต้องเข้าไปค้นหาในหน้าการตั้งค่า

## [2026-07-02 11:35]

- **Files Modified:** `app/build.gradle.kts`
- **Changes:**
  - ปรับปรุงหมายเลขเวอร์ชันของแอปพลิเคชันเป็นเวอร์ชัน 3.0 (`versionName = "3.0"`) และปรับ `versionCode` เป็น 300
- **Reason:** อัปเดตเวอร์ชันของแอปพลิเคชันให้ตรงกับ Release v.3.0 บน GitHub ที่เพิ่งปล่อยออกไป

## [2026-07-02 11:09]

- **Files Modified:** `app/src/main/java/com/universalwatermark/ui/components/ProComponents.kt`
- **Changes:**
  - เพิ่มพารามิเตอร์ `enabled` ให้กับคอมโพเนนต์ `ProButton` เพื่อให้รองรับการเปิด-ปิดสถานะการกดได้เหมือนกับ `Button` มาตรฐาน
- **Reason:** แก้ปัญหา Compile Error (`No parameter with name 'enabled' found`) ที่เกิดขึ้นในหน้า `WorkflowSettingsScreen` หลังจากเปลี่ยนมาใช้งาน `ProButton`

## [2026-07-02 11:05]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/ui/screen/dashboard/DashboardScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/StyleSettingsScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/WorkflowSettingsScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/history/WatermarkHistoryScreen.kt`
  - `app/src/main/java/com/universalwatermark/ui/components/FloatingWidgetView.kt`
  - `app/src/main/java/com/universalwatermark/ui/components/ProComponents.kt`
- **Changes:**
  - ไล่เปลี่ยนการใช้งาน `Button` และ `Card` แบบมาตรฐาน ให้กลายเป็น `ProButton` และ `ProCard` เพื่อให้ครอบคลุมหน้าจอหลักทั้งหมด (ได้แก่ หน้าแดชบอร์ด, การตั้งค่าการออกแบบ, ข้อมูลงาน, หน้าประวัติ และวิดเจ็ตลอย)
  - ปรับปรุง `ProCard` ให้รองรับพารามิเตอร์ `elevation` สำหรับจัดการแสงเงาเพิ่มเติม
- **Reason:** ผู้ใช้ต้องการให้แอปมีความเป็นเนื้อเดียวกันทั้ง 100% ในด้านของ Design System (Dark Tech UI Pro Max)

## [2026-07-02 10:49]

- **Files Modified:** `app/build.gradle.kts`
- **Changes:**
  - ลบ Dependency `br.com.devsrsouza.compose.icons:lucide:1.1.1` ออกจากโปรเจกต์เนื่องจากไม่มีแพ็กเกจนี้อยู่จริงในไลบรารี (ไม่มีการเรียกใช้งานในโค้ด)
- **Reason:** แก้ปัญหา Gradle Build Failed (`Could not find br.com.devsrsouza.compose.icons:lucide:1.1.1`) ที่บล็อกการทำงานของแอปพลิเคชัน

## [2026-07-02 10:45]

- **Files Modified:**
  - `app/build.gradle.kts`
  - `app/src/main/res/values/font_certs.xml` [NEW]
  - `app/src/main/java/com/universalwatermark/ui/theme/Color.kt`
  - `app/src/main/java/com/universalwatermark/ui/theme/Theme.kt`
  - `app/src/main/java/com/universalwatermark/ui/theme/Type.kt` [NEW]
  - `app/src/main/java/com/universalwatermark/ui/components/ProComponents.kt` [NEW]
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/SystemSettingsScreen.kt`
- **Changes:**
  - อัปเกรด Design System ทั้งแอปพลิเคชันเป็นรูปแบบ **Dark Tech UI Pro Max**
  - เปลี่ยนชุดสี Color Palette เป็น Tinted Dark (Slate/Zinc) เช่น พื้นหลัง `#0F172A` และการ์ด `#1E293B`
  - อัปเดต Typography โดยดึงฟอนต์ `Inter` และ `Outfit` ผ่าน Google Fonts API (`ui-text-google-fonts`)
  - สร้าง Custom Components (`ProButton`, `ProCard`) พร้อมระบบ Tactile Feedback (Ripple + Scale Down) เมื่อถูกกด
  - นำ Design System แบบใหม่ไปประยุกต์ใช้กับหน้าจอตั้งค่าระบบ (`SystemSettingsScreen`) เป็นอันดับแรก
- **Reason:** ผู้ใช้ต้องการยกระดับ UI/UX ของแอปพลิเคชันให้เป็นเครื่องมือระดับโปร (Premium Tech) ตามมาตรฐานความสวยงามและการใช้งานขั้นสูง

## [2026-07-02 10:05]

- **Files Modified:**
  - `.github/ISSUE_TEMPLATE/bug_report.md` [NEW]
  - `.github/ISSUE_TEMPLATE/feature_request.md` [NEW]
  - `.github/pull_request_template.md` [NEW]
  - `docs/wiki/Home.md` [NEW]
- **Changes:**
  - สร้างเทมเพลตสำหรับใช้งานบน GitHub (Issue Templates สำหรับรายงานบั๊ก/ขอฟีเจอร์ใหม่ และ Pull Request Template)
  - สร้างไฟล์ตัวอย่างหน้า Wiki หน้าแรกสำหรับใช้อ้างอิงการทำงานของระบบ
- **Reason:** ผู้ใช้ต้องการตั้งค่าระบบโครงสร้างพื้นฐานสำหรับ Issue, Pull Request และ Wiki บน GitHub ของโปรเจกต์ Universal Watermark

## [2026-07-02 10:00]

- **Files Modified:** `.gitignore`
- **Changes:**
  - ปรับการตั้งค่าละเว้นไฟล์ (Ignore) ให้ครอบคลุมโฟลเดอร์ IDE ได้แก่ `/.idea/` และ `/.vscode/` แบบเต็มรูปแบบ
- **Reason:** เพื่อเตรียมความพร้อมในการเคลียร์ไฟล์ที่ไม่จำเป็นออกจาก Repository บน GitHub อย่างสมบูรณ์

## [2026-07-02 09:49]

- **Files Modified:** `README.md`
- **Changes:**
  - อัปเดตเนื้อหาในส่วนของ **Smart File Organization** โดยเพิ่มฟีเจอร์ **Auto-Cleanup** ที่ช่วยให้ลบรูปต้นฉบับอัตโนมัติ
  - เพิ่มข้อมูลสิทธิ์ `MANAGE_EXTERNAL_STORAGE` ลงในหัวข้อ **Required Permissions** สำหรับอุปกรณ์ Android 11+
- **Reason:** เพื่อให้เอกสารคู่มือครอบคลุมฟีเจอร์ล่าสุดที่เพิ่งถูกเพิ่มเข้าไปในโปรเจกต์

## [2026-07-02 09:46]

- **Files Modified:** `.gitignore`
- **Changes:**
  - อัปเดตไฟล์ `.gitignore` เพื่อยกเว้นการติดตามไฟล์นามสกุล `.md` (ยกเว้น `README.md` และ `CHANGELOG.md`), ไฟล์สคริปต์ `.py` รวมถึงไฟล์ขยะและไฟล์ชั่วคราวอื่นๆ (เช่น `.log`, `.tmp`)
- **Reason:** ผู้ใช้ต้องการตั้งค่าการละเว้นไฟล์ที่ไม่จำเป็นในการอัปโหลดขึ้น Repository อย่างถูกต้อง

## [2026-07-01 16:44]

- **Files Modified:**
  - `app/src/main/AndroidManifest.xml`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/SystemSettingsScreen.kt`
  - `app/src/main/java/com/universalwatermark/worker/WatermarkWorker.kt`
- **Changes:**
  - เพิ่มสิทธิ์ `MANAGE_EXTERNAL_STORAGE` (All Files Access) ใน Manifest เพื่อให้สามารถลบรูปถ่ายจากแอปกล้องหลักได้โดยไม่โดนบล็อก
  - เพิ่มเงื่อนไขตรวจสอบสิทธิ์และพาไปยังหน้าตั้งค่าของระบบ (Settings) อัตโนมัติหากผู้ใช้เปิดสวิตช์แต่ยังไม่เคยให้สิทธิ์นี้
  - `WatermarkWorker.kt`: แก้ไขข้อผิดพลาด (Crash) บนระบบปฏิบัติการ ColorOS (OPPO) ที่เกิดจาก `SystemUI` แสดงผลแจ้งเตือนไม่ได้ โดยเปลี่ยนไปใช้ไอคอนหลักของแอป (`R.mipmap.ic_launcher`) และนำ `bigLargeIcon(null)` ออก
- **Reason:** แก้ปัญหาระบบไม่สามารถลบไฟล์ภาพ (เกิด RecoverableSecurityException) บน Android 10+ และแก้ปัญหา SystemUI Crash ตอนแสดงผล Notification

## [2026-07-01 16:38]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/engine/renderer/WatermarkRenderer.kt`
  - `app/src/main/java/com/universalwatermark/service/WatermarkMonitorService.kt`
- **Changes:**
  - `WatermarkRenderer.kt`: เปลี่ยนไปใช้ `ImageDecoder` สำหรับ Android 9 ขึ้นไป และเพิ่มการจัดการ EXIF Orientation เพื่อแก้ปัญหารูปหมุนผิดทิศทาง และปรับ JPEG Compression เป็น 100%
  - `WatermarkMonitorService.kt`: เพิ่มการหน่วงเวลา (Initial Delay) เป็น 5 วินาที เพื่อให้เวลากล้องทำการประมวลผล HDR เสร็จสมบูรณ์ก่อนใส่ลายน้ำ
- **Reason:** ผู้ใช้แจ้งปัญหาภาพที่ได้มีความคมชัดลดลง ภาพซอฟต์และเบลอกว่าต้นฉบับ เนื่องจากแอปดึงภาพพรีวิวความละเอียดต่ำมาประมวลผลก่อนที่กล้องจะประมวลผลภาพจริงเสร็จ

## [2026-07-01 16:30]

- **Files Modified:**
  - `app/src/main/java/com/universalwatermark/data/SettingsRepository.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/SettingsViewModel.kt`
  - `app/src/main/java/com/universalwatermark/ui/screen/settings/SystemSettingsScreen.kt`
  - `app/src/main/java/com/universalwatermark/worker/WatermarkWorker.kt`
- **Changes:**
  - เพิ่มฟีเจอร์ตัวเลือกการลบรูปภาพต้นฉบับในหน้าต่างการตั้งค่า
  - เพิ่ม `deleteOriginalPhoto` ลงใน `SettingsRepository` และ DataStore
  - ทำการตรวจสอบตัวเลือกก่อนสั่งลบไฟล์ภาพต้นฉบับผ่าน ContentResolver ภายใน `WatermarkWorker.kt`
- **Reason:** ผู้ใช้ต้องการปุ่มเปิด-ปิดเพื่อให้แอปพลิเคชันลบรูปถ่ายต้นฉบับโดยอัตโนมัติเมื่อทำการใส่ลายน้ำเสร็จสิ้น
