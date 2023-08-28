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

/**
 * An adaptation of Beam's {@link PayloadSerializer} for JSON strings.
 *
 * <p>This is the key class for transforming JSON encoded strings into Beam {@link Row}s. If you
 * follow the guidelines shown in {@link com.cloudcode.dataflow.ExampleModel}, you can reuse this
 * class for your own custom types, assuming they can convert to and from JSON; there may be some
 * restrictions you discover when running your tests.
 */
public class UserTypeJsonPayloadSerializer<UserT> implements Serializable {

  /**
   * The {@link TypeDescriptor} argument is all that is needed to convert your custom type to and
   * from JSON encoded strings.
   *
   * <p>Using both {@link DefaultSchemaProvider} and a {@link TypeDescriptor} allows us to derive:
   * 1. a {@link Schema}, 2. a {@link SerializableFunction} that converts from a user type to a Beam
   * {@link Row}, and 3. a {@link SerializableFunction} that converts from a Beam {@link Row} to a
   * user type.
   *
   * <p>{@link UserTypeJsonPayloadSerializer} takes advantage of this using Beam's {@link
   * JsonPayloadSerializerProvider} to convert between JSON encoded strings and user types.
   *
   * <p>To convert from JSON encoded strings, this class processes via the following path: JSON
   * encoded string -> byte[] array -> Beam Row
   *
   * <p>To convert from Beam Rows, this class processes via the following path: Beam Row -> byte[]
   * array -> Beam Row -> JSON encoded string
   */
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
