/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudcode.dataflow.example.options;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubClient;
import org.junit.jupiter.api.Test;

/** Tests for {@link SubscriptionPathOptionValue}. */
class SubscriptionPathOptionValueTest {

  @Test
  void validValueBuildsSubscriptionPath() {
    String option = "projects/aProject/subscriptions/aSubscription";
    SubscriptionPathOptionValue optionValue = new SubscriptionPathOptionValue(option);
    PubsubClient.SubscriptionPath actual = optionValue.getValue();
    assertEquals("aSubscription", actual.getName());
    assertEquals(option, actual.getPath());
    assertEquals("/subscriptions/aProject/aSubscription", actual.getFullPath());
  }

  @Test
  void inputErrorInformsValidFormat() {
    String option = "aSubscription";
    IllegalArgumentException error =
        assertThrows(IllegalArgumentException.class, () -> new SubscriptionPathOptionValue(option));
    String className = PubsubClient.SubscriptionPath.class.getName();
    String expected =
        String.format(
            "error parsing '%s' into class %s: "
                + "Malformed subscription path aSubscription: must be of the form \"projects/\" + <project id> + \"subscriptions\"",
            option, className);
    assertEquals(expected, error.getMessage());
  }
}
