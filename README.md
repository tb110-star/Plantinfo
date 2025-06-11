# PlantCareApp — Plant Care & Identification Application (Kotlin Multiplatform)

## Overview

PlantCareApp is a cross-platform application developed with Kotlin Multiplatform. It enables users to identify plants from photos, detect plant diseases, and receive detailed care information using Plant.id API and complementary services. Users can capture or select a plant image to get accurate plant care and treatment advice.

---

## Features

- **Capture & Select Plant Image:** Take photos using the camera or select from the gallery
- **Plant Identification:** Identify plants via the Plant.id API
- **Disease Detection:** Detect common plant diseases from images
- **Plant Care Advice:** Provide watering, lighting, and soil recommendations
- **History & Favorites:** Save results and manage favorite plants locally
- **Offline Mode with Fake Data:** Use a Fake Repository with local JSON data for development and testing without API access
- **Multi-platform Support:** Runs on Android and iOS using shared Kotlin Multiplatform UI

---

## Architecture & Technology Implementation

- **Architecture Pattern:**
  - MVVM (Model-View-ViewModel) for clean separation of concerns
  - Repository Pattern to abstract data sources 

- **Kotlin Multiplatform:**
  - Shared business logic and networking code in `shared` module
  - Ktor Client for network requests

- **Dependency Injection:**
  - Koin used for managing dependencies 

- **UI Frameworks:**
  - Android: Jetpack Compose
  - iOS: Kotlin Multiplatform shared UI 
- **Networking:**
  - Ktor Client for REST API communication
  - FakeRepository serves local mock JSON data for testing

- **Data Persistence:**
  - Room database for favorites and history 

---

## Libraries

- Kotlinx Serialization (JSON)
- Ktor Client
- Koin (Dependency Injection)
- Jetpack Compose / Compose Multiplatform UI
- Room (Android) and cross-platform database library for iOS

---

## Development Plan

1. Design and implement `FakeRepository` using mock JSON data for offline development
2. Implement `ApiRepository` to connect with Plant.id API for real data
3. Develop Repository and ViewModels to manage app state and business logic
4. Build the shared UI layer to display plant identification results and care info
5. Implement local persistence for favorites and user history with Room (Android) and cross-platform DB (iOS)
6. Optimize and add advanced features as needed

---

## Notes

- This project structure is a development plan, prioritizing the FakeRepository for early UI and logic testing without API dependency.
- Switching from fake to real API is handled seamlessly by changing dependency injection setup in Koin.

---

## Contact / Author

Tarlan Bakhtiari
