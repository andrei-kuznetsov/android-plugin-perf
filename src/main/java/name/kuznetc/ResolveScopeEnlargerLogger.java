package name.kuznetc;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.ResolveScopeEnlarger;
import com.intellij.psi.search.SearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveScopeEnlargerLogger extends ResolveScopeEnlarger {
  private static final Logger LOG = Logger.getInstance(ResolveScopeEnlargerLogger.class);

  @Override
  public @Nullable SearchScope getAdditionalResolveScope(@NotNull VirtualFile file, Project project) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Do getAdditionalResolveScope: file=" + file);
    }
    return null;
  }
}
