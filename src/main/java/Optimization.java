import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by Ксения on 02.05.2017.
 */

public class Optimization {

    public static void redundant(String string) throws Exception {
        ArrayList<String> loops = new ArrayList<String>();
        MyHashMap hashMap;

        if (string.equals("generalization")) {
            hashMap = Controller.generalizationsInterface;
        } else if (string.equals("realization")) { ///// doesn't work!!!!
            hashMap = Controller.realizations;
        } else if (string.equals("aggregation")) {
            hashMap = Controller.aggregations;
        } else if (string.equals("composition")) {
            hashMap = Controller.compositions;
        } else {
            throw new Exception("Incorrect parameters");
        }

        for (Concept concept : Controller.concepts) {
            String child = concept.getId();
            if (hashMap.get(child) != null) {
                ArrayList<String> parentList = hashMap.get(child);
                for (String parent : hashMap.get(child)) {
                    if (getAll(hashMap, loops).get(parent) != null) {
                        for (String nextParent :
                                getAll(hashMap, loops).get(parent)) {
                            if (parentList.contains(nextParent)) {
                                System.out.println("Redundant "
                                        + string + " between "
                                        + Controller.findConcept(nextParent)
                                        .getName() + " and "
                                        + Controller.findConcept(child)
                                        .getName() + " can be deleted");
                            }
                        }
                    }
                }
            }

        }
    }

    public static ArrayList<String> getInterfacesParentForList(MyHashMap hashMap, ArrayList<String> list) {
        ArrayList<String> allParents = new ArrayList<>();
        for (String con : list) {
            ArrayList<String> all = new ArrayList<>();
            getAllParentsForConcept(con, all, con, hashMap, new ArrayList<>());
            allParents.addAll(all);
        }
        return allParents;
    }

    public static void incoherentInterface() {
        ArrayList<String> list = new ArrayList<>();

        for (Concept concept : Controller.concepts) {
            if (concept.getType().equals("Class")) {
                if (Controller.realizations.get(concept.getId()) != null)
                    list.addAll(Controller.realizations.get(concept.getId()));
            }
        }
        for (Concept parent : Controller.concepts) {
            if (parent.getType().equals("Interface")) {
                if (!getInterfacesParentForList(Controller.generalizationsInterface, list)
                        .contains(parent.getId())
                        & !list.contains(parent.getId())) {
                    System.out.println("Incoherent interface " + parent.getName());
                }
            }
        }
    }

    public static void multipleInheritance() {
        for (Concept concept : Controller.concepts) {
            if (concept.getType().equals("Class")) {
                String child = concept.getId();
                if (Controller.generalizationsClass.get(child) != null) {
                    if (Controller.generalizationsClass.get(child).size() > 1) {
                        System.out.println("Multiple inheritance in class " + Controller.findConcept(child).getName());
                    }
                }
            }
        }
    }

    public static void cyclic(String string) throws Exception {
        ArrayList<String> loops = new ArrayList<String>();
        MyHashMap hashMap;

        if (string.equals("class generalization")) {
            hashMap = Controller.generalizationsClass;
        } else if (string.equals("interface generalization")) {
            hashMap = Controller.generalizationsInterface;
        } else if (string.equals("aggregation")) {
            hashMap = Controller.aggregations;
        } else if (string.equals("composition")) {
            hashMap = Controller.compositions;
        } else if (string.equals("association")) {
            hashMap = Controller.associations;
        } else {
            throw new Exception("Incorrect parameters");
        }

        getAll(hashMap, loops);
        if (!loops.isEmpty()) {
            StringBuilder str = new StringBuilder();
            if (!loops.isEmpty()) {
                str.append("Cyclic " + string + " in classes:");
                for (String conceptId : loops) {
                    str.append(" " + Controller.findConcept(conceptId).getName() + ",");
                }
                str.deleteCharAt(str.lastIndexOf(","));
                System.out.println(str.toString());
            }
        }
    }


    public static ArrayList<String> getAllParentsForConcept(String child, ArrayList<String> allParents,
                                                            String rootChild, MyHashMap hashMap, ArrayList<String> loops) {

        ArrayList<String> parents = hashMap.get(child);
        if (parents != null) {
            for (String parent : parents) {
                if (parent.equals(child)) {
                    if (!loops.contains(child)) {
                        loops.add(child);
                    }
                    if (!loops.contains(parent)) {
                        loops.add(parent);
                    }
                    break;
                }
                if (parent.equals(rootChild)) {
                    if (!loops.contains(rootChild)) {
                        loops.add(rootChild);
                    }
                    if (!loops.contains(parent)) {
                        loops.add(parent);
                    }
                    break;
                }
                if (parent != null & !allParents.contains(parent)) {
                    allParents.add(parent);
                    getAllParentsForConcept(parent, allParents, rootChild, hashMap, loops);
                }
            }
        }
        return allParents;
    }

    public static MyHashMap getAll(MyHashMap hashMap, ArrayList<String> loops) {
        MyHashMap allParents = new MyHashMap();
        for (Concept con : Controller.concepts) {
            ArrayList<String> all = new ArrayList<String>();
            allParents.addParentsList(con.getId(), getAllParentsForConcept(con.getId(),
                    all, con.getId(), hashMap, loops));
        }
        return allParents;
    }
}


