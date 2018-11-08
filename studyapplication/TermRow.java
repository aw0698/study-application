

package studyapplication;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class TermRow {
    public static int spacing = 15;
    public static Node[] rowElements = null;
    public static int height = 60;
    public static int rowCount = 3;
    public static double btnLblWidth = 100;
    
    //-------------------CREATE ROW WITH TERM AND DEFINTION---------------------
    //action: "final" to display label or "empty" to dispaly new textArea
    public static HBox createRow(Subject subject, VBox termList, Term t, HBox row, Boolean editable){
        //---------------------row styling-------------------------------------=
        row.setPadding(new Insets(spacing,spacing,spacing,spacing));
        row.setAlignment(Pos.CENTER_LEFT);
        
        //-----------------------ROW ELEMENTS-----------------------------------
        Button editBtn = new Button();
        //---------------styling----------
        ImageView editImage = new ImageView("/resources/images/pencil.png");
        editImage.setFitHeight(20);
        editImage.setFitWidth(20);
        editBtn.setGraphic(editImage);
        //---------------------ADDITIONAL ROW ELEMENTS--------------------------
        //uneditable term row 
        if(editable == false){
            row.getChildren().add(editBtn);
            rowElements = createRowLabels(t, row);
        }
        //editable term row
        else{
            //----------------------ELEMENTS----------------------------
            Button saveBtn = IconButton.saveButton();
            Button deleteBtn = IconButton.deleteButton();
            
            //--------------SAVE BUTTON ACTION---------------------
            //makes the row an uneditable row
            saveBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent e){
                    //saves the edited terms and definitions
                    String newTerm = ((TextArea)row.getChildren().get(2)).getText();
                    String newDef = ((TextArea)row.getChildren().get(3)).getText();
                    t.setTerm(newTerm);
                    t.setDef(newDef);
                    //resets the row
                    row.getChildren().clear();
                    rowElements = createRowLabels(t, row);
                    row.getChildren().add(editBtn);
                    row.getChildren().addAll(rowElements);
                    
                }
            });
            //------------DELETE BUTTON ACTION---------------------
            deleteBtn.setOnAction(new EventHandler <ActionEvent>(){
                @Override
                public void handle(ActionEvent e){
                    subject.deleteTerm(t); //deletes term from subject
                    termList.getChildren().remove(row); //deletes row
                }
            });
            
            //-------------------ADD ELEMENTS TO ROW------------------
            row.getChildren().addAll(saveBtn,deleteBtn);
            rowElements = editTermRow(t, row);
        }
        //-------------------ADD ELEMENTS TO ROW------------------
        row.getChildren().addAll(rowElements);
        

       
        //---------------------EDIT BUTTON ACTION-------------------------------
        editBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                //--------------------ELEMENTS-------------------
                Button saveBtn = new Button();
                Button deleteBtn = new Button();
                //---------------styling---------------
                //save button
                ImageView saveImage = new ImageView("/resources/images/save.png");
                saveImage.setFitHeight(20);
                saveImage.setFitWidth(20);
                saveBtn.setGraphic(saveImage);
                //delete button
                ImageView deleteImage = new ImageView("/resources/images/trashcan.png");
                deleteImage.setFitHeight(20);
                deleteImage.setFitWidth(20);
                deleteBtn.setGraphic(deleteImage);
                //-----------------SAVE BUTTON ACTION---------------------
                //makes the row an uneditable row
                saveBtn.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e){
                        //saves the edited terms and definitions
                        String newTerm = ((TextArea)row.getChildren().get(2)).getText();
                        String newDef = ((TextArea)row.getChildren().get(3)).getText();
                        t.setTerm(newTerm);
                        t.setDef(newDef);
                        //resets the row
                        row.getChildren().clear();
                        rowElements = createRowLabels(t, row);
                        row.getChildren().add(editBtn);
                        row.getChildren().addAll(rowElements);
                    }
                });
                    
                //-------------------DELETE BUTTON ACTION---------------------
                //deletes the row
                deleteBtn.setOnAction(new EventHandler <ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e){
                        subject.deleteTerm(t); //deletes term from subject
                        termList.getChildren().remove(row); //deletes row
                    }
                });
                
                //------------------ADD ELEMENTS TO ROW----------------
                row.getChildren().clear();
                row.getChildren().addAll(saveBtn,deleteBtn);
                row.getChildren().addAll(editTermRow(t,row));
              
            }
        });
        
        return row;
    }
    
    //----------------CREATE LABEL FOR ROWS-------------------------------------
    public static Node[] createRowLabels(Term t, HBox row){
        Text termLbl = new Text(t.term);
        Text defLbl = new Text(t.definition);
        
        //----------styling----------------
        termLbl.wrappingWidthProperty().bind(row.prefWidthProperty().subtract(btnLblWidth).subtract(spacing*5).multiply(.3));
        defLbl.wrappingWidthProperty().bind(row.prefWidthProperty().subtract(btnLblWidth).subtract(spacing*5).subtract(termLbl.wrappingWidthProperty()));
        
        Text[] result = {termLbl, defLbl};
        return result;
    }
    
    //-----------------------CREATE EDITABLE FIELDS FOR ROWS--------------------
    public static Node[] editTermRow(Term t, HBox row){
        TextArea termTA = new TextArea(t.term);
        TextArea defTA = new TextArea(t.definition);
        Control[] result = {termTA, defTA};
        
        //-----------styling---------
        result = sizeTextArea(result);
        result = sizeElements(result, row);
        
        return result;
    }
    
    //-------------------STYLING TEXTAREA---------------------------------------
    public static Control[] sizeTextArea(Control[] elements){
        for(Control control: elements){
            TextArea t = (TextArea) control;
            t.setPrefRowCount(rowCount);
            t.setWrapText(true);
            control = t;
        }
        return elements;
    }
    
    //-------------------STYLING LABELS-----------------------------------------
    public static Control[] sizeElements(Control[] elements, HBox row){
        elements[0].prefWidthProperty().bind(row.prefWidthProperty().subtract(btnLblWidth).subtract(spacing*5).multiply(.3));
        elements[1].prefWidthProperty().bind(row.prefWidthProperty().subtract(btnLblWidth).subtract(spacing*5).subtract(elements[0].prefWidthProperty()));
        return elements;
    }
    
    //-----------------------CREATE ADD ROW ELEMENT-----------------------------
    public static VBox createAddRow(Subject subject, VBox termList){
        //---------------ADDROW LAYOUT--------------------------
        HBox addRow = new HBox(spacing);
        //--------styling-------------
        addRow.setAlignment(Pos.CENTER);
        addRow.setPrefHeight(height);
        addRow.setStyle("-fx-background-color: #8bd5f9");
        //-------------ADDROW ELEMENTS----------------
        Button addTermBtn = new Button("ADD TERM");
        //adds element to addRow
        addRow.getChildren().add(addTermBtn);
        
        //adds addRow to termList VBox
        termList.getChildren().add(addRow);
        //-------------------ADD TERM BUTTON ACTION-----------------------------
        addTermBtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
                HBox row = new HBox(spacing);
                row.prefWidthProperty().bind(termList.widthProperty());
                Term newTerm = new Term("", "");
                subject.addTerm(newTerm);
                row = TermRow.createRow(subject, termList, newTerm, row, true);
                termList.getChildren().add(termList.getChildren().size()-1, row);
                //-------------styling--------------------
                row.setStyle("-fx-background-color: #8bd5f9");
       }
        });
        return termList;
    }
}
    
