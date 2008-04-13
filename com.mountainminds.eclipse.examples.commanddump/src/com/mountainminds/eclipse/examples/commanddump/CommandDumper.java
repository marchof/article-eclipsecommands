package com.mountainminds.eclipse.examples.commanddump;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class CommandDumper implements IApplication {

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

			out
					.println("  <tr style=\"background-color:#cccccc\"><td><b>Category "
							+ name
							+ "</b></td><td><code>"
							+ id
							+ "</code></td></tr>");
			for (IConfigurationElement c : getCommands(id)) {
				String cid = c.getAttribute("id");
				String cname = c.getAttribute("name");
				out.println("  <tr><td>" + cname + "</td><td><code>" + cid
						+ "</code></td></tr>");
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
