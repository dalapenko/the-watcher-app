# The Watcher Mobile App [Android]

<img src="https://user-images.githubusercontent.com/30049446/222979545-77666bc7-2f8d-4a8e-877a-3ac7a32f9ed9" width="118" height="250" />

## Description:

Simple app in education purposes.

MVVM architecture, Retrofit+Room (without Dagger). Single activity. Kotlin

## Building:

Before build app add token in local.gradle file as `tmdb.token={value}`.

Then just use IDE interface or command `./gradlew app:assembleDebug` and install app from `app/build/output/apk/debug` dir with drag&drop or ADB

### Note:

Because in app used TMDB api - application may not work in Russia without VPN.

### Screenshots:

