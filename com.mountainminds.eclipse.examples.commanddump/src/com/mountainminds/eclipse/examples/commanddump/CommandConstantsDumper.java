package com.mountainminds.eclipse.examples.commanddump;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class CommandConstantsDumper implements IApplication {

	public Object start(IApplicationContext context) throws Exception {
		dump(System.out);
		return EXIT_OK;
	}

	public void stop() {
	}

	private void dump(PrintStream out) {
		for (IConfigurationElement cat : getCategories()) {
			String id = cat.getAttribute("id");
			String name = cat.getAttribute("name");
			out.println("    // " + name + " Category:");
			out.println();
			for (IConfigurationElement c : getCommands(id)) {
				String cid = c.getAttribute("id");
				String cname = c.getAttribute("name").replace(".", "");
				out.println("    /**");
				out.println("     * Id for command \"" + cname
						+ "\" in category \"" + name + "\"");
				out.println("     * (value is <code>\"" + cid + "\"</code>).");
				out.println("     */");
				out.println("    public static final String "
						+ name.toUpperCase() + "_"
						+ cname.toUpperCase().replace(" ", "") + " = \"" + cid
						+ "\"; //$NON-NLS-1$");
				out.println();
			}
		}
	}

	private List<IConfigurationElement> getCommands(String catid) {
		List<IConfigurationElement> list = new ArrayList<IConfigurationElement>();
		for (IConfigurationElement e : Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.ui.commands")) {
			if (e.getContributor().getName().equals("org.eclipse.ui")
					&& e.getName().equals("command")
					&& e.getAttribute("categoryId").equals(catid)) {
				list.add(e);
			}
		}
		return list;
	}

	private List<IConfigurationElement> getCategories() {
		List<IConfigurationElement> list = new ArrayList<IConfigurationElement>();
		for (IConfigurationElement e : Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.ui.commands")) {
			if (e.getContributor().getName().equals("org.eclipse.ui")
					&& e.getName().equals("category")) {
				list.add(e);
			}
		}
		return list;
	}
	
}
