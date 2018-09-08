package net.vectorpublish.desktop.vp.image.grid.layer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import net.vectorpublish.desktop.vp.api.DrawParticipant;
import net.vectorpublish.desktop.vp.api.ui.MouseParticipant;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.Rect;
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
		Dimension dim = getDimensions();
		int countHorizontal = dim.width / w + 1;
		int countVertical = dim.height / h + 1;
		short piece = 0;
		for (int blockY = 0; blockY < countVertical; blockY++) {
			for (int blockX = 0; blockX < countHorizontal; blockX++) {
				int techX = relativeRecalculator.calcTechnicalX(blockX * w + x);
				int techY = relativeRecalculator.calcTechnicalY(blockY * h + y);
				piece++;
				drawCross(graphics, techX, techY);
				String number = String.valueOf(piece);
				Rectangle2D textDimensions = graphics.getFontMetrics().getStringBounds(number, graphics);
				int textX = relativeRecalculator
						.calcTechnicalX((int) (blockX * w + x + w / 2 - textDimensions.getWidth() / 2));
				int textY = relativeRecalculator
						.calcTechnicalY((int) (blockY * h + y + h / 2 - textDimensions.getHeight() / 2));
				graphics.drawString(number, textX, textY);
			}
		}

	}

	private void drawCross(VectorPublishGraphics graphics, int techX, int techY) {
		graphics.setColor(Color.WHITE);
		int space = 10;
		graphics.drawLine(techX - space, techY, techX + space, techY);
		graphics.drawLine(techX, techY - space, techX, techY + space);
	}

	public Map<Short, Rect> getPieces() {
		Map<Short, Rect> rects = new LinkedHashMap<>();
		Dimension dim = getDimensions();
		int countHorizontal = dim.width / w + 1;
		int countVertical = dim.height / h + 1;
		short piece = 0;
		for (int blockY = 0; blockY < countVertical; blockY++) {
			for (int blockX = 0; blockX < countHorizontal; blockX++) {
				rects.put(++piece, new Rect(blockX * w + x, blockY * h + y, w, h));
			}
		}
		return rects;
	}

}
