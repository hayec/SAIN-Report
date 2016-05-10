package user;

import java.util.ArrayList;

import eventHandlers.ModelChangedEventObject;
import eventHandlers.ModelListener;
import report.Course;
/**
 * Contains an ArrayList of Majors.  Implements a variety algorithms for conveniently adding, removing, and searching for majors.
 */
public class MajorBag {
    private ArrayList < Major > majors = new ArrayList < Major > ();
    private ModelListener listenerModel;
    /**
     * Adds the specified major to the ArrayList.
     * @param major Major to add.
     */
    public void addMajor(Major major) {
        majors.add(major);
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /**
     * Adds all majors in the specified array to the ArrayList.
     * @param newMajors Array of majors to add.
     */
    public void addMajor(Major[] newMajors) {
        for (int i = 0; i < newMajors.length; i++)
            majors.add(newMajors[i]);
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /**
     * Returns the specified major from the ArrayList.  If the major is not found, null is returned instead.
     * @param major Name of the major to be searched for
     * @return Search results
     */
    public Major getMajor(String major) {
        Major returnMajor = null;
        for (Major m: majors) {
            if (m.getName().equals(major)) {
                returnMajor = m;
                break;
            }
        }
        return returnMajor;
    }
    /**
     * Removes the specified major from the ArrayList
     * @param major Name of the major to be removed
     */
    public void removeMajor(String major) {
        for (Major m: (ArrayList < Major > ) majors.clone()) {
            if (m.getName().equals(major)) {
                majors.remove(m);
            }
        }
        if (listenerModel != null) {
            listenerModel.modelChanged(new ModelChangedEventObject(new Object()));
        }
    }
    /**
     * Returns all majors in the ArrayList
     * @return The array of all majors in the ArrayList
     */
    public Major[] getMajors() {
        return majors.toArray(new Major[majors.size()]);
    }
    /**
     * Returns the names of all majors in the ArrayList
     * @return The string array of the names of all of the majors in the ArrayList
     */
    public String[] getMajorNames() {
        ArrayList < String > majorNames = new ArrayList < String > ();
        for (Major m: majors)
            majorNames.add(m.getName());
        return majorNames.toArray(new String[majorNames.size()]);
    }
    public void setModelListener(ModelListener listenerModel) {
        this.listenerModel = listenerModel;
    }
}