package com.cloudcode.dataflow.example.logging;

import com.google.auto.value.AutoValue;
import java.io.Serializable;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** An implementation of logging within a Beam context. */
@AutoValue
public abstract class Log implements Serializable {

  static Builder builder() {
    return new AutoValue_Log.Builder();
  }

  public static <T> PTransform<PCollection<T>, PDone> info(String prefix, Class<?> loggerClass) {
    return new LogTransform<>(
        builder().setSeverity(Severity.info).setPrefix(prefix).setLoggerClass(loggerClass).build());
  }

  public static <T> PTransform<PCollection<T>, PDone> error(String prefix, Class<?> loggerClass) {
    return new LogTransform<>(
        builder()
            .setSeverity(Severity.error)
            .setPrefix(prefix)
            .setLoggerClass(loggerClass)
            .build());
  }

  abstract Severity getSeverity();

  abstract Class<?> getLoggerClass();

  abstract String getPrefix();

  private static class LogTransform<T> extends PTransform<PCollection<T>, PDone> {
    private final Log spec;

    private LogTransform(Log spec) {
      this.spec = spec;
    }

    @Override
    public PDone expand(PCollection<T> input) {
      input.apply("logFn", ParDo.of(new LogFn<T>(spec)));
      return PDone.in(input.getPipeline());
    }
  }

  private static class LogFn<T> extends DoFn<T, Void> {
    private final Log spec;
    private transient Logger logger;

    private LogFn(Log spec) {
      this.spec = spec;
    }

    @Setup
    public void setup() {
      logger = LoggerFactory.getLogger(spec.getLoggerClass());
    }

    @ProcessElement
    public void process(@Element T element) {
      switch (spec.getSeverity()) {
        case info:
          logger.info("{}: {}", spec.getPrefix(), element);
        case error:
          logger.error("{}: {}", spec.getPrefix(), element);
      }
    }
  }

  @AutoValue.Builder
  abstract static class Builder {

    abstract Builder setSeverity(Severity value);

    abstract Builder setLoggerClass(Class<?> value);

    abstract Builder setPrefix(String value);

    abstract Log build();
  }

  enum Severity {
    info,
    error,
  }
}
