package com.cloudcode.dataflow.example.conversions;

import static com.cloudcode.dataflow.example.conversions.JsonToRow.ERROR;
import static com.cloudcode.dataflow.example.conversions.JsonToRow.ROW;
import static com.cloudcode.dataflow.example.testdata.TestData.DATA;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collections;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.junit.jupiter.api.Test;

class JsonToRowTest {

  private static final Gson GSON = new Gson();

  @Test
  void jsonToRow_WithoutErrorsPopulatesOutput() {
    Pipeline pipeline = Pipeline.create();
    PCollection<String> json = pipeline.apply(Create.of(DATA.exampleJson));
    PCollectionTuple result = json.apply(JsonToRow.of(DATA.typeDescriptor));
    PAssert.that(result.get(ROW)).containsInAnyOrder(DATA.exampleRows);
    PAssert.that(result.get(ERROR)).containsInAnyOrder(Collections.emptyList());
    pipeline.run();
  }

  @Test
  void jsonToRow_MissingFieldPopulatesFailures() {
    Pipeline pipeline = Pipeline.create();
    String original = DATA.exampleJson.get(0);
    JsonObject obj = GSON.fromJson(original, JsonObject.class);
    obj.remove(obj.keySet().stream().findFirst().orElseThrow());
    String fieldRemoved = GSON.toJson(obj);
    PCollection<String> json = pipeline.apply(Create.of(fieldRemoved));
    PCollectionTuple result = json.apply(JsonToRow.of(DATA.typeDescriptor));
    PAssert.thatSingleton(result.get(ROW).apply("count result", Count.globally())).isEqualTo(0L);
    PAssert.thatSingleton(result.get(ERROR).apply("count error", Count.globally())).isEqualTo(1L);

    pipeline.run();
  }

  @Test
  void jsonToRow_InvalidFieldTypePopulatesFailures() {
    Pipeline pipeline = Pipeline.create();
    String original = DATA.exampleJson.get(0);
    JsonObject obj = GSON.fromJson(original, JsonObject.class);
    String nonStringKey =
        obj.keySet().stream()
            .filter(key -> !obj.get(key).getAsJsonPrimitive().isString())
            .findFirst()
            .orElseThrow();
    JsonElement originalValue = obj.get(nonStringKey);
    obj.addProperty(nonStringKey, originalValue.getAsString());
    String fieldTypeChanged = GSON.toJson(obj);
    PCollection<String> json = pipeline.apply(Create.of(fieldTypeChanged));
    PCollectionTuple result = json.apply(JsonToRow.of(DATA.typeDescriptor));
    PAssert.thatSingleton(result.get(ROW).apply("count result", Count.globally())).isEqualTo(0L);
    PAssert.thatSingleton(result.get(ERROR).apply("count error", Count.globally())).isEqualTo(1L);

    pipeline.run();
  }
}
