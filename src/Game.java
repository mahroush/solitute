import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    void displayCards(Stack cards, int php) {
        if (php >= 2 && php <= 8) {
            System.out.println("Column " + (php - 1));
        } else if (php >= 9 && php <= 12) {
            System.out.println("Build Pile " + (php - 8));
        } else if (php == 1) {
            System.out.println("Draw Pile");
        } else {
            System.out.println("Discard Pile");
        }
    }

    void DisplayTableau(Stack cards) {
        for (int i = 0; i < 13; i++) {
            displayCards(cards, i + 1);
        }
    }

    public static void playGame() {
        new Game().run();
    }

    // ===== No-Color Tableau + Regular Foundations =====
    private Stack stock;
    private Stack waste;
    private Stack[] foundations;
    private Stack[] down;
    private Stack[] up;

    private void run() {
        System.out.println("########################################");
        System.out.println("#  TWUST SOLITAIRE (No Color Rule)           #");
        System.out.println("########################################");
        System.out.println("Rules:");
        System.out.println(" - Tableau: build DOWN by rank only (no color rule)");


        Scanner input = new Scanner(System.in);
        newGame();
        printHelp();
        printBoard();

        while (true) {
            if (isWin()) {
                System.out.println("\nYOU WIN! (all foundations complete)");
                System.out.print("Type 'new' for a new game or 'q' to quit: ");
            } else {
                System.out.print("\nCommand (help): ");
            }

            String line = input.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.length() == 0) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();

            if (cmd.equals("q")) {
                break;
            } else if (cmd.equals("help")) {
                printHelp();
            } else if (cmd.equals("show")) {
                printBoard();
            } else if (cmd.equals("new")) {
                newGame();
                printBoard();
            } else if (cmd.equals("d")) {
                draw();
                printBoard();
            } else if (cmd.equals("wt") && parts.length == 2) {
                int t = parseIndex(parts[1], 1, 7);
                if (t != -1) moveWasteToTableau(t - 1);
                printBoard();
            } else if (cmd.equals("wf") && parts.length == 2) {
                int f = parseIndex(parts[1], 1, 4);
                if (f != -1) moveWasteToFoundation(f - 1);
                printBoard();
            } else if (cmd.equals("tf") && parts.length == 3) {
                int t = parseIndex(parts[1], 1, 7);
                int f = parseIndex(parts[2], 1, 4);
                if (t != -1 && f != -1) moveTableauToFoundation(t - 1, f - 1);
                printBoard();
            } else if (cmd.equals("ft") && parts.length == 3) {
                int f = parseIndex(parts[1], 1, 4);
                int t = parseIndex(parts[2], 1, 7);
                if (t != -1 && f != -1) moveFoundationToTableau(f - 1, t - 1);
                printBoard();
            } else if (cmd.equals("tt") && parts.length == 4) {
                int from = parseIndex(parts[1], 1, 7);
                int to = parseIndex(parts[2], 1, 7);
                int n = parseIndex(parts[3], 1, 52);
                if (from != -1 && to != -1 && n != -1) moveTableauToTableau(from - 1, to - 1, n);
                printBoard();
            } else {
                System.out.println("Unknown command. Type 'help'.");
            }
        }

        input.close();
        System.out.println("Goodbye!");
    }

    private void newGame() {
        stock = new Stack();
        waste = new Stack();
        foundations = new Stack[4];
        down = new Stack[7];
        up = new Stack[7];

        for (int i = 0; i < 4; i++) foundations[i] = new Stack();
        for (int i = 0; i < 7; i++) { down[i] = new Stack(); up[i] = new Stack(); }

        Deck deck = new Deck();
        shuffleStack(deck.m_cardDeck);
        stock = deck.m_cardDeck;

        for (int col = 0; col < 7; col++) {
            for (int k = 0; k <= col; k++) {
                Card c = stock.pop();
                if (c == null) break;
                if (k == col) up[col].push(c);
                else down[col].push(c);
            }
        }
    }

    private void printHelp() {
        System.out.println("\nCommands:");
        System.out.println("  d     draw 1 card ");
        System.out.println("  wt    move Waste top -> Tableau (1-7)");
        System.out.println("  wf    move Waste top");
        System.out.println("  tt    move faceup cards");
        System.out.println("  tf    move Tableau top -> Foundation");
        System.out.println("  ft    move Foundation ");
        System.out.println("  show  re-print board");
        System.out.println("  new   start a new game");
        System.out.println("  q      quit");
    }

    private void printBoard() {
        System.out.println("\n================= BOARD =================");
        System.out.println("Stock: " + stock.size() + " cards");
        System.out.println("Waste: " + waste.size() + " cards | top: " + topShort(waste));

        System.out.print("Foundations: ");
        for (int i = 0; i < 4; i++) {
            System.out.print("F" + (i + 1) + "(" + foundations[i].size() + ":" + topShort(foundations[i]) + ")  ");
        }
        System.out.println("\n");

        for (int i = 0; i < 7; i++) {
            System.out.println("T" + (i + 1) + "  down=" + down[i].size() + "  up=" + up[i].size()
                    + "  | " + tableauUpString(up[i]));
        }
        System.out.println("=========================================");
    }

    private void draw() {
        if (!stock.isEmpty()) {
            Card c = stock.pop();
            if (c != null) waste.push(c);
            return;
        }
        if (waste.isEmpty()) {
            System.out.println("Nothing to draw (stock and waste empty).");
            return;
        }
        Stack temp = new Stack();
        while (!waste.isEmpty()) temp.push(waste.pop());
        while (!temp.isEmpty()) stock.push(temp.pop());
        System.out.println("Recycled waste back into stock.");
    }

    private void moveWasteToTableau(int dest) {
        if (waste.isEmpty()) { System.out.println("Waste is empty."); return; }
        Card c = waste.peek();
        if (c == null) return;

        if (!canPlaceOnTableau(c, dest)) {
            System.out.println("Invalid move: waste -> tableau.");
            return;
        }
        waste.pop();
        up[dest].push(c);
    }

    private void moveWasteToFoundation(int f) {
        if (waste.isEmpty()) { System.out.println("Waste is empty."); return; }
        Card c = waste.peek();
        if (c == null) return;

        if (!canPlaceOnFoundation(c, f)) {
            System.out.println("Invalid move: waste -> foundation.");
            return;
        }
        waste.pop();
        foundations[f].push(c);
    }

    private void moveTableauToFoundation(int t, int f) {
        if (up[t].isEmpty()) { System.out.println("Tableau has no face-up cards."); return; }
        Card c = up[t].peek();
        if (c == null) return;

        if (!canPlaceOnFoundation(c, f)) {
            System.out.println("Invalid move: tableau -> foundation.");
            return;
        }
        up[t].pop();
        foundations[f].push(c);
        flipIfNeeded(t);
    }

    private void moveFoundationToTableau(int f, int t) {
        if (foundations[f].isEmpty()) { System.out.println("Foundation is empty."); return; }
        Card c = foundations[f].peek();
        if (c == null) return;

        if (!canPlaceOnTableau(c, t)) {
            System.out.println("Invalid move: foundation -> tableau.");
            return;
        }
        foundations[f].pop();
        up[t].push(c);
    }

    private void moveTableauToTableau(int from, int to, int n) {
        if (from == to) { System.out.println("Same source and destination."); return; }
        if (n <= 0) return;
        if (up[from].size() < n) { System.out.println("Not enough face-up cards in source tableau."); return; }

        Stack temp = new Stack();
        Card prev = null;

        for (int i = 0; i < n; i++) {
            Card cur = up[from].pop();
            if (cur == null) { restore(temp, up[from]); System.out.println("Move failed."); return; }

            if (prev != null) {
                if (!(rankIndex(cur) == rankIndex(prev) + 1)) {
                    up[from].push(cur);
                    restore(temp, up[from]);
                    System.out.println("Invalid selection: not a correct rank sequence.");
                    return;
                }
            }
            temp.push(cur);
            prev = cur;
        }

        Card bottom = temp.peek();
        if (bottom == null || !canPlaceOnTableau(bottom, to)) {
            restore(temp, up[from]);
            System.out.println("Invalid move: tableau -> tableau.");
            return;
        }

        while (!temp.isEmpty()) up[to].push(temp.pop());
        flipIfNeeded(from);
    }

    private void restore(Stack fromTemp, Stack backTo) {
        while (!fromTemp.isEmpty()) backTo.push(fromTemp.pop());
    }

    private void flipIfNeeded(int col) {
        if (up[col].isEmpty() && !down[col].isEmpty()) {
            Card flipped = down[col].pop();
            if (flipped != null) up[col].push(flipped);
        }
    }

    private boolean canPlaceOnTableau(Card moving, int destCol) {
        boolean destEmpty = down[destCol].isEmpty() && up[destCol].isEmpty();
        if (destEmpty) return rankIndex(moving) == 13; // King only

        if (up[destCol].isEmpty()) return rankIndex(moving) == 13;

        Card destTop = up[destCol].peek();
        if (destTop == null) return false;

        return rankIndex(moving) == rankIndex(destTop) - 1; // rank only, no colors
    }

    private boolean canPlaceOnFoundation(Card moving, int f) {
        Stack foundation = foundations[f];
        if (foundation.isEmpty()) return rankIndex(moving) == 1; // Ace

        Card top = foundation.peek();
        if (top == null) return false;

        return sameSuit(moving, top) && rankIndex(moving) == rankIndex(top) + 1;
    }

    private boolean sameSuit(Card a, Card b) {
        if (a == null || b == null) return false;
        return a.m_suit.equals(b.m_suit);
    }

    private int rankIndex(Card c) {
        if (c == null) return -1;
        String r = c.getRank();
        if (r.equals("Ace")) return 1;
        if (r.equals("Jack")) return 11;
        if (r.equals("Queen")) return 12;
        if (r.equals("King")) return 13;
        try { return Integer.parseInt(r); }
        catch (Exception e) { return -1; }
    }

    private String cardShort(Card c) {
        if (c == null) return "(empty)";
        String r = c.getRank();
        String rr;
        if (r.equals("Ace")) rr = "A";
        else if (r.equals("Jack")) rr = "J";
        else if (r.equals("Queen")) rr = "Q";
        else if (r.equals("King")) rr = "K";
        else rr = r;

        String s = c.m_suit;
        String ss;
        if (s.equals("Hearts")) ss = "H";
        else if (s.equals("Diamonds")) ss = "D";
        else if (s.equals("Spades")) ss = "S";
        else if (s.equals("Clubs")) ss = "C";
        else ss = "?";

        return rr + ss;
    }

    private String topShort(Stack s) {
        if (s == null || s.isEmpty()) return "(empty)";
        Card c = s.peek();
        if (c == null) return "(empty)";
        return cardShort(c);
    }

    private String tableauUpString(Stack s) {
        if (s == null || s.isEmpty()) return "(none)";

        ArrayList<String> list = new ArrayList<String>();
        Node cur = s.top;
        while (cur != null) {
            list.add(cardShort(cur.thisCard));
            cur = cur.next;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
            if (i != 0) sb.append(" ");
        }
        return sb.toString();
    }

    private boolean isWin() {
        for (int i = 0; i < 4; i++) {
            if (foundations[i].size() != 13) return false;
        }
        return true;
    }

    private int parseIndex(String s, int min, int max) {
        try {
            int v = Integer.parseInt(s);
            if (v < min || v > max) {
                System.out.println("Number must be between " + min + " and " + max + ".");
                return -1;
            }
            return v;
        } catch (Exception e) {
            System.out.println("Invalid number: " + s);
            return -1;
        }
    }

    private void shuffleStack(Stack pile) {
        if (pile == null) return;
        ArrayList<Card> cards = new ArrayList<Card>();
        while (!pile.isEmpty()) {
            Card c = pile.pop();
            if (c != null) cards.add(c);
        }

        Random rng = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            Card tmp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, tmp);
        }

        for (int i = 0; i < cards.size(); i++) {
            pile.push(cards.get(i));
        }
    }
}