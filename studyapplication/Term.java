

package studyapplication;


public class Term{
    String term;
    String definition;
    
    public Term(String term, String definition){
        this.term = term;
        this.definition = definition;
    }
    
    //-------------GETTERS---------
    public String getTerm(){
        return term;
    }
    public String getDefinition(){
        return definition;
    }
    
    
    //----------SETTERS---------------
    public void setTerm(String text){
        term = text;
    }
    public void setDef(String text){
        definition = text;
    }
}
