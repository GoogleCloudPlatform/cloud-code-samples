package com.cloudcode.dataflow.example.conversions;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema.DefaultSchemaProvider;
import org.apache.beam.sdk.schemas.io.payloads.JsonPayloadSerializerProvider;
import org.apache.beam.sdk.schemas.io.payloads.PayloadSerializer;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;

/** An adaptation of Beam's {@link PayloadSerializer} for JSON strings. */
public class UserTypeJsonPayloadSerializer<UserT> implements Serializable {

  public static <UserT> UserTypeJsonPayloadSerializer<UserT> of(TypeDescriptor<UserT> userType) {
    return new UserTypeJsonPayloadSerializer<>(userType);
  }

  private static final DefaultSchemaProvider SCHEMA_PROVIDER = new DefaultSchemaProvider();

  private final SerializableFunction<UserT, Row> userTypeToRowFn;

  private final SerializableFunction<Row, UserT> rowToUserTypeFn;

  private final PayloadSerializer internal;

  private final Schema schema;

  private UserTypeJsonPayloadSerializer(TypeDescriptor<UserT> userType) {
    this.schema = requireNonNull(SCHEMA_PROVIDER.schemaFor(userType));
    this.userTypeToRowFn = SCHEMA_PROVIDER.toRowFunction(userType);
    this.rowToUserTypeFn = SCHEMA_PROVIDER.fromRowFunction(userType);
    this.internal =
        new JsonPayloadSerializerProvider().getSerializer(this.schema, ImmutableMap.of());
  }

  /** Return the {@link Schema} of the user type {@link TypeDescriptor}. */
  Schema getSchema() {
    return schema;
  }

  /** Convert a {@link Row} to its JSON string encoding. */
  @Nonnull
  public String serialize(@Nonnull Row row) {
    byte[] bytes = internal.serialize(row);
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /** Convert a JSON string to a {@link Row}. */
  @Nonnull
  public Row deserialize(@Nonnull String json) {
    byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
    return internal.deserialize(bytes);
  }

  /** A {@link SerializableFunction} for converting from a user type to a {@link Row}. */
  public SerializableFunction<UserT, Row> getUserTypeToRowFn() {
    return this.userTypeToRowFn;
  }
}
