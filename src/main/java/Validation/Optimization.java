package Validation;

import java.util.ArrayList;

/**
 * Created by Ксения on 02.05.2017.
 */

public class Optimization {

    protected static class IncorrectParametersException extends Exception {
        IncorrectParametersException() {
            super("Incorrect parameters");
        }
    }

    public static String redundant(String string) {
        ArrayList<String> loops = new ArrayList<String>();
        MyHashMap hashMap;
        StringBuilder report = new StringBuilder();
        boolean dual;

        try {
            if (string.equals("generalization")) {
                hashMap = Controller.generalizationsInterface;
                dual = true;
            } else if (string.equals("realization")) { ///// doesn't work!!!!
                hashMap = Controller.realizations;
                dual = true;
            } else if (string.equals("aggregation")) {
                hashMap = Controller.aggregations;
                dual = true;
            } else if (string.equals("composition")) {
                hashMap = Controller.compositions;
                dual = true;
            } else {
                throw new IncorrectParametersException();
            }

            if (!string.equals("realization")) {
                for (Concept concept : Controller.concepts) {
                    String child = concept.getId();
                    if (hashMap.get(child) != null) {
                        ArrayList<String> parentList = hashMap.get(child);
                        for (String parent : parentList) {
                            if (getAll(hashMap, loops, dual).get(parent) != null) {
                                for (String nextParent :
                                        getAll(hashMap, loops, dual).get(parent)) {
                                    if (parentList.contains(nextParent)) {

                                        report.append("Redundant "
                                                + string + " between " + Controller.findConcept(nextParent).getName()
                                                + " and " + Controller.findConcept(child).getName()
                                                + " can be deleted" + "\r\n");
                                    }
                                }
                            }
                        }
                    }

                }
            } else {
                for (Concept concept : Controller.concepts) {
                    String child = concept.getId();
                    if (concept.getType() == "Class" & Controller.realizations.get(child) != null) {
                        ArrayList<String> parentList = Controller.realizations.get(child);
                        if (parentList.size() > 1) {
                            MyHashMap allParents = getAll(Controller.generalizationsInterface, loops, dual);
                            for (String parent : parentList) {
                                if (allParents.get(parent) != null) {
                                    for (String nextParent :
                                            allParents.get(parent)) {
                                        if (parentList.contains(nextParent)) {
                                            report.append("Redundant "
                                                    + string + " between " + Controller.findConcept(nextParent).getName()
                                                    + " and " + Controller.findConcept(child).getName()
                                                    + " can be deleted" + "\r\n");
                                        }
                                    }
                                }
                            }
                        }

                    }
                    if (concept.getType() == "Class" & Controller.generalizationsClass.get(child) != null) {
                        ArrayList<String> parentList = Controller.generalizationsClass.get(child);
                        ArrayList allInterfaces = new ArrayList();
                        for (String parent : parentList) {
                            if (Controller.realizations.get(parent) != null)
                            allInterfaces.addAll(Controller.realizations.get(parent));
                        }
                        if (Controller.realizations.get(concept.getId()) != null) {
                            for (String interF : Controller.realizations.get(concept.getId())) {
                                if (allInterfaces.contains(interF)) {
                                    report.append("Redundant "
                                            + string + " between " + Controller.findConcept(concept.getId()).getName()
                                            + " and " + Controller.findConcept(interF).getName()
                                            + " can be deleted" + "\r\n");
                                }
                            }
                        }
                       /* if (allParents.get(parent) != null) {
                            for (String nextParent :
                                    allParents.get(parent)) {
                                if (parentList.contains(nextParent)) {
                                    report.append("Redundant "
                                            + string + " between " + Controller.findConcept(nextParent).getName()
                                            + " and " + Controller.findConcept(child).getName()
                                            + " can be deleted" + "\r\n");
                                }
                            }
                        }*/


                    }
                }
            }
            return report.toString();
        } catch (IncorrectParametersException e) {
            e.printStackTrace();
            return report.append("Redundant " + string + " detection hasn't been provided because of Exception" + "\r\n").toString();
        }
    }

    public static ArrayList<String> getInterfacesParentForList(MyHashMap hashMap, ArrayList<String> list) {
        ArrayList<String> allParents = new ArrayList<>();
        for (String con : list) {
            ArrayList<String> all = new ArrayList<>();
            getAllParentsForConcept(con, all, con, hashMap, new ArrayList<>(), true, null);
            allParents.addAll(all);
        }
        return allParents;
    }

    public static String incoherentInterface() {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder report = new StringBuilder();

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
                    report.append("Incoherent interface " + parent.getName() + "\r\n");
                }
            }
        }
        return report.toString();
    }

    public static String incoherentClass() {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder report = new StringBuilder();

        for (Concept concept : Controller.concepts) {
            if (concept.getType().equals("Class")) {
                if (Controller.realizations.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.realizations.get(concept.getId()));
                }
                if (Controller.associations.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.associations.get(concept.getId()));
                }
                if (Controller.aggregations.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.aggregations.get(concept.getId()));
                }
                if (Controller.compositions.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.compositions.get(concept.getId()));
                }
                if (Controller.dependencies.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.dependencies.get(concept.getId()));
                }
                if (Controller.generalizationsClass.get(concept.getId()) != null) {
                    list.add(concept.getId());
                    list.addAll(Controller.generalizationsClass.get(concept.getId()));
                }
            }
        }
        for (Concept concept : Controller.concepts) {
            if (!list.contains(concept.getId())) {
                report.append("Incoherent class " + concept.getName() + "\r\n");
            }
        }
        return report.toString();
    }


    public static String multipleInheritance() {
        StringBuilder report = new StringBuilder();
        for (Concept concept : Controller.concepts) {
            if (concept.getType().equals("Class")) {
                String child = concept.getId();
                if (Controller.generalizationsClass.get(child) != null) {
                    if (Controller.generalizationsClass.get(child).size() > 1) {
                        report.append("Multiple inheritance in class " + Controller.findConcept(child).getName() + "\r\n");
                    }
                }
            }
        }
        return report.toString();
    }

    public static String cyclic(String string) {
        ArrayList<String> loops = new ArrayList<String>();
        MyHashMap hashMap;
        StringBuilder report = new StringBuilder();
        boolean dual;

        try {
            if (string.equals("class generalization")) {
                hashMap = Controller.generalizationsClass;
                dual = true;
            } else if (string.equals("interface generalization")) {
                hashMap = Controller.generalizationsInterface;
                dual = true;
            } else if (string.equals("aggregation")) {
                hashMap = Controller.aggregations;
                dual = true;
            } else if (string.equals("composition")) {
                hashMap = Controller.compositions;
                dual = true;
            } else if (string.equals("association")) {
                hashMap = Controller.associations;
                dual = false;
            } else {
                throw new IncorrectParametersException();
            }

            getAll(hashMap, loops, dual);
            if (!loops.isEmpty()) {
                report.append("Cyclic " + string + " in classes:");
                for (String conceptId : loops) {
                    report.append(" " + Controller.findConcept(conceptId).getName() + ",");
                }
                report.deleteCharAt(report.lastIndexOf(","));
                report.append("\r\n");
            }
            return report.toString();
        } catch (IncorrectParametersException e) {
            e.printStackTrace();
            return report.append("Cyclic " + string + " detection hasn't been provided because of Exception" + "\r\n").toString();
        }
    }


    public static ArrayList<String> getAllParentsForConcept(String child, ArrayList<String> allParents,
                                                            String rootChild, MyHashMap hashMap,
                                                            ArrayList<String> loops, boolean dual, String previous) {

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

                if (dual) {
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
                        getAllParentsForConcept(parent, allParents, rootChild, hashMap, loops, dual, child);
                    }
                } else {
                    if (parent.equals(rootChild) & !parent.equals(previous)) {
                        if (!loops.contains(rootChild)) {
                            loops.add(rootChild);
                        }
                        if (!loops.contains(parent)) {
                            loops.add(parent);
                        }
                        break;
                    }
                    if (parent != null & !allParents.contains(parent) & !parent.equals(previous)) {
                        allParents.add(parent);
                        getAllParentsForConcept(parent, allParents, rootChild, hashMap, loops, dual, child);
                    }
                }
            }
        }
        return allParents;
    }

    public static MyHashMap getAll(MyHashMap hashMap, ArrayList<String> loops, boolean dual) {
        MyHashMap allParents = new MyHashMap();
        for (Concept con : Controller.concepts) {
            ArrayList<String> all = new ArrayList<String>();
            allParents.addParentsList(con.getId(), getAllParentsForConcept(con.getId(),
                    all, con.getId(), hashMap, loops, dual, null));
        }
        return allParents;
    }
}


