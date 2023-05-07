# <img src="./readme-img/logo.svg" width="40px" alt="SFU Surge logo" />  SFU Surge: Android Application

Android application for event check-in at SFU Surge events.

Download the app from the Google Play Store [here](https://play.google.com/store/apps/details?id=com.sfusurge.app.primary).

The application connects with [this server](https://github.com/sfusurge/checkin-server).

NOTE : This application is only compatible with devices that have an NFC(Near field Communications) chip.
 
## Setup Instructions

### Codebase

Clone the repository and navigate into the directory:
```shell
git clone https://github.com/sfusurge/android.git
cd android/
```

### Android

Install [Android Studio](https://developer.android.com/studio/) and import the project.

#### Creating a Production Release

Create a new file in the root directory to hold the secure keystore details:
```shell
cp keystore.properties.template keystore.properties
```

This file will be ignored by Git.

Set your keystore information in this file.

## User Instructions

Download the "SFU Surge" app from Google Play.

Open the application and enter your information.

Enable NFC on your device.

Tap your device against the NFC card (ask an executive!)
