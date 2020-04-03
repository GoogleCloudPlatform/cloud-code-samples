using System.Collections.Generic;

namespace backend
{
    public interface IMessageRepository
    {
        IEnumerable<GuestbookEntry> FindAll();
        void Save(GuestbookEntry entry);
    }
}