// Copyright 2023 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

﻿using System;
using System.Runtime.Serialization;
using MongoDB.Bson.Serialization.Attributes;

namespace backend
{
    [BsonIgnoreExtraElements]
    [DataContract]
    public class GuestbookEntry
    {
        [DataMember(Name = "name")]
        [BsonElement("Name")]
        public string Name { get; set; }

        [DataMember(Name = "message")]
        [BsonElement("Message")]
        public string Message { get; set; }

        [DataMember(Name = "date")]
        [BsonElement("Date")]
        public DateTime Date { get; set; }
    }
}
