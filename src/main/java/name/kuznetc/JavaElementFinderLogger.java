package name.kuznetc;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFinder;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaElementFinderLogger extends PsiElementFinder {
  private static final Logger LOG = Logger.getInstance(JavaElementFinderLogger.class);

  @Override
  public @Nullable PsiClass findClass(@NotNull String qualifiedName, @NotNull GlobalSearchScope scope) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Do findClass: " + qualifiedName + ", scope: " + scope);
    }
    return null;
  }

  @Override
  public PsiClass [] findClasses(@NotNull String qualifiedName, @NotNull GlobalSearchScope scope) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Do findClasses: " + qualifiedName + ", scope: " + scope);
    }
    return PsiClass.EMPTY_ARRAY;
  }

  @Override
  public @Nullable PsiPackage findPackage(@NotNull String qualifiedName) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Do findPackage: " + qualifiedName);
    }
    return null;
  }
}
