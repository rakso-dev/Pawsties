﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;
using Microsoft.EntityFrameworkCore;
using PawstiesAPI.Services;
using PawstiesAPI.Business;
using PawstiesAPI.Helper;

namespace PawstiesAPI
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

            services.AddControllers();
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "PawstiesAPI", Version = "v1" });
            });

            services.AddDbContext<PawstiesAPI.Models.pawstiesContext>(options => options.UseNpgsql(Configuration.GetConnectionString("PawstiesConnectionString"), x => x.UseNetTopologySuite()));

            //add custom services
            services.AddScoped<IRescatistaService, RescatistaService>();
            services.AddScoped<IMascotaService, MascotaService>();
            services.AddScoped<IPerroService, PerroService>();
            services.AddScoped<IGatoService, GatoService>();
            services.AddScoped<IAdoptanteService, AdoptanteService>();
            services.AddScoped<IJSONPoint, JSONPoint>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "PawstiesAPI v1"));
            }

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
