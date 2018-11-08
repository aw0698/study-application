

package studyapplication;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;



public class IconButton {

    public static Button deleteButton(){
        Button deleteBtn = new Button();
        ImageView deleteImage = new ImageView("/resources/images/trashcan.png");
        deleteImage.setFitHeight(20);
        deleteImage.setFitWidth(20);
        deleteBtn.setGraphic(deleteImage);
        deleteBtn.setGraphic(deleteImage);
        
        return deleteBtn;
    }
    
    public static Button saveButton(){
        Button saveBtn = new Button();
        ImageView saveImage = new ImageView("/resources/images/save.png");
        saveImage.setFitHeight(20);
        saveImage.setFitWidth(20);
        saveBtn.setGraphic(saveImage);

        return saveBtn;
    }
}
