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

package com.cloudcode.dataflow.example.io;

import static com.cloudcode.dataflow.example.conversions.JsonToRow.ERROR;
import static com.cloudcode.dataflow.example.conversions.JsonToRow.ROW;

import com.cloudcode.dataflow.example.conversions.JsonToRow;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubClient.SubscriptionPath;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;

/**
 * Reads Pub/Sub message JSON strings and converts to a {@link ReadResult} containing the resulting
 * {@link Row} {@link PCollection}.
 *
 * <p>Feel free to reuse this class for your own custom types without any changes, with the
 * following considerations: 1) your class follows the pattern found in {@link
 * com.cloudcode.dataflow.ExampleModel}, 2) your class has properties that are JSON
 * serializable/deserializable. See {@link JsonToRow}.
 */
public class ReadPubsubToRow<UserT> extends PTransform<PBegin, ReadResult> {

  /**
   * Instantiate a {@link ReadPubsubToRow} from a {@link SubscriptionPath} and the user type {@link
   * TypeDescriptor}.
   */
  public static <UserT> ReadPubsubToRow<UserT> of(
      SubscriptionPath subscription, TypeDescriptor<UserT> userType) {
    return new ReadPubsubToRow<>(subscription, userType);
  }

  private final SubscriptionPath subscription;
  private final TypeDescriptor<UserT> userType;

  private ReadPubsubToRow(SubscriptionPath subscription, TypeDescriptor<UserT> userType) {
    this.subscription = subscription;
    this.userType = userType;
  }

  @Override
  public ReadResult expand(PBegin input) {
    // We implement this PTransform in two main steps.  First, we read Pub/Sub JSON encoded
    // string messages.
    PCollection<String> json =
        input.apply(
            "Read Json Messages", PubsubIO.readStrings().fromSubscription(subscription.getPath()));

    // Next, we apply the JsonToRow PTransform to convert JSON strings to Beam Rows.
    PCollectionTuple toRowResult = json.apply("To Row", JsonToRow.of(userType));

    // Finally, we bundle our errors and expected results into a ReadResult convenience class.
    PCollection<String> errors = toRowResult.get(ERROR);
    PCollection<Row> rows = toRowResult.get(ROW);
    return ReadResult.of(
        // ***IMPORTANT: Notice the rows.setRowSchema(rows.getSchema()) semantic. This
        // avoids errors at pipeline construction time when it complains that it cannot
        // derive your Schema.***
        input.getPipeline(), ROW, rows.setRowSchema(rows.getSchema()), ERROR, errors);
  }
}
