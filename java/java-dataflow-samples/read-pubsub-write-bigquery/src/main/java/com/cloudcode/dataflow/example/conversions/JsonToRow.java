package com.cloudcode.dataflow.example.conversions;

import static java.util.Objects.requireNonNull;
import static org.apache.beam.sdk.values.TypeDescriptors.rows;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.WithFailures.Result;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TypeDescriptor;

/**
 * {@link JsonToRow} is a {@link PTransform} that converts JSON encoded strings into a {@link
 * PCollectionTuple} containing the {@link PCollection} of converted {@link Row}s and the {@link
 * PCollection} of errors.
 *
 * <p>This example shows how to use an additional outputs pattern to output multiple PCollections.
 * We do this when we want to output our expected PCollection from successful processing as well as
 * an error PCollection to collect any errors. The value of this design pattern is that we can allow
 * the pipeline to continue normal processing while sending errors to further processing, storage or
 * logs. See: https://beam.apache.org/documentation/programming-guide/#additional-outputs.
 */
public class JsonToRow<UserT> extends PTransform<PCollection<String>, PCollectionTuple> {

  private static final UserTypeJsonPayloadSerializer<ConversionError> ERROR_PAYLOAD_SERIALIZER =
      UserTypeJsonPayloadSerializer.of(TypeDescriptor.of(ConversionError.class));

  /** Instantiates a {@link JsonToRow} from a custom user type's {@link TypeDescriptor} */
  public static <UserT> JsonToRow<UserT> of(TypeDescriptor<UserT> userType) {
    return new JsonToRow<>(userType);
  }

  /**
   * The {@link TupleTag} that assigns the {@link Row} {@link PCollection} to the resulting {@link
   * PCollectionTuple}.
   */
  public static final TupleTag<Row> ROW = new TupleTag<>() {};

  /**
   * The {@link TupleTag} that assigns the String {@link PCollection} of errors to the resulting
   * {@link PCollectionTuple}.
   */
  public static final TupleTag<String> ERROR = new TupleTag<>() {};

  private final UserTypeJsonPayloadSerializer<UserT> payloadSerializer;

  private JsonToRow(TypeDescriptor<UserT> userType) {
    this.payloadSerializer = UserTypeJsonPayloadSerializer.of(userType);
  }

  @Override
  public PCollectionTuple expand(PCollection<String> input) {
    // We implement this PTransform using two steps.
    // The first step parses the PCollection of JSON encoded strings to Beam Rows.
    Result<PCollection<Row>, String> result = input.apply("To Row", jsonToRowWithFailures());

    // The second step bundles the expected output and any errors into a PCollectionTuple.
    // See https://beam.apache.org/documentation/programming-guide/#additional-outputs
    // for more information about a PCollectionTuple.
    return PCollectionTuple.of(ROW, result.output().setRowSchema(payloadSerializer.getSchema()))
        .and(ERROR, result.failures());
  }

  /**
   * Instead of using the typical {@link MapElements} to map from a JSON String to a Beam {@link
   * Row}, we use {@link MapElements.MapWithFailures} to collect any possible errors.
   */
  private MapElements.MapWithFailures<String, Row, String> jsonToRowWithFailures() {
    return MapElements.into(rows())
        .via((String json) -> payloadSerializer.deserialize(requireNonNull(json)))
        .exceptionsInto(strings())
        .exceptionsVia(
            exceptionElement -> {
              String element = Optional.ofNullable(exceptionElement.element()).orElse("null json");
              ConversionError error =
                  ConversionError.builder()
                      .setElement(element)
                      .setMessage(
                          String.format(
                              "%s Expected Beam Schema: %s",
                              exceptionElement.exception().getMessage(),
                              prettyStringOf(payloadSerializer.getSchema())))
                      .build();
              Row row = ERROR_PAYLOAD_SERIALIZER.getUserTypeToRowFn().apply(error);
              return ERROR_PAYLOAD_SERIALIZER.serialize(row);
            });
  }

  private static String prettyStringOf(Schema schema) {
    List<String> elements = new ArrayList<>();
    for (String field : schema.getFieldNames()) {
      elements.add(String.format("%s:%s", field, schema.getField(field).getType().getTypeName()));
    }
    return String.join(", ", elements);
  }
}
