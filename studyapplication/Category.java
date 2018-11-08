

package studyapplication;

import java.util.ArrayList;
import java.util.List;

/*
Class to create category object.

*/

public class Category {
    String category;
    List<Subject> subjectList = new ArrayList<>();
    
    //-------------CONSTRUCTOR----------
    Category(String category){
        this.category = category;
    }
    
    //----------------GETTERS-----------
    public  List<Subject> getSubjectList(){
        return subjectList;
    }
    public Subject getSubject(int i){
        return subjectList.get(i);
    }
    public String getCategory(){
        return category;
    }
    
    //------------SETTERS----------------
    public void setCategory(String text){
        category = text;
    }
    
    //-------------OTHER-----------------
    public void addSubject(Subject subject){
        subjectList.add(subject);
    }
    
    public void deleteSubjectAtIndex(int i){
        subjectList.remove(i);
    }
    
}
