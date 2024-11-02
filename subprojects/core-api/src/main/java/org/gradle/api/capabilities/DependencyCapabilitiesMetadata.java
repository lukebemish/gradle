/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.capabilities;

import org.gradle.api.artifacts.capability.CapabilitySelector;

/**
 * A container holding the capabilities requested by a dependency within a variant's
 * metadata. If no selectors are present, the "implicit" capability is requested, which
 * corresponds to the GAV coordinates of the component.
 */
public interface DependencyCapabilitiesMetadata {
    /**
     * Add a selector to require a single capability.
     *
     * @param group the capability group
     * @param name the capability module name
     *
     * @see org.gradle.api.artifacts.ModuleDependencyCapabilitiesHandler#requireCapability(Object)
     */
    void addRequestedCapability(String group, String name);

    /**
     * Require a capability of a component based on the name of the feature provided by the component.
     *
     * @param feature the name of the feature to require
     *
     * @see org.gradle.api.artifacts.ModuleDependencyCapabilitiesHandler#requireFeature(String)
     */
    void addRequestedFeature(String feature);

    /**
     * Add a single capability selector to the dependency.
     *
     * @param selector the selector to add
     */
    void addSelector(CapabilitySelector selector);

    /**
     * Remove any selectors that request a capability with the given group and name; this includes both specific
     * capability selectors and feature capability selectors.
     *
     * @param group the group of the capability to remove
     * @param name the name of the capability to remove
     */
    void removeRequestedCapability(String group, String name);

    /**
     * Remove any selectors that request a feature with the given name; this includes both specific capability
     * selectors and feature capability selectors.
     *
     * @param feature the name of the feature to remove
     */
    void removeRequestedFeature(String feature);

    /**
     * Remove a specific selector from the dependency.
     *
     * @param selector the selector to remove
     */
    void removeSelector(CapabilitySelector selector);
}
