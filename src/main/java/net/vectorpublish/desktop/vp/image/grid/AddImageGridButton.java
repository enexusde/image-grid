package net.vectorpublish.desktop.vp.image.grid;

import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import net.vectorpublish.desktop.vp.History;
import net.vectorpublish.desktop.vp.api.history.Redo;
import net.vectorpublish.desktop.vp.api.layer.LayerSelectionListener;
import net.vectorpublish.desktop.vp.api.ui.Dialog;
import net.vectorpublish.desktop.vp.api.ui.ToolBar;
import net.vectorpublish.desktop.vp.api.ui.VPAbstractAction;
import net.vectorpublish.desktop.vp.api.vpd.VectorPublishNode;
import net.vectorpublish.desktop.vp.image.grid.history.AddImageGridOffsetStep;
import net.vectorpublish.desktop.vp.image.grid.history.data.AddImageGridOffsetData;
import net.vectorpublish.desktop.vp.image.layer.ImageLayer;
import net.vectorpublish.desktop.vp.ui.ImageKey;
import net.vectorpublish.desktop.vp.utils.SetUtils;

@Named
public class AddImageGridButton extends VPAbstractAction implements LayerSelectionListener {

	public AddImageGridButton() {
		super(Texts.ADD_GRID, Texts.ADD_GRID_TOOL_TIP, true);
		System.out.println("HM?");
	}

	private ImageLayer targetImageLayer = null;

	@Autowired
	public final Redo redo = null;

	@Autowired
	public final ToolBar tb = null;
	@Autowired
	public final History history = null;
	@Autowired
	public final Dialog dlg = null;

	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Future<Integer> questionOffsetX = dlg.ask(Texts.NS, "Offset X", 0);
					Future<Integer> questionOffsetY = dlg.ask(Texts.NS, "Offset Y", 0);
					Future<Integer> widthQuestion = dlg.ask(Texts.NS, "Width", 0);
					Future<Integer> heightQuestion = dlg.ask(Texts.NS, "Height", 0);
					int x = questionOffsetX.get();
					int y = questionOffsetY.get();
					int width = widthQuestion.get();
					int height = heightQuestion.get();

					List<Integer> pathToImageLayer = SetUtils.nodeToImmutableIndex(targetImageLayer);
					AddImageGridOffsetData data = new AddImageGridOffsetData(x, y, width, height, pathToImageLayer);
					AddImageGridOffsetStep step = new AddImageGridOffsetStep(history,
							history.getCurrentDocument().getLastExecutedHistoryStep(), data);
					redo.actionPerformed(null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}).start();
		;
	}

	@PostConstruct
	public void setup() {
		setIcons(Texts.NS, ImageKey.get("add.image.grid"));
		tb.add(this);
	}

	public void notify(Set<VectorPublishNode> selection) {
		Set<ImageLayer> il = new LinkedHashSet<ImageLayer>();
		for (VectorPublishNode vectorPublishNode : selection) {
			if (vectorPublishNode instanceof ImageLayer) {
				il.add((ImageLayer) vectorPublishNode);
			}
		}
		setEnabled(il.size() == 1);
		if (isEnabled()) {
			targetImageLayer = il.iterator().next();
		}
	}

}
