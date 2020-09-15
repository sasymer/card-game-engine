package view.components;

import view.components.ViewCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ViewDeck implements Iterable<ViewCard> {
    private List<ViewCard> myCards;

    public ViewDeck(Collection<ViewCard> cards)
    {
        myCards = new ArrayList<>(cards);
    }

    public ViewCard getCard(int id)
    {
        for(ViewCard c:myCards)
        {
            if(c.getID() == id)
            {
                return c;
            }
        }
        return null;
    }

    @Override
    public Iterator iterator() {
        return myCards.iterator();
    }
}
