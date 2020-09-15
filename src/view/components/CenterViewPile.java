package view.components;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ResourceBundle;


public class CenterViewPile extends ViewPile {

    private static final String EMPTYIMAGE = "normaldeck" + "/" + "red_back.png";
    private static final String CARDSIZE = "resources.CardSizing";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final int TOP = 0;

    private ResourceBundle cardSizing = ResourceBundle.getBundle(CARDSIZE);

    private ImageView myView;
    private Image empty;
    private boolean isVisible;

    public CenterViewPile(int id,boolean visible)
    {
        super(id);
        isVisible = visible;
        empty = new Image(this.getClass().getClassLoader().getResourceAsStream(EMPTYIMAGE));
        myView = new ImageView(empty);
        myView.setFitWidth(Integer.parseInt(cardSizing.getString(WIDTH)));
        myView.setFitHeight(Integer.parseInt(cardSizing.getString(HEIGHT)));

    }

    public void addTop(ViewCard addition)
    {
        myViewCards.add(TOP,addition);
        addition.setPosition(myPos);
    }

    public void setPosition(Point p)
    {
        myPos = p;

        myView.setX(myPos.getX());
        myView.setY(myPos.getY());

        for(ViewCard c :myViewCards)
        {
            c.setPosition(myPos);
        }

    }
    public Node getView() {
        showTop();
        if(!myViewCards.isEmpty() && isVisible)
        {
            getTop().setPosition(myPos);
            return getTop();
        }
        else
            return myView;
    }
}
