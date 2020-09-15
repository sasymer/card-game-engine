package view.components;

import javafx.scene.Node;
import view.components.ViewCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ViewPile {

    private static final int TOPINDEX = 0;
    private static final int DEFAULTX = 0;
    private static final int DEFAULTY = 0;
    protected List<ViewCard> myViewCards;
    protected int myID;
    protected Point myPos;

    public ViewPile(int pileID)
    {

        myID = pileID;
        myViewCards = new ArrayList<>();
        myPos = new Point(DEFAULTX, DEFAULTY);
    }

    public ViewCard getTop()
    {
            return myViewCards.get(TOPINDEX);
    }

    public ViewCard getBottom()
    {
        return myViewCards.get(myViewCards.size()-1);
    }

    public void setTop(ViewCard changed){
        myViewCards.set(TOPINDEX,changed);
    }

    public abstract void addTop(ViewCard addition);

    public void addCards(Collection<ViewCard> addition)
    {
        myViewCards.addAll(addition);

        for(ViewCard c :addition)
        {
            c.setPosition(myPos);
        }


    }

    public abstract Node getView();

    public void showTop()
    {
        if(!myViewCards.isEmpty())
        {
            myViewCards.get(TOPINDEX).setFaceUp(true);
        }

    }

    public void hideTop()
    {
        myViewCards.get(TOPINDEX).setFaceUp(false);
    }

    public int getID()
    {
        return myID;
    }

    public Point getPosition()
    {
        return myPos;
    }

    public List<ViewCard> getCards()
    {
        return myViewCards;
    }

    public abstract void setPosition(Point p);

    public void clearPile()
    {
        myViewCards.clear();
    }

    public int size()
    {
        return myViewCards.size();
    }



}
