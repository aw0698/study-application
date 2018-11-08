

package studyapplication;


import java.util.Arrays;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/*
Class with methods to create multiple choice pane
*/

public class MultipleChoice {
    private static int answerChoicesCount = 4;
    
    //---------------------RETURNS QUESTION TEXT--------------------------------
    public static Text createQuestion(Term term){
        Text questionTxt = new Text(term.getTerm());
        return questionTxt;
    }
    
    //--------------------GETS 3 RANDOM ANSWER CHOICES--------------------------
    public static Term[] getAnswerChoices(Subject subject, Term term){
        Term[] answerChoices = new Term[answerChoicesCount];
        answerChoices[0] = term;
        for(int i=0; i<answerChoicesCount-1; i++){
            Term option;
            //prevents from having repeated answer choices
            do{
            option = subject.getRandomTerm();
            }while(Arrays.asList(answerChoices).contains(option) );
            answerChoices[i+1] = option;
        }
        
        return answerChoices;
    }
    
    //-------------------PUTS ANSWER CHOICES IN RADIO BUTTONS-------------------
    public static RadioButton[] getRadioButtons(Term[] answerChoices){
        //randomizes the answer choices
        Collections.shuffle(Arrays.asList(answerChoices));
        
        RadioButton[] result = new RadioButton[answerChoicesCount];
        for(int i=0; i<answerChoicesCount; i++){
            RadioButton rb = new RadioButton(answerChoices[i].getDefinition());
            rb.setWrapText(true);
            result[i] = rb;
        }
        return result;
    }
    
    //------------------------CREATE QUESTION NAVIGATION BAR--------------------
    public static HBox createNavBar(HBox navBar, StackPane sPane, Term term, ToggleGroup tGroup){
        //--------------------------NAVBAR ELEMENTS-----------------------------
        Button submitBtn = new Button("Submit");
        Button nextBtn = new Button("Next Question");
        navBar.getChildren().addAll(submitBtn, nextBtn);
        
        //--------------------SUBMIT BUTTON ACTION------------------------------
        submitBtn.setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Text text;
                //checks if selected toggle button is correct
                if(tGroup.getSelectedToggle() != null){
                    String selected = ((RadioButton)tGroup.getSelectedToggle()).getText();
                    if(selected.equals(term.getDefinition()))
                        text = new Text("CORRECT!");
                    else
                        text = new Text("WRONG!");
                }
                //no toggle selected
                else
                    text = new Text("Make a selection");
                
                //prints correct/wrong after buttons
                if(navBar.getChildren().size() == 2)
                    navBar.getChildren().add(text);
                else
                    navBar.getChildren().set(2, text);
            }
        });
        //-------------NEXT BUTTON ACTION---------------------------------------
        nextBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                //goes to next question in stackpane
                ObservableList<Node> childs = sPane.getChildren();
                Node topNode = childs.get(childs.size()-1);
                topNode.toBack();
            }
        });
        
        return navBar;
    }
}
