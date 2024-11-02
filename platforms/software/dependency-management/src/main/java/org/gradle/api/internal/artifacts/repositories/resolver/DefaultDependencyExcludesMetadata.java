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

package org.gradle.api.internal.artifacts.repositories.resolver;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.artifacts.DependencyExcludesMetadata;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.internal.artifacts.DefaultExcludeRule;
import org.gradle.api.internal.artifacts.DefaultModuleIdentifier;
import org.gradle.internal.component.external.descriptor.DefaultExclude;
import org.gradle.internal.component.model.ExcludeMetadata;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultDependencyExcludesMetadata implements DependencyExcludesMetadata {
    private final Set<ExcludeMetadata> rules;

    public DefaultDependencyExcludesMetadata(Collection<ExcludeMetadata> rules) {
        this.rules = new LinkedHashSet<>(rules);
    }

    @Override
    public Set<ExcludeRule> getRules() {
        return rules.stream()
            .map(rule -> new DefaultExcludeRule(rule.getModuleId().getGroup(), rule.getModuleId().getName()))
            .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public void addRule(ExcludeRule rule) {
        rules.add(new DefaultExclude(DefaultModuleIdentifier.newId(rule.getGroup(), rule.getModule()), new String[] {"*"}, null));
    }

    @Override
    public void addExclude(String group, String module) {
        rules.add(new DefaultExclude(DefaultModuleIdentifier.newId(group, module), new String[] {"*"}, null));
    }

    @Override
    public void removeRule(ExcludeRule rule) {
        rules.removeIf(exclude -> exclude.getModuleId().getGroup().equals(rule.getGroup()) && exclude.getModuleId().getName().equals(rule.getModule()));
    }

    @Override
    public void removeExclude(String group, String module) {
        rules.removeIf(exclude -> exclude.getModuleId().getGroup().equals(group) && exclude.getModuleId().getName().equals(module));
    }

    public Set<ExcludeMetadata> getExcludeMetadata() {
        return rules;
    }
}
