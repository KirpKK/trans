package Validation;

import GUI.MainWindow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ксения on 08.05.2017.
 */
public class Controller {
    /*    private static String UmlPath =
                "C:\\Users\\Ксения\\IBM\\rationalsdp\\workspace\\Translator\\Blank Package.xmi";
        private static String emxPath = "c1.xmi";*/
    //public static final Reader reader = new Reader();
    public static File diagram;
    public static String stringReport;

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
        new MainWindow();
    }

    public static Concept findConcept(String id) {
        for (Concept con : concepts) {
            if (con.getId().equals(id)) return con;
        }
        return null;
    }

    public static String validate() throws NoDiagramException, IOException {
        StringBuilder report = new StringBuilder();

        if (diagram != null) {
            long start = System.nanoTime();
            Reader.read(diagram);

/*            System.out.println("generalization" + generalizationsClass.toString() + generalizationsInterface.toString());
            System.out.println("aggregation" + aggregations.toString());
            System.out.println("association" + associations.toString());
            System.out.println("composition" + compositions.toString());
            System.out.println("realization" + realizations.toString());

            System.out.println("generalization - " + (generalizationsClass.size()+ generalizationsInterface.size()));
            System.out.println("aggregation - " + aggregations.size());
            System.out.println("association - " + associations.size());
            System.out.println("composition - " + compositions.size());
            System.out.println("realization - " + realizations.size());
            System.out.println("classes - " + concepts.size());*/



            long startRGen = System.nanoTime();
            report.append(Optimization.redundant("generalization"));
            long startRAgg = System.nanoTime();
            report.append(Optimization.redundant("aggregation"));
            long startRComp = System.nanoTime();
            report.append(Optimization.redundant("composition"));
            long startRReal = System.nanoTime();
            report.append(Optimization.redundant("realization"));

            long startMInh = System.nanoTime();
            report.append(Optimization.multipleInheritance());

            long startCCGen = System.nanoTime();
            report.append(Optimization.cyclic("class generalization"));
            long startCIGen = System.nanoTime();
            report.append(Optimization.cyclic("interface generalization"));
            long startCAgg = System.nanoTime();
            report.append(Optimization.cyclic("aggregation"));
            long startCComp = System.nanoTime();
            report.append(Optimization.cyclic("composition"));
            long startCAss = System.nanoTime();
            report.append(Optimization.cyclic("association"));
            long startIncI = System.nanoTime();
            report.append(Optimization.incoherentInterface());
            long startIncC = System.nanoTime();
            report.append(Optimization.incoherentClass());
            long finish = System.nanoTime();


            /*System.out.println("Redundant generalization - " + (startRAgg - startRGen));
            System.out.println("Redundant aggregation - " + (startRComp - startRAgg));
            System.out.println("Redundant composition - " + (startRReal - startRComp));
            System.out.println("Redundant realization - " + (startMInh - startRReal));
            System.out.println("Multiple inheritance - " + (startCCGen - startMInh));
            System.out.println("Cyclic class generalization - " + (startCIGen - startCCGen));
            System.out.println("Cyclic interface generalization - " + (startCAgg - startCIGen));
            System.out.println("Cyclic aggregation - " + (startCComp - startCAgg));
            System.out.println("Cyclic composition - " + (startCAss - startCComp));
            System.out.println("Cyclic association - " + (startIncI - startCAss));
            System.out.println("Incoherent interface - " + (startIncC - startIncI));
            System.out.println("Incoherent class - " + (finish - startIncC));

            System.out.println("Время валидации диаграммы : " + (finish - start) + "нс");*/

            Controller.stringReport = report.toString();
            if (Controller.stringReport.equals("")) {
                stringReport = "Report for diagram " + diagram.getName() + "\r\n" + "No errors detected";
            } else {
                stringReport = "Report for diagram " + diagram.getName() + "\r\n" + stringReport;
            }
       //     System.out.println(stringReport);

            report.append("Redundant generalization - " + (startRAgg - startRGen))
                    .append("Redundant aggregation - " + (startRComp - startRAgg))
                    .append("Redundant composition - " + (startRReal - startRComp))
                    .append("Redundant realization - " + (startMInh - startRReal))
                    .append("Multiple inheritance - " + (startCCGen - startMInh))
                    .append("Cyclic class generalization - " + (startCIGen - startCCGen))
                    .append("Cyclic interface generalization - " + (startCAgg - startCIGen))
                    .append("Cyclic aggregation - " + (startCComp - startCAgg))
                    .append("Cyclic composition - " + (startCAss - startCComp))
                    .append("Cyclic association - " + (startIncI - startCAss))
                    .append("Incoherent interface - " + (startIncC - startIncI))
                    .append("Incoherent class - " + (finish - startIncC))
                    .append("Время валидации диаграммы : " + (finish - start) + "нс");
            stringReport = report.toString();
            return stringReport;
        } else {
            throw new NoDiagramException();
        }

    }

/*    public static File createReport(String stringReport) throws IOException {
        File report = new File("report");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(report))) {
            bw.write(stringReport);
            return report;
        }
    }*/

    public static class NoDiagramException extends Exception {
        NoDiagramException() {
            super("No diagram selected");
        }
    }

    public static void clear() {
        concepts.clear();
        generalizationsClass.clear(); // предки
        generalizationsInterface.clear(); // предки
        realizations.clear(); //классы
        dependencies.clear(); // предки
        associations.clear(); // предки
        aggregations.clear(); // предки
        compositions.clear(); // предки
        allAssociations.clear();
    }
}
