import os

files_to_process = [
    (r"D:\APP\timestamp-camera\app\src\main\java\com\example\timestampcamera\ui\SettingsBottomSheet.kt", 
     r"D:\APP\timestamp-camera3\app\src\main\java\com\universalwatermark\ui\SettingsBottomSheet.kt"),
    (r"D:\APP\timestamp-camera\app\src\main\java\com\example\timestampcamera\ui\ReorderTextDialog.kt", 
     r"D:\APP\timestamp-camera3\app\src\main\java\com\universalwatermark\ui\ReorderTextDialog.kt"),
    (r"D:\APP\timestamp-camera\app\src\main\java\com\example\timestampcamera\ui\AddressResolutionDialog.kt", 
     r"D:\APP\timestamp-camera3\app\src\main\java\com\universalwatermark\ui\AddressResolutionDialog.kt")
]

for src, dest in files_to_process:
    with open(src, 'r', encoding='utf-8') as f:
        content = f.read()
    
    content = content.replace("com.example.timestampcamera", "com.universalwatermark")
    
    os.makedirs(os.path.dirname(dest), exist_ok=True)
    with open(dest, 'w', encoding='utf-8') as f:
        f.write(content)
        
print("Files copied and packages renamed successfully.")
