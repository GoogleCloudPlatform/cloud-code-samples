package com.cloudcode.dataflow.example.conversions;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

/** A value class for coupling an error and error causing element. */
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
