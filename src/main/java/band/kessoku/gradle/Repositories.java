package band.kessoku.gradle;

import band.kessoku.gradle.utils.MirrorUtil;
import org.gradle.api.Project;

public class Repositories {

    public static void apply(Project project) {
        var repositories = project.getRepositories();
        repositories.mavenCentral();

        repositories.maven(maven -> {
            maven.setName("Mojang Libraries");
            maven.setUrl(MirrorUtil.getLibrariesBase(project));
        });
    }

}
