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

package com.cloudcode.dataflow.example.testdata;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.values.Row;
import org.joda.time.Instant;

/**
 * Contains all primitive Java types i.e. String, Integer, etc. The purpose of this class is to test
 * schema-aware PTransforms with flat {@link Schema} {@link Row}s.
 */
@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class FlatSchemaJavaBean {

  public static FlatSchemaJavaBean flatSchemaJavaBean(
      Boolean aBoolean, Double aDouble, Integer anInteger, String aString, Instant instant) {
    return builder()
        .setABoolean(aBoolean)
        .setADouble(aDouble)
        .setAnInteger(anInteger)
        .setAString(aString)
        .setInstant(instant)
        .build();
  }

  static Builder builder() {
    return new AutoValue_FlatSchemaJavaBean.Builder();
  }

  public abstract Boolean getABoolean();

  public abstract Double getADouble();

  public abstract Integer getAnInteger();

  public abstract String getAString();

  public abstract Instant getInstant();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setABoolean(Boolean value);

    public abstract Builder setADouble(Double value);

    public abstract Builder setAnInteger(Integer value);

    public abstract Builder setAString(String value);

    public abstract Builder setInstant(Instant value);

    public abstract FlatSchemaJavaBean build();
  }
}
