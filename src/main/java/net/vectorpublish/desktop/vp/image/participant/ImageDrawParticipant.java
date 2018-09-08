package net.vectorpublish.desktop.vp.image.participant;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import net.vectorpublish.desktop.vp.api.DrawParticipant;
import net.vectorpublish.desktop.vp.api.ui.MouseParticipant;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.buttons.move.MovableAxis;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;
import net.vectorpublish.desktop.vp.pd.official.RelativeKeyframeRecalculator;
import net.vectorpublish.desktop.vp.pd.official.TechnicalMouseDrag;
import net.vectorpublish.desktop.vp.pd.official.VectorPublishGraphics;

public class ImageDrawParticipant implements DrawParticipant, MovableAxis {

	private final ImageLayer layer;
	private int y, x;
	private BufferedImage image;
	private final Dimension dim;

	public ImageDrawParticipant(BufferedImage image, ImageLayer layer) {
		this.image = image;
		this.dim = new Dimension(image.getWidth(), image.getHeight());
		this.layer = layer;
		this.x = 0;
		this.y = 0;
	}

	public Cursor updateMouse(int markerX, int markerY, float docRelX, float docRelY, RelativeKeyframeRecalculator rel,
			TechnicalMouseDrag pressedLMBSince) {
		return null;
	}

	public Dimension getDimensions() {
		return dim;
	}

	public boolean opacity() {
		return false;
	}

	public void paint(VectorPublishGraphics graphics, int documentWidth, int documentHeight) {
		graphics.drawImage(image, x, y, null);
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
		Enumeration<VectorPublishNode> children = layer.children();
		while (children.hasMoreElements()) {
			VectorPublishNode vectorPublishNode = (VectorPublishNode) children.nextElement();
			MouseParticipant participant = vectorPublishNode.getParticipant();
			if (participant instanceof DrawParticipant) {
				DrawParticipant drawParticipant = (DrawParticipant) participant;
				drawParticipant.paintOutside(graphics, relativeRecalculator, documentWidth, documentHeight);
			}
		}

	}

	public void moveHor(int x) {
		this.x += x;
	}

	public void moveVert(int y) {
		this.y += y;
	}

}
