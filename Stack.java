import java.util.Vector;

public class Pile {
    Stack mPile = new Stack();



    void clearPile() {
        while(!mPile.isEmpty()) {
            mPile.pop();
        }
    }

    Void displayPile() {
        Stack temp = mPile;
        while(!temp.isEmpty()) {
            Card thisCard = temp.pop();
            System.out.print(thisCard.getCard());
        }

    }

    int cardCount() {
        return mPile.size();
    }
}