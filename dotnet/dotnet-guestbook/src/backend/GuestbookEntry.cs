using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;
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
