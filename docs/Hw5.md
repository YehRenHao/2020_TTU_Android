# 技巧

## 要存取SD card檔案，在xml中須加上
``` XML
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="{your.package.name}"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/provider_paths" />
</provider>
```
在xml之中，有provider_paths，這是要控制你可以存取的位置
``` XML
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <root-path name="external_files" path="/storage/"/>
</paths>
```
等效於/storage/emulated/0/Android/data/{your.package.name}/files
因此你的檔案需要放在這邊
## 取出想要的檔案
``` JAVA
File file = new File(getExternalFilesDir(null), "XXX.xxx");
// getExternalFilesDir(null) = /storage/emulated/0/Android/data/{your.package.name}/files
it.setDataAndType(FileProvider.getUriForFile(this, getPackageName() + ".provider", file), "xxx/*");
it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
```