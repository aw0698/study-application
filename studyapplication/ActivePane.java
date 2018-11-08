

package studyapplication;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/*
class to get the term list, flashcard, matching, or multiple choice panes for 
subject layout matching pane
*/
public class ActivePane {
    private static final int spacing = 15; //spacing for panes
    
    //----------------------LIST OF TERMS AND DEFINITION -----------------------
    public static ScrollPane termListPane(Subject subject, Pane root){
        //----------------------SCROLLPANE LAYOUT-------------------------------
        ScrollPane sPane = new ScrollPane();
        //------------ layout styling ----------------------
        sPane.setFitToWidth(true);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sPane.prefWidthProperty().bind(root.widthProperty()); 
        sPane.prefHeightProperty().bind(root.heightProperty());
        
        //---------------------SCROLLPANE ELEMENTS------------------------------
        VBox termList = new VBox(spacing);
        //---------styling--------------
        termList.prefWidthProperty().bind(sPane.widthProperty());
        //---------TERMLIST ELEMENTS--------------------
        for(Term t: subject.termArrayList){            
            HBox row = new HBox(spacing);
            row = TermRow.createRow(subject, termList, t, row, false);
            termList.getChildren().add(row);
            //--------------------------styling----------------------
            row.prefWidthProperty().bind(termList.prefWidthProperty());
            row.setStyle("-fx-background-color: #8bd5f9");
        }
        //----------------------adds "add" term row-------------------
        termList = TermRow.createAddRow(subject, termList);
        //adds rows to scrollpane
        sPane.setContent(termList);
        
        return sPane;
    }
    
    
    //-------------------- FLASHCARDS ------------------------------------------
    public static BorderPane flashcardPane(Subject subject, Pane root){
        //----------------------BORDERPANE LAYOUT-------------------------------
        BorderPane bPane = new BorderPane();
        //-----------styling--------------------
        bPane.prefHeightProperty().bind(root.heightProperty());
        bPane.prefWidthProperty().bind(root.widthProperty());
        bPane.setPadding(new Insets(spacing, spacing, spacing, spacing));
        
        //----------------------BORDERPANE ELEMENTS-----------------------------
        StackPane sPane = new StackPane();
        HBox navBar = new HBox(spacing);
        //--------STACKPANE ELEMENTS-----------
        
        if(subject.getTermArrayList().size() >= 1){
            for(Term t: subject.getTermArrayList()){
                Label card = Flashcards.createFlashcard(t, sPane);
                sPane.getChildren().add(0, card);
            }
            //----------HBOX NAVBAR ELEMENTS--------------
            navBar = Flashcards.createNavBar(navBar, sPane);
            bPane.setCenter(sPane); 
            bPane.setBottom(navBar);
        }
        else{
            Text text = new Text("Must have at least 1 term for flashcards");
            bPane.setCenter(text);
        }
        //adds elements to borderpane
        
        
        return bPane;
    }
    
    //---------------QUESTION PANE----------------------------------------------
    public static BorderPane questionPane(Subject subject, Pane root){
        //-----------------BORDERPANE LAYOUT------------------------------------
        BorderPane bPane = new BorderPane();
        //------styling------------
        bPane.prefHeightProperty().bind(root.heightProperty());
        bPane.prefWidthProperty().bind(root.widthProperty());
        bPane.setPadding(new Insets(spacing, spacing, spacing, spacing));
        
        //------------------BORDERPANE ELEMENTS---------------------------------
        StackPane sPane = new StackPane();
        if(subject.getTermArrayList().size() >= 4){
            for(Term t: subject.getTermArrayList()){
                //------------STACKPANE ELEMENTS--------------
                VBox questionBox = new VBox(spacing);
                //----------styling---------
                questionBox.setStyle("-fx-background-color: white");
                
                //--------QUESTIONBOX VBOX ELEMENTS---------
                Text question = MultipleChoice.createQuestion(t);
                Term[] answerChoices = MultipleChoice.getAnswerChoices(subject, t);
                RadioButton[] radioButtons = MultipleChoice.getRadioButtons(answerChoices);
                ToggleGroup tGroup = new ToggleGroup();
                HBox navBar = new HBox(spacing);
                navBar = MultipleChoice.createNavBar(navBar, sPane, t, tGroup);
                //adds elements to questionbox VBox
                questionBox.getChildren().add(question);
                for(RadioButton rB: radioButtons){
                    rB.setToggleGroup(tGroup);
                    questionBox.getChildren().add(rB);
                }
                questionBox.getChildren().add(navBar);
                
                //adds elements to stackpane
                sPane.getChildren().add(0, questionBox);//puts new pane in back
            }
            //adds stackpane to borderpane
            bPane.setCenter(sPane);
        }
        else{
            Text text = new Text("Must have at least 4 terms to do multiple choice");
            bPane.setCenter(text);
        }
        
        return bPane;
    }
    
    //--------------------------MATCHING----------------------------------------
    public static VBox matchingPane(Subject subject, Pane root){
        //--------------------------VBOX LAYOUT---------------------------------
        VBox vBox = new VBox(spacing);
        //--------styling----------
        vBox.prefHeightProperty().bind(root.heightProperty());
        vBox.prefWidthProperty().bind(root.widthProperty());
        //-------------------------VBOX ELEMENTS--------------------------------
        GridPane termPane = null, defPane = null;
        HBox navBar = new HBox(spacing);
        //------styling---------
         navBar.setPadding(new Insets(0, spacing, spacing, spacing));
         navBar.setAlignment(Pos.CENTER);
        //-------------TERMPANE AND DEFPANE LAYOUT------------
        if(subject.getTermArrayList().size() >= 8){ //NEEDS ATLEAST 8 TERMS
            //-----TERMPANE AND DEFPANE ELEMENTS----------
            Term[] termOptions = Matching.getTermOptions(subject);
            ToggleButton[] termButtons = Matching.getTermButtons(termOptions, vBox);
            ToggleButton[] defButtons = Matching.getDefButtons(termOptions, vBox);
            ToggleGroup termGroup = Matching.createToggleGroup(termButtons);
            ToggleGroup defGroup = Matching.createToggleGroup(defButtons);
            //adds elements to termpane and defpane
            termPane = Matching.fillGridPane(termButtons);
            defPane = Matching.fillGridPane(defButtons);
            //adds termpane and defpane to vbox layout
            vBox.getChildren().addAll(termPane, defPane);

            //----------NAVBAR HBOX ELEMENTS-------------------
            Button refreshBtn = new Button("Refresh");
            //adds elements to navbar hbox
            navBar.getChildren().add(refreshBtn);
            
            //adds navbar to vbox
            vBox.getChildren().add(navBar);

            //------------TERMGROUP TOGGLE GROUP LISTENER-----------------------
            termGroup.selectedToggleProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    /*if an element in termgroup is selected and an element in def group is selected,
                    checks if elements are correlating term and definition*/ 
                    if (termGroup.getSelectedToggle() != null && defGroup.getSelectedToggle() != null) {
                        //gets the term and definition selected
                        String termString = ((ToggleButton)termGroup.getSelectedToggle()).getText();
                        String definitionString = ((ToggleButton)defGroup.getSelectedToggle()).getText();
                        //finds the Term object with selected termString
                        Term term = subject.findTermObject(termString);
                        //if the definition matches the Term object definition, match is correct
                        if(term != null){
                            if(term.getDefinition().equals(definitionString)){
                                ((ToggleButton)termGroup.getSelectedToggle()).setText("Correct");
                                ((ToggleButton)defGroup.getSelectedToggle()).setText("Correct");
                            }
                        }
                        
                    }
                }
            });
            //------------DEFGROUP TOGGLE GROUP LISTENER------------------------
            defGroup.selectedToggleProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                     /*if an element in termgroup is selected and an element in def group is selected,
                    checks if elements are correlating term and definition*/ 
                    if (termGroup.getSelectedToggle() != null && defGroup.getSelectedToggle() != null) {
                        //gets the term and definition selected
                        String termString = ((ToggleButton)termGroup.getSelectedToggle()).getText();
                        String definitionString = ((ToggleButton)defGroup.getSelectedToggle()).getText();
                        //finds the Term object with selected termString
                        Term term = subject.findTermObject(termString);
                        //if the definition matches the Term object definition, match is correct
                        if(term != null){
                            if(term.getDefinition().equals(definitionString)){
                                ((ToggleButton)termGroup.getSelectedToggle()).setText("Correct");
                                ((ToggleButton)defGroup.getSelectedToggle()).setText("Correct");
                            }
                        }
                        
                    }
                }
            });
            //-----------------------REFRESH BUTTON ACTION---------------------
            refreshBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent e){
                    root.getChildren().set(0, matchingPane(subject, root));

                }
             });
        }
        else{
            Text text = new Text("Must have at least 8 terms to do matching");
            vBox.getChildren().add(text);
            vBox.setAlignment(Pos.CENTER);
        }
        
        return vBox;
    }
}
