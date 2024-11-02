/*
 * Copyright 2025 the original author or authors.
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

package org.gradle.api.internal.artifacts.repositories.resolver;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.artifacts.DirectDependencyMetadata;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.internal.artifacts.DefaultExcludeRule;
import org.gradle.api.internal.artifacts.capability.FeatureCapabilitySelector;
import org.gradle.api.internal.artifacts.capability.SpecificCapabilitySelector;
import org.gradle.internal.component.external.model.DefaultImmutableCapability;
import org.gradle.internal.component.model.ExcludeMetadata;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DirectDependencyMetadataInternal extends DirectDependencyMetadata {
    List<ExcludeMetadata> getExcludes();

    @Override
    default Set<ExcludeRule> getExcludeRules() {
        return getExcludes().stream()
            .map(rule -> {
                ModuleIdentifier moduleId = rule.getModuleId();
                return new DefaultExcludeRule(moduleId.getGroup(), moduleId.getName());
            })
            .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    default List<Capability> getRequestedCapabilities() {
        return getCapabilitySelectors().stream()
            .map(c -> {
                if (c instanceof SpecificCapabilitySelector) {
                    return new DefaultImmutableCapability(
                        ((SpecificCapabilitySelector) c).getGroup(),
                        ((SpecificCapabilitySelector) c).getName(),
                        null
                    );
                } else if (c instanceof FeatureCapabilitySelector) {
                    return new DefaultImmutableCapability(
                        getModule().getGroup(),
                        getModule().getName() + "-" + ((FeatureCapabilitySelector) c).getFeatureName(),
                        null
                    );
                } else {
                    throw new UnsupportedOperationException("Unsupported capability selector type: " + c.getClass().getName());
                }
            })
            .collect(Collectors.toList());
    }
}
