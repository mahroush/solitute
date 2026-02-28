import java.util.Vector;
public class Deck {
    Stack m_cardDeck = new Stack();
    void Deck() {


            Vector suits = new Vector<String>(4);
            suits.add("Hearts");
            suits.add("Diamonds");
            suits.add("Spades");
            suits.add("Clubs");
            Vector ranks = new Vector<String>(14);
            ranks.add("2");
            ranks.add("3");
            ranks.add("4");
            ranks.add("5");
            ranks.add("6");
            ranks.add("7");
            ranks.add("8");
            ranks.add("9");
            ranks.add("10");
            ranks.add("Jack");
            ranks.add("Queen");
            ranks.add("King");
            ranks.add("Ace");
            Vector values = new Vector<Integer>(14);
            values.add(2);
            values.add(3);
            values.add(4);
            values.add(5);
            values.add(6);
            values.add(7);
            values.add(8);
            values.add(9);
            values.add(10);
            values.add(10);
            values.add(10);
            values.add(10);
            values.add(11);

            for (int i = 0; i < suits.size(); i++) {
                String tempSuit = suits.get(i).toString();
                for (int j = 0; j < ranks.size(); j++) {
                    String tempRanks = ranks.get(j).toString();
                    int tempValue = (int) values.get(j);
                    Card temp = new Card(tempSuit, tempRanks, tempValue);
                    m_cardDeck.push(temp);
                }
            }
        }
        void PrintDeck(Stack m_cardDeck){
        Stack temp = m_cardDeck;
            for (int i=0; i < m_cardDeck.size(); i++) {
                System.out.println(temp.pop());
                System.out.println();
            }
            System.out.println();
            System.out.println(m_cardDeck.size());
        }

        void shuffleDeck() {

        }

        Card dealCard() {
            Card temp;
            temp = m_cardDeck.peek();
            m_cardDeck.pop();
            return temp;
        }

        int getCount() {
            return m_cardDeck.size();
        }
    }

