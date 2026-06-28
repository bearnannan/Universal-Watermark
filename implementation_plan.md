# แผนการพัฒนา: ระบบลายเซ็นดิจิทัลประจำเครื่อง (Hardware-backed Digital Signature)

การพัฒนาระบบนี้มีจุดประสงค์เพื่อยืนยันว่ารูปภาพที่ถ่ายจากแอปพลิเคชัน **Universal Watermark** ไม่ได้ถูกดัดแปลง และถูกสร้างขึ้นจากอุปกรณ์นั้นๆ จริงๆ โดยอาศัยความปลอดภัยระดับฮาร์ดแวร์ (Android Keystore) ในการสร้างคู่กุญแจ (Key Pair) และทำการเข้ารหัสลายเซ็นดิจิทัล (Digital Signature) ฝังลงไปใน Metadata (EXIF) ของรูปภาพ

## User Review Required

> [!IMPORTANT]
> **EXIF Tag Selection:** เนื่องจากเราต้องบันทึกข้อมูล 3 ส่วนลงในรูปภาพ (1. SHA-256 Hash, 2. Digital Signature, 3. Public Key) เราจะทำการเก็บข้อมูลทั้งหมดในรูปแบบ JSON string และบันทึกลงใน EXIF Tag ชื่อ `UserComment` หรือ `ImageDescription` (เนื่องจากเป็น Tag ที่อนุญาตให้เก็บ String ยาวๆ ได้อย่างปลอดภัยและมาตรฐาน) รบกวนยืนยันว่าเห็นด้วยกับการใช้ JSON format ใน EXIF หรือไม่?

> [!WARNING]
> **Performance Impact:** การคำนวณ Hash ของรูปภาพ (ซึ่งมีขนาดหลาย MB) และการทำ Digital Signature ต้องอ่านข้อมูลรูปภาพอีกครั้งหลังจาก Render เสร็จ อาจทำให้ระยะเวลาประมวลผลเพิ่มขึ้นเล็กน้อย (ระดับมิลลิวินาที) แต่เนื่องจากโค้ดรันอยู่ใน `WatermarkWorker` (Background thread) อยู่แล้ว จึงไม่น่ากระทบต่อ UI ของผู้ใช้งาน

## Open Questions

> [!NOTE]
> 1. **Public Key Storage:** คุณต้องการบันทึก Public Key ลงใน EXIF ของทุกรูปภาพเพื่อความสะดวกในการตรวจสอบแบบ Offline ทันที หรือต้องการให้แอปเก็บ Public Key ไว้แค่ใน Database ภายในแล้วส่งขึ้น Server ของคุณในอนาคตครับ? (ในแผนนี้ผมออกแบบให้ฝังลงไปใน EXIF ก่อนเพื่อความสมบูรณ์ในตัวของรูปภาพครับ)
> 2. **Algorithm:** ผมจะเลือกใช้ `SHA256withECDSA` (Elliptic Curve) แทน RSA เพราะ Key มีขนาดเล็กกว่ามากและประมวลผลเร็วกว่า เหมาะกับการฝังลงใน EXIF คุณโอเคกับอัลกอริทึมนี้หรือไม่?

## Proposed Changes

---

### 1. Crypto Engine (ระบบเข้ารหัสและลายเซ็น)
ส่วนนี้จะรับผิดชอบการสร้างคู่กุญแจใน Android Keystore และจัดการการ Hash/Sign ข้อมูล

#### [NEW] [CryptoManager.kt](file:///d:/APP/Universal%20Watermark/app/src/main/java/com/universalwatermark/engine/crypto/CryptoManager.kt)
- คลาสใหม่สำหรับจัดการ `java.security.KeyStore` โดยเฉพาะ
- ฟังก์ชัน `getOrCreateKeyPair()` สำหรับดึง Key Pair ถ้ามีอยู่แล้ว หรือสร้างใหม่ถ้ายังไม่มี
- ฟังก์ชัน `generateHash(imageBytes: ByteArray): String` สำหรับสร้าง SHA-256
- ฟังก์ชัน `signData(data: String): String` สำหรับใช้ Private Key เซ็นข้อมูล
- ฟังก์ชัน `getPublicKeyBase64(): String` สำหรับดึง Public Key ไปใช้งาน

#### [NEW] [CryptoModule.kt](file:///d:/APP/Universal%20Watermark/app/src/main/java/com/universalwatermark/di/CryptoModule.kt)
- Hilt Module สำหรับ Inject `CryptoManager` เข้าไปใช้งานในคลาสอื่นๆ เช่น `WatermarkWorker`

---

### 2. EXIF Manipulation (ระบบจัดการ Metadata)
ปรับปรุงการเขียนข้อมูลลงในรูปภาพให้รองรับการเขียน String ขนาดยาวลงใน EXIF ของไฟล์ภาพผลลัพธ์

#### [NEW] [ExifWriter.kt](file:///d:/APP/Universal%20Watermark/app/src/main/java/com/universalwatermark/engine/metadata/ExifWriter.kt)
- คลาสใหม่สำหรับเขียนข้อมูลกลับลงไปในไฟล์รูป
- ฟังก์ชัน `writeSignatureToExif(file: File, signatureJson: String)` โดยใช้ `androidx.exifinterface.media.ExifInterface`

---

### 3. Worker (ระบบประมวลผลหลัก)
แก้ไขระบบ Workflow การทำงานเบื้องหลัง ให้รองรับขั้นตอนการทำ Hash และ Sign

#### [MODIFY] [WatermarkWorker.kt](file:///d:/APP/Universal%20Watermark/app/src/main/java/com/universalwatermark/worker/WatermarkWorker.kt)
- Inject `CryptoManager` เข้ามา
- **หลังจากการ Render เสร็จสมบูรณ์:**
  1. อ่านไฟล์รูปภาพผลลัพธ์ที่อยู่ใน MediaStore
  2. คำนวณ SHA-256 Hash
  3. สั่ง `CryptoManager.signData(hash)`
  4. สร้าง JSON Payload `{ "hash": "...", "signature": "...", "publicKey": "..." }`
  5. บันทึก JSON ลงใน EXIF ของไฟล์รูปผ่าน `ExifWriter`
- ปรับปรุงการบันทึกข้อมูล `WatermarkHistoryEntity` ให้เก็บ Signature นี้ลงใน Local DB ด้วย เพื่อใช้ในการอ้างอิง

## Verification Plan

### Automated Tests
- เขียน Unit Test สำหรับ `CryptoManager.kt` เพื่อตรวจสอบว่า `signData` และกระบวนการ Verify สามารถตรวจสอบลายเซ็นได้ถูกต้อง 100%
- ทดสอบว่าหากข้อมูลมีการเปลี่ยนสลับตัวอักษรแค่ตัวเดียว (Simulate การ Tampering) การตรวจสอบ (Verify) จะต้องล้มเหลวทันที

### Manual Verification
1. ถ่ายรูปผ่านแอปและรอให้รูปถูกบันทึกสำเร็จ
2. ดึงรูปออกมาจากเครื่อง (ผ่านสาย USB หรือแชร์) นำไปตรวจสอบ EXIF ผ่านเว็บ (เช่น [jimpl.com](https://jimpl.com) หรือ ExifTool) 
3. ยืนยันว่าพบ Tag `UserComment` หรือ `ImageDescription` ที่มีข้อมูล JSON พร้อม Hash และ Signature
4. นำรูปภาพเดียวกันไปเปิดใน Photoshop ลากพู่กัน 1 ขีด แล้ว Save เพื่อให้รูปเปลี่ยนไป นำรูปที่เปลี่ยนไปมาคำนวณ SHA-256 จะต้องพบว่าค่า Hash ไม่ตรงกับที่บันทึกไว้ใน EXIF ถือเป็นการแจ้งเตือนว่า "รูปถูกปลอมแปลง" สำเร็จ
