package de.vogella.plugin.cobide.wizard;

import java.net.URI;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import de.vogella.plugin.cobide.wizard.CreateProject;

public class newwiz extends Wizard implements INewWizard {

	protected ProjectPage one;

	public newwiz() {
		// TODO Auto-generated constructor stub
	    super();
	    setNeedsProgressMonitor(true);		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	  public String getWindowTitle() {
	    return "Export My Data";
	  }
	

	  @Override
	  public void addPages() {
	    one = new ProjectPage();
	    addPage(one);
	  }

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
	    // Print the result to the console
	    //System.out.println(one.getText1());
	    try {
	        // create a new COBOL project...
	        final ProjectPage newProjectPage = (ProjectPage) one;
	        final String name = newProjectPage.getProjectName();
	        URI location = null;
	        if (!newProjectPage.isUseDefaultLocation()) {
	          location = new URI(newProjectPage.getLocation());
	        } // else location == null
	        System.out.println("Project Name & Location :"+name+":"+location);
	        CreateProject.createProject(name, location);
	      }
	      catch (Throwable t) {
	        System.out.println("Problem Creating a Project");
	        return false;
	      }
	      return true;
	}

}
