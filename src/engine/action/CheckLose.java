package engine.action;

import engine.CardStatus;
import engine.User;
import engine.UserStatus;

/**
 * Not implemented (?)
 * Was going to be used to check for losers
 * @author Libba
 */
public class CheckLose extends Action{

  private static final int MIN = 0;

  public CheckLose(UserStatus userStatus, CardStatus cardStatus) {
    super(userStatus, cardStatus);
  }


  @Override
  public void act() {
    checkCenterCards();
    checkUserCards();
  }

  private boolean checkUserCards(){
    //checks if only 1 user has all the cards
    int count =0;
    for(User user:userStatus.getUsers()){
      if(user.getPile().getNumCards()==0){
        count ++;
      }
    }
    return count == userStatus.getUsers().size()-2;
  }

  private boolean checkCenterCards(){
    //if all the cards are gone, check the num of matches
    // if the user Num matches is not the biggest, set them to be a loser
    int count =0;
    for(User user: userStatus.getUsers()){
      if(user.getPile().getNumCards()==0){
        count ++;
      }
    }
    int max = MIN;
    if(count==userStatus.getUsers().size()-1){
      for(User user:userStatus.getUsers()){
        if(user.getNumPairs()> max){
          max = user.getNumPairs();
        }
      }
    }
    return true;
  }

}
