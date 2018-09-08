package net.vectorpublish.desktop.vp.image.grid.layer;

import java.util.Map;

import net.vectorpublish.desktop.vp.api.ui.MouseParticipant;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext.LayerNodeImpl;
import net.vectorpublish.desktop.vp.image.Rect;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;

public class GridLayer extends LayerNodeImpl {

	private final GridDrawParticipant drawGrid;

	public GridLayer(ModificationContext ctx, ImageLayer il, int x, int y, int w, int h) {
		ctx.super(il);
		drawGrid = new GridDrawParticipant(x, y, w, h, this);
	}

	public MouseParticipant getParticipant() {
		return drawGrid;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public ImageLayer getParent() {
		return (ImageLayer) super.getParent();
	}

	public Map<Short, Rect> getPieces() {
		return drawGrid.getPieces();
	}
}
