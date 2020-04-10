using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

namespace dotnet_cloud_run_hello_world.Pages
{
    public class IndexModel : PageModel
    {
        private readonly ILogger<IndexModel> _logger;

        public IndexModel(ILogger<IndexModel> logger, IEnvironmentInfo envInfo)
        {
            _logger = logger;
            EnvironmentInfo = envInfo;
        }

        public IEnvironmentInfo EnvironmentInfo{ get; private set; }

        public void OnGet()
        {

        }
    }
}
