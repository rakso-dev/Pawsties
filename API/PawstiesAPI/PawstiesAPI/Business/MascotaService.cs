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
        
        public MascotaService(pawstiesContext context, ILogger<MascotaService> logger)
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
                var result = from e in _context.Mascota
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
                             };;
                

                return result;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetAll)}");
                throw;
            }
        }

        public IEnumerable GetMascotaByRescatista(int rescatistaid)
        {
            try
            {
                var mascotas = _context.Mascota.Where(e => e.RRescatista == rescatistaid);
                return mascotas;
            } catch(Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetMascotaByRescatista)}", rescatistaid);
                throw;
            }
        }

        public Mascotum GetMascota(int id)
        {
            try
            {
                _logger.LogInformation($"Using method {nameof (GetMascota)}");
                Mascotum mascota = _context.Mascota.Where(e => e.Petid == id).FirstOrDefault();
                return mascota;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"An error ocurred on method {nameof(GetMascota)}", id);
                throw;
            }
        }

        /*public bool UpdateMascota(Mascotum pet, int petid)
        {
            try
            {
                Mascotum mascota = _context.Mascota.Where(e => e.Petid == petid).FirstOrDefault();
                mascota.Nombre = pet.Nombre;
                mascota.Sexo = pet.Sexo;
                mascota.Edad = pet.Edad;
                mascota.RColor = pet.RColor;
                mascota.Vaxxed = pet.Vaxxed;
                mascota.RTemper = pet.RTemper;
                mascota.Pelaje = pet.Pelaje;
                mascota.Esterilizado = pet.Esterilizado;
                mascota.Discapacitado = pet.Discapacitado;
                mascota.RRescatista = pet.RRescatista;
                mascota.Nombre = pet.Nombre;
                mascota.Descripcion = pet.Descripcion;
                return true;
            } catch (Exception ex)
            {
                throw;
            }
        }

        public bool UpdateMascota(int id, Mascotum mascota)
        {
            throw new NotImplementedException();
        }*/
    }
}
