using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace backend
{
    public class Startup
    {
        ILogger logger;

        public Startup(
            IConfiguration configuration,
            ILoggerFactory loggerFactory)
        {
            Configuration = configuration;
            logger = loggerFactory.CreateLogger<Startup>();
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            // GUESTBOOK_DB_ADDR environment variable is provided in guestbook-backend.deployment.yaml.
            string databaseAddr = Environment.GetEnvironmentVariable("GUESTBOOK_DB_ADDR");
            logger.LogError($"Db address is set to {databaseAddr}");
            if (string.IsNullOrEmpty(databaseAddr))
            {
                logger.LogError("GUESTBOOK_DB_ADDR environment variable is not set");
                Environment.Exit(-1);
            }

            // PORT environment variable is provided in guestbook-backend.deployment.yaml.
            string port = Environment.GetEnvironmentVariable("PORT");
            logger.LogInformation($"Port env var is set to {port}");
            if (string.IsNullOrEmpty(port))
            {
                logger.LogError("PORT environment variable is not set");
                Environment.Exit(-1);
            }

            // Pass the configuration for connecting to MongoDB to Dependency Injection container
            services.Configure<MongoConfig>(c => c.DatabaseAddress = $"mongodb://{databaseAddr}");

            services.AddScoped<IMessageRepository, MessageRepository>();
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseMvc();
        }
    }
}
