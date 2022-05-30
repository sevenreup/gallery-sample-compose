<h1 align="center">Gallery Sample Compose</h1></br>

<p align="center">
A demo Gallery application using Jetpack Compose and Hilt using the MVVM Architecture. This app fetches data from <a href="https://docs.kiliaro.io">Kiliaro api</a>.
</p>


# Techstack
- Minimum SDK level 21
- Kotlin
    - Coroutines
    - Flow
- Jetpack
    - [Jetpack Compose](https://developer.android.com/jetpack/compose) (UI)
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
- Hilt for dependency Injection
- [Retrofit & OkHttp3](https://github.com/square/retrofit) for Network requests
- Architecture
  - MVVM
  - Repository pattern
- [Landscapist](https://github.com/skydoves/landscapist) for loading Images using Glide

# MAD Score
<img src="./previews/mad_scorecard/summary.png"/>
<img src="./previews/mad_scorecard/kotlin.png"/>
