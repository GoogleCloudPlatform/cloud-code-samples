using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace backend.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class MessagesController : ControllerBase
    {
        private ILogger _logger;

        public MessagesController(ILoggerFactory factory)
        {
            _logger = factory.CreateLogger<MessagesController>();
        }

        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<GuestbookEntry>> Get()
        {
            return new GuestbookEntry[]
                {
                    new GuestbookEntry { Author = "a", Message = "xxx" },
                    new GuestbookEntry { Author = "b", Message = "yyy" }
                };
        }

        // POST api/values
        [HttpPost]
        public ActionResult<IEnumerable<GuestbookEntry>> Post([FromBody] GuestbookEntry value)
        {
            _logger.LogInformation("called");

            value.Date = DateTime.UtcNow;

            return new GuestbookEntry[]
            {
                new GuestbookEntry { Author = "a", Message = "xxx" },
                new GuestbookEntry { Author = "b", Message = "yyy" },
                value
            };
        }
    }
}
