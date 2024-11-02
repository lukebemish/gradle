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

package org.gradle.internal.component.external.model;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.artifacts.capability.CapabilitySelector;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.capabilities.DependencyCapabilitiesMetadata;
import org.gradle.api.internal.artifacts.capability.DefaultFeatureCapabilitySelector;
import org.gradle.api.internal.artifacts.capability.DefaultSpecificCapabilitySelector;
import org.gradle.api.internal.artifacts.capability.FeatureCapabilitySelector;
import org.gradle.api.internal.artifacts.capability.SpecificCapabilitySelector;
import org.gradle.api.internal.capabilities.ImmutableCapability;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultDependencyCapabilitiesMetadata implements DependencyCapabilitiesMetadata {
    private final Set<CapabilitySelector> descriptors;
    private final String group;
    private final String name;

    public DefaultDependencyCapabilitiesMetadata(String group, String name, Set<CapabilitySelector> descriptors) {
        this.descriptors = new LinkedHashSet<>(descriptors);
        this.group = group;
        this.name = name;
    }

    @Override
    public Set<Capability> getRequestedCapabilities() {
        return getSelectors().stream()
            .map(c -> {
                if (c instanceof SpecificCapabilitySelector) {
                    return ((DefaultSpecificCapabilitySelector) c).getBackingCapability();
                } else if (c instanceof FeatureCapabilitySelector) {
                    return new DefaultImmutableCapability(
                        group,
                        name + "-" + ((FeatureCapabilitySelector) c).getFeatureName(),
                        null
                    );
                } else {
                    throw new UnsupportedOperationException("Unsupported capability selector type: " + c.getClass().getName());
                }
            })
            .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public Set<CapabilitySelector> getSelectors() {
        return ImmutableSet.copyOf(descriptors);
    }

    @Override
    public void addRequestedCapability(String group, String name) {
        ImmutableCapability capability = new DefaultImmutableCapability(group, name, null);
        descriptors.add(new DefaultSpecificCapabilitySelector(capability));
    }

    @Override
    public void addRequestedFeature(String feature) {
        descriptors.add(new DefaultFeatureCapabilitySelector(feature));
    }

    @Override
    public void addSelector(CapabilitySelector selector) {
        descriptors.add(selector);
    }

    @Override
    public void removeRequestedCapability(String group, String name) {
        ImmutableCapability capability = new DefaultImmutableCapability(group, name, null);
        descriptors.remove(new DefaultSpecificCapabilitySelector(capability));
    }

    @Override
    public void removeRequestedFeature(String feature) {
        descriptors.remove(new DefaultFeatureCapabilitySelector(feature));
        ImmutableCapability capability = new DefaultImmutableCapability(group, name+"-"+feature, null);
        descriptors.remove(new DefaultSpecificCapabilitySelector(capability));
    }

    @Override
    public void removeSelector(CapabilitySelector selector) {
        descriptors.remove(selector);
    }
}
