using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;

namespace backend
{
    [DataContract]
    public class GuestbookEntry
    {
        [DataMember(Name = "author")]
        public string Author { get; set; }

        [DataMember(Name = "message")]
        public string Message { get; set; }

        [DataMember(Name = "date")]
        public DateTime Date { get; set; }
    }
}
