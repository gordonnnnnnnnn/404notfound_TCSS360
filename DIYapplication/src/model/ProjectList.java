package model;

import java.util.ArrayList;

/**
 * Class for the project list.
 * 
 * @author Tammy Vo
 */
public class ProjectList {

	/** ArrayList to store the projects. */
	private ArrayList<Project> myProjectList;

	/** Constructor for ProjectList. */
	public ProjectList() {
		myProjectList = new ArrayList<>();
	}

	
	/**
	 * Method to add projects to project list.
	 * @author Tammy Vo
	 * @param theProject
	 */
	public void addProject(final Project theProject) {
		myProjectList.add(theProject);
	}

	
	/**
	 * Method to delete projects from project list.
	 * @author Tammy Vo
	 * @param theProject
	 */
	public void deleteProject(final Project theProject) {
		myProjectList.remove(theProject);
	}

	
	/**
	 * Method to get project list.
	 * @author Tammy Vo
	 * @return project list. 
	 */
	public ArrayList<Project> getProjectList() {
		return myProjectList;
	}
	
	/**
	 * Determines whether the project list contains a project with the given
	 * name.
	 * 
	 * @author Gordon McCreary
	 * @param theName The name to search for.
	 * @return True if the project already exists.
	 */
	public boolean containsName(final String theName) {
		boolean result = false;
		for (Project p : myProjectList) {
			if (p.getMyName().equals(theName)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
