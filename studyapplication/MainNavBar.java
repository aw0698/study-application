

package studyapplication;


import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import static studyapplication.StudyApplication.spacing;
import static studyapplication.StudyApplication.categories;

/*
Class for creating the main navigation bar with categories and subjects
*/

public class MainNavBar {
    
    private static Subject selectedSubject = null;
    private static Region selectedRegion = null;
    
    //--------------------------CREATES NAVIGATION BAR--------------------------
    public static BorderPane createNavBar(BorderPane bPane, BorderPane root, List<Category> categories){
        //------------------------BPANE ELEMENTS--------------------------------
        Label studyAppLabel = new Label("STUDY APPLICATION");
        VBox categoriesVBox = new VBox(spacing);
        
        //-----------styling--------------
        studyAppLabel.prefHeightProperty().bind(bPane.heightProperty().multiply(.1));
        studyAppLabel.prefWidthProperty().bind(bPane.widthProperty());
        
        //--------------------CATEGORIES VBOX ELEMENTS--------------------------
        Accordion accordion = new Accordion(); //accordion to display categories with subjects
        categoriesVBox.getChildren().add(accordion);
        //Adds the categories to the accordion
        for(int i=0; i<categories.size(); i++){
            TitledPane tp = new TitledPane();
            tp = createTitledPane(tp, categories.get(i), root);
            accordion.getPanes().add(tp);
        }
        HBox buttonBox = new HBox();
        //------------styling------------------------------
        buttonBox.setAlignment(Pos.CENTER);
        //-----------------BUTTON BOX ELEMENETS---------------------------------
        Button addCategoryBtn = new Button("Add");
        Button editCategoryBtn = new Button("Edit");
        buttonBox.getChildren().addAll(addCategoryBtn, editCategoryBtn);
        //------------styling------------
        addCategoryBtn.prefWidthProperty().bind(bPane.prefWidthProperty().divide(2));
        editCategoryBtn.prefWidthProperty().bind(bPane.prefWidthProperty().divide(2));
        //--------------------ADD CATEGORY BUTTON ACTION------------------------
        addCategoryBtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
               //----------NAVBAR ELEMENT------------
               VBox vBox = new VBox(spacing);
               //-----styling--------
               vBox.setPadding(new Insets(spacing,spacing,spacing,spacing));
               vBox.setStyle("-fx-background-color: #0f2733");
               vBox.setAlignment(Pos.CENTER);
               //------------VBOX ELEMENTS---------------
               TextArea categoryTA = new TextArea();
               HBox buttonBox = new HBox(spacing);
               //---------styling--------------
               categoryTA.setPrefRowCount(1);
               //----------BUTTONBOX ELEMENTS-----------
               Button submitBtn = new Button("Add");
               Button deleteBtn = new Button("Delete");
               //adds elements to buttonbox
               buttonBox.getChildren().addAll(submitBtn, deleteBtn);
               //adds elements to vbox
               vBox.getChildren().addAll(categoryTA, buttonBox);
               //adds elements to navbar
               categoriesVBox.getChildren().add(0, vBox);
               
               //----------------SUBMIT BUTTON ACTION----------------------
               submitBtn.setOnAction(new EventHandler<ActionEvent>(){
                   @Override
                   public void handle(ActionEvent e){
                       //saves new category to the list
                       Category category = new Category(categoryTA.getText());
                       categories.add(0, category);
                       //---------ACCORDION ELEMENTS--------
                       TitledPane tp = new TitledPane();
                       tp = createTitledPane(tp, category, root);
                       //adds element to accordion
                       accordion.getPanes().add(0,tp);
                       //adds accordion to navbar
                       categoriesVBox.getChildren().remove(vBox);
                   }
               });
               //----------------DELETE BUTTON ACTION----------------------
               deleteBtn.setOnAction(new EventHandler<ActionEvent>(){
                   @Override
                   public void handle(ActionEvent e){
                       categoriesVBox.getChildren().remove(vBox);
                   }
               });
           }
        });
        //----------------EDIT CATEGORY BUTTON ACTION---------------------------
        editCategoryBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                //clears accordion
                accordion.getPanes().clear();
                for(int i=0; i<categories.size(); i++){
                    //-------ACCORDION ELEMENTS------------
                    TitledPane tp = new TitledPane();
                    tp = editTitledPane(accordion, tp, categories.get(i), root);
                    accordion.getPanes().add(tp);
                }
                //----------BUTTON BOX ELEMENTS----------------
                buttonBox.getChildren().clear();
                Button saveBtn = new Button("SAVE");
                buttonBox.getChildren().add(saveBtn);
                //---------------SAVE BUTTON ACTION---------------------
                saveBtn.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e){
                        updateCategories(accordion);
                        //-------ACCORDION ELEMENTS-------------
                        accordion.getPanes().clear();
                        for(int i=0; i<categories.size(); i++){
                            TitledPane tp = new TitledPane();
                            tp = createTitledPane(tp, categories.get(i), root);
                            accordion.getPanes().add(tp);
                        }
                        buttonBox.getChildren().clear();
                        buttonBox.getChildren().addAll(addCategoryBtn, editCategoryBtn);
                        
                        root.setCenter(SubjectLayout.welcome());
                    }
                });

            }
        });
        
        //----------------------ADD ELEMENTS TO NAVBAR--------------------------
        bPane.setTop(studyAppLabel);
        bPane.setCenter(categoriesVBox);
        bPane.setBottom(buttonBox);
        
        return bPane;
    }
    
    //-----------------------CREATES TITLEDPANE---------------------------------
    public static TitledPane createTitledPane(TitledPane tp, Category category, BorderPane root){
        tp.setText(category.getCategory());
        
        //----------------------TITLEDPANE ELEMENTS-----------------------------
        VBox content = new VBox(10);
        content.setPrefHeight(100);
        tp.setContent(content);
        tp.prefHeightProperty().bind(content.prefHeightProperty());
        //-------------------CONTENT VBOX ELEMENTS------------------------------
        Button addBtn = new Button("Add Subject");
        for(Subject s: category.getSubjectList()){
            Button btn = new Button(s.subject);
            //-------styling--------=
            btn.setMaxWidth(Double.MAX_VALUE);
            content.getChildren().add(btn);
            //-------------------BTN ACTION------------------
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    VBox temp = SubjectLayout.changeSubjectLayout(s, null);
                    root.setCenter(temp);
                    selectedSubject = s;
                    selectedRegion = null;
                }
            });
        }
        //adds elements to content VBox
        content.getChildren().add(addBtn);
        
        //------------------ADDBTN ACTION ---------------------
        addBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                VBox addBox = addNewButton(category,content, root);
                content.getChildren().add(addBox);
            }
        });
        
        return tp;
    }
    
    //-----------------------EDITABLE TITLEDPANE--------------------------------
    public static TitledPane editTitledPane(Accordion accordion, TitledPane tp, Category category, BorderPane root){
        tp.setText("Edit: " + category.getCategory());
        //------------------------TITLEDPANE ELEMENTS---------------------------
        VBox content = new VBox(spacing);
        //adds content to titledpane
        tp.setContent(content);
        
        //-------------------------CONTENT VBOX ELEMENTS------------------------
        HBox categoryRow = new HBox(spacing);
        categoryRow.setAlignment(Pos.CENTER_RIGHT);
        //-----------------------ROW HBOX ELEMENTS------------------------------
        Button categoryDeleteBtn = IconButton.deleteButton();
        categoryDeleteBtn.setStyle("-fx-background-color: white");
        TextField editCategoryTF = new TextField(category.getCategory());
        //-------styling---------
        editCategoryTF.setStyle("-fx-font-weight: bold; -fx-font-size: 30;");
        //adds elements to row
        categoryRow.getChildren().addAll(categoryDeleteBtn, editCategoryTF);
        //add elements to content
        content.getChildren().add(categoryRow);
        //--------------DELETE BUTTON ACTION--------------------
        categoryDeleteBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                StudyApplication.categories.remove(category);//delete category
                accordion.getPanes().remove(tp);//deletes titledpane
            }
        });
        
        //---------------VBOX CONTENT ELEMENTS----------------------------------
        for(Subject s: category.getSubjectList()){
            //---------------------LAYOUT----------------------------------
            HBox row = new HBox(spacing);
            //--------styling--------
            row.setAlignment(Pos.CENTER_LEFT);
            //------------ROW ELEMENTS--------------
            Button deleteBtn = IconButton.deleteButton();
            TextField tf = new TextField(s.subject);
            //--------styling-----------
            deleteBtn.setStyle("-fx-background-color: white");
            row.getChildren().addAll(deleteBtn, tf);
            
            content.getChildren().add(row);
            
            //--------------DELETE BUTTON ACTION--------------------
            deleteBtn.setOnAction(new EventHandler<ActionEvent>(){
                public int index;
                @Override
                public void handle(ActionEvent e){
                    index = content.getChildren().indexOf(row);
                    category.deleteSubjectAtIndex(index-1);//delete subject
                    content.getChildren().remove(index);//delete row
                }
            });
        }
        
        
        
        return tp;
    }
    
    //-------------UPDATES CATEGORY LIST FROM EDTIABLE TITLEDPANE---------------
    public static void updateCategories(Accordion accordion){
        //------------ACCORDION ELEMENTS-----------------
        for(int i=0; i<accordion.getPanes().size(); i++){
            TitledPane tp = (TitledPane)accordion.getPanes().get(i);
            VBox vBox = (VBox)tp.getContent();
            for(int j=0; j<vBox.getChildren().size(); j++){
                HBox hBox = (HBox)vBox.getChildren().get(j);
                TextField tf = (TextField)hBox.getChildren().get(1);
                //category name update
                if(j == 0){
                    categories.get(i).setCategory(tf.getText());
                }
                //subject name update
                else{
                    categories.get(i).subjectList.get(j-1).setSubject(tf.getText());
                }
            }
        }
    }
    
    public static VBox addNewButton(Category category, VBox content, BorderPane root){
        //-----------------------ADDVBOX LAYOUT---------------------------------
        VBox addVBox = new VBox();
        //---------styling-------------
        addVBox.setPadding(new Insets(15,15,15,15));
        addVBox.setStyle("-fx-background-color: #0f2733");
        addVBox.setAlignment(Pos.CENTER);
        //-------------ADDVBOX ELEMENTS--------------------
        TextArea subjectTA = new TextArea();
        HBox buttonBox = new HBox(15);
        //---------styling----------
        subjectTA.setPrefRowCount(1);
        //--------BUTTONBOX ELEMENTS-------------
        Button submitBtn = new Button("Submit");
        Button deleteBtn = new Button("Delete");
        //adds elements to buttonbox
        buttonBox.getChildren().addAll(submitBtn, deleteBtn);
        //adds elements to addVBox
        addVBox.getChildren().addAll(subjectTA, buttonBox);

        //---------------------SUBMIT BUTTON ACTION-----------------------------
        submitBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                //adds subject to categories
                Subject subject = new Subject(subjectTA.getText());
                category.addSubject(subject);
                //----------CONTENT ELEMENTS-------------
                Button btn = new Button(subject.getSubject());
                //-----styling-------
                btn.setMaxWidth(Double.MAX_VALUE);
                //adds subject btn to content vbox
                content.getChildren().remove(addVBox);
                content.getChildren().add(content.getChildren().size()-1, btn);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        VBox temp = SubjectLayout.changeSubjectLayout(subject, null);
                        root.setCenter(temp);
                    }
                });
            }
        });
        //-------------------------DELETE BUTTON ACTION-------------------------
        deleteBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                content.getChildren().remove(addVBox);
            }
        });
        
        return addVBox;
    }
   
    //------------SETTER------------------------
    public static void setSelectedPane(Region region){
        selectedRegion = region;
    }
}
