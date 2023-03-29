# Android News App

This is an Android news application built using the MVVM architecture pattern and Jetpack libraries such as Navigation Component, Room API, Pagination3, Retrofit, Glide, and OkHttp. The app fetches news articles from the [NewsAPI](http://newsapi.org "NewsAPI") and displays them in a clean and organized manner.

### Screenshots
Please find below some screenshots of the app:

![ezgif com-resize (2)](https://user-images.githubusercontent.com/38327072/228473278-f982429b-a48d-4eef-b04c-b21627e7f3d5.gif)
![ezgif com-resize](https://user-images.githubusercontent.com/38327072/228472277-e5a69ebd-f1d5-4269-9ef8-a30f93bfa552.gif)
![ezgif com-resize (3)](https://user-images.githubusercontent.com/38327072/228474087-8f704a48-6509-4356-b522-877bd0b4256d.gif)


### Features
- Browse news articles from various categories, such as business, entertainment, general, health, science, sports, and technology.
- Read full articles within the app, with the option to open the article in the devices default browser.
- Save articles to read later, even without internet connectivity.
- Search for news articles by keyword.

### Libraries Used
- [AndroidX](https://developer.android.com/jetpack/androidx "AndroidX") - Android support library.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel "ViewModel") - Architecture component for managing UI-related data.
- [Flow](https://developer.android.com/kotlin/flow "Flow") - Architecture component for building lifecycle-aware applications.
- [Room](https://developer.android.com/topic/libraries/architecture/room "Room") - Persistence library for SQLite databases.
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started "Navigation Component") - AndroidX library for managing navigation between destinations.
- [Retrofit](https://square.github.io/retrofit/ "Retrofit") - HTTP client for Android and Java.
- [OkHttp](https://square.github.io/okhttp/ "OkHttp") - HTTP client that supports HTTP/2 and allows for interceptors and network event logging.
- [Glide](https://github.com/bumptech/glide "Glide") - Image loading library for Android.
- [Pagination3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview "Pagination3") - AndroidX library for paginating data in a RecyclerView.
### Architecture
The app follows the MVVM architecture pattern, where the ViewModel acts as the mediator between the View and the Model. The View contains the UI logic, while the Model contains the business logic and data access code. The ViewModel fetches data from the Model and exposes it to the View, and also updates the Model with user input from the View.

### How to Build
To build the app, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build the project using the Gradle build system.
4. Run the app on an Android device or emulator.
