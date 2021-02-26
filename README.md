# Sample app for auto finder

#Tools and devices
1. Android studio
2. Emulator Nexus 5X API 30
3. Emulator Pixel XL 4 API 30

#This project includes 
1. MVVM architecture
2. Retrofit
3. Coroutines
4. Google hilt
5. Data binding
6. Live data
7. UI and unit test cases. UI test cases will be green if internet is available.
8. Screen rotation data persists
9. network-security-config inspired from https://developer.android.com/training/articles/security-config
10. Generic error handling
11. Different layout for odd and even rows
12. Use of Repository layer is not done. However, I have done similar code here. 
  a. https://github.com/tauqir295/Clean-Network-Layer/tree/main/network
  b. https://github.com/tauqir295/CatGalleryImageSelector/tree/main/cat-gallery/src/main/java/com/gallery/cat/network
  
#Enhancement
1. Can add configuration structure. 
2. Can pass base url from host app via configuration. 
3. Use security protocols for network calls.
4. Bug fixes and ui changes can be scope for future changes.
5. Release and proguard configuration.
6. Remove redundant code if any.

# Note
Architecture source is inspired from android developer community
![App architecture](final-architecture.png "final-architecture")
https://developer.android.com/jetpack/guide


Why to use hilt and not Dagger or Koin?
Hilt is built on top of the Dagger dependency injection library, 
providing a standard way to incorporate Dagger into an Android application.
More details from android developer site - 
https://developer.android.com/training/dependency-injection/hilt-android#hilt-and-dagger

The proposed solution can be done in numerous ways. 
