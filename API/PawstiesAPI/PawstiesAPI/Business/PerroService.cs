using System;
using System.Collections;
using System.Linq;
using Microsoft.Extensions.Logging;
using NetTopologySuite.Geometries;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class PerroService: IPerroService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<PerroService> _logger;
        public PerroService(pawstiesContext context, ILogger<PerroService> logger)
        {
            _context = context;
            _logger = logger;
        }

        public IEnumerable GetAll(JSONPoint point, int distance)
        {
            if (point == null) return null;
            try
            {
                var perros = from e in _context.Perros
                             join r in _context.Rescatista
                             on e.RRescatista equals r.Rescatistaid
                             where r.Ort.Distance(new Point(point.Longitude, point.Latitude)) <= distance
                             select new 
                             {
                                 petid = e.Petid,
                                 nombre = e.Nombre,
                                 sexo = e.Sexo,
                                 edad = (DateTime.Today - e.Edad).Days,
                                 rColor = e.RColor,
                                 vaxxed = e.Vaxxed,
                                 rTemper = e.RTemper,
                                 pelaje = e.Pelaje,
                                 esterilizado = e.Esterilizado,
                                 discapacitado = e.Discapacitado,
                                 descripcion = e.Descripcion,
                                 talla = e.RTalla
                             };
                
                return perros;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetAll)}");
                throw;
            }
        }

        public Perro GetPerro(int petid)
        {
            try
            {
                Perro perro = _context.Perros.Where(e => e.Petid == petid).FirstOrDefault();
                return perro;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetPerro)}");
                throw;
            }
        }

        public bool SavePerro(Perro dog)
        {
            if (dog == null) return false;
            try
            {
                _context.Perros.Add(dog);
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(SavePerro)}");
                throw;
            }
        }

        public bool UpdatePerro(int id, Perro perro)
        {
            try
            {
                Perro dog = _context.Perros.Where(e => e.Petid == id).FirstOrDefault();
                if (dog == null) return false;
                dog.Nombre = perro.Nombre;
                dog.Sexo = perro.Sexo;
                dog.Edad = perro.Edad;
                dog.RColor = perro.RColor;
                dog.Vaxxed = perro.Vaxxed;
                dog.RTemper = perro.RTemper;
                dog.Pelaje = perro.Pelaje;
                dog.Esterilizado = perro.Esterilizado;
                dog.Discapacitado = perro.Discapacitado;
                //dog.RRescatista = perro.RRescatista;
                dog.Nombre = perro.Nombre;
                dog.Descripcion = perro.Descripcion;
                dog.RTalla = perro.RTalla;
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(UpdatePerro)}");
                throw;
            }
        }
    }
}
