using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace dotnet_cloud_run_hello_world
{
    public class Startup
    {
        public Startup(IConfiguration configuration, IWebHostEnvironment environment)
        {
            Configuration = configuration;
            WebEnvironment = environment;
        }

        public IConfiguration Configuration { get; }
        public IWebHostEnvironment WebEnvironment { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddRazorPages();

            // Populate Cloud Run environment information: service, revision and Cloud Project
            //

            // Cloud Run Service
            string service;
            try
            {
                service = Environment.GetEnvironmentVariable("K_SERVICE");
            }
            catch (ArgumentNullException)
            {
                service = "???";
            }

            // Cloud Run Revision
            string revision;
            try
            {
                revision = Environment.GetEnvironmentVariable("K_REVISION");
            }
            catch (ArgumentNullException)
            {
                revision = "???";
            }
            
            var envInfo = new EnvironmentInfo(service, revision);
            services.AddSingleton<IEnvironmentInfo>(envInfo);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            // Cloud Run automatically redirects to HTTPS
            // however we keep it here for portability to other platforms
            // that might not do it by default
            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapRazorPages();
            });
        }
    }
}
