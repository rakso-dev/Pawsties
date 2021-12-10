using System;
using System.Linq;
using System.Collections;
using Microsoft.Extensions.Logging;
using NetTopologySuite.Geometries;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class MascotaService: IMascotaService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<MascotaService> _logger;
        //private IJSONPoint _point;

        public MascotaService(pawstiesContext context, ILogger<MascotaService> logger, IJSONPoint point)
        {
            _context = context;
            _logger = logger;
           // _point = point;
        }

        public IEnumerable GetAll(JSONPoint point, int distance)
        {
            var result = from e in _context.Mascota
                         join r in _context.Rescatista
                         on e.RRescatista equals r.Rescatistaid
                         where r.Ort.Distance(new Point(point.Longitude, point.Latitude)) <= distance
                         select new
                         {
                             Petid = e.Petid,
                             Nombre = e.Nombre,
                             sexo = e.Sexo,
                             Edad = e.Edad,
                             rColor = e.RColor,
                             vaxxed = e.Vaxxed,
                             rTemper = e.RTemper,
                             pelaje = e.Pelaje,
                             esterilizado = e.Esterilizado,
                             discapacitado = e.Discapacitado                         
                         };

            return result;
        }

        public Mascotum GetMascota(int id)
        {
            throw new NotImplementedException();
        }

        public bool SaveMascota(Mascotum mascota)
        {
            throw new NotImplementedException();
        }

        public bool UpdateMascota(int id, Mascotum mascota)
        {
            throw new NotImplementedException();
        }
    }
}
