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

public enum ComponentName {
  CLOUDDRIVER("clouddriver"),
  ECHO("echo"),
  DECK("deck"),
  FIAT("fiat"),
  FRONT50("front50"),
  GATE("gate"),
  IGOR("igor"),
  ROSCO("rosco"),
  SPINNAKER("spinnaker");

  String id;

  String filetype = "yml";

  String profilePrefix;

  public String getId() {
    return id;
  }

  public String getProfile(String profileName) {
    return profilePrefix + "-" + profileName + "." + filetype;
  }

  public String getProfile() {
    return profilePrefix + "." + filetype;
  }

  ComponentName(String id) {
    if (id.equals("deck")) {
      filetype = "js";
      profilePrefix = "settings";
    } else {
      profilePrefix = id;
    }

    this.id = id;
  }
}
