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
using System.Collections.Generic;
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
