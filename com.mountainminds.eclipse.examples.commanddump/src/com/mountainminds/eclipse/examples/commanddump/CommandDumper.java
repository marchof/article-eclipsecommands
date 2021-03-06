package com.mountainminds.eclipse.examples.commanddump;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, String> icons = getIcons();
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
				String icon = icons.get(cid);
				if (icon == null) {
					icon = "noicon.gif";
				}
				out.println("  <tr><td><img src=\"images/" + icon + 
						"\" width=\"16\" height=\"16\" alt=\"" + cname + "\"/> "
						+ cname + "</td><td><code>" + cid
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
	
	private Map<String, String> getIcons() {
		Map<String, String> map = new HashMap<String, String>();
		for (IConfigurationElement e : Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.ui.commandImages")) {
			String id = e.getAttribute("commandId");
			String icon = e.getAttribute("icon");
			int pos = icon.lastIndexOf('/');
			if (pos != -1) {
				icon = icon.substring(pos + 1);
			}
			map.put(id, icon);
		}		
		return map;		
	}

}
