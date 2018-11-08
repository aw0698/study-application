
package studyapplication;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import static studyapplication.StudyApplication.categories;
import static studyapplication.StudyApplication.primaryStage;
import static studyapplication.StudyApplication.spacing;

/*
Class to get subject layout for selected subject from navbar
*/

public class SubjectLayout {
    
    public static VBox welcome(){
        VBox main = new VBox();
        Label welcomeLbl = new Label("WELCOME!");
        main.getChildren().add(welcomeLbl);
        //---------styling---------
        //main
        main.setStyle("-fx-background-color: #0f2733; -fx-font-size: 60; ");
        main.setAlignment(Pos.CENTER);
        //welcome label
        welcomeLbl.setStyle("-fx-text-fill: white;");
        
        return main;
    }
    
    //----------------------CREATE THE SUBJECT LAYOUT---------------------------
    public static VBox changeSubjectLayout(Subject s, Region region){
        //-------------------BACKGROUND VBOX LAYOUT-----------------------------
        VBox backgroundBox = new VBox();
        //---------styling--------
        backgroundBox.setPadding(new Insets(spacing,spacing,spacing,spacing));
        backgroundBox.setStyle("-fx-background-color: #0f2733");
        //-------------------BACKGROUND VBOX ELEMENTS---------------------------
        VBox main = new VBox(spacing);
        //---------styling------------
        main.setId("main");
        main.setPadding(new Insets(0,spacing,spacing,spacing));
        
        //-------------------------VBOX MAIN ELEMENTS---------------------------
        Pane subjectHeader = new Pane();
        Pane activePane = new Pane();
        subjectHeader.setStyle("-fx-background-color:white; "
                + "-fx-font-size: 35;"
                + "-fx-font-weight: bold;");
        //-----------styling-----------
        //subjectHeader
        subjectHeader.prefHeightProperty().bind(primaryStage.heightProperty().divide(20).multiply(3));
        
        //-----------------SUBJECT HEADER ELEMENTS-------------------
        Label subjectHeaderLbl = createHeaderLbl(s, subjectHeader);
        //adds element to subjectHeader
        subjectHeader.getChildren().add(subjectHeaderLbl);
        //adds element to main VBox
        main.getChildren().add(subjectHeader);
        
        //----------------------STUDY BUTTON ELEMENTS-----------------------------
        HBox studyBtns = new HBox(10);
        studyBtns.prefHeightProperty().bind(primaryStage.heightProperty().divide(20). multiply(2));
        //---------------------STUDY METHOD BUTTONS-----------------------------
        Button listBtn = createStudyButton("List", main);
        listBtn.setOnAction((ActionEvent event) -> {
            activePane.getChildren().clear();
            activePane.getChildren().add(ActivePane.termListPane(s,activePane));
            MainNavBar.setSelectedPane(ActivePane.termListPane(s,activePane));
        });
        Button flashcardBtn = createStudyButton("Flashcard", main);
        flashcardBtn.setOnAction((ActionEvent event) -> {
            activePane.getChildren().clear();
            activePane.getChildren().add(ActivePane.flashcardPane(s,activePane));
            MainNavBar.setSelectedPane(ActivePane.flashcardPane(s,activePane));
        });
        Button matchingBtn = createStudyButton("Matching", main);
        matchingBtn.setOnAction((ActionEvent event) -> {
            activePane.getChildren().clear();
            activePane.getChildren().add(ActivePane.matchingPane(s,activePane));
            MainNavBar.setSelectedPane(ActivePane.matchingPane(s,activePane));
        });
        Button questionBtn = createStudyButton("Multiple Choice", main);
        questionBtn.setOnAction((ActionEvent event) -> {
            activePane.getChildren().clear();
            activePane.getChildren().add(ActivePane.questionPane(s,activePane));
            MainNavBar.setSelectedPane(ActivePane.questionPane(s,activePane));
        });
        studyBtns.getChildren().addAll(listBtn, flashcardBtn, matchingBtn, questionBtn);
        //adds the study buttons and activePane to the VBox
        main.getChildren().add(studyBtns);
        
        //----------------------ACTIVEPANE ELEMENTS-----------------------------
        if(region == null){
            activePane.getChildren().clear();
            activePane.getChildren().add(ActivePane.termListPane(s,activePane));
        }
        else{
            activePane.getChildren().clear();
            activePane.getChildren().add(region);
        }
        //------------------styling-------------------
        activePane.prefHeightProperty().bind(primaryStage.heightProperty().divide(20).multiply(14));
        activePane.setStyle("-fx-background-color: white");
        //adds activePane to main VBox
        main.getChildren().add(activePane);
        
        //adds main to background
        backgroundBox.getChildren().add(main);
        return backgroundBox;
    }
    
    //----------------------CREATES HEADER LABEL--------------------------------
    public static Label createHeaderLbl(Subject s, Pane pane){
        Label lbl = new Label(s.subject);
        //----------styling--------------
        lbl.setPadding(new Insets(0,0,0,10));
        lbl.layoutYProperty().bind(pane.heightProperty().subtract(lbl.heightProperty()).divide(2));
        
        return lbl;
    }
    
    //----------------------------CREATES EDITABLE HEADER-----------------------
    public static TextArea editHeader(Pane subjectHeader){
        Label subjectLbl = (Label)subjectHeader.getChildren().get(0);
        
        //----------------ELEMENTS-----------------
        TextArea editTA = new TextArea();
        editTA.setText(subjectLbl.getText());
        //-----------------styling--------------
        editTA.setPrefRowCount(1);
        editTA.prefWidthProperty().bind(subjectHeader.widthProperty());
        editTA.layoutYProperty().bind(subjectHeader.heightProperty().subtract(editTA.heightProperty()).divide(2));        
        
        return editTA;
    }
    
    //--------------------------CREATE STUDY BUTTON-----------------------------
    public static Button createStudyButton(String studyOption, VBox main){
        Button b = new Button(studyOption);//b is the study method
        //----------styling---------------
        b.setPrefHeight(Double.MAX_VALUE);
        b.prefWidthProperty().bind(main.widthProperty().divide(4));
        return b;
    }
    
}
