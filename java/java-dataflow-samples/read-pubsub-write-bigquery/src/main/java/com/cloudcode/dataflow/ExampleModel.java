package com.cloudcode.dataflow;

import com.google.auto.value.AutoValue;
import com.google.common.base.Objects;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.schemas.annotations.SchemaCaseFormat;
import org.apache.beam.vendor.guava.v26_0_jre.com.google.common.base.CaseFormat;
import org.joda.time.Instant;

/**
 * An Example Java value class based on the Pub/Sub topic projects/pubsub-public-data/topics/taxirides-realtime.
 * This demonstrates the following concepts.
 * 1. Using {@link DefaultSchema} with {@link AutoValueSchema} tells Beam how to determine the fields and data types.
 * 2. Using {@link SchemaCaseFormat} with {@link com.google.common.base.CaseFormat} tells beam how to name fields.
 */
@DefaultSchema(AutoValueSchema.class)
@SchemaCaseFormat(CaseFormat.LOWER_UNDERSCORE)
@AutoValue
public abstract class ExampleModel {

  public static Builder builder() {
    return new AutoValue_ExampleModel.Builder();
  }

  public abstract String getRideId();

  public abstract Integer getPointIdx();

  public abstract Double getLatitude();

  public abstract Double getLongitude();

  public abstract Instant getTimestamp();

  public abstract Double getMeterReading();

  public abstract Double getMeterIncrement();

  public abstract String getRideStatus();

  public abstract Integer getPassengerCount();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRideId(String value);

    public abstract Builder setPointIdx(Integer value);

    public abstract Builder setLatitude(Double value);

    public abstract Builder setLongitude(Double value);

    public abstract Builder setTimestamp(Instant value);

    public abstract Builder setMeterReading(Double value);

    public abstract Builder setMeterIncrement(Double value);

    public abstract Builder setRideStatus(String value);

    public abstract Builder setPassengerCount(Integer value);

    public abstract ExampleModel build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExampleModel that = (ExampleModel) o;
    return Objects.equal(getRideId(), that.getRideId())
        && Objects.equal(getPointIdx(), that.getPointIdx())
        && Objects.equal(getLatitude(), that.getLatitude())
        && Objects.equal(getLongitude(), that.getLongitude())
        && Objects.equal(getTimestamp(), that.getTimestamp())
        && Objects.equal(getMeterReading(), that.getMeterReading())
        && Objects.equal(getMeterIncrement(), that.getMeterIncrement())
        && Objects.equal(getRideStatus(), that.getRideStatus())
        && Objects.equal(getPassengerCount(), that.getPassengerCount());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        getRideId(),
        getPointIdx(),
        getLatitude(),
        getLongitude(),
        getTimestamp(),
        getMeterReading(),
        getMeterIncrement(),
        getRideStatus(),
        getPassengerCount());
  }
}
