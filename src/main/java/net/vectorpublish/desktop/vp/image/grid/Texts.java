package net.vectorpublish.desktop.vp.image.grid;

import net.vectorpublish.desktop.vp.i8n.I8nText;
import net.vectorpublish.desktop.vp.ui.Namespace;

public enum Texts implements I8nText {
	ADD_GRID_TOOL_TIP, ADD_GRID, EXPORT_PIECES_TOOL_TIP, EXPORT_PIECES
	;

	public final static Namespace NS = Namespace.getNamespace("de.e-nexus", "imagegrid");

	public Namespace getNamespace() {
		return NS;
	}

}
