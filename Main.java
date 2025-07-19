import java.util.*;

public class Main {
    private static ArrayList<Album> albums = new ArrayList<>();

    public static void main(String[] args) {
        Album album = new Album("Album1", "AC/DC");
        album.addSong("Thunderstruck", 4.5);
        album.addSong("Back in Black", 4.2);
        album.addSong("Shoot to Thrill", 5.0);
        albums.add(album);

        album = new Album("Album2", "Eminem");
        album.addSong("Lose Yourself", 5.3);
        album.addSong("Not Afraid", 4.8);
        album.addSong("Stan", 6.2);
        albums.add(album);

        LinkedList<Song> playList = new LinkedList<>();
        albums.get(0).addToPlayList("Thunderstruck", playList);
        albums.get(0).addToPlayList("Back in Black", playList);
        albums.get(1).addToPlayList("Lose Yourself", playList);
        albums.get(1).addToPlayList("Stan", playList);

        play(playList);
    }

    private static void play(LinkedList<Song> playList) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        boolean goingForward = true;

        ListIterator<Song> listIterator = playList.listIterator();

        if (playList.isEmpty()) {
            System.out.println("No songs in the playlist.");
            return;
        } else {
            System.out.println("Now playing: " + listIterator.next().toString());
            printMenu();
        }

        while (!quit) {
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("Playlist complete.");
                    quit = true;
                    break;
                case 1:
                    if (!goingForward) {
                        if (listIterator.hasNext()) {
                            listIterator.next();
                        }
                        goingForward = true;
                    }
                    if (listIterator.hasNext()) {
                        System.out.println("Now playing: " + listIterator.next().toString());
                    } else {
                        System.out.println("We have reached the end of the playlist.");
                        goingForward = false;
                    }
                    break;
                case 2:
                    if (goingForward) {
                        if (listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                        goingForward = false;
                    }
                    if (listIterator.hasPrevious()) {
                        System.out.println("Now playing: " + listIterator.previous().toString());
                    } else {
                        System.out.println("We are at the start of the playlist.");
                        goingForward = true;
                    }
                    break;
                case 3:
                    if (goingForward) {
                        if (listIterator.hasPrevious()) {
                            System.out.println("Replaying: " + listIterator.previous().toString());
                            goingForward = false;
                        } else {
                            System.out.println("We are at the start of the list.");
                        }
                    } else {
                        if (listIterator.hasNext()) {
                            System.out.println("Replaying: " + listIterator.next().toString());
                            goingForward = true;
                        } else {
                            System.out.println("We have reached the end of the list.");
                        }
                    }
                    break;
                case 4:
                    printList(playList);
                    break;
                case 5:
                    printMenu();
                    break;
                case 6:
                    if (playList.size() > 0) {
                        listIterator.remove();
                        System.out.println("Song removed from playlist.");
                        if (listIterator.hasNext()) {
                            System.out.println("Now playing: " + listIterator.next());
                        } else if (listIterator.hasPrevious()) {
                            System.out.println("Now playing: " + listIterator.previous());
                        }
                    }
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Available actions:\nPress:");
        System.out.println("0 - to quit");
        System.out.println("1 - to play next song");
        System.out.println("2 - to play previous song");
        System.out.println("3 - to replay the current song");
        System.out.println("4 - list songs in the playlist");
        System.out.println("5 - print available actions");
        System.out.println("6 - delete current song from playlist");
    }

    private static void printList(LinkedList<Song> playList) {
        Iterator<Song> iterator = playList.iterator();
        System.out.println("====================");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("====================");
    }
}
