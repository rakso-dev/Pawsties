using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.ApplicationInsights;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Serilog;

namespace PawstiesAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .UseSerilog ((context, config) =>
                {
                    config.WriteTo.Console();
                    config.WriteTo.File("Logs.txt", Serilog.Events.LogEventLevel.Information);
                    config.WriteTo.ApplicationInsights(new TelemetryClient()
                    {
                        InstrumentationKey = "990d76fd-b293-43a4-99ea-8ca74e38be62",
                    }, TelemetryConverter.Events);
                })
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                });
    }
}
