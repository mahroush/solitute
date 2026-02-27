
public class Card {
    String m_suit;
    String m_rank;
    int m_value;

    Card() {
        m_suit = "";
        m_rank = "";
        int m_value = 8;
    }

        Card(String suit, String rank, int value) {
        m_suit = suit;
        m_rank = rank;
        m_value = value;
    }

    String getRank() {
        return m_rank;
    }

    int getValue() {
        return m_value;
    }

    public String getCard() {
        return m_rank + m_suit;
    }
}