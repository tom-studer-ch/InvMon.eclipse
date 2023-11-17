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

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.renderers.swt.ToolBarManagerRenderer;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;

/**
 * Currently not used. Can be deleted.
 */
@SuppressWarnings("restriction")
public class InvMonToolBarManagerRenderer extends ToolBarManagerRenderer {

	public InvMonToolBarManagerRenderer() {
	}

	/**
	 * @param model
	 * @return mapped manager, if any
	 */
	public ToolBarManager getManager(MToolBar model) {
		int orientation = getOrientation(model);
		int style = orientation | SWT.WRAP | SWT.FLAT | SWT.RIGHT;
		ToolBarManager manager = super.getManager(model);
		if (manager == null) {
			manager = new InvMonToolBarManager(style);
			IContributionManagerOverrides overrides = null;
			MApplicationElement parentElement = model.getParent();
			if (parentElement == null) {
				parentElement = modelService.getContainer(model);
			}

			if (parentElement != null) {
				overrides = (IContributionManagerOverrides) parentElement.getTransientData().get(
						IContributionManagerOverrides.class.getName());
			}

			manager.setOverrides(overrides);
			linkModelToManager((MToolBar) model, manager);
		} else {
			ToolBar toolBar = manager.getControl();
			if (toolBar != null && !toolBar.isDisposed() && (toolBar.getStyle() & orientation) == 0) {
				toolBar.dispose();
			}
			manager.setStyle(style);
		}
		return manager;
	}
	
	private int getOrientation(final MUIElement element) {
		MUIElement theParent = element.getParent();
		if (theParent instanceof MTrimBar) {
			MTrimBar trimContainer = (MTrimBar) theParent;
			SideValue side = trimContainer.getSide();
			if (side.getValue() == SideValue.LEFT_VALUE || side.getValue() == SideValue.RIGHT_VALUE) {
				return SWT.VERTICAL;
			}
		}
		return SWT.HORIZONTAL;
	}


}
