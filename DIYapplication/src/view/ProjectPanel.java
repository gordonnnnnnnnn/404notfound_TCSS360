package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.Controller;
import model.Project;

/**
 * 
 * @author zheli
 *
 */
public class ProjectPanel extends JPanel {
	/**
	 * id.
	 */
	private static final long serialVersionUID = 7412616505273929177L;
	/**
	 * edit button.
	 */
	private final JButton myEdit;
	/**
	 * delete button.
	 */
	private final JButton myDelete;

	/**
	 * right panel.
	 */
	private final JPanel myRightPanel;
	/**
	 * project.
	 * @param theProject
	 * @author Zhe Li
	 * @author Ken Gil Romero
	 */
	public ProjectPanel(final Project theProject, final Controller theController) {
		//super();
		
		//setup layout.
		setLayout(new BorderLayout());
		//two buttons.
		myEdit = new JButton("edit");
		myDelete = new JButton("delete");
		//setup display stuff.
		setPreferredSize(new Dimension(240, 50));
		setBackground(new Color(245,245,245));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createTitledBorder(theProject.getMyName())));
		
//		add(myProject);
//		myProject.setBorder(BorderFactory.createTitledBorder(theProject.getMyName()));
//		myProject.setPreferredSize(new Dimension(240, 50));
		
		//new panel for those two buttons.
		myRightPanel = new JPanel();
		myRightPanel.setLayout(new GridLayout(2,1));
		//setup position.
		myRightPanel.add(myEdit, BorderLayout.EAST);
		myRightPanel.add(myDelete, BorderLayout.EAST);
		add(myRightPanel, BorderLayout.EAST);
		revalidate();
		
		//actionlistener for edit button.
		myEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				theController.editProject(theProject);
			}
			
		});
		//actionlistener for delete button.
		myDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				theController.deleteProject(theProject);
				removeAll();
				revalidate();
				theController.refreshProjects();
				repaint();
			}
			
		});
		
		/**
		 * mouselistener which used to listen user's mouse action.
		 * if a project panel is clicked, this project info will display.
		 * 
		 */
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				theController.getDescription(theProject);
			}
			
		});
	}
}
