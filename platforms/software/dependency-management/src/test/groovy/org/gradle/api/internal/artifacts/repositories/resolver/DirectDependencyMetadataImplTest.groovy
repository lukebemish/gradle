/*
 * Copyright 2020 the original author or authors.
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


import spock.lang.Specification

class DirectDependencyMetadataImplTest extends Specification {
    def "endoreStrictVersion is off by default"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        expect:
        metadata.endorsingStrictVersions == false
    }

    def "can toggle endoreStrictVersion"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        when:
        metadata.endorseStrictVersions()

        then:
        metadata.endorsingStrictVersions
    }

    def "can reset endoreStrictVersion"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        when:
        metadata.endorseStrictVersions()
        metadata.doNotEndorseStrictVersions()

        then:
        metadata.endorsingStrictVersions == false
    }

    def "no selectors for newly added dependencies"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        when:
        def selectors = metadata.artifactSelectors

        then:
        selectors == []
    }

    def "can modify dependency capabilities"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        when:
        metadata.capabilities {
            it.addRequestedCapability("foo", "bar")
            it.addRequestedFeature("feature-name")
        }

        then:
        metadata.requestedCapabilities.collect { "$it.group:$it.name" } == [
            "foo:bar",
            "g:a-feature-name"
        ]
    }

    def "can modify dependency excludes"() {
        given:
        def metadata = new DirectDependencyMetadataImpl("g", "a", "v")

        when:
        metadata.excludes {
            it.addExclude("foo", "bar")
            it.addExclude("baz", "*")
        }

        then:
        metadata.excludeRules.collect { "$it.moduleId.group:$it.moduleId.name" } == [
            "foo:bar",
            "baz:*"
        ]
    }

}
