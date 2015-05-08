# Android WebView app template

## Prerequisites

* Oracle JDK 8 or OpenJDK 8
* Android SDK
* Gradle
* Environment variable of `ANDRIOD_HOME`

## How to build

Install Android SDK Tools revision 24.x,
Android SDK Platform-tools revision x,
Android SDK Build-tools revision 22.x.y,
SDK Platform Android 5.1.1 API 22 revision x,
Android Support Repository revision x,
Android Support Library revision 22.x.y and
Google Play Services revision x.

```sh
android list -u -a
#=>
#Packages available for installation or update: 138
#   1- Android SDK Tools, revision 24.2
#   2- Android SDK Platform-tools, revision 22
#   3- Android SDK Build-tools, revision 22.0.1
#   4- Android SDK Build-tools, revision 22 (Obsolete)
# ...
#  21- Documentation for Android SDK, API 22, revision 1
#  22- SDK Platform Android 5.1.1, API 22, revision 2
#  23- SDK Platform Android 5.0.1, API 21, revision 2
# ...
# 125- Sources for Android SDK, API 14, revision 1 (Obsolete)
# 126- Android Support Repository, revision 14
# 127- Android Support Library, revision 22.1.1
# 128- Google AdMob Ads SDK, revision 11 (Obsolete)
# ...
# 131- Google Play services for Froyo, revision 12 (Obsolete)
# 132- Google Play services, revision 24
# 133- Google Repository, revision 17
# ...

android update sdk -u -a -t 1,2,3,22,126,127,132
```

Prepare your product flavor to `app/build.gradle`.

e.g.:

```diff
  ...
  android {
      compileSdkVersion 22
      buildToolsVersion "22.0.1"
  
      defaultConfig {
          applicationId "com.example.app"
          minSdkVersion 15
          targetSdkVersion 22
          versionCode 1
          versionName "1.0"
      }
  
      compileOptions {
          sourceCompatibility JavaVersion.VERSION_1_6
          targetCompatibility JavaVersion.VERSION_1_6
      }
      buildTypes {
          release {
              minifyEnabled false
              proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
          }
      }
  
+     productFlavors {
+         mysampleapp {
+             applicationId "net.kaosfield.webview"
+         }
+     }
  }
  ...
```

```sh
./gradlew build
```

## License

[MIT](http://opensource.org/licenses/MIT)

Copyright (C) 2015 ka
