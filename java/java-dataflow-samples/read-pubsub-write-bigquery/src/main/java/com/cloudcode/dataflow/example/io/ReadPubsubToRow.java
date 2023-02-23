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
    PCollection<String> json =
        input.apply(
            "Read Json Messages", PubsubIO.readStrings().fromSubscription(subscription.getPath()));
    PCollectionTuple toRowResult = json.apply("To Row", JsonToRow.of(userType));
    PCollection<String> errors = toRowResult.get(ERROR);
    PCollection<Row> rows = toRowResult.get(ROW);
    return ReadResult.of(
        input.getPipeline(), ROW, rows.setRowSchema(rows.getSchema()), ERROR, errors);
  }
}
