package net.vectorpublish.desktop.vp.image.grid.history.data;

import java.util.List;

import net.vectorpublish.desktop.vp.api.history.ReadOnlyHistoryStepDataBean;

public class AddImageGridOffsetData implements ReadOnlyHistoryStepDataBean {

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final List<Integer> pathToImageLayer;

	public AddImageGridOffsetData(int x, int y, int width, int height, List<Integer> pathToImageLayer) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.pathToImageLayer = pathToImageLayer;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Integer> getPathToImageLayer() {
		return pathToImageLayer;
	}
}
