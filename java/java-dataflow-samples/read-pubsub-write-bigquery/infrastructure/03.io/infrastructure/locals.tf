# Copyright 2023 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

resource "random_string" "id" {
  length  = 6
  upper   = false
  special = false
}

locals {
  bigquery = {
    sink = {
      dataset_id = "sink_${random_string.id.result}"
    }
  }
  pubsub = {
    source = {
      name = "${data.google_pubsub_topic.reference.name}-source-${random_string.id.result}"
    }
  }
  storage = {
    temporary = {
      bucket = "temp-${random_string.id.result}"
    }
  }
}
