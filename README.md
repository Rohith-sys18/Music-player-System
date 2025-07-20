
# 🎵 Music Player System

A **Core Java OOP** console project to manage songs, albums, and playlists in a menu-driven music player.  
Beginner-friendly—great for learning Java classes, collections, and user interaction!

## 🔧 Tech Stack

- ☕ Java (JDK 8+)
- 🏗️ Core Java OOP (Classes, Objects)
- 📚 ArrayList & LinkedList (Collections Framework)
- 🖥️ Console menu interface (no GUI yet)

## 🎯 Features

- ➕ Add songs to albums
- 📀 Manage albums and their songs
- 💽 Build custom playlists across albums
- ▶️ Play, skip, replay, and remove songs
- 📑 Display full playlist at any time
- 🛠️ Completely OOP—easy to expand further

## 🎬 Sample Output

```
Welcome to the Music Player System!

Available Actions:
0 - Quit
1 - Play next song
2 - Play previous song
3 - Replay current song
4 - List all songs in playlist
5 - Show menu options
6 - Remove current song

Now playing: Shape of You
Select action: 1
Now playing: Despacito
Select action: 3
Replaying: Despacito
Select action: 4

===== Playlist =====
1. Shape of You
2. Despacito
===================

Select action: 0
Music player stopped.
```

## 📸 Application Preview

> **Tip:** Add your screenshots to a folder named `assets` or `screenshots` in your repo, and reference them below.


```
music-player-system/
├── src/
│   ├── Song.java           // Song class (title, duration)
│   ├── Album.java          // Album class (name, artist, songs)
│   └── Main.java           // Main method, playlist manager
├── assets/
│   ├── menu-example.png
│   └── playlist-example.png
└── README.md
```

## 🚀 How to Run

### 1. Clone & Enter the Folder
```bash
git clone https://github.com/your-username/music-player-system.git
cd music-player-system
```

### 2. Compile & Execute
```bash
javac src/*.java
java -cp src Main
```

### 3. Follow the on-screen menu for actions!

## 📚 What You'll Learn

- Java OOP design: classes, encapsulation, methods
- Data structures: ArrayList & LinkedList
- Console input handling & menus
- Project structure and clean Java code

## ✅ Interview Points

- Clean separation of concerns (`Song`, `Album`, playlist logic)
- Used `ArrayList` and `LinkedList` for best-performance
- Encapsulation and class interaction
- Menu-driven design for easy usability
- Easily extensible for GUI/audio in future

## 🔮 Future Ideas

- Swing/JavaFX UI version
- Actual audio file playback
- Save/load playlists to disk (file I/O)
- Song search & filter
- Integration with streaming APIs
- Export playlist as .txt or .csv

## 👨‍💻 Author

**Devisetty Rohith**  
B.Tech Student | Java & OOP Enthusiast

## 📬 Contact

Questions, suggestions, or want to connect?  
Message or connect via [GitHub](https://github.com/your-username).

**Good luck and happy coding! 🚀**

Let me know if you want customization, actual images added, or any more sections!
