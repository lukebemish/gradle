/*
 * Copyright 2019 the original author or authors.
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

package org.gradle.api.internal.artifacts.repositories.resolver

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ModuleComponentSelector
import org.gradle.api.internal.attributes.ImmutableAttributes
import org.gradle.internal.component.external.model.ImmutableCapabilities
import org.gradle.internal.component.external.model.ModuleDependencyMetadata
import org.gradle.internal.component.external.model.RealisedConfigurationMetadata
import org.gradle.internal.component.external.model.ivy.IvyDependencyDescriptor
import org.gradle.internal.component.external.model.ivy.IvyDependencyMetadata
import org.gradle.internal.component.model.ConfigurationMetadata

class DependenciesMetadataAdapterOnIvyMetadataTest extends DependenciesMetadataAdapterTest {

    @Override
    ModuleDependencyMetadata newDependency(ModuleComponentSelector requested, ModuleComponentIdentifier identifier) {
        IvyDependencyDescriptor dependencyDescriptor = new IvyDependencyDescriptor(requested, ArrayListMultimap.create())
        ConfigurationMetadata configurationMetadata = new RealisedConfigurationMetadata(identifier, "compile", true, true, ImmutableSet.of(), ImmutableList.of(), ImmutableList.of(), ImmutableAttributes.EMPTY, ImmutableCapabilities.EMPTY, false, false)
        return new IvyDependencyMetadata(configurationMetadata, dependencyDescriptor)
    }

}
