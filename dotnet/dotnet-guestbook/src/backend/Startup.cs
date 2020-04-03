using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace backend
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            // GUESTBOOK_DB_ADDR environment variable is provided in guestbook-backend.deployment.yaml.
            var databaseAddr = Environment.GetEnvironmentVariable("GUESTBOOK_DB_ADDR");
            if (string.IsNullOrEmpty(databaseAddr))
            {
                throw new ArgumentException("GUESTBOOK_DB_ADDR environment variable is not set");
            }

            // PORT environment variable is provided in guestbook-backend.deployment.yaml.
            var port = Environment.GetEnvironmentVariable("PORT");
            if (string.IsNullOrEmpty(port))
            {
                throw new ArgumentException("PORT environment variable is not set");
            }

            services.AddControllers();

            // Pass the configuration for connecting to MongoDB to Dependency Injection container
            services.Configure<MongoConfig>(c => c.DatabaseAddress = $"mongodb://{databaseAddr}");

            services.AddScoped<IMessageRepository, MessageRepository>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
