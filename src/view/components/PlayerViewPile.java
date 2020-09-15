package view.components;

import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ResourceBundle;


public class PlayerViewPile extends ViewPile {

    private static final String UI_TEXT = "resources.UIText";
    private static final String CURRENT_USER = "CurrentUser";
    private static final String CARDSIZE = "resources.CardSizing";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String NOTCURRENTUSER = "";
    private static final int TOP = 0;
    private static final int SPACING = 10;
    private static final String DEFAULT = "normaldeck" + "/" + "back.png";
    private static final String PLAYER_NUMBER = "playerNumber";
    private static final int OFFSET = 1;

    private ResourceBundle visualText = ResourceBundle.getBundle(UI_TEXT);
    private ResourceBundle cardSizing = ResourceBundle.getBundle(CARDSIZE);

    private ImageView placeHolder;
    private VBox myView;
    private Label playerNumber;
    private ScrollPane fanScroll;
    private VBox fanView;
    private HBox fannedCards;
    private Image back;
    private boolean flippable;

    public PlayerViewPile(int pileID,boolean flip)
    {
        super(pileID);
        flippable = flip;

        myView = new VBox();
        myView.setAlignment(Pos.CENTER);

        playerNumber = makePlayerLabel();

        back = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT));

        initFanView();
        initScrollingFanned();
        initPlaceHolderImage();

        myView.getChildren().addAll(placeHolder,playerNumber);

    }

    private void initPlaceHolderImage() {
        placeHolder = new ImageView(back);
        placeHolder.setFitWidth(Integer.parseInt(cardSizing.getString(WIDTH)));
        placeHolder.setFitHeight(Integer.parseInt(cardSizing.getString(HEIGHT)));
    }

    private void initFanView() {
        fanView = new VBox();
        fannedCards = new HBox();
        fannedCards.setSpacing(SPACING);
        fanView.setAlignment(Pos.CENTER);
    }

    private void initScrollingFanned() {
        fanScroll = new ScrollPane();
        fanScroll.pannableProperty().set(true);
        fanScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        fanScroll.fitToHeightProperty().set(true);
    }

    private Label makePlayerLabel()
    {
        Label playerLabel = new Label();
        playerLabel.setText(String.format(visualText.getString(PLAYER_NUMBER),myID+ OFFSET));
        return playerLabel;
    }

    public void flipTopCard()
    {
        if(flippable)
        {
            showTop();
            placeHolder.setImage(getTop().getImage());
        }
    }

    public void setCurrentUser()
    {
        playerNumber.setId(CURRENT_USER);
    }

    public void resetView()
    {
        placeHolder.setImage(back);
        playerNumber.setId(NOTCURRENTUSER);
    }
    public void setPosition(Point p)
    {
        myPos = p;
        myView.setTranslateX(myPos.getX());
        myView.setTranslateY(myPos.getY());

    }
    @Override
    public Node getView() {
        return myView;
    }

    public void addTop(ViewCard addition)
    {
        myViewCards.add(TOP,addition);
    }

    public Node showFannedCards(boolean faceUp)
    {
        hideFannedCards();
        fanView.getChildren().add(makePlayerLabel());

        prepareViewCards(faceUp);

        fannedCards.getChildren().addAll(myViewCards);
        fanScroll.setContent(fannedCards);
        fanView.getChildren().add(fanScroll);

        return fanView;
    }

    private void prepareViewCards(boolean faceUp) {
        for(ViewCard c:myViewCards)
        {
            c.setFaceUp(faceUp);
            c.resetPos();
        }
    }

    public void hideFannedCards()
    {
        fanView.getChildren().clear();
        fannedCards.getChildren().clear();
        fanScroll.setContent(null);
    }




}
