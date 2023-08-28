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
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using dotnet_guestbook.Models;
using Microsoft.Extensions.Logging;
using System.Net.Http;
using frontend;
using System.Net.Http.Headers;

namespace dotnet_guestbook.Controllers
{
    public class HomeController : Controller
    {
        private ILogger _logger;
        private IEnvironmentConfiguration _envConfig;
        private IHttpClientFactory _factory;

        public HomeController(
            IHttpClientFactory httpFactory,
            ILoggerFactory loggerFactory,
            IEnvironmentConfiguration environmentConfiguration)
        {
            _factory = httpFactory;
            _logger = loggerFactory.CreateLogger<HomeController>();
            _envConfig = environmentConfiguration;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            _logger.LogInformation($"Getting all messages");

            // Get the entries from the backend
            try
            {
                var httpClient = _factory.CreateClient();
                httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                // TODO - Good spot for adding a logpoint to get the backend address
                _logger.LogInformation($"Making a request to {_envConfig.BackendAddress}");
                var response = await httpClient.GetAsync(_envConfig.BackendAddress);
                response.EnsureSuccessStatusCode();
                var entries = await response.Content.ReadAsAsync<IEnumerable<GuestbookEntry>>();

                return View(entries);
            }
            catch (Exception e)
            {
                _logger.LogError(e.ToString());
                return View();
            }
        }

        [HttpPost("post")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Post([FromForm] GuestbookEntry entry)
        {
            _logger.LogInformation($"Calling backend at {_envConfig.BackendAddress} for message authored by {entry.Name}");

            try
            {
                var httpClient = _factory.CreateClient();
                httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                await httpClient.PostAsJsonAsync<GuestbookEntry>(_envConfig.BackendAddress, entry);

                return RedirectToAction("Index");
            }
            catch (Exception e)
            {
                _logger.LogError(e.ToString());
                return View();
            }
        }
    }
}
