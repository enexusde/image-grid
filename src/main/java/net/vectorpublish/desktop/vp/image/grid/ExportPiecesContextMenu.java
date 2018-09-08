package net.vectorpublish.desktop.vp.image.grid;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import net.vectorpublish.desktop.vp.api.layer.LayerPopupContributor;
import net.vectorpublish.desktop.vp.api.layer.LayerSelectionListener;
import net.vectorpublish.desktop.vp.api.ui.VPAbstractAction;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.Rect;
import net.vectorpublish.desktop.vp.image.grid.layer.GridLayer;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;

@Named
public class ExportPiecesContextMenu extends VPAbstractAction implements LayerPopupContributor, LayerSelectionListener {

	private GridLayer grid;

	public ExportPiecesContextMenu() {
		super(Texts.EXPORT_PIECES, Texts.EXPORT_PIECES_TOOL_TIP, false);
	}

	public boolean hasPopupItem(Set<VectorPublishNode> selection) {
		return selection.size() == 1 && selection.iterator().next() instanceof GridLayer;
	}

	public JMenuItem createPopupItem(Set<VectorPublishNode> selection) {
		if (hasPopupItem(selection)) {
			return new JMenuItem(this);
		}
		return null;
	}

	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {

			public void run() {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						Map<Short, Rect> pieces = grid.getPieces();
						for (short i = 1; i < pieces.size(); i++) {
							Rect rect = pieces.get(i);
							ImageLayer parent = grid.getParent();
							BufferedImage bi = parent.cut(rect);
							FileOutputStream fos = new FileOutputStream(
									new File(chooser.getSelectedFile(), String.valueOf(i) + ".png"));
							ImageIO.write(bi, "png", fos);
							fos.close();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public void notify(Set<VectorPublishNode> selection) {
		if (hasPopupItem(selection)) {
			this.grid = (GridLayer) selection.iterator().next();
		}
	}

}
