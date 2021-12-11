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
    public class GatoService: IGatoService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<GatoService> _logger;

        public GatoService(pawstiesContext context, ILogger<GatoService> logger)
        {
            _context = context;
            _logger = logger;
        }

        public IEnumerable GetAll(JSONPoint point, int distance)
        {
            if (point == null)
            {
                return null;
            }
            try
            {
                var result = from e in _context.Gatos
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
                                 descripcion = e.Descripcion
                             };

                return result;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetAll)}");
                throw;
            }
        }

        public Gato GetGato(int id)
        {
            try
            {
                _logger.LogInformation($"Using method {nameof(GetGato)}");
                Gato gato = _context.Gatos.Where(e => e.Petid == id).FirstOrDefault();
                return gato;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"An error ocurred on method {nameof(GetGato)}", id);
                throw;
            }
        }

        public bool SaveGato(Gato cat)
        {
            if (cat == null) return false;
            try
            {
                _context.Gatos.Add(cat);
                _context.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(SaveGato)}");
                throw;
            }
        }

        public bool UpdateGato(int id, Gato gato)
        {
            try
            {
                Gato cat = _context.Gatos.Where(e => e.Petid == id).FirstOrDefault();
                if (cat == null) return false;
                cat.Nombre = gato.Nombre;
                cat.Sexo = gato.Sexo;
                cat.Edad = gato.Edad;
                cat.RColor = gato.RColor;
                cat.Vaxxed = gato.Vaxxed;
                cat.RTemper = gato.RTemper;
                cat.Pelaje = gato.Pelaje;
                cat.Esterilizado = gato.Esterilizado;
                cat.Discapacitado = gato.Discapacitado;
                //dog.RRescatista = perro.RRescatista;
                cat.Nombre = gato.Nombre;
                cat.Descripcion = gato.Descripcion;
                _context.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(UpdateGato)}");
                throw;
            }
        }
    }
}
