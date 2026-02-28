class Stack {
    Node top;
    int count;

    Stack() {
        top = null;
        count = 0;
    }

    void push(Card myCard) {
        Node temp = new Node(myCard);
        temp.next = top;
        top = temp;
        count++;
    }

    void clearStack(Stack mPile){
        while(!mPile.isEmpty()) {
            mPile.pop();
        }
    }

     void displayStack(Stack mPile) {
        Node cur = (mPile == null) ? null : mPile.top;
        boolean first = true;
        while (cur != null) {
            if (!first) System.out.print(" | ");
            System.out.print(cur.thisCard.getCard());
            first = false;
            cur = cur.next;
        }
        System.out.println();
    }

    int cardCount(Stack mPile) {
        return mPile.size();
    }

    Card pop() {
        if (top == null) {
            System.out.println("Stack Underflow");
            return null;
        }
        Node temp = top;
        top = top.next;
        Card val = temp.thisCard;
        count--;
        return val;
    }

    Card peek() {
        if (top == null) {
            System.out.println("Stack is Empty");
            return null;
        }
        return top.thisCard;
    }

    boolean isEmpty() {
        return top == null;
    }

    int size() {
        return count;
    }
}