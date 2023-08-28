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

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

/**
 * A value class for coupling an error and error causing element. This allows us to couple the error
 * message with the element causing the error. Notice how we annotate with the {@link DefaultSchema}
 * and {@link AutoValueSchema}. This tells Beam how to construct a {@link
 * org.apache.beam.sdk.schemas.Schema} using properties of this class.
 */
@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class ConversionError {

  static Builder builder() {
    return new AutoValue_ConversionError.Builder();
  }

  /** The error message of the thrown exception. */
  public abstract String getMessage();

  /** The JSON encoded string of the error causing element. */
  public abstract String getElement();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setMessage(String value);

    public abstract Builder setElement(String value);

    public abstract ConversionError build();
  }
}
