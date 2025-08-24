# 🎵 Music Player System  

A lightweight **Java console-based music player** where you can create albums, add songs, and manage playlists interactively.  
Built with **Core Java OOP + Collections Framework** – great for learning and interviews!  

![Java](https://img.shields.io/badge/Java-8+-blue?logo=java)  
![OOP](https://img.shields.io/badge/OOP-Concepts-green)  
![Collections](https://img.shields.io/badge/Collections-ArrayList%20%7C%20LinkedList-orange)  

---

## 🔧 Tech Stack
- ☕ Java (JDK 8+)  
- 🏗 Core Java OOP (Classes, Objects, Encapsulation)  
- 📚 Collections (`ArrayList`, `LinkedList`, `ListIterator`)  
- 🖥 Console menu interface (no GUI yet)  

---

## 🎯 Features
- ➕ Add songs to albums  
- 📀 Manage albums and their songs  
- 💽 Build custom playlists across albums  
- ▶️ Play, skip, replay, and remove songs  
- 📑 Display full playlist at any time  
- 🛠 Fully OOP – easy to expand further  

---

## 🎬 Sample Output
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

Shape of You

Despacito
===================

Select action: 0
Music player stopped.

yaml
Copy
Edit

---

## 📸 Preview
music-player-system/
├── src/
│ ├── Song.java // Song class (title, duration)
│ ├── Album.java // Album class (name, artist, songs)
│ └── Main.java // Main method, playlist manager
├── assets/
│ ├── menu-example.png // Console screenshot
│ └── playlist-example.png
└── README.md

yaml
Copy
Edit

📷 Add screenshots here:  
```md
![Menu Example](assets/menu-example.png)
![Playlist Example](assets/playlist-example.png)
🚀 How to Run
1. Clone & Enter the Folder
bash
Copy
Edit
git clone https://github.com/your-username/music-player-system.git
cd music-player-system
2. Compile & Execute
bash
Copy
Edit
javac src/*.java
java -cp src Main
3. Use menu options to manage playlist 🎶
📚 What You'll Learn
Java OOP design (classes, encapsulation, methods)

Data structures (ArrayList for albums, LinkedList for playlist)

ListIterator for forward/backward traversal

Console input handling & menu-driven systems

Clean project structure & modular code

✅ Interview Points
Encapsulation: Each class handles its own responsibility

Collections: Used ArrayList for songs, LinkedList for playlists

ListIterator: Allows bidirectional playlist navigation

Separation of Concerns: Song, Album, and Main have distinct roles

Extensibility: Can easily add GUI, file I/O, or APIs in future

🔮 Future Scope
🎨 GUI (Swing/JavaFX) version

🎵 Actual audio file playback

💾 Save/load playlists to disk (File I/O)

🔍 Song search & filtering

☁️ API integration for real music data

📤 Export playlist as .txt or .csv

👨‍💻 Author
Devisetty Rohith
B.Tech CSE (Data Science) | Java & OOP Enthusiast

📬 Connect via GitHub
