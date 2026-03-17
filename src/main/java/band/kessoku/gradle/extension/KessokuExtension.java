/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package band.kessoku.gradle.extension;

import org.gradle.api.provider.Property;

public interface KessokuExtension {

    Property<String> minecraftVersion();

    Property<Boolean> reobfuscate();

}