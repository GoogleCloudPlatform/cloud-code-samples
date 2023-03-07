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
