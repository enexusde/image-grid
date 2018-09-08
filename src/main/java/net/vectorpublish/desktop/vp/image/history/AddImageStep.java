package net.vectorpublish.desktop.vp.image.history;

import net.vectorpublish.desktop.vp.History;
import net.vectorpublish.desktop.vp.History.HistoryStep;
import net.vectorpublish.desktop.vp.api.vpd.DocumentNode;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext;
import net.vectorpublish.desktop.vp.api.vpd.ModificationContext.LayerNodeImpl;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.history.data.AddImageData;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;

public class AddImageStep extends HistoryStep<AddImageData> {

	public AddImageStep(History history, HistoryStep<?> last, AddImageData data) {
		history.super(last, data);
	}

	@Override
	protected void execute(ModificationContext ctx) {
		VectorPublishNode parent = (VectorPublishNode) ctx.getDocument().findByIndex(data.getPathToParent());
		ImageLayer imageLayer;
		if (parent instanceof DocumentNode) {
			DocumentNode documentNode = (DocumentNode) parent;
			imageLayer = new ImageLayer(ctx, documentNode, data.getImage());
		} else if (parent instanceof LayerNodeImpl) {
			LayerNodeImpl layerNodeImpl = (LayerNodeImpl) parent;
			imageLayer = new ImageLayer(ctx, layerNodeImpl, data.getImage());
		}
	}

	@Override
	protected void rollback(ModificationContext ctx) {

	}

}
