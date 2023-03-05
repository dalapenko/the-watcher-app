# The Watcher Mobile App [Android]

<p align="center">
    <img width="118" height="250" src="https://user-images.githubusercontent.com/30049446/222979877-79646ad4-572f-41cb-9ed0-7b461e8785ed.png#gh-dark-mode-only">
    <img width="118" height="250" src="https://user-images.githubusercontent.com/30049446/222979906-382c0e28-ca84-49f3-89cb-919312abd260.png#gh-dark-mode-only">
    <img width="118" height="250" src="https://user-images.githubusercontent.com/30049446/222980109-57420e6a-b971-47cf-99ba-258c9459d167.png#gh-light-mode-only">
    <img width="118" height="250" src="https://user-images.githubusercontent.com/30049446/222980110-d4bc355e-dd0d-42a5-8d57-94ec97f2cfb1.png#gh-light-mode-only">
</p>



## Description:

Simple app in education purposes.

MVVM architecture, Retrofit+Room (without Dagger). Single activity. Kotlin

## Building:

Before build app add token in local.gradle file as `tmdb.token={value}`.

Then just use IDE interface or command `./gradlew app:assembleDebug` and install app from `app/build/output/apk/debug` dir with drag&drop or ADB

### Note:

Because in app used TMDB api - application may not work in Russia without VPN.