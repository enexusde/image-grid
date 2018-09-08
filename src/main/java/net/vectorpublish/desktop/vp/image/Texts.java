package net.vectorpublish.desktop.vp.image;

import net.vectorpublish.desktop.vp.i8n.I8nText;
import net.vectorpublish.desktop.vp.ui.Namespace;

public enum Texts implements I8nText {
	LOAD_IMAGE, LOAD_IMAGE_TOOL_TIP;
	public final static Namespace NS = Namespace.getNamespace("de.e-nexus", "loadimage");

	public Namespace getNamespace() {
		return NS;
	}

}
