package studyapplication;

import com.sun.javafx.css.Style;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class StudyApplication extends Application {
    public static List<Category> categories = new ArrayList<>();
    public static Stage primaryStage;
    public static Boolean termListAddEditable = true;
    public static Boolean isDeletable = false;
    public static final int spacing = 10;
//    public static Pane activePane;
    
    @Override
    public void start (Stage primaryStage){
        this.primaryStage = primaryStage;
        //-----------------BORDERPANE LAYOUT------------------------------------
        BorderPane root = new BorderPane();
            
        //------------------BORDERPANE ELEMENTS---------------------------------
        VBox main = SubjectLayout.welcome();
        BorderPane navBar = new BorderPane();
        navBar = MainNavBar.createNavBar(navBar, root, categories);
        //---------styling---------
        //navBar
        navBar.setId("navBar");
        navBar.prefWidthProperty().bind(primaryStage.widthProperty().multiply(.2));
        navBar.prefHeightProperty().bind(primaryStage.heightProperty());
        //adds elements to root borderpane
        root.setLeft(navBar);
        root.setCenter(main);
        
        //-------------------------SCENE & STAGE--------------------------------
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        
        //category 1
        Category cat1 = new Category("American Government");
        categories.add(cat1);
        cat1.addSubject(new Subject("Bill of Rights"));
        cat1.addSubject(new Subject("Executive Branch"));
        Subject one = cat1.getSubject(0);
        Subject two = cat1.getSubject(1);
        
        one.addTerm(new Term("1st Amendment", "Protect Freedom of Religion, Speech, Petition, Press, and Assembly"));
        one.addTerm(new Term("2nd Amendment", "You have the right to own guns"));
        one.addTerm(new Term("3rd Amendment", "The government cannot force people to keep troops in their private homes"));
        one.addTerm(new Term("4th Amendment","Protection against Unreasonable Search and Seizure"));
        one.addTerm(new Term("5th Amendment","The Right to Remain Silent/Double Jeopardy, right to due process"));
        one.addTerm(new Term("6th Amendment","The right to a Speedy Trial by jury, representation by an attorney for an accused person"));
        one.addTerm(new Term("7th Amendment","The right to a trial by Jury in civil cases over $20.00"));
        one.addTerm(new Term("8th Amendment","Prohibits excessive fines and excessive bail/and cruel unusual punishment"));
        one.addTerm(new Term("9th Amendment","Rights not included in Constitution go to the people"));
        one.addTerm(new Term("10th Amendment","Powers not given to federal government go to people and States"));
        
        two.addTerm(new Term("Candidate for President", "Must be a natural born citizen. at least 35 and must have lived in the US for at least 14 yrs."));
        two.addTerm(new Term("National Convention", "A national meeting of delegates elected in primaries, caucuses, or state conventions who assemble once every four years to nominate candidates for president and vice president, ratify the party platform, elect officers, and adopt rules."));
        two.addTerm(new Term("Election Day", "1st tuesday after 1st monday in November of every fourth year"));
        two.addTerm(new Term("Winning an Election", "Candidate must get majority of the votes cast by the electoral college"));
        
        //category 2
        Category cat2 = new Category("Chemistry");
        categories.add(cat2);
        cat2.addSubject(new Subject("Chapter 1"));
        Subject one1 = cat2.getSubject(0);
        
        one1.addTerm(new Term("1 in = _________ cm", "2.54 cm"));
        one1.addTerm(new Term("1 ft = _________ m", "0.3048 m"));
        one1.addTerm(new Term("1 yd = ________ m", "0.914 m"));
        one1.addTerm(new Term("1 m =_________ yd", "1.093 yd"));
        one1.addTerm(new Term("1 km = _________ mi", "0.621 mi"));
        one1.addTerm(new Term("10^9", "Giga"));
        one1.addTerm(new Term("10^6", "Mega"));
        one1.addTerm(new Term("10^3", "kilo"));
        one1.addTerm(new Term("10^2", "hecta"));
        one1.addTerm(new Term("10^1", "deca"));
        one1.addTerm(new Term("10^-1", "deci"));
        one1.addTerm(new Term("10^-2", "centi"));
        one1.addTerm(new Term("10^-3", "milli"));
        one1.addTerm(new Term("10^-6", "micro"));
        
        launch(args);
    }
}
