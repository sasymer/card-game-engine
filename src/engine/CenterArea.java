package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is designed to hold the backend aspects of the center area-particularly the center piles
 *
 * @author Sanna
 */
public class CenterArea implements Iterable<Pile>{

  List<Pile> centerPiles;

  public CenterArea() {
    centerPiles = new ArrayList<>();
  }

  public void addPileToCenter(Pile pile) {
    centerPiles.add(pile);
  }

  public void addPileToCenter(Pile pile, int id) {
    centerPiles.add(id, pile);
  }


  public Pile getPile(int id) {
    return centerPiles.get(id);
  }

  public void setPile(Pile pile) {
    centerPiles.clear();
    centerPiles.add(pile);
  }

  public void setPile(Pile pile, int id) {
    centerPiles.set(id, pile);
  }

  @Override
  public Iterator<Pile> iterator() {
    return centerPiles.iterator();
  }
}
