/*
 * Copyright 2016 Google, Inc.
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

package com.netflix.spinnaker.halyard.config.config.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

@Component
public class ResourceConfig {
  /**
   * Path to where the halconfig file is located.
   *
   * @param path Defaults to ~/.hal/config.
   * @return The path with home (~) expanded.
   */
  @Bean
  String halconfigPath(@Value("${halconfig.directory.halconfig:~/.hal/config}") String path) {
    return normalizePath(path);
  }

  /**
   * Version of halyard.
   *
   * This is useful for implementing breaking version changes in Spinnaker that need to be migrated by some tool
   * (in this case Halyard).
   *
   * @return the version of halyard.
   */
  @Bean
  String halyardVersion() {
    return getClass().getPackage().getImplementationVersion();
  }

  @Bean
  String spinconfigBucket(@Value("${spinnaker.config.input.bucket:halconfig}") String spinconfigBucket) {
    return spinconfigBucket;
  }

  @Bean
  String spinnakerOutputPath(@Value("${spinnaker.config.output.directory:~/.halyard}") String path) {
    return normalizePath(path);
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  Yaml yamlParser() {
    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    return new Yaml(options);
  }

  private String normalizePath(String path) {
    return path.replaceFirst("^~", System.getProperty("user.home"));
  }
}
