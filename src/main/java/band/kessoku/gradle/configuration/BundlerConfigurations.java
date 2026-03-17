package band.kessoku.gradle.configuration;

import band.kessoku.gradle.extension.KessokuExtension;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

import javax.inject.Inject;

public abstract class BundlerConfigurations {

    @Inject
    public abstract Project getProject();

    public void apply() {
        registerNonTransitive("libraries", Role.RESOLVABLE);
        registerNonTransitive("runtimeOnlyLibraries", Role.RESOLVABLE);

        registerNonTransitive("minecraft", Role.NONE);

        if (getProject().getRootProject().getExtensions().getByType(KessokuExtension.class).reobfuscate().get()) {
            for (SourceSet sourceSet : getProject().getExtensions().getByType(JavaPluginExtension.class).getSourceSets()) {
                var name = sourceSet.getName();
                if (name.equals("main")) continue;
                var impl = registerNonTransitive(name + "ModImplementation", Role.CONSUMABLE);
                var runtime = registerNonTransitive(name + "ModRuntimeOnly", Role.CONSUMABLE);
                var compile = registerNonTransitive(name + "ModCompileOnly", Role.CONSUMABLE);
                impl.configure(configuration -> {
                    configuration.extendsFrom(runtime.get());
                    configuration.extendsFrom(compile.get());
                });
                var api = register(name + "ModApi", Role.CONSUMABLE);
            }
        }
    }

    private NamedDomainObjectProvider<Configuration> register(String name, Role role) {
        return getProject().getConfigurations().register(name, role::apply);
    }

    private NamedDomainObjectProvider<Configuration> registerNonTransitive(String name, Role role) {
        final NamedDomainObjectProvider<Configuration> provider = register(name, role);
        provider.configure(configuration -> configuration.setTransitive(false));
        return provider;
    }

    public void extendsFrom(String a, String b) {
        getProject().getConfigurations().getByName(a, configuration -> configuration.extendsFrom(getProject().getConfigurations().getByName(b)));
    }

    public enum Role {
        NONE(false, false),
        CONSUMABLE(true, false),
        RESOLVABLE(false, true);

        private final boolean canBeConsumed;
        private final boolean canBeResolved;

        Role(boolean canBeConsumed, boolean canBeResolved) {
            this.canBeConsumed = canBeConsumed;
            this.canBeResolved = canBeResolved;
        }

        public void apply(Configuration configuration) {
            configuration.setCanBeConsumed(canBeConsumed);
            configuration.setCanBeResolved(canBeResolved);
        }
    }

}
