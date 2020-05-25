// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package name.kuznetc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade;

public class KotlinJavaPsiFacadeFindFileDuration extends AnAction {
  public static final int INVOCATIONS = 100000;
  public static volatile Object res;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    ProgressManager.getInstance().runProcessWithProgressSynchronously(() -> measureDuration(project),
                                                                      "Measure duration of " + INVOCATIONS + " invocations.", true,
                                                                      project);
  }

  private void measureDuration(Project project) {
    KotlinJavaPsiFacade kotlinPsi = KotlinJavaPsiFacade.getInstance(project);
    while (true) {
      @NotNull GlobalSearchScope scope = GlobalSearchScopes.projectProductionScope(project);
      JavaClassFinder.Request request = new JavaClassFinder.Request(ClassId.fromString("kotlin.internals"), null, null);
      long duration = ReadAction.compute(() -> {
        long started = System.currentTimeMillis();
        for (int i = 0; i < INVOCATIONS; i++) {
          res = kotlinPsi.findClass(request, scope);
        }
        long ended = System.currentTimeMillis();
        return (ended - started);
      });
      ProgressManager.progress(duration + " ms");
    }
  }
}
