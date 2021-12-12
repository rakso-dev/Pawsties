using System;
using System.Linq;
using System.Collections;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class AdopcionService: IAdopcionService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<AdopcionService> _logger;
        public AdopcionService(pawstiesContext context, ILogger<AdopcionService> logger)
        {
            _context = context;
            _logger = logger;
        }

        public IEnumerable GetAdopciones(int rescatistaid)
        {
            try
            {
                var adopciones = from a in _context.Adopcions
                                 join m in _context.Mascota
                                 on a.RMascota equals m.Petid
                                 where m.RRescatista == rescatistaid
                                 select new
                                 {
                                     fecha = a.Fecha,
                                     nombre = m.Nombre
                                 };
                return adopciones;
            } catch (Exception ex)
            {
                throw;
            }
        }

        public bool SaveAdopcion(Adopcion adopcion, int adoptanteid, int petid)
        {   try
            {
                Adoptante adoptante = _context.Adoptantes.Where(e => e.Adoptanteid == adoptanteid).FirstOrDefault();
                Mascotum mascota = _context.Mascota.Where(e => e.Petid == petid).FirstOrDefault();
                if (adoptante == null || mascota == null || adopcion == null) return false;
                _context.Adopcions.Add(adopcion);
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                throw;
            }

        }
    }
}
