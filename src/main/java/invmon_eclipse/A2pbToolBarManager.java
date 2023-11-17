package invmon_eclipse;

import java.lang.reflect.Field;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Currently not used. Can be deleted.
 */
public class A2pbToolBarManager extends ToolBarManager {

	private int itemStyle;
	
	
	public A2pbToolBarManager() {
	}

	public A2pbToolBarManager(int style) {
		super(style);
		this.itemStyle = style;
	}

	public A2pbToolBarManager(ToolBar toolbar) {
		super(toolbar);
		this.itemStyle = toolbar.getStyle();
	}
	
	@Override
	public void setStyle(int style) {
		super.setStyle(style);
		this.itemStyle = style;
	}

	@Override
	public ToolBar createControl(Composite parent) {
		if (!toolBarExist() && parent != null) {
			ToolBar toolBar = new A2pbToolBar(parent, itemStyle);
			toolBar.setMenu(getContextMenuControl());
			update(true);

			toolBar.getAccessible().addAccessibleListener(getAccessibleListener());
			
			Field privateField;
			try {
				privateField = ToolBarManager.class.getDeclaredField("toolBar");
		        privateField.setAccessible(true);
		        privateField.set(this, toolBar);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return getControl();
	}

	private boolean toolBarExist() {
		return getControl() != null && !getControl().isDisposed();
	}

	private Menu getContextMenuControl() {
		if ((getContextMenuManager() != null) && (getControl() != null)) {
			Menu menuWidget = getContextMenuManager().getMenu();
			if ((menuWidget == null) || (menuWidget.isDisposed())) {
				menuWidget = getContextMenuManager().createContextMenu(getControl());
			}
			return menuWidget;
		}
		return null;
	}

	private AccessibleListener getAccessibleListener() {
		return new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				if (e.childID != ACC.CHILDID_SELF) {
					ToolItem item = getControl().getItem(e.childID);
					if (item != null) {
						String toolTip = item.getToolTipText();
						if (toolTip != null) {
							e.result = toolTip;
						}
					}
				}
			}
		};

	}

}
