/*
 * TCSS 360 - Spring 2019
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controller.Controller;
import model.Energy;
import model.Materials;
import model.Project;

/**
 * The new and edit project window for DIYProjectPlanner application.
 * 
 * @author Gordon McCreary
 * @version June 4, 2019
 */
public class NewEditProjectWindow {
	
	/* The application controller. */
	private Controller myController;
	
	/* The frame that is the main application. */
	private View myApp;
	
	/* The frame that is this window. */
	private JFrame myFrame;
	
	/* The project being edited. */
	private Project myProject;
	
	/* Deep copy of project materials to be edited before submitting. */
	private Materials myMaterials;
	
	/* True if creating a new project. */
	private boolean myNewFlag;
	
	/* The text field for project name. */
	private JTextField myNameField;
	
	/* The text field for project cost. */
	private JTextField myCostField;
	
	/* The text field for project duration days. */
	private JTextField myDaysField;
	
	/* The text field for project duration weeks. */
	private JTextField myWeeksField;
	
	/* The combo box for project energy efficiency. */
	private JComboBox<String> myEnergyField;
	
	/* The text area for project notes. */
	private JTextArea myNotesField;
	
	/* JLabel for material amount. */
	private JLabel myMaterialLabel;
	
	/* The JButton to submit the project information. */
	private JButton mySubmitButton;
	
	/**
	 * Constructor for the NewEditProjectWindow.
	 * 
	 * @author Gordon McCreary
	 * @param theProject The project being edited.
	 * @param theApp The main application that this window was called from.
	 */
	public NewEditProjectWindow(final Project theProject,
			   final View theApp, final Controller theController) {
		theApp.setEnabled(false);
		myController = theController;
		myApp = theApp;
		myProject = theProject;
		myMaterials = theProject.getMyMaterials();
		JPanel mainPanel = new JPanel();
		buildPanel(mainPanel);
		
		// Sets JFrame title and fields based on whether new or editing.
		myNewFlag = "".equals(theProject.getMyName());
		if (myNewFlag) {
			myFrame = new JFrame("Create New Project");
		} else {
			myFrame = new JFrame("Edit Project");
			myNameField.setText(theProject.getMyName());
			myCostField.setText(myController.formatCost("" + 
					   								   theProject.getMyCost()));
			myDaysField.setText("" + (theProject.getMyDays() % 7));
			myWeeksField.setText("" + (theProject.getMyDays() / 7));
			myEnergyField.setSelectedIndex(
									   theProject.getMyEnergy().getValue() - 1);
			myNotesField.setText(theProject.getMyNotes());
			mySubmitButton.setText("Update");
		}
		
		// Finish up and show frame.
		myFrame.add(mainPanel);
		myFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent theEvent) {
				myApp.setEnabled(true);
			}
		});
		showFrame(myFrame);
	}
	
	/**
	 * Builds the panel by adding all the labels, fields, buttons, etc.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The panel to add the components to.
	 */
	private void buildPanel(final JPanel thePanel) {
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		addName(thePanel);
		addCost(thePanel);
		addDuration(thePanel);
		addEnergyEfficiency(thePanel);
		addNotes(thePanel);
		addMaterials(thePanel);
		addSubmit(thePanel);
		thePanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
	}
	
	/**
	 * Adds the name JLabel and JTextField to the main JPanel.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addName(final JPanel thePanel) {
		myNameField = new JTextField(15);
		JPanel Name = new JPanel(new BorderLayout());
		
		// Formats name field once focus is lost.
				myNameField.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent theEvent) {
						myNameField.setText(myController.formatLength(
													myNameField.getText(), 20));
					}
					@Override
					public void focusGained(FocusEvent e) {}
				});
		
		//Panel layout.
		Name.add(new JLabel("Name:"), BorderLayout.WEST);
		Name.add(myNameField, BorderLayout.EAST);
		Name.setBorder(BorderFactory.createEmptyBorder(25, 0, 5, 0));
		thePanel.add(Name);
	}
	
	/**
	 * Adds the cost JLabel and JTextField to the main JPanel.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addCost(final JPanel thePanel) {
		myCostField = new JTextField("$", 10);
		JPanel Cost = new JPanel(new BorderLayout());
		
		// Formats cost field once focus is lost.
		myCostField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent theEvent) {
				myCostField.setText(
							    myController.formatCost(myCostField.getText()));
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		// Panel layout.
		Cost.add(new JLabel("Total Cost:"), BorderLayout.WEST);
		Cost.add(myCostField, BorderLayout.EAST);
		Cost.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 30));
		thePanel.add(Cost);
	}
	
	/**
	 * Adds the duration JLabel and JTextFields to the main JPanel, both for
	 * Days and Weeks.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addDuration(final JPanel thePanel) {
		myDaysField = new JTextField(5);
		myDaysField.setHorizontalAlignment(JTextField.CENTER);
		myWeeksField = new JTextField(5);
		myWeeksField.setHorizontalAlignment(JTextField.CENTER);
		JPanel Duration = new JPanel(new BorderLayout());
		JPanel DurEntries = new JPanel();
		JPanel Days = new JPanel(new BorderLayout());
		JPanel Weeks = new JPanel(new BorderLayout());
		
		// Formats days and weeks fields when focus is lost.
		myWeeksField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent theEvent) {
				myWeeksField.setText(
								myController.formatInt(myWeeksField.getText()));
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		myDaysField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent theEvent) {
				int days = Integer.parseInt(0 +
							     myController.formatInt(myDaysField.getText()));
				int weeks = (days / 7) + Integer.parseInt(0 +
						                                myWeeksField.getText());
				if (weeks > 9999999) {
					weeks = 9999999;
				}
				myDaysField.setText("" + (days % 7));
				myWeeksField.setText("" + weeks);
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		// Panel layout.
		Days.add(new JLabel("     Days"), BorderLayout.EAST);
		Days.add(myDaysField, BorderLayout.WEST);
		Weeks.add(new JLabel("     Weeks"), BorderLayout.EAST);
		Weeks.add(myWeeksField, BorderLayout.WEST);
		DurEntries.setLayout(new BoxLayout(DurEntries, BoxLayout.Y_AXIS));
		DurEntries.add(Days);
		DurEntries.add(Weeks);
		Duration.add(new JLabel("Duration:"), BorderLayout.WEST);
		Duration.add(DurEntries, BorderLayout.EAST);
		Duration.setPreferredSize(new Dimension(200, 60));
		Duration.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 25));
		thePanel.add(Duration);
	}
	
	/**
	 * Adds the material JPanel consisting of the label, fields, and buttons.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addMaterials(final JPanel thePanel) {
		myMaterialLabel = new JLabel("");
		JTextField materialName = new JTextField(9);
		JTextField materialCost = new JTextField("$", 8);
		JPanel Mats = new JPanel(new BorderLayout());
		JPanel southPanel = new JPanel();
		JButton viewMats = new JButton("View");
		JButton addMat = new JButton("+");
		
		// Formats material cost field once focus is lost.
		materialCost.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent theEvent) {
				materialCost.setText(
							 myController.formatCost(materialCost.getText()));
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		// Formats material name field once focus is lost.
		materialName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent theEvent) {
				materialName.setText(myController.formatLength(
												 materialName.getText(), 12));
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		// Adds action listener to view materials button.
		viewMats.addActionListener(theEvent -> new MaterialsWindow(myFrame,
															myMaterials, this));
		
		// Adds action listener to add material button.
		addMat.addActionListener(theEvent -> {
			if ("".equals(materialName.getText())) {
				JOptionPane.showMessageDialog(null,
										   	  "You must enter a material name!",
										   				 	 "Empty Name Field",
										   	   JOptionPane.INFORMATION_MESSAGE);
			} else {
				try {
					myMaterials.addMaterial(materialName.getText(),
					   Double.parseDouble(materialCost.getText().substring(1)));
				} catch (final NumberFormatException theException) {
					myMaterials.addMaterial(materialName.getText(), 0);
				}
				materialName.setText("");
				materialCost.setText("$");
				updateMatLabel();
			}
		});
		
		//Panel layout.
		southPanel.add(viewMats);
		southPanel.add(myMaterialLabel);
		Mats.add(new JLabel("Material name:     Cost:"), BorderLayout.NORTH);
		Mats.add(materialName, BorderLayout.WEST);
		Mats.add(materialCost, BorderLayout.CENTER);
		Mats.add(southPanel, BorderLayout.SOUTH);
		Mats.add(addMat, BorderLayout.EAST);
		Mats.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		updateMatLabel();
		thePanel.add(Mats);
	}
	
	/**
	 * Update the label the displays the number of materials.
	 * @author Gordon McCreary
	 */
	protected void updateMatLabel() {
		myMaterialLabel.setText("Total Materials: "
										 + myMaterials.getMaterialMap().size());
	}
	
	/**
	 * Adds the energy JLabel and JComboBox to the main JPanel.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addEnergyEfficiency(final JPanel thePanel) {
		myEnergyField = new JComboBox<String>(
										new String[] {"Low", "Medium", "High"});
		JPanel EnergyEfficiency = new JPanel(new BorderLayout());
		
		// Panel layout.
		EnergyEfficiency.add(new JLabel("Energy Efficiency:"),
															 BorderLayout.WEST);
		EnergyEfficiency.add(myEnergyField, BorderLayout.EAST);
		EnergyEfficiency.setBorder(
								BorderFactory.createEmptyBorder(12, 0, 12, 15));
		thePanel.add(EnergyEfficiency);
	}
	
	/**
	 * Adds the notes JLabel and JTextArea to the main JPanel.
	 * 
	 * @author Gordon McCreary
	 * @param thePanel The main JPanel.
	 */
	private void addNotes(final JPanel thePanel) {
		myNotesField = new JTextArea(4, 15);
		myNotesField.setLineWrap(true);
		JPanel Notes = new JPanel(new BorderLayout());
		JScrollPane scrollNotes = new JScrollPane(myNotesField);
		
		// Panel layout.
		Notes.add(new JLabel("Notes:"), BorderLayout.WEST);
		Notes.add(scrollNotes, BorderLayout.EAST);
		Notes.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		thePanel.add(Notes);
	}
	
	/**
	 * Adds the submit JButton to the main JPanel.
	 * 
	 * @author Gordon McCreary
	 * @author Ken Gil Romero
	 * @param thePanel The main JPanel.
	 */
	private void addSubmit(final JPanel thePanel) {
		mySubmitButton = new JButton("Create");
		
		// Add action listener to submit button.
		mySubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				if ("".equals(myNameField.getText())) {
					JOptionPane.showMessageDialog(null,
											   "You must enter a project name!",
											   				 "Empty Name Field",
						   					   JOptionPane.INFORMATION_MESSAGE);
				} else  if (myNewFlag && myController.projectExists(myNameField.getText())) {
					JOptionPane.showMessageDialog(null,
												 "Enter a unique project name!",
												 	   "Duplicate Project Name",
											   JOptionPane.INFORMATION_MESSAGE);
				} else {
					// If not empty or duplicate, submit information and close.
					submitProject();
					myApp.setAlwaysOnTop(true);
					myFrame.dispose();
					myApp.setAlwaysOnTop(false);
					if (myNewFlag) {
						myController.addCreatedProject(myProject);
					}
					myController.setSaveFlagFalse();
					myController.refreshProjects();
					myController.getDescription(myProject);
					myApp.setEnabled(true);
				}
			}
		});
		
		// Panel layout.
		final JPanel Submit = new JPanel(new BorderLayout());
		Submit.add(mySubmitButton, BorderLayout.EAST);
		Submit.setBorder(BorderFactory.createEmptyBorder(5, 0, 25, 0));
		Submit.setPreferredSize(new Dimension(200, 50));
		thePanel.add(Submit);
	}
	
	/**
	 * Submits the project information to the project, updating all of its
	 * fields to the inputed values.
	 * @author Gordon McCreary
	 */
	private void submitProject() {
		myProject.setMyName(myNameField.getText());
		try {
			myProject.setMyCost(Double.parseDouble(0 +
										   myCostField.getText().substring(1)));
		} catch (final StringIndexOutOfBoundsException theException) {
			myProject.setMyCost(0);
		}
		myProject.setMyDays(Integer.parseInt("0" + myDaysField.getText()) +
				  		  (7 * Integer.parseInt("0" + myWeeksField.getText())));
		myProject.setMyEnergy(Energy.valueOf(
					 ((String) myEnergyField.getSelectedItem()).toUpperCase()));
		myProject.setMyNotes(myNotesField.getText());
		myProject.setMyMaterials(myMaterials);
		myProject.calculateCostBenefit(myProject.getMyDays(),
								myProject.getMyCost(), myProject.getMyEnergy());
	}
	
	/**
	 * Shows the frame.
	 * 
	 * @author Gordon McCreary
	 * @param theFrame The frame to be shown.
	 */
	private void showFrame(final JFrame theFrame) {
		theFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		theFrame.pack();
		theFrame.setLocationRelativeTo(null);
		theFrame.setResizable(false);
		theFrame.setVisible(true);
	}
}