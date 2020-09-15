package engine;

import java.util.ArrayList;
import java.util.List;

public class Memory {

  private List<Card> cards;

  public Memory(){
    cards = new ArrayList<>();
  }

  public void setMemory(Pile myCards){
    cards = new ArrayList<>();
    for (Card c:myCards){
      cards.add(c);
    }
  }

  public List<Card> getMemory(){ return cards; }


}
