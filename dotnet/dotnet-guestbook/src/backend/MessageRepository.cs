using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
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