package engine.action;

import engine.CardStatus;
import engine.UserStatus;


/**
 * Action Abstract Class
 *
 * @Param UserStatus, CardStatus
 */
public abstract class Action {

  protected UserStatus userStatus;
  protected CardStatus cardStatus;

  public Action(UserStatus userStatus, CardStatus cardStatus) {
    this.cardStatus = cardStatus;
    this.userStatus = userStatus;
  }


  public abstract void act();

}
