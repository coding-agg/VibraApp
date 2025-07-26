# 🎶 VibraApp

**VibraApp** is a modern Android music application built with **Jetpack Compose** and backed by a **Spring Boot** server. It allows users to:

- Sign in with Firebase Authentication
- Explore and play music
- Save favorite songs and playlists
- Connect with friends using a session code to sync music playback

---

## 🚀 Features

### 🔐 Authentication
- Google Sign-In and Email/Password login using Firebase
- First-time users register a **unique username**
- Token-based authentication with secure Spring Boot backend

### 🎧 Music Player
- Explore and stream music from backend
- Play/Pause/Skip controls
- Beautiful UI using Jetpack Compose

### 📚 Library & Playlists
- Save liked songs to library
- Create and manage playlists

### 🔗 Connect with Friends
- Start a session and share a **6-digit session code**
- Friends join with the code to sync playback in real time
- Host controls are reflected across all connected users

---

## 🧱 Tech Stack

| Layer        | Technology                        |
|--------------|-----------------------------------|
| **Frontend** | Jetpack Compose, Kotlin, Retrofit |
| **Backend**  | Spring Boot, Firebase Admin SDK   |
| **Database** | MongoDB (via Spring Data)         |
| **Auth**     | Firebase Authentication           |

## Full FLow

### 1. Authentication & Onboarding Flow
- Launch App
- └── Check Firebase user (already logged in?)
- ├── ✅ Yes → Check if username is set
- │       ├── ✅ Yes → Navigate to HomeScreen
- │       └── ❌ No  → Navigate to UsernameEntryScreen
- └── ❌ No  → Navigate to LoginScreen (Google / Email-Password)

### 2. Home Screen Layout
- HomeScreen
- ├── 👋 Welcome message with username/email
- ├── 🎵 Explore Music
- ├── 🎧 My Library
- ├── 📝 My Playlists
- ├── 👤 Profile
- ├── 🔗 Connect (NEW)
- │     ├── Start Session
- │     └── Join Session
- └── 🚪 Logout

### 3. Connect Feature Flow
#### Option A: Start Session
- User A (host)
- └── Clicks “Start Session”
- └── Backend generates 6-character code (e.g., X9F3KD)
- └── Returns code to show on UI
- └── Stores session in DB with host + song state

#### Option B: Join Session
- User B (friend)
- └── Clicks “Join Session”
- └── Enters code → backend validates code
- └── If valid → user added to session participants
- └── Session state is now synced (song, playback)



