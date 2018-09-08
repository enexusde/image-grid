package net.vectorpublish.desktop.vp.image.history;

import net.vectorpublish.desktop.vp.api.vpd.ModificationContext;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext.LayerNodeImpl;

public abstract class AllowImageNode extends LayerNodeImpl {
	public AllowImageNode(ModificationContext mc, LayerNodeImpl i) {
		mc.super(i);
	}
}
