

package studyapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import static studyapplication.StudyApplication.categories;

public class FileReading {
   
    
    //----------READS THE TEXT FILES TO POPULATE CATEGORIES---------------------
    public static void readCategories() throws FileNotFoundException, IOException{
        //---------------GETS FOLDER----------------------------        
        File folder = new File("src\\resources\\textFiles");
        
        //---------------GETS FILES--------------------------
        for (File fileEntry : folder.listFiles()) {
            //--------------CREATING CATEGORY------------------------------
            String fileName = fileEntry.getName().replaceAll(".txt", "");
            Category category = new Category(fileName);
            categories.add(category);
            
            //--------------READS TEXT FILE--------------------------------
            String line = "";
            try{
                Scanner reader = new Scanner(fileEntry);
                Subject subject = null;
                while(reader.hasNext()){
                    line = reader.nextLine();
                    String[] lineElements = line.split("\t");
                    //line has subject
                    if(lineElements.length == 1){
                        subject = new Subject(lineElements[0]);
                        category.addSubject(subject);
                    }
                    //line has terms and definition
                    else if(lineElements.length == 2){
                        Term term = new Term(lineElements[0], lineElements[1]);
                        subject.addTerm(term);
                    }
                }
                reader.close();
            }
            catch(FileNotFoundException ex){
                System.out.println("File '" +fileName+ "' not found");
            }
            
        }
    }
    
    //---------------SAVES CATEGORIES AT THE END OF PROGRAM---------------------
    public static void saveCategories() throws IOException{        
        //-----------------SAVES DATA TO FILES----------------------------------
        for(Category category: categories){
            //----------CREATES CATEGORY FILE------------------
            
            File file = new File("src\\resources\\textFiles\\" +category.getCategory()+ ".txt");
            
            //------------PRINTS TO FILE------------------
            try (PrintWriter writer = new PrintWriter(file)) {
                //prints subject
                for(Subject s: category.getSubjectList()){
                    writer.println(s.getSubject());
                    //prints terms and definitions
                    for(Term t: s.getTermArrayList()){
                        writer.println(t.getTerm() +"\t"+ t.getDefinition());
                    }
                }
                writer.close();
            }
        }
    }
    
    public static void clearDirectory() {
        File directory = new File("src\\resources\\textFiles");
        for(final File file: directory.listFiles()){
            file.delete();
        }
    }

}

