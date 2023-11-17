/*********************************************************************
* Copyright (c) 2023-11-23 Tom Studer @ nCubate Software GmbH
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package invmon_eclipse;

import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.renderers.swt.SashRenderer;
import org.eclipse.e4.ui.workbench.renderers.swt.WorkbenchRendererFactory;

@SuppressWarnings("restriction")
public class InvMonWorkbenchRendererFactory extends WorkbenchRendererFactory {

	private SashRenderer sashRenderer;
	
	private InvMonStackRenderer stackRenderer;
	
	
	@Override
	public AbstractPartRenderer getRenderer(MUIElement uiElement, Object parent) {
		
		if (uiElement instanceof MPartSashContainer) {
			if (sashRenderer == null) {
				sashRenderer = new InvMonSashRenderer();
				initRenderer(sashRenderer);
			}
			return sashRenderer;
		} else if (uiElement instanceof MPartStack) {
			if (stackRenderer == null) {
				stackRenderer = new InvMonStackRenderer();
				initRenderer(stackRenderer);
			}
			return stackRenderer;
		} else {
			return super.getRenderer(uiElement, parent);
		}
	}
	
}
