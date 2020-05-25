// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package name.kuznetc;

import com.android.tools.idea.res.ProjectSystemPsiPackageFinder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AndroidFindPackageDuration extends AnAction {
  private static final int INVOCATIONS = 500;
  public static volatile Object res;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    assert project != null;

    @Nullable ProjectSystemPsiPackageFinder ext =
      PsiElementFinder.EP.findExtension(ProjectSystemPsiPackageFinder.class, project);

    if (ext == null) {
      throw new IllegalStateException("Could not find ProjectSystemPsiPackageFinder");
    }

    ProgressManager.getInstance().runProcessWithProgressSynchronously(() -> measureDuration(ext),
                                                                      "Measure duration of " + INVOCATIONS + " invocations.", true,
                                                                      project);
  }

  private void measureDuration(ProjectSystemPsiPackageFinder ext) {
    while (true) {
      long duration = ReadAction.compute(() -> {
        long started = System.currentTimeMillis();
        for (int i = 0; i < INVOCATIONS; i++) {
          res = ext.findPackage("kotlin.does.not.have.this.package");
        }
        long ended = System.currentTimeMillis();
        return (ended - started);
      });
      ProgressManager.progress(duration + " ms");
    }
  }
}
