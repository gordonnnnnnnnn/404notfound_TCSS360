package controller;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.*;
import view.*;

/**
 * 
 * @author 
 */
public class Controller {

	/**
	 * The project view. 
	 */
	private View myView;

	/** 
	 * The project model. 
	 */
	private Model myModel;

	/**
	 * Constructor for the Controller.
	 * 
	 * @author Gordon McCreary
	 * @param theLogin The login window.
	 */
	public Controller(final Login theLogin) {
		theLogin.assignController(this);
	}

	/**
	 * This method is automatically called by the login window after a login is
	 * successful.
	 * 
	 * @author Gordon McCreary
	 * @param theView  The view received from the login.
	 * @param theModel The model received from the login.
	 */
	public void loginSuccess(final View theView, final Model theModel) {
		myView = theView;
		myModel = theModel;
	}

	/**
	 * Calling this method will bring up the New Project Window.
	 * 
	 * @author Gordon McCreary
	 */
	public void createNewProject() {
		new NewEditProjectWindow(new Project(), myView, this);
	}

	/**
	 * This method is automatically called by the New Project Window once the create
	 * button is clicked.
	 * 
	 * @author Gordon McCreary
	 * @param theProject The project that was created.
	 */
	public void addCreatedProject(final Project theProject) {
		myModel.addProject(theProject);
	}
	
	/**
	 * Sets the save flag for prompting user to save to false
	 */
	public void setSaveFlagFalse() {
		myView.setSaveFlag(false);
	}

	/**
	 * This method is used to display the project's info when user click one of
	 * project from project list.
	 * 
	 * @param theProject the project
	 * @author Zhe Li
	 */
	public void getDescription(final Project theProject) {
		myView.getDescriptionPanel().displayDescription(theProject);
	}

	/**
	 * Calling this method will bring up the Edit Project Window.
	 * 
	 * @author Gordon McCreary
	 * @param theProject The project that will be edited.
	 */
	public void editProject(final Project theProject) {
		new NewEditProjectWindow(theProject, myView, this);
	}

	/**
	 * Calling this method will delete given project from project list.
	 * 
	 * @param theProject the project which will be delete.
	 * @author
	 * @author Ken Gil Romero
	 */
	public void deleteProject(final Project theProject) {
		myModel.getProjectList().deleteProject(theProject);
		myView.setSaveFlag(false);
		if (!myModel.getProjectList().getProjectList().isEmpty()) {
			getDescription(myModel.getProjectList().getProjectList().get(0));
		} else {
			myView.getDescriptionPanel().resetDescription();
		}
	}

	/**
	 * Calling this method refreshes the GUI display of the current project list.
	 * This method should be called whenever a project is added, deleted, or
	 * whenever sorting is performed on the ProjectList.
	 * 
	 * @author Ken Gil Romero
	 */
	public void refreshProjects() {
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * The action of Opening a project list txt file
	 * 
	 * @author Ken Gil Romero
	 */
	public void openProjects() {
		if (myModel.getFileChooser().showOpenDialog(myView) == JFileChooser.APPROVE_OPTION) {
			try {
				myModel.loadProjects(myModel.getFileChooser().getSelectedFile());
				myView.setSaveFlag(true);
				putFileInTitle(myModel.getFileChooser().getSelectedFile().getName());
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(myView, "Error loading file.");
			}
		}
		refreshProjects();
		if (!myModel.getProjectList().getProjectList().isEmpty()) {
			getDescription(myModel.getProjectList().getProjectList().get(0));
		}
	}

	/**
	 * Sets the action of Save, returns true if save was successful
	 * 
	 * @author Ken Gil Romero
	 */
	public boolean saveProjects() {
		if (myModel.getFileChooser().showSaveDialog(myView) == JFileChooser.APPROVE_OPTION) {
			try {
				myModel.saveProjects(myModel.getFileChooser().getSelectedFile());
				myView.setSaveFlag(true);
				putFileInTitle(myModel.getFileChooser().getSelectedFile().getName());
				return true;
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(myView, "Error saving file.");
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @return the firstname of the user
	 * @author Ken Gil Romero
	 */
	public String getFirstName() {
		return myModel.getFirstName();
	}

	/**
	 * @return the email of the user
	 * @author Ken Gil Romero
	 */
	public String getEmailAddress() {
		return myModel.getEmailAddress();
	}

	/**
	 * @return the size of the list of projects
	 * @author Ken Gil Romero
	 */
	public int getProjectsSize() {
		return myModel.getProjectList().getProjectList().size();
	}

	/**
	 * Determines whether a project already exists with the given name.
	 * 
	 * @author Gordon McCreary
	 * @param theProjectName The project name to search for.
	 * @return True if the project already exists.
	 */
	public boolean projectExists(final String theProjectName) {
		return myModel.getProjectList().containsName(theProjectName);
	}

	/**
	 * Removes the non-integer characters from a given string.
	 * 
	 * @author Gordon McCreary
	 * @param theInput The string to be formatted.
	 * @return The original string with non-integer character removed.
	 */
	public String formatInt(final String theInput) {
		return model.StringFormatters.removeNonInt(theInput);
	}

	/**
	 * Formats the given string to represent a US dollar cost.
	 * 
	 * @author Gordon McCreary
	 * @param theInput The string to be formatted.
	 * @return The original string formatted to represent a US dollar cost.
	 */
	public String formatCost(final String theInput) {
		return model.StringFormatters.formatCost(theInput);
	}

	/**
	 * If the given string is longer than the given length, the string return with
	 * any character past the given length removed.
	 * 
	 * @author Gordon McCreary
	 * @param theInput  The string to be formatted.
	 * @param theLength The maximum length allowed.
	 * @return The string with length less than or equal to the given length.
	 */
	public String formatLength(final String theInput, final int theLength) {
		return model.StringFormatters.formatLength(theInput, theLength);
	}

	/**
	 * Sorts the project panels by name in alphabetical order.
	 */
	public void sortByName() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByName());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by name in reverse alphabetical order.
	 */
	public void sortByNameR() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByNameReversed());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by cost in ascending order.
	 */
	public void sortByCost() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByCost());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by cost in descending order.
	 */
	public void sortByCostR() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByCostReversed());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by duration in ascending order.
	 */
	public void sortByDuration() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByDuration());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by duration in descending order.
	 */
	public void sortByDurationR() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByDurationReversed());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by energy efficiency in normal order.
	 */
	public void sortByEnergy() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByEnergy());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by energy efficiency in reversed order.
	 */
	public void sortByEnergyR() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByEnergyReversed());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by cost vs benefit in normal order.
	 */
	public void sortByCalc() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByCalculation());
		myView.buildProjectPanels(myModel.getProjectList());
	}

	/**
	 * Sorts the project panels by cost vs benefit in reversed order.
	 */
	public void sortByCalcR() {
		myModel.getProjectList().getProjectList().sort(ProjectComparator.sortByCalculationReversed());
		myView.buildProjectPanels(myModel.getProjectList());
	}
	
	/**
	 * Changes the view's JFrame title to display the file that is currently
	 * open.
	 * 
	 * @author Gordon McCreary
	 * @param theFileName The name of the file that is currently open.
	 */
	private void putFileInTitle(final String theFileName) {
		myView.setTitle("DIY Project Planner - " + theFileName);
	}
}
