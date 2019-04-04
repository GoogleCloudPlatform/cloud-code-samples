using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using frontend;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Google.Cloud.Diagnostics.AspNetCore;

namespace dotnet_guestbook
{
    public class Startup
    {
        private ILogger logger;
        private EnvironmentConfiguration envConfig;

        public Startup(
            IConfiguration configuration,
            ILoggerFactory loggerFactory)
        {
            Configuration = configuration;
            envConfig = new EnvironmentConfiguration();
            logger = loggerFactory.CreateLogger<Startup>();
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.Configure<CookiePolicyOptions>(options =>
            {
                // This lambda determines whether user consent for non-essential cookies is needed for a given request.
                options.CheckConsentNeeded = context => true;
                options.MinimumSameSitePolicy = SameSiteMode.None;
            });

            services.AddHttpClient();
            services.AddSingleton<IEnvironmentConfiguration>(envConfig);

            services.AddLogging();

            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(
            IApplicationBuilder app, 
            IHostingEnvironment env)
        {
            // GUESTBOOK_API_ADDR environment variable is provided in guestbook-frontend.deployment.yaml.
            string backendAddr = Environment.GetEnvironmentVariable("GUESTBOOK_API_ADDR");
            logger.LogError($"Backend address is set to {backendAddr}");
            if (string.IsNullOrEmpty(backendAddr))
            {
                logger.LogError("GUESTBOOK_API_ADDR environment variable is not set");
                Environment.Exit(-1);
            }

            // PORT environment variable is provided in guestbook-frontend.deployment.yaml.
            string port = Environment.GetEnvironmentVariable("PORT");
            logger.LogInformation($"Port env var is set to {port}");
            if (string.IsNullOrEmpty(port))
            {
                logger.LogError("PORT environment variable is not set");
                Environment.Exit(-1);
            }

            // Set the address of the backend microservice
            envConfig.BackendAddress = $"http://{backendAddr}:{port}/messages";


            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                //app.UseHsts();
            }

            app.UseStaticFiles();
            app.UseCookiePolicy();

            app.UseMvc(routes =>
            {
                routes.MapRoute(name: "postRoute", template: "{controller}/post",
                    defaults: new { controller = "Home", action = "Post" });

                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });
        }
    }
}
