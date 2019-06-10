package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Controller;
import model.ProjectList;

/**
* Class for the left component of the main application
* @author Ken Gil Romero
* @version Spring 19
*/
public class ProjectsPanel extends JPanel {
	
	/**
	 * Dimension of the Panel of project list
	 */
	private static final Dimension PROJLISTDIMENSION = new Dimension(250, 370);
	
	/**
	 * Dimension of the button for adding projects
	 */
	private static final Dimension ADDBTNDIMENSION = new Dimension(250, 50);
	
	/**
	 * Size of project panel with gap
	 */
	private static final int PROJPANELSIZE = 55;

    /**
     * A generated serial version UID for object Serialization.
     */
	private static final long serialVersionUID = 5924207307409471100L;
	
	/**
	 * Panel that contains all the projects panel	
	 */
	private final JPanel myProjectsPanel;
	
    /** 
     * The model for reference. 
     */
    private final Controller myController;
	
    /**
     * Constructor of the ProjectsPanel
     * @param theController to references the constructor
     * @author Ken Gil Romero
     */
	ProjectsPanel(final Controller theController) {
		super();
		final JPanel LeftPanel = new JPanel(new BorderLayout());
		myController = theController;
		
        final JButton add = new JButton("  +  ");
        add.setBackground(Color.WHITE);
        add.setPreferredSize(ADDBTNDIMENSION);    
        add.addActionListener(theEvent -> myController.createNewProject());
        
        myProjectsPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(myProjectsPanel, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(PROJLISTDIMENSION);
        LeftPanel.add(add, BorderLayout.NORTH);
        LeftPanel.add(new JPanel(), BorderLayout.CENTER);
		LeftPanel.add(scrollPane, BorderLayout.SOUTH);
		
        add(LeftPanel);
	}

	/**
	 * Builds the project panels using the projectsList
	 * @param theProjectsList the panels list of projects
	 * @author Ken Gil Romero
	 */
	public void buildProjectPanels(final ProjectList theProjectsList) {
		myProjectsPanel.removeAll();
		for (int i = 0; i < theProjectsList.getProjectList().size(); i++) { //TODO advance for loop
			myProjectsPanel.add(new ProjectPanel(theProjectsList.getProjectList().get(i), myController));
		}
		final int temp = myController.getProjectsSize() * PROJPANELSIZE + 5;
		myProjectsPanel.setPreferredSize(new Dimension(myProjectsPanel.getWidth(), temp));
		myProjectsPanel.revalidate();
		myProjectsPanel.repaint();
	}
}
