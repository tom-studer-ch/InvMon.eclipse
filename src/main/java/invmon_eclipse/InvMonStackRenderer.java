/*********************************************************************
* Copyright (c) 2025 nCubate Software GmbH
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package invmon_eclipse;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.renderers.swt.StackRenderer;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/**
 * Custom StackRenderer that adds a border around the content area of part stacks.
 * The border is drawn on the left, bottom, and right sides, connecting to the tab.
 */
@SuppressWarnings("restriction")
public class InvMonStackRenderer extends StackRenderer {

	private static final int BORDER_COLOR_LIGHT = 0x808080;  // matches swt-tab-outline in light CSS
	private static final int BORDER_COLOR_DARK = 0x808080;   // matches swt-tab-outline in dark CSS

	private static boolean darkMode = false;


	/**
	 * Sets the dark mode flag. Called during styling initialization from InvMon.
	 */
	public static void setDarkMode(boolean dark) {
		darkMode = dark;
	}


	@Override
	public Object createWidget(MUIElement element, Object parent) {
		Object widget = super.createWidget(element, parent);

		if (widget instanceof CTabFolder && element instanceof MPartStack) {
			CTabFolder tabFolder = (CTabFolder) widget;
			MPartStack partStack = (MPartStack) element;
			addContentAreaBorder(tabFolder, partStack);
		}

		return widget;
	}

	/**
	 * Adds a paint listener to draw a border around the content area
	 * (left, bottom, right sides connecting to the tab).
	 * Skips rendering for Standalone part stacks.
	 */
	private void addContentAreaBorder(CTabFolder tabFolder, MPartStack partStack) {
		tabFolder.addPaintListener(e -> {
			// Skip border for Standalone part stacks
			if (partStack.getTags().contains("Standalone")) {
				return;
			}

			Point size = tabFolder.getSize();
			int tabHeight = tabFolder.getTabHeight();
			if (size.x <= 0 || size.y <= 0) {
				return;
			}

			GC gc = e.gc;
			Color borderColor = getBorderColor(tabFolder.getDisplay());
			gc.setForeground(borderColor);

			// Draw border around content area (below tabs)
			int x1 = 0;
			int y1 = tabHeight + 1;
			int x2 = size.x - 1;
			int y2 = size.y - 1;

			gc.drawLine(x1, y1, x1, y2);  // left
			gc.drawLine(x1, y2, x2, y2);  // bottom
			gc.drawLine(x2, y1, x2, y2);  // right
		});
	}

	/**
	 * Returns the appropriate border color based on the current theme.
	 */
	private Color getBorderColor(Display display) {
		int rgb = darkMode ? BORDER_COLOR_DARK : BORDER_COLOR_LIGHT;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = rgb & 0xFF;
		return new Color(display, r, g, b);
	}

}
