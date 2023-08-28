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

using System.Collections.Generic;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace backend
{
    public class MessageRepository : IMessageRepository
    {
        private ILogger logger;
        private IMongoCollection<GuestbookEntry> entities;

        public MessageRepository(
            ILoggerFactory loggingFactory,
            IOptionsMonitor<MongoConfig> config)
        {
            logger = loggingFactory.CreateLogger<MessageRepository>();

            logger.LogInformation($"Trying to connect to {config.CurrentValue.DatabaseAddress}");

            var client = new MongoClient(config.CurrentValue.DatabaseAddress);
            var database = client.GetDatabase("guestbook");
            entities = database.GetCollection<GuestbookEntry>("GuestBookEntries");
        }

        public IEnumerable<GuestbookEntry> FindAll()
        {
            using (var cursor = entities.Find<GuestbookEntry>(entry => true).SortByDescending(e => e.Date).ToCursor())
            {
                while (cursor.MoveNext())
                {
                    foreach (var current in cursor.Current)
                    {
                        yield return current;
                    }
                }
            }
        }

        public void Save(GuestbookEntry entry)
        {
            entities.InsertOne(entry);
        }
    }
}