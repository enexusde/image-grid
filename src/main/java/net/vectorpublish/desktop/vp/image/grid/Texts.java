package net.vectorpublish.desktop.vp.image.grid;

import net.vectorpublish.desktop.vp.i8n.I8nText;
import net.vectorpublish.desktop.vp.ui.Namespace;

public enum Texts implements I8nText {
	ADD_GRID_TOOL_TIP, ADD_GRID
	;

	public final static Namespace NS = Namespace.getNamespace("de.e-nexus", "imagegrid");

	public Namespace getNamespace() {
		return NS;
	}

}
