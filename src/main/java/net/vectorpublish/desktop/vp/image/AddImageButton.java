package net.vectorpublish.desktop.vp.image;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import net.vectorpublish.desktop.vp.History;
import net.vectorpublish.desktop.vp.api.history.Redo;
import net.vectorpublish.desktop.vp.api.layer.LayerSelectionListener;
import net.vectorpublish.desktop.vp.api.ui.ToolBar;
import net.vectorpublish.desktop.vp.api.ui.VPAbstractAction;
import net.vectorpublish.desktop.vp.api.ui.Warn;
import net.vectorpublish.desktop.vp.api.vpd.DocumentNode;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.history.AddImageStep;
import net.vectorpublish.desktop.vp.image.history.AllowImageNode;
import net.vectorpublish.desktop.vp.image.history.data.AddImageData;
import net.vectorpublish.desktop.vp.ui.ImageKey;

/**
 * The button to add an image to a Document.
 */
@Named
public class AddImageButton extends VPAbstractAction implements LayerSelectionListener {

	private VectorPublishNode targetNode;

	/**
	 * Constructor called by spring.
	 */
	public AddImageButton() {
		super(Texts.LOAD_IMAGE, Texts.LOAD_IMAGE_TOOL_TIP, true);
	}

	/**
	 * Dialog to warn if image could not be read.
	 */
	@Autowired
	private final Warn wrn = null;

	/**
	 * The toolbar to add a button.
	 */
	@Autowired
	private final ToolBar tb = null;

	/**
	 * History to add an history entry for adding an image.
	 */
	@Autowired
	private final History hist = null;

	/**
	 * The redo button to do the history change of adding an image.
	 */
	@Autowired
	private final Redo redo = null;

	/**
	 * Add the image to a document.
	 */
	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			public void run() {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(new JFrame());
				File file = fc.getSelectedFile();
				if (file.canRead()) {
					BufferedImage image;
					try {
						image = ImageIO.read(file);
						AddImageData data;
						if (targetNode instanceof DocumentNode) {
							DocumentNode documentNode = (DocumentNode) targetNode;
							data = new AddImageData(documentNode, image);
						} else if (targetNode instanceof AllowImageNode) {
							AllowImageNode allowImageNode = (AllowImageNode) targetNode;
							data = new AddImageData(allowImageNode, image);
						} else {
							return;
						}
						new AddImageStep(hist, hist.getCurrentDocument().getLastExecutedHistoryStep(), data);
						redo.actionPerformed(null);
					} catch (IOException e) {
						wrn.addWarning(e.getMessage());
					}
				}
			}
		}).start();
	}

	/**
	 * Notifys what image should be add.
	 */
	public void notify(Set<VectorPublishNode> selection) {
		Set<VectorPublishNode> allowedChildren = new LinkedHashSet<VectorPublishNode>();
		for (VectorPublishNode vectorPublishNode : selection) {
			if (vectorPublishNode instanceof AllowImageNode) {
				AllowImageNode allowImageNode = (AllowImageNode) vectorPublishNode;
				allowedChildren.add(allowImageNode);
			}
			if (vectorPublishNode instanceof DocumentNode) {
				DocumentNode documentNode = (DocumentNode) vectorPublishNode;
				allowedChildren.add(documentNode);
			}
		}
		setEnabled(allowedChildren.size() == 1);
		if (isEnabled()) {
			targetNode = allowedChildren.iterator().next();
		}

	}

	@PostConstruct
	public void setup() {
		setIcons(Texts.NS, ImageKey.get("addimage"));
		tb.add(this);
	}

}
