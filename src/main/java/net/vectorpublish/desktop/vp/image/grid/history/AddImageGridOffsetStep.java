package net.vectorpublish.desktop.vp.image.grid.history;

import java.util.Enumeration;

import net.vectorpublish.desktop.vp.History;
import net.vectorpublish.desktop.vp.History.HistoryStep;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.grid.history.data.AddImageGridOffsetData;
import net.vectorpublish.desktop.vp.image.grid.layer.GridLayer;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;

public class AddImageGridOffsetStep extends HistoryStep<AddImageGridOffsetData> {

	public AddImageGridOffsetStep(History h, HistoryStep<?> last, AddImageGridOffsetData data) {
		h.super(last, data);
	}

	@Override
	protected void execute(ModificationContext ctx) {
		ImageLayer imageLayer = (ImageLayer) ctx.getDocument().findByIndex(data.getPathToImageLayer());
		GridLayer gn = new GridLayer(ctx, imageLayer, data.getX(), data.getY(), data.getWidth(), data.getHeight());
	}

	@Override
	protected void rollback(ModificationContext ctx) {
		ImageLayer imageLayer = (ImageLayer) ctx.getDocument().findByIndex(data.getPathToImageLayer());
		Enumeration<VectorPublishNode> children = imageLayer.children();
		while (children.hasMoreElements()) {
			VectorPublishNode vectorPublishNode = (VectorPublishNode) children.nextElement();
			if (vectorPublishNode instanceof GridLayer) {
				GridLayer gridLayer = (GridLayer) vectorPublishNode;
				gridLayer.removeFromParent(ctx);
				return;
			}
		}
	}

}
