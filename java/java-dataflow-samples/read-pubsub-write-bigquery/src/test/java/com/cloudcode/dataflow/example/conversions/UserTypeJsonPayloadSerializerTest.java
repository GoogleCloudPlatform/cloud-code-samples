package com.cloudcode.dataflow.example.conversions;

import static com.cloudcode.dataflow.example.testdata.TestData.DATA;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cloudcode.dataflow.example.testdata.FlatSchemaJavaBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Set;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.junit.jupiter.api.Test;

/** Tests for {@link UserTypeJsonPayloadSerializer}. */
class UserTypeJsonPayloadSerializerTest {

  private static final Gson GSON = new Gson();
  private static final UserTypeJsonPayloadSerializer<FlatSchemaJavaBean> PAYLOAD_SERIALIZER =
      UserTypeJsonPayloadSerializer.of(TypeDescriptor.of(FlatSchemaJavaBean.class));

  @Test
  void serialize() {
    Row input = DATA.exampleRows.get(0);
    String actualJson = PAYLOAD_SERIALIZER.serialize(input);
    JsonObject actual = GSON.fromJson(actualJson, JsonObject.class);
    JsonObject expected = DATA.exampleJsonObjects.get(0);
    Set<String> fields = new HashSet<>(actual.keySet());
    fields.addAll(expected.keySet());
    for (String field : fields) {
      assertEquals(expected.get(field).getAsString(), actual.get(field).getAsString(), field);
    }
  }

  @Test
  void deserialize() {
    String input = DATA.exampleJson.get(0);
    JsonObject expected = DATA.exampleJsonObjects.get(0);
    Row actual = PAYLOAD_SERIALIZER.deserialize(input);
    Schema schema = actual.getSchema();
    Set<String> fields = new HashSet<>(schema.getFieldNames());
    fields.addAll(expected.keySet());
    for (String field : fields) {
      assertEquals(
          expected.get(field).getAsString(),
          requireNonNull(actual.getValue(field)).toString(),
          field);
    }
  }
}
