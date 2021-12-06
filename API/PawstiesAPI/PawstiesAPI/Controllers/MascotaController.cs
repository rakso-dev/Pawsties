using System;
using Microsoft.AspNetCore.Mvc;
using PawstiesAPI.Models;
using System.Linq;
using System.Collections.Generic;

namespace PawstiesAPI.Controllers
{
    public class MascotaController: ControllerBase
    {
        private readonly pawstiesContext _context;
        public MascotaController(pawstiesContext context)
        {
            _context = context;
        }

        /*[HttpGet ("pawstiesAPI/mascota/{distance}")]
        public IActionResult Get(double distance)
        {
            var mascota = _context.Mascota.Where(e => );

        }*/

        /*private double distance(double lat1, double lat2, double long1, double long2)
        {
            long1 = toRadians(long1);
            long2 = toRadians(long2);
            lat1 = toRadians(lat1);
            lat2 = toRadians(lat2);

            double dlon = long2 - long1;
            double dlat = lat2 - lat1;
            double a = Math.Pow(Math.Sin(dlat /2), 2) + 
        }*/

        private double toRadians(double angle)
        {
            return (angle * Math.PI) / 180;
        }
    }
}
