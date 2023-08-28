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

import static com.cloudcode.dataflow.example.testdata.FlatSchemaJavaBean.flatSchemaJavaBean;

import com.cloudcode.dataflow.example.conversions.UserTypeJsonPayloadSerializer;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/** Convenience class for holding reusable test data. */
public final class TestData {

  public static final TestData DATA = new TestData();
  private static final DateTimeFormatter DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime();

  private TestData() {}

  public final TypeDescriptor<FlatSchemaJavaBean> typeDescriptor =
      TypeDescriptor.of(FlatSchemaJavaBean.class);

  public final UserTypeJsonPayloadSerializer<FlatSchemaJavaBean> payloadSerializer =
      UserTypeJsonPayloadSerializer.of(typeDescriptor);

  public final List<FlatSchemaJavaBean> exampleBeans =
      ImmutableList.of(
          flatSchemaJavaBean(false, 1.2345, 1, "a", Instant.ofEpochSecond(100000L)),
          flatSchemaJavaBean(true, 2.2345, 2, "b", Instant.ofEpochSecond(200000L)),
          flatSchemaJavaBean(false, 3.2345, 3, "c", Instant.ofEpochSecond(300000L)));

  public final List<Row> exampleRows =
      exampleBeans.stream()
          .map(payloadSerializer.getUserTypeToRowFn()::apply)
          .collect(Collectors.toList());

  public final List<String> exampleJson =
      exampleRows.stream().map(payloadSerializer::serialize).collect(Collectors.toList());

  public final List<JsonObject> exampleJsonObjects =
      exampleBeans.stream()
          .map(
              exampleBean -> {
                JsonObject object = new JsonObject();
                object.add("aBoolean", new JsonPrimitive(exampleBean.getABoolean()));
                object.add("aDouble", new JsonPrimitive(exampleBean.getADouble()));
                object.add("anInteger", new JsonPrimitive(exampleBean.getAnInteger()));
                object.add("aString", new JsonPrimitive(exampleBean.getAString()));
                object.add(
                    "instant",
                    new JsonPrimitive(exampleBean.getInstant().toString(DATE_TIME_FORMATTER)));
                return object;
              })
          .collect(Collectors.toList());
}
