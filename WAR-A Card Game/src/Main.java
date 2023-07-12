import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numOfPlayers = scanner.nextInt();
        scanner.nextLine();

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);
            players.add(player);
        }

        Deck deck = new Deck();
        deck.shuffle();

        // Distribute cards among players
        int currentPlayerIndex = 0;
        while (!deck.isEmpty()) {
            Card card = deck.draw();
            players.get(currentPlayerIndex).drawCard(card);
            currentPlayerIndex = (currentPlayerIndex + 1) % numOfPlayers;
        }
        // Play rounds
        int numRounds = players.get(0).getHandSize();
        for (int i = 0; i < numRounds; i++) {
            System.out.println("\n--- Round " + (i + 1) + " ---");
            List<Card> roundCards = new ArrayList<>();

            for (Player player : players) {
                Card playedCard = player.playCard();
                roundCards.add(playedCard);
                System.out.println(player.getName() + " plays " + playedCard.toString());
            }

            // Determine the round winner
            Card highestCard = roundCards.get(0);
            int roundWinnerIndex = 0;

            for (int j = 1; j < roundCards.size(); j++) {
                Card currentCard = roundCards.get(j);

                if (compareRanks(currentCard.getRank(), highestCard.getRank()) > 0) {
                    highestCard = currentCard;
                    roundWinnerIndex = j;
                }
            }

            Player roundWinner = players.get(roundWinnerIndex);
            roundWinner.drawCard(highestCard);
            System.out.println("Round winner: " + roundWinner.getName());
        }

        // Determine the game winner
        Player gameWinner = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            if (currentPlayer.getHandSize() > gameWinner.getHandSize()) {
                gameWinner = currentPlayer;
            }
        }

        System.out.println("\n--- Game Over ---");
        System.out.println("Game winner: " + gameWinner.getName() + " with " + gameWinner.getHandSize() + " cards.");
    }

    private static int compareRanks(String rank1, String rank2) {
        String[] rankHierarchy = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        int index1 = 0;
        int index2 = 0;

        for (int i = 0; i < rankHierarchy.length; i++) {
            if (rank1.equals(rankHierarchy[i])) {
                index1 = i;
            }
            if (rank2.equals(rankHierarchy[i])) {
                index2 = i;
            }
        }

        return Integer.compare(index1, index2);
    }
}
