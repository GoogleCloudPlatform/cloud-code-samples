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
        private IMessageRepository _repository;

        public MessagesController(
            ILoggerFactory factory,
            IMessageRepository repository)
        {
            _logger = factory.CreateLogger<MessagesController>();
            _repository = repository;
        }

        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<GuestbookEntry>> Get()
        {
            return Ok(_repository.FindAll());
        }

        // POST api/values
        [HttpPost]
        public ActionResult<IEnumerable<GuestbookEntry>> Post([FromBody] GuestbookEntry value)
        {
            _logger.LogInformation("called");

            value.Date = DateTime.UtcNow;
            _repository.Save(value);
            return RedirectToAction("Get");
        }
    }
}
