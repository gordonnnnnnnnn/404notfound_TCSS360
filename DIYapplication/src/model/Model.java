package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * Class for model part of the MVC application
 * @author Ken Gil Romero
 * @version Spring 19
 */
public class Model {
	
	/**
	 * Header of the project workspace
	 */
	private static final String myFileHeader = "#DIYProjects";
	
	/**
	 * Header of the projects' user's name
	 */
	private static final String myFileNameHeader = "#Name: ";
	
	/**
	 * Header of the projects' user's email address
	 */
	private static final String  myfileEmailAddrHeader = "#EmailAddress: ";
	
	/**
	 * Header of the number of projects
	 */
	private static final String myFileProjectsSizeHeader = "#ProjectsSize: ";
	
	/**
	 * Header of a project's name
	 */
	private static final String myFileProjectNameHeader = "#ProjectName: ";
	
	/**
	 * Header of the project's cost
	 */
	private static final String myFileCostHeader = "#Cost: ";
	
	/**
	 * Header of the project's duration for days
	 */
	private static final String myFileDurationDayHeader = "#DurationDay: ";
	
	/**
	 * Header of the project's energy efficiency
	 */
	private static final String myFileEnergyEffHeader = "#EnergyEfficiency: ";
	
	/**
	 * Header of the project's notes
	 */
	private static final String myFileNotesHeader = "#Notes: ";
	
	/**
	 * Header of the project's number of materials
	 */
	private static final String myFileMaterialsSizeHeader = "#MaterialSize: ";
	
	/**
	 * Header of the project's material's name
	 */
	private static final String myFileMaterialNameHeader = "#MaterialName: ";
	
	/**
	 * Header of the project's material's cost
	 */
	private static final String myFileMaterialCostHeader = "#MaterialCost: ";
	
	/**
	 * Header of the project's notes end
	 */
	private static final String myFileEndNotes = "-endnotes-";
	
	/**
	 * The list of projects
	 */
	private ProjectList myProjects;
	
	/**
	 * The user's name
	 */
	private String myUserFirstName;
	
	/**
	 * The user's email address
	 */
	private String myUserEmailAddr;
	
	/** The file chooser for opening and saving an image. */
	private final JFileChooser myJFileChooser;
	
		
	/**
	 * Constructor of  the DIYFileManager
	 * @author Ken Gil Romero
	 */
	public Model(final String theFirstName, final String theEmailAddr) {
		myProjects= new ProjectList();
		myUserFirstName = theFirstName;
		myUserEmailAddr = theEmailAddr;
		myJFileChooser = new JFileChooser(".");
	}
	
	/**
	 * @return the first name of the DIYer
	 */
	public String getFirstName() {
		return myUserFirstName;
	}
	
	/**
	 * @return the file chooser of the model
	 * @author Ken Gil Romero
	 */
	public JFileChooser getFileChooser() {
		return myJFileChooser;
	}
	
	/**
	 * @return the email address of the DIYer
	 * @author Ken Gil Romero
	 */
	public String getEmailAddress() {
		return myUserEmailAddr;
	}
	
	/**
	 * @param theProject to be added to the list of projects
	 * @author Ken Gil Romero
	 */
	public void addProject(final Project theProject) {
		myProjects.addProject(theProject);
	}
	
	/**
	 * loading the list of projects
	 * @param theProjectsFile the file to be loaded
	 * @throws IOException the exception to be thrown
	 * @author Ken Gil Romero
	 */
    public void loadProjects(final File theProjectsFile) throws IOException {
    	ArrayList<Project> tempProjects = new ArrayList<>();
    	final Scanner scan = new Scanner(theProjectsFile); 
    	int projectsSize = 0;
    	checkHeader(scan, myFileHeader);
    	checkHeader(scan, myFileNameHeader);
    	myUserFirstName = scan.next(); 
    	checkHeader(scan, myfileEmailAddrHeader);
    	myUserEmailAddr = scan.next(); 
    	checkHeader(scan, myFileProjectsSizeHeader);
    	if (scan.hasNextInt()) {
    		projectsSize = scan.nextInt();
    	} else {
    		closeScanThrowIOEx(scan);
    	}
    	//new project list
    	for (int i = 0; i < projectsSize; i++) {
        	checkHeader(scan, myFileProjectNameHeader);
        	String projectName = scan.nextLine().trim();    
        	checkHeader(scan, myFileCostHeader);
        	double projectCost = 0;    
        	if (scan.hasNextDouble()) {
        		projectCost = scan.nextDouble();
        	} else {
        		closeScanThrowIOEx(scan);
        	}
        	checkHeader(scan, myFileDurationDayHeader);
        	int projectDurationDay = 0;    	
        	if (scan.hasNextInt()) {
        		projectDurationDay = scan.nextInt();
        	} else {
        		closeScanThrowIOEx(scan);
        	}
        	checkHeader(scan, myFileEnergyEffHeader);
        	int energyEff = 0;
        	if (scan.hasNextInt()) {
        		energyEff = scan.nextInt();
        	} else {
        		closeScanThrowIOEx(scan);
        	}
        	checkHeader(scan, myFileNotesHeader);
        	String projectNotes = "";
        	scan.nextLine();
        	while (scan.hasNextLine()) {
        		String currLine = scan.nextLine();    	
        		if (currLine.equals(myFileEndNotes)) {
        			break;
        		} else {
        			projectNotes += currLine + "\n";    
        		}
        	}
        	checkHeader(scan, myFileMaterialsSizeHeader);
        	int materialsSize = 0;
        	if (scan.hasNextInt()) {
        		materialsSize = scan.nextInt();
        	} else {
        		closeScanThrowIOEx(scan);
        	}
        	Materials materials = new Materials();
        	for (int j = 0; j < materialsSize; j++) {
        		checkHeader(scan, myFileMaterialNameHeader);
        		String materialName = scan.nextLine().trim();
        		checkHeader(scan, myFileMaterialCostHeader);
        		double materialCost = 0.0;
            	if (scan.hasNextDouble()) {
            		materialCost = scan.nextDouble();
            	} else {
            		closeScanThrowIOEx(scan);
            	}
            	materials.addMaterial(materialName, materialCost);
        	}
        	Project project = new Project(projectName, projectDurationDay, projectCost
        			, materials, Energy.getEnumVal(energyEff), projectNotes);
        	tempProjects.add(project);
    	}
    	scan.close();
    	//deleting current projects
    	myProjects = new ProjectList();
    	//adding the loaded projects
    	for (Project project: tempProjects) {
    		myProjects.addProject(project);
    	}
    }

	/**
	 * @param theScan the scanner to be closed
	 * @throws IOException the exception to be thrown
	 * @author Ken Gil Romero
	 */
	private void closeScanThrowIOEx(final Scanner theScan) throws IOException {
		theScan.close();
		throw new IOException();
	}
    
    /**
     * Closes the scan and throw IOException.
     * @param theScan the scanner to be closed
     * @param theHeader the header to be checked
     * @throws IOException the exception thrown.
     * @author Ken Gil Romero
     */
    private void checkHeader(final Scanner theScan, final String theHeader) throws IOException {
    	String scan1 = theScan.next();
    	if(!scan1.equals(theHeader.trim())) {	
    		closeScanThrowIOEx(theScan);
    	}
    }
    
    /**
     * @param theProjectsFile the file to be saved
     * @throws IOException the exception to be thrown
     * @author Ken Gil Romero
     */
    public void saveProjects(final File theProjectsFile) throws IOException {
    	FileWriter fileWriter = new FileWriter(theProjectsFile);
    	fileWriter.write(myFileHeader + "\n");
    	fileWriter.write(myFileNameHeader + myUserFirstName + "\n"); 
    	fileWriter.write(myfileEmailAddrHeader + myUserEmailAddr + "\n"); 
    	fileWriter.write(myFileProjectsSizeHeader + myProjects.getProjectList().size() + "\n"); 
    	for (int i = 0; i < myProjects.getProjectList().size(); i++) {
    		fileWriter.write(myFileProjectNameHeader + myProjects.getProjectList().get(i).getMyName() + "\n");  	
    		fileWriter.write(myFileCostHeader + myProjects.getProjectList().get(i).getMyCost() + "\n");
    		fileWriter.write(myFileDurationDayHeader + myProjects.getProjectList().get(i).getMyDays() + "\n");
    		fileWriter.write(myFileEnergyEffHeader + myProjects.getProjectList().get(i).getMyEnergy().getValue() + "\n");
    		fileWriter.write(myFileNotesHeader + "\n" + myProjects.getProjectList().get(i).getMyNotes().trim());
    		if (!myProjects.getProjectList().get(i).getMyNotes().trim().equals("")) {
    			fileWriter.write("\n");
    		}
    		fileWriter.write(myFileEndNotes + "\n");
    		fileWriter.write(myFileMaterialsSizeHeader + myProjects.getProjectList().get(i).getMyMaterials().getMaterialMap().size() + "\n");
    		for (Map.Entry<String,Double> entry : myProjects.getProjectList().get(i).getMyMaterials().getMaterialMap().entrySet()) {
    			fileWriter.write(myFileMaterialNameHeader + entry.getKey() + "\n");
    			fileWriter.write(myFileMaterialCostHeader + entry.getValue() + "\n");
    		}
    	}
    	fileWriter.close();
    }  

    /**
     * @return the project list
     * @author Ken Gil Romero
     */
    public ProjectList getProjectList() {
    	return myProjects;
    }
}
