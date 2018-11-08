

package studyapplication;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/*
Class with methods to create flashcard pane
*/

public class Flashcards {
    
    //-------------------------CREATES FRONT OF CARD----------------------------
    public static Label createFlashcard(Term t, StackPane sPane){
        //-------------------ELEMENTS------------------
        Label cardLbl = new Label(t.getTerm());
        //---------styling---------
        cardLbl.setPrefHeight(Double.MAX_VALUE);
        cardLbl.setPrefWidth(Double.MAX_VALUE); 
        cardLbl.setStyle("-fx-background-color: white");
        cardLbl.setAlignment(Pos.CENTER);
        
        //---------------ELEMENTS ACTION-------------------------------------
        //changes term to definition or definition to term
        cardLbl.setOnMouseClicked(new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                if(cardLbl.getText().equals(t.getTerm())){
                    cardLbl.setText(t.getDefinition());
                }
                else{
                    cardLbl.setText(t.getTerm());
                }
            }
        });
        
        return cardLbl;
    }
    
    //-----------------------CREATES NAVIGATION BAR-----------------------------
    public static HBox createNavBar(HBox navBar, StackPane sPane){
        //-------------ELEMENTS--------------------
        Button leftBtn = new Button("<==");
        Button rightBtn = new Button("==>");
        Button shuffleBtn = new Button("Shuffle");
        //adds elements to navigation bar
        navBar.getChildren().addAll(leftBtn, shuffleBtn, rightBtn);
        
        //--------------------ACTION-----------------------------------
        //left button
        leftBtn.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                ObservableList<Node> childs = sPane.getChildren();
                Node topNode = childs.get(0);
                topNode.toFront();
            }
        });
        //shuffle button
        shuffleBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                ObservableList<Node> childs = sPane.getChildren();
                FXCollections.shuffle(childs);
            }
        });
        //right button
        rightBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                ObservableList<Node> childs = sPane.getChildren();
                Node topNode = childs.get(childs.size()-1);
                topNode.toBack();
            }
        });
        
        return navBar;
    }
}
