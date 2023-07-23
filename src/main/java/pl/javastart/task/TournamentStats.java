package pl.javastart.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TournamentStats {
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private final static String FILE_NAME = "stats.csv";

    void run(Scanner scanner) {
        List<Player> players = new LinkedList<>();
        while (true) {
            if (isUserStop(scanner, players)) {
                break;
            }
        }
        Comparator<Player> playerComparator = getPlayerComparator(scanner);
        List<Player> playerList = sortPlayers(scanner, players, playerComparator);
        savePlayersToCSVFile(playerList);
    }

    private static void savePlayersToCSVFile(List<Player> playerList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Player player : playerList) {
                writer.write(String.valueOf(player));
                writer.newLine();
            }
            System.out.println("Dane posortowano i zapisano do pliku " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku");
        }
    }

    private static List<Player> sortPlayers(Scanner scanner, List<Player> players, Comparator<Player> playerComparator) {
        System.out.println("Sortować rosnąco czy malejąco? (1- rosnąco, 2- malejąco)");
        int sortOrder = scanner.nextInt();
        if (sortOrder == 1) {
            players.sort(playerComparator);
        } else if (sortOrder == 2) {
            Comparator<Player> reverseOrder = Collections.reverseOrder(playerComparator);
            players.sort(reverseOrder);
        }
        return players;
    }

    private static Comparator<Player> getPlayerComparator(Scanner scanner) {
        System.out.println("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3- wynik)");
        int sortOrder = scanner.nextInt();
        Comparator<Player> comparator = null;
        switch (sortOrder) {
            case ONE -> comparator = new FirstNameComparator();
            case TWO -> comparator = new LastNameComparator();
            case THREE -> comparator = new ScoreComparator();
            default -> {
                System.out.println("Błędne kryterium sortowania");
                System.exit(0);
            }
        }
        return comparator;
    }

    private static boolean isUserStop(Scanner scanner, List<Player> players) {
        System.out.println("Podaj wynik kolejnego gracza (lub STOP)");
        String playerOrStop = scanner.nextLine();
        if (!playerOrStop.equals("STOP")) {
            addPlayerToList(players, playerOrStop);
        } else {
            return true;
        }
        return false;
    }

    private static void addPlayerToList(List<Player> players, String playerOrStop) {
        String[] playerInfo = playerOrStop.trim().split(" ");
        Player player = new Player(playerInfo[0], playerInfo[1], Integer.parseInt(playerInfo[2]));
        players.add(player);
    }
}
