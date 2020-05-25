package name.kuznetc;

import com.android.tools.idea.databinding.LayoutBindingProjectComponent;
import com.intellij.facet.ProjectFacetManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.annotations.NotNull;

public class AndroidStats extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    assert project != null;

    int androidFacetCount = ProjectFacetManager.getInstance(project).getFacets(AndroidFacet.ID).size();
    LayoutBindingProjectComponent layoutBindingComponent = project.getComponent(LayoutBindingProjectComponent.class);
    int allBindingEnabledFacetsCount = layoutBindingComponent.getAllBindingEnabledFacets().size();
    int dataBindingEnabledFacetsCount = layoutBindingComponent.getDataBindingEnabledFacets().size();
    int viewBindingEnabledFacetsCount = layoutBindingComponent.getViewBindingEnabledFacets().size();

    Messages.showInfoMessage(project, "androidFacetCount=" + androidFacetCount + "\n" +
                                      "allBindingEnabledFacetsCount=" + allBindingEnabledFacetsCount + "\n" +
                                      "dataBindingEnabledFacetsCount=" + dataBindingEnabledFacetsCount + "\n" +
                                      "viewBindingEnabledFacetsCount=" + viewBindingEnabledFacetsCount,
                             "Android stats");
  }
}
