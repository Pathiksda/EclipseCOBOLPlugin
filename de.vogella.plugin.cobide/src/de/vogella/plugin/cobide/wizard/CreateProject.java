package de.vogella.plugin.cobide.wizard;

import java.net.URI;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Nerger
 * @see http
 *      ://cvalcarcel.wordpress.com/2009/07/26/writing-an
 *      -eclipse
 *      -plug-in-part-4-create-a-custom-project-in-eclipse
 *      -new-project-wizard-the-behavior/
 */
public class CreateProject {

/**
   * For this marvelous project we need to: - create the
   * default Eclipse project - add the custom project nature
   * - create the folder structure
   * 
   * @param projectName
   * @param location
   * @param natureId
   * @return
   */
  public static IProject createProject(String projectName, URI location) {
    Assert.isNotNull(projectName);
    Assert.isTrue(projectName.trim().length() > 0);
    System.out.println("Project Name received is :"+projectName);
    IProject project = createBaseProject(projectName, location);
    System.out.println("Project wouldve been created by now!!");
    try {
      // add default source paths
      String[] paths = { "src/LIBRYMIS","src/LIBRYBOR","cat","card"}; //$NON-NLS-1$ //$NON-NLS-2$
      addToProjectStructure(project, paths);
      /*
      //manna
	  try {
		createFiles(project);
	  } catch (UnsupportedEncodingException e) { e.printStackTrace();}
      //manna
	  */
	  // add CODE Builder
      //addBuilder(project, CODEConstants.CODE_FULLQUALIFIED_BUILDER_ID);
      // add CODE Nature
      //addNature(project, CODEConstants.CODE_FULLQUALIFIED_NATURE_ID);
    }
    catch (CoreException e) {
      e.printStackTrace();
      project = null;
    }
    
    return project;
  }

//manna
  /*
	private static void createFiles(IProject project) throws CoreException, UnsupportedEncodingException{

		IFolder folder = project.getFolder("src/");
		String pgmname="XX0001";
		String filetype="COB";
		String templ_code="Template_OLTP";
		IFile file = folder.getFile(pgmname+"."+filetype);
		String templatePath = "/resources/"+templ_code;
		createFile(file, templatePath,templ_code,pgmname);

	}
	*/
	
	public static void createFile(IFile file, String templatePath,String templatecode,String pgmname) throws CoreException, UnsupportedEncodingException {
		InputStream inputStream = null;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();		
		inputStream = classloader.getResourceAsStream(templatePath);
		if (!file.exists()) {
			try {
				file.create(inputStream, IResource.NONE, null);
			}catch (Exception exception) { exception.printStackTrace();}
		}

	}
  
  /**
   * Just do the basics: create a basic project.
   * 
   * @param location
   * @param projectName
   */
  private static IProject createBaseProject(String projectName, URI location) {
    // it is acceptable to use the ResourcesPlugin class
    IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    
    if (!newProject.exists()) {
      URI projectLocation = location;
      IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
      if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
        projectLocation = null;
      }
      
      desc.setLocationURI(projectLocation);
      try {
        newProject.create(desc, null);
        if (!newProject.isOpen()) {
          newProject.open(null);
        }
      }
      catch (CoreException e) {
        e.printStackTrace();
      }
    }
    
    return newProject;
  }
  
  private static void createFolder(IFolder folder) throws CoreException {
    IContainer parent = folder.getParent();
    if (parent instanceof IFolder) {
      createFolder((IFolder) parent);
    }
    if (!folder.exists()) {
      folder.create(false, true, null);
    }
  }
  
  /**
   * Create a folder structure with a parent root, overlay,
   * and a few child folders.
   * 
   * @param newProject
   * @param paths
   * @throws CoreException
   */
  private static void addToProjectStructure(IProject newProject, String[] paths)
      throws CoreException {
    for (String path : paths) {
      IFolder etcFolders = newProject.getFolder(path);
      createFolder(etcFolders);
    }
  }
  
}