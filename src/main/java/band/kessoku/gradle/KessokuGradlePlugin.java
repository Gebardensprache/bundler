/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package band.kessoku.gradle;

import band.kessoku.gradle.extension.KessokuExtension;
import band.kessoku.gradle.extension.KessokuSubExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.PluginAware;

import java.util.Objects;

@SuppressWarnings("unused")
public class KessokuGradlePlugin implements Plugin<PluginAware> {

	public static final String VERSION = Objects.requireNonNullElse(
			KessokuGradlePlugin.class.getPackage().getImplementationVersion(),
			"00.0.0-unknown"
	);

	@Override
	public void apply(PluginAware target) {
		switch (target) {
			case Project project -> apply(project);
			case Settings settings -> apply(settings);
			default -> {}
		}
	}

	private void apply(final Project project) {
		// Apply default plugins
		project.getPlugins().apply(JavaLibraryPlugin.class);

		// Lifecycle bundler version
		project.getLogger().lifecycle("Kessoku Bundler: " + VERSION);

		if (project == project.getRootProject()) {
			project.getExtensions().add("kessoku", KessokuExtension.class);
		} else {
			project.getExtensions().add("kessokuModule", KessokuSubExtension.class);
		}
	}

	private void apply(final Settings settings) {
	}

}
