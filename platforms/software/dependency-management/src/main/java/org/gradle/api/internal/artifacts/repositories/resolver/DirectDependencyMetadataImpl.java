/*
 * Copyright 2017 the original author or authors.
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

import org.gradle.api.Action;
import org.gradle.api.artifacts.DependencyArtifact;
import org.gradle.api.artifacts.DependencyExcludesMetadata;
import org.gradle.api.artifacts.DirectDependencyMetadata;
import org.gradle.api.artifacts.capability.CapabilitySelector;
import org.gradle.api.capabilities.DependencyCapabilitiesMetadata;
import org.gradle.internal.component.external.model.DefaultDependencyCapabilitiesMetadata;
import org.gradle.internal.component.model.ExcludeMetadata;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DirectDependencyMetadataImpl extends AbstractDependencyImpl<DirectDependencyMetadata> implements DirectDependencyMetadata {

    private boolean endorsing = false;
    private Set<ExcludeMetadata> excludes = new LinkedHashSet<>();
    private Set<CapabilitySelector> capabilities = new LinkedHashSet<>();

    public DirectDependencyMetadataImpl(String group, String name, String version) {
        super(group, name, version);
    }

    @Override
    public void endorseStrictVersions() {
        endorsing = true;
    }

    @Override
    public void doNotEndorseStrictVersions() {
        endorsing = false;
    }

    @Override
    public boolean isEndorsingStrictVersions() {
        return endorsing;
    }

    @Override
    public List<DependencyArtifact> getArtifactSelectors() {
        return Collections.emptyList();
    }

    @Override
    public void excludes(Action<? super DependencyExcludesMetadata> configureAction) {
        DefaultDependencyExcludesMetadata excludesMetadata = new DefaultDependencyExcludesMetadata(excludes);
        configureAction.execute(excludesMetadata);
        excludes = excludesMetadata.getExcludeMetadata();
    }

    @Override
    public void capabilities(Action<? super DependencyCapabilitiesMetadata> configureAction) {
        DefaultDependencyCapabilitiesMetadata capabilitiesMetadata = new DefaultDependencyCapabilitiesMetadata(getGroup(), getName(), capabilities);
        configureAction.execute(capabilitiesMetadata);
        capabilities = capabilitiesMetadata.getSelectors();
    }
}
