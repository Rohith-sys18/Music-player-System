# 🎵 Music Player — Full Stack Application

A full-stack **Music Player** built with **Spring Boot REST API** backend and **JavaFX** frontend, connected to a **MySQL** database using **JPA & Hibernate**.

---

## 🏗️ Project Architecture

```
musicplayer/
├── backend/                          ← Spring Boot REST API
│   ├── src/main/java/com/musicplayer/
│   │   ├── MusicPlayerApplication.java      ← Main entry point
│   │   ├── controller/
│   │   │   ├── SongController.java          ← Song REST endpoints
│   │   │   ├── AlbumController.java         ← Album REST endpoints
│   │   │   └── PlaylistController.java      ← Playlist REST endpoints
│   │   ├── service/
│   │   │   ├── SongService.java             ← Song business logic
│   │   │   ├── AlbumService.java            ← Album business logic
│   │   │   └── PlaylistService.java         ← Playlist business logic
│   │   ├── repository/
│   │   │   ├── SongRepository.java          ← JPA Song queries
│   │   │   ├── AlbumRepository.java         ← JPA Album queries
│   │   │   └── PlaylistRepository.java      ← JPA Playlist queries
│   │   └── model/
│   │       ├── Song.java                    ← Song entity
│   │       ├── Album.java                   ← Album entity
│   │       └── Playlist.java                ← Playlist entity
│   └── src/main/resources/
│       └── application.properties           ← DB config
│
├── frontend/                         ← JavaFX UI
│   └── src/main/java/com/musicplayer/ui/
│       └── MusicPlayerApp.java              ← JavaFX UI + REST calls
│
├── database/
│   └── schema.sql                           ← MySQL setup script
│
└── README.md
```

---

## 🔧 Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.2 |
| **REST API** | Spring Web (REST Controllers) |
| **ORM** | JPA & Hibernate |
| **Database** | MySQL |
| **Frontend** | JavaFX |
| **Build Tool** | Maven |
| **Architecture** | MVC — Controller → Service → Repository |

---

## 🌐 API Endpoints

### Songs
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/songs` | Get all songs |
| GET | `/api/songs/{id}` | Get song by ID |
| POST | `/api/songs` | Add new song |
| PUT | `/api/songs/{id}` | Update song |
| DELETE | `/api/songs/{id}` | Delete song |
| GET | `/api/songs/search/artist?name=Ed` | Search by artist |
| GET | `/api/songs/search/title?name=Shape` | Search by title |

### Albums
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/albums` | Get all albums |
| GET | `/api/albums/{id}` | Get album by ID |
| POST | `/api/albums` | Add new album |
| PUT | `/api/albums/{id}` | Update album |
| DELETE | `/api/albums/{id}` | Delete album |

### Playlists
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/playlists` | Get all playlists |
| GET | `/api/playlists/{id}` | Get playlist by ID |
| POST | `/api/playlists` | Create new playlist |
| PUT | `/api/playlists/{id}` | Update playlist |
| DELETE | `/api/playlists/{id}` | Delete playlist |
| POST | `/api/playlists/{pId}/songs/{sId}` | Add song to playlist |
| DELETE | `/api/playlists/{pId}/songs/{sId}` | Remove song from playlist |

---

## 🚀 How to Run

### Step 1: Database Setup
```sql
-- Open MySQL Workbench and run:
source database/schema.sql
```

### Step 2: Configure Database
Open `backend/src/main/resources/application.properties` and update:
```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### Step 3: Run Spring Boot Backend
```bash
cd backend
mvn spring-boot:run
```
Backend starts at: `http://localhost:8080`

### Step 4: Run JavaFX Frontend
Add VM options in IntelliJ/Eclipse:
```
--module-path "path_to_javafx_lib" --add-modules javafx.controls,javafx.fxml
```
Run `MusicPlayerApp.java` as a JavaFX Application.

---

## ✅ Features

- 🎵 View all songs from MySQL database dynamically
- ➕ Add new songs via UI form (saved via REST API)
- 🗑️ Delete songs from library
- 🎶 Create and manage playlists
- ➕ Add / ➖ Remove songs from playlist
- ▶️ Play, ⏭ Next, ⏮ Previous controls
- 🔄 Real-time refresh from backend

---

## 🧠 Interview Points

- Clean **MVC Architecture**: Controller → Service → Repository
- **JPA & Hibernate** for ORM — no raw SQL in Java code
- **REST API** with proper HTTP methods (GET, POST, PUT, DELETE)
- **@CrossOrigin** for frontend-backend communication
- **CompletableFuture** for async HTTP calls in JavaFX
- **Foreign key constraints** in MySQL schema
- **Many-to-Many** relationship between Playlist and Songs

---

## 👨‍💻 Author

**Devisetty Rohith**
B.Tech CSE (Data Science) | Java Backend Developer
GitHub: [github.com/Rohith-sys18](https://github.com/Rohith-sys18)
LinkedIn: [linkedin.com/in/devisetty-rohith](https://linkedin.com/in/devisetty-rohith)
