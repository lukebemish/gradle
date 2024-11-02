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

import java.util.Set;

/**
 * TODO: Document this
 */
public interface DependencyCapabilitiesMetadata {
    Set<Capability> getRequestedCapabilities();

    Set<CapabilitySelector> getSelectors();

    void addRequestedCapability(String group, String name);

    void addRequestedFeature(String feature);

    void addSelector(CapabilitySelector selector);

    void removeRequestedCapability(String group, String name);

    void removeRequestedFeature(String feature);

    void removeSelector(CapabilitySelector selector);
}
