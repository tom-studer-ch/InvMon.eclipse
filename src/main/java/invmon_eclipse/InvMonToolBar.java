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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

/**
 * Currently not used. Can be deleted.
 */
public class InvMonToolBar extends ToolBar {

	public InvMonToolBar(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected void checkSubclass () {
	}

}

