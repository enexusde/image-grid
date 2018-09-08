package net.vectorpublish.desktop.vp.image.history.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;

import net.vectorpublish.desktop.vp.api.history.ReadOnlyHistoryStepDataBean;
import net.vectorpublish.desktop.vp.api.vpd.DocumentNode;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.history.AllowImageNode;
import net.vectorpublish.desktop.vp.utils.SetUtils;

public class AddImageData implements ReadOnlyHistoryStepDataBean {
	private static final long serialVersionUID = -1944173850898735127L;

	private final List<Integer> pathToParent;

	private final byte[] image;

	public AddImageData(DocumentNode node, BufferedImage image) {
		this(image, node);
	}

	protected AddImageData(BufferedImage image, VectorPublishNode node) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.image = baos.toByteArray();
		pathToParent = SetUtils.nodeToImmutableIndex(node);

	}

	public AddImageData(AllowImageNode node, BufferedImage image) {
		this(image, node);
	}

	public List<Integer> getPathToParent() {
		return pathToParent;
	}

	public BufferedImage getImage() {
		try {
			return ImageIO.read(new ByteArrayInputStream(image));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
