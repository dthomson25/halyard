/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.halyard.config.spinnaker.v1;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Data
public class BillOfMaterials {
  String version;
  SpinnakerComponents services;

  @Data
  static class SpinnakerComponents {
    Component clouddriver;
    Component echo;
    Component deck;
    Component fiat;
    Component front50;
    Component gate;
    Component igor;
    Component rosco;
    Component spinnaker;

    public String getComponentVersion(ComponentName name) {
      Optional<Field> field = Arrays.stream(SpinnakerComponents.class.getDeclaredFields())
          .filter(f -> f.getName().equals(name.getId()))
          .findFirst();

      if (!field.isPresent()) {
        throw new RuntimeException("No supported spinnaker component named " + name.getId());
      }

      try {
        return ((Component) field.get().get(this)).getVersion();
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (NullPointerException e) {
        throw new RuntimeException("Spinnaker component " + name.getId() + " is not listed in the BOM");
      }
    }

    @Data
    static class Component {
      String version;
      // TODO(lwander) dependencies will go here.
    }
  }
}
