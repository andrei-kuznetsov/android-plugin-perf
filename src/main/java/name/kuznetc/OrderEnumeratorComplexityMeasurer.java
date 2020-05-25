package name.kuznetc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicLong;

public class OrderEnumeratorComplexityMeasurer extends AnAction {
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    assert project != null;

    @Nullable VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (virtualFile == null) {
      Messages.showErrorDialog(project, "No file or directory selected.", "Looking for source module");
      return;
    }

    @Nullable Module module = ModuleUtilCore.findModuleForFile(virtualFile, project);
    if (module == null) {
      Messages.showErrorDialog(project, "No module found for file: " + virtualFile, "Looking for source module");
      return;
    }

    long nonRecursiveIterationCount = nonRecursiveIterationCount(module);
    long recursiveIterationCount = recursiveIterationCount(module);
    long recursiveExportedIterationCount = recursiveExportedIterationCount(module);
    Messages.showInfoMessage(project, "Stats for module: " + module + "\n" +
                                      "nonRecursiveIterationCount=" + nonRecursiveIterationCount + "\n" +
                                      "recursiveIterationCount=" + recursiveIterationCount + "\n" +
                                      "recursiveExportedIterationCount=" + recursiveExportedIterationCount,
                             "Stats");
  }

  private long nonRecursiveIterationCount(@NotNull Module module) {
    AtomicLong counter = new AtomicLong(0);
    ModuleRootManager.getInstance(module)
      .orderEntries()
      .librariesOnly()
      .forEachLibrary(unused -> {
        counter.incrementAndGet();
        return true;
      });
    return counter.get();
  }

  private long recursiveIterationCount(@NotNull Module module) {
    AtomicLong counter = new AtomicLong(0);
    ModuleRootManager.getInstance(module)
      .orderEntries()
      .librariesOnly()
      .recursively()
      .forEachLibrary(unused -> {
        counter.incrementAndGet();
        return true;
      });
    return counter.get();
  }

  private long recursiveExportedIterationCount(@NotNull Module module) {
    AtomicLong counter = new AtomicLong(0);
    ModuleRootManager.getInstance(module)
      .orderEntries()
      .librariesOnly()
      .recursively()
      .exportedOnly()
      .forEachLibrary(unused -> {
        counter.incrementAndGet();
        return true;
      });
    return counter.get();
  }
}
