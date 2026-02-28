public class Game {

    Stack getPile(Stack hand){
        return hand;
    }
    void displayCards(Stack cards, int php){//php states location
        if(php==1){//Draw pile

        }
        else if (php==2) {//column1
            System.out.println("column 1");
            //print cards in column 1
        }
        else if (php==3) {//column1
            System.out.println("column 2");
            //print cards in column 2

        }
        else if (php==4) {//column3
            System.out.println("column 3");
            //print cards in pile 3

        }
        else if (php==5) {//column3
            System.out.println("column 3");
            //print cards in pile 3

        }
        else if (php==6) {//column3
            System.out.println("column 3");
            //print cards in pile 3

        }
        else if (php==7) {//column3
            System.out.println("column 3");
            //print cards in pile 3

        }
        else if (php==8) {//column3
            System.out.println("column 3");
            //print cards in pile 3

        }
        else if (php==9) {//build pile 1
            System.out.println("build pile 1");
            //print cards in build pile 1
        }
        else if (php==10) {//build pile 2
            System.out.println("build pile 2");
            //print cards in build pile 1
        }
        else if (php==11) {//build pile 3
            System.out.println("build pile 3");
            //print cards in build pile 3
        }
        else if (php==12) {//build pile 4
            System.out.println("build pile 4");
            //print cards in build pile 4
        }
        else if (php==13) {//build pile 4
            System.out.println("build pile 4");
            //print cards in build pile 4
        } else {//discard pile
            System.out.println("discard pile");
        }
    }
    void DisplayTableau(Stack cards){
        for(int i=0;i<13;i++){
            displayCards(cards,i+1);
        }
    }
    void playGame(){
        System.out.println("###########################");
        System.out.println("#  The Game of Solitaire  #");
        System.out.println("###########################");
        char playYN = 'y';
        while (playYN == 'y') {
            Stack m_deck = new Stack();
            DisplayTableau(m_deck);

        }
    }

}
