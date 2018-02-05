import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ксения on 08.05.2017.
 */
public class Controller {
    private static String UmlPath =
            "C:\\Users\\Ксения\\IBM\\rationalsdp\\workspace\\Translator\\Blank Package.xmi";
    private static String emxPath = "c1.xmi";
    public static final Reader reader = new Reader();

    static ArrayList<Concept> concepts = new ArrayList<Concept>();
    static MyHashMap generalizationsClass = new MyHashMap(); // предки
    static MyHashMap generalizationsInterface = new MyHashMap(); // предки
    static MyHashMap realizations = new MyHashMap(); //классы
    static MyHashMap dependencies = new MyHashMap(); // предки
    static MyHashMap associations = new MyHashMap(); // предки
    static MyHashMap aggregations = new MyHashMap(); // предки
    static MyHashMap compositions = new MyHashMap(); // предки
    static HashMap<String, AttrAssociation> allAssociations = new HashMap();

    public static void main(String[] args) {
        try {
            reader.read(emxPath);
            
            System.out.println("generalization" + generalizationsClass.toString() + generalizationsInterface.toString());
            System.out.println("association" + associations.toString());
            System.out.println("aggregation" + aggregations.toString());
            System.out.println("composition" + compositions.toString());
            System.out.println("realization" + realizations.toString());

            Optimization.redundant("generalization");
            Optimization.redundant("realization");
            Optimization.multipleInheritance();
            Optimization.cyclic("class generalization");
            Optimization.cyclic("interface generalization");
            Optimization.cyclic("aggregation");
            Optimization.cyclic("composition");
            Optimization.cyclic("association");
            Optimization.incoherentInterface();

        } catch (IOException e) {
            System.out.println("File not found!");
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
        }
    }

    public static Concept findConcept(String id) {
        for (Concept con : concepts) {
            if (con.getId().equals(id)) return con;
        }
        return null;
    }
}
