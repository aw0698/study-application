
package studyapplication;

import java.util.Arrays;
import java.util.Collections;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/*
Class with methods to create matching pane
*/

public class Matching {

    //----------------RETURN 8 TERM OPTIONS-------------------------------------
    public static Term[] getTermOptions(Subject subject){
        Term[] termOptions = new Term[8];
        //gets 8 unique Term objects
        for(int i=0; i<8; i++){
            Term option = null;
            do{
                option = subject.getRandomTerm();
            }while(Arrays.asList(termOptions).contains(option));
            termOptions[i] = option;
        }
        return termOptions;
    }
    
    //----------------MAKES TERM TOGGLEBUTTONS WITH TERM OPTIONS----------------
    public static ToggleButton[] getTermButtons(Term[] termOptions, VBox vBox){
        ToggleButton[] buttons = new ToggleButton[8];
        for(int i=0; i<8; i++){
            ToggleButton termBtn = new ToggleButton(termOptions[i].getTerm());
            //---------styling---------
            termBtn.prefHeightProperty().bind(vBox.prefHeightProperty().multiply(.25));
            termBtn.prefWidthProperty().bind(vBox.prefWidthProperty().multiply(.25));
            termBtn.setWrapText(true);
            termBtn.setTextAlignment(TextAlignment.CENTER);
            
            buttons[i] = termBtn;
        }
        return buttons;
    }
    
    //----------------MAKES DEF TOGGLEBUTTONS WITH TERM OPTIONS----------------
    public static ToggleButton[] getDefButtons(Term[] termOptions, VBox vBox){
        Collections.shuffle(Arrays.asList(termOptions)); //shuffles options to get new order for gridpane
        
        ToggleButton[] buttons = new ToggleButton[8];
        ToggleGroup tGroup = new ToggleGroup();
        for(int i=0; i<8; i++){
            ToggleButton defBtn = new ToggleButton(termOptions[i].getDefinition());
            //----------styling------------
            defBtn.prefHeightProperty().bind(vBox.prefHeightProperty().multiply(.25));
            defBtn.prefWidthProperty().bind(vBox.prefWidthProperty().multiply(.25));
            defBtn.setWrapText(true);
            defBtn.setTextAlignment(TextAlignment.CENTER);
            
            buttons[i] = defBtn;
        }
        return buttons;
    }
    
    //--------------------SETS TOGGLEBUTTON ARRAY TO TOGGLE GROUP---------------
    public static ToggleGroup createToggleGroup(ToggleButton[] toggleBtns){
        ToggleGroup tGroup = new ToggleGroup();
        for(ToggleButton btn: toggleBtns){
            btn.setToggleGroup(tGroup);
        }
        return tGroup;
    }
    
    //----------------------PUTS TOGGLEBUTTONS IN GRIDPANE----------------------
    public static GridPane fillGridPane(ToggleButton[] btns){
        GridPane gPane = new GridPane();
        for(int i=0; i<4; i++){
            for(int j=0; j<2; j++){
                gPane.add(btns[(4*j)+i], i, j);
            }
        }
        return gPane;
    }
}
