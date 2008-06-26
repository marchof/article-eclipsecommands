package com.mountainminds.eclipse.examples.commandservices;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

public class CommandDefiningView extends ViewPart {

	public void createPartControl(Composite parent) {
		IWorkbench workbench = getSite().getWorkbenchWindow().getWorkbench();
		
		// Retrieve the Command Service
		ICommandService cs = (ICommandService) workbench.getService(ICommandService.class);
		
		// Define our Category
		Category category = cs.getCategory("com.mountainminds.eclipse.examples.sessions");
		category.define("User Sessions", "Management of user sessions");
		
		// Define Login Command
		Command login = cs.getCommand("com.mountainminds.eclipse.examples.login");
		login.define("Login", "Login a new user", category);
		
		// Define Logout Command
		Command logout = cs.getCommand("com.mountainminds.eclipse.examples.logout");
		logout.define("Logout", "Logout the current user", category);
	}

	public void setFocus() {
	}

}
