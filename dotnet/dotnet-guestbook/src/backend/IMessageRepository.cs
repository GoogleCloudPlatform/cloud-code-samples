using System.Collections.Generic;
using System.Threading.Tasks;
using MongoDB.Driver;

namespace backend
{
    public interface IMessageRepository
    {
        IEnumerable<GuestbookEntry> FindAll();
        void Save(GuestbookEntry entry);
    }
}