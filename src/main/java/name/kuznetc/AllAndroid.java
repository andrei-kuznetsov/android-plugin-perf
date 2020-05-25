// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package name.kuznetc;

import com.intellij.facet.FacetManager;
import com.intellij.facet.ModifiableFacetModel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.xml.DomElement;

public class AllAndroid extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    assert project != null;

    Module[] modules = ModuleManager.getInstance(project).getModules();
    for (Module module : modules) {
      if (AndroidFacet.getInstance(module) == null) {
        FacetManager facetManager = FacetManager.getInstance(module);
        AndroidFacet facet = facetManager.createFacet(AndroidFacet.getFacetType(), AndroidFacet.NAME, null);
        ModifiableFacetModel model = facetManager.createModifiableModel();
        model.addFacet(facet);
        ApplicationManager.getApplication().runWriteAction(model::commit);
      }
    }
  }
}
