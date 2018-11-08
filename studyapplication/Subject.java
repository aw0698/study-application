

package studyapplication;

import java.util.ArrayList;
import java.util.Collections;

public class Subject{
    String subject;
    ArrayList<Term> termArrayList = new ArrayList<>();
    
    
    public Subject(String subject){
        this.subject = subject;
    }
    
    
    //----------------GETTERS---------------
    public String getSubject(){
        return subject;
    }
    public ArrayList<Term> getTermArrayList(){
        return termArrayList;
    }
    public Term getRandomTerm(){
        int value = (int) (Math.random() * termArrayList.size());
        return termArrayList.get(value);
    }
    
    //----------------SETTERS---------------
    public void setSubject(String s){
        this.subject = s;
    }
    
    //------------------OTHER--------------------
    public void shuffleTerms(){
        Collections.shuffle(termArrayList);
    }
    
    public Term findTermObject(String term){
        for(Term t: termArrayList){
            if(t.getTerm().equals(term))
                return t;
        }
        return null;
    }
    
    public void addTerm(Term term){
        termArrayList.add(term);
    }
    
    public void deleteTerm(Term t){
        termArrayList.remove(t);
    }
    
    public void updateTerm(ArrayList termArrayList){
        this.termArrayList = termArrayList;
    }
}
