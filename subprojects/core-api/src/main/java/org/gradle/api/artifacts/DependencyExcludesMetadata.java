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

package org.gradle.api.artifacts;

/**
 * A container holding the exclude rules for transitive dependencies of a dependency within a variant's metadata.
 */
public interface DependencyExcludesMetadata {
    /**
     * Add an exclude rule to the dependency.
     *
     * @param rule the rule to add
     */
    void addRule(ExcludeRule rule);

    /**
     * Add an exclude rule to the dependency; <code>"*"</code> can be used as a wildcard for the group or module.
     *
     * @param group the group to exclude
     * @param module the module to exclude
     */
    void addExclude(String group, String module);

    /**
     * Remove an exclude rule from the dependency.
     *
     * @param rule the rule to remove
     */
    void removeRule(ExcludeRule rule);

    /**
     * Remove an exclude rule from the dependency by group and module excluded.
     *
     * @param group the group of the rule to remove
     * @param module the module of the rule to remove
     */
    void removeExclude(String group, String module);
}
