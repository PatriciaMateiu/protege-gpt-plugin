package utcn.iva.gpt3.protege;

import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tab extends OWLWorkspaceViewsTab {

    private static final Logger log = LoggerFactory.getLogger(Tab.class);

    public Tab() {
        setToolTipText("Custom tooltip text for Example Tab (2)");
    }

    @Override
    public void initialise() {
        super.initialise();
        log.info("Example Workspace Tab (2) initialized");
    }

    @Override
    public void dispose() {
        super.dispose();
        log.info("Example Workspace Tab (2) disposed");
    }
}