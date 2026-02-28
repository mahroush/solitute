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
    static void displayStack(Stack mPile) {
        Stack temp = mPile;
        while(!temp.isEmpty()) {
            Card thisCard = temp.pop();
            System.out.print(thisCard.getCard());
        }
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
    //check stack for contents
    boolean isEmpty() {
        return top == null;
    }

    // size of stack
    int size() {
        return count;
    }
}