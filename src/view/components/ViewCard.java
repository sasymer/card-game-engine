package view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ResourceBundle;

public class ViewCard extends ImageView {

    private static final String SUITS = "resources.Suits";
    private static final String CARD_VALUES = "resources.CardValues";
    private static final String BACK = "back.png";
    private static final String FILETYPE = ".png";
    private static final String CARDSIZE = "resources.CardSizing";
    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";

    private ResourceBundle cardSizing = ResourceBundle.getBundle(CARDSIZE);

    private String cardName;
    private String myDeckType;
    private Image front;
    private Image back;
    private boolean facingUp;
    private int myID;
    private Point myPos;


    private ResourceBundle suitNumbers = ResourceBundle.getBundle(SUITS);
    private ResourceBundle cardNumbers = ResourceBundle.getBundle(CARD_VALUES);


    public ViewCard(boolean facing,int number,int suit, String decktype,int cardID)
    {
        myDeckType = decktype;
        cardName = cardNumbers.getString(number+"") + suitNumbers.getString(suit+"");

        front = new Image(this.getClass().getClassLoader().getResourceAsStream(decktype+ "/"+cardName+ FILETYPE));
        back = new Image(this.getClass().getClassLoader().getResourceAsStream(decktype+ "/"+ BACK));

        myID = cardID;

        facingUp = facing;
        this.setImage(facingUp?front:back);

        myPos = new Point(DEFAULT_X, DEFAULT_Y);

        initView();

    }

    private void initView() {
        this.setPosition(myPos);
        this.setFitHeight(Integer.parseInt(cardSizing.getString(HEIGHT)));
        this.setFitWidth(Integer.parseInt(cardSizing.getString(WIDTH)));
    }

    public void resetPos()
    {
        setPosition(new Point(DEFAULT_X, DEFAULT_Y));
    }

    public void flip()
    {
        facingUp = !facingUp;
        this.setImage(facingUp?front:back);
    }

    public void setFaceUp(boolean up)
    {
        facingUp = up;
        this.setImage(facingUp?front:back);

    }


    public int getID()
    {
        return myID;
    }

    public Point getPosition()
    {
        return myPos;
    }

    public String getDeckType()
    {
        return myDeckType;
    }

   public void setPosition(Point p)
    {
        myPos = p;
        this.setTranslateX(myPos.getX());
        this.setTranslateY(myPos.getY());
    }




}
