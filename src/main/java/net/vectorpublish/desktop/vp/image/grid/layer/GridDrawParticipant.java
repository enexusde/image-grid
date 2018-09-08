package net.vectorpublish.desktop.vp.image.grid.layer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Enumeration;

import net.vectorpublish.desktop.vp.api.DrawParticipant;
import net.vectorpublish.desktop.vp.api.ui.MouseParticipant;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;
import net.vectorpublish.desktop.vp.image.participant.ImageDrawParticipant;
import net.vectorpublish.desktop.vp.pd.official.RelativeKeyframeRecalculator;
import net.vectorpublish.desktop.vp.pd.official.TechnicalMouseDrag;
import net.vectorpublish.desktop.vp.pd.official.VectorPublishGraphics;

public class GridDrawParticipant implements DrawParticipant {

	private int x;
	private int y;
	private int w;
	private int h;
	private final GridLayer layer;

	public GridDrawParticipant(int x, int y, int w, int h, GridLayer layer) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.layer = layer;
	}

	public Cursor updateMouse(int markerX, int markerY, float docRelX, float docRelY, RelativeKeyframeRecalculator rel,
			TechnicalMouseDrag pressedLMBSince) {
		return null;
	}

	public Dimension getDimensions() {
		ImageLayer parent = layer.getParent();
		ImageDrawParticipant participant = parent.getParticipant();
		return participant.getDimensions();
	}

	public boolean opacity() {
		return false;
	}

	public void paint(VectorPublishGraphics graphics, int documentWidth, int documentHeight) {
		Enumeration<VectorPublishNode> children = layer.children();
		while (children.hasMoreElements()) {
			VectorPublishNode vectorPublishNode = (VectorPublishNode) children.nextElement();
			MouseParticipant participant = vectorPublishNode.getParticipant();
			if (participant instanceof DrawParticipant) {
				DrawParticipant drawParticipant = (DrawParticipant) participant;
				drawParticipant.paint(graphics, documentWidth, documentHeight);
			}
		}
	}

	public void paintOutside(VectorPublishGraphics graphics, RelativeKeyframeRecalculator relativeRecalculator,
			int documentWidth, int documentHeight) {
		Dimension dimensions = getDimensions();
		int countHorizontal = getDimensions().width / w + 1;
		int countVertical = getDimensions().height / h + 1;
		for (int blockX = 0; blockX < countHorizontal; blockX++) {
			for (int blockY = 0; blockY < countVertical; blockY++) {
				int techX = relativeRecalculator.calcTechnicalX(blockX * w + x);
				int techY = relativeRecalculator.calcTechnicalY(blockY * h + y);
				drawCross(graphics, techX, techY);
			}
		}

	}

	private void drawCross(VectorPublishGraphics graphics, int techX, int techY) {
		graphics.setColor(Color.WHITE);
		int space = 10;
		graphics.drawLine(techX - space, techY, techX + space, techY);
		graphics.drawLine(techX, techY - space, techX, techY + space);
	}

}
