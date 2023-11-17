package invmon_eclipse;

import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.renderers.swt.SashRenderer;
import org.eclipse.e4.ui.workbench.renderers.swt.WorkbenchRendererFactory;

@SuppressWarnings("restriction")
public class A2pbWorkbenchRendererFactory extends WorkbenchRendererFactory {

	private SashRenderer sashRenderer;
	
	private A2pbStackRenderer stackRenderer;
	
	
	@Override
	public AbstractPartRenderer getRenderer(MUIElement uiElement, Object parent) {
		
		if (uiElement instanceof MPartSashContainer) {
			if (sashRenderer == null) {
				sashRenderer = new A2pbSashRenderer();
				initRenderer(sashRenderer);
			}
			return sashRenderer;
		} else if (uiElement instanceof MPartStack) {
			if (stackRenderer == null) {
				stackRenderer = new A2pbStackRenderer();
				initRenderer(stackRenderer);
			}
			return stackRenderer;
		} else {
			return super.getRenderer(uiElement, parent);
		}
	}
	
}
