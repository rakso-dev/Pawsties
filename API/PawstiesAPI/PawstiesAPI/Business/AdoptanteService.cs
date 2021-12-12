using System;
using System.Linq;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class AdoptanteService: IAdoptanteService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<AdoptanteService> _logger;

        public AdoptanteService(pawstiesContext context, ILogger<AdoptanteService> logger)
        {
            _context = context;
            _logger = logger;
        }

        public Adoptante GetAdoptante(int adoptanteid)
        {
            try
            {
                Adoptante adoptante = _context.Adoptantes.Where(e => e.Adoptanteid == adoptanteid).FirstOrDefault();
                return adoptante;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(GetAdoptante)}", adoptanteid);
                throw;
            }
        }

        public bool SaveAdoptante(Adoptante adoptante)
        {
            try
            {
                if (adoptante == null) return false;
                Adoptante tmp = _context.Adoptantes.Where(e => e.Mail.Equals(adoptante.Mail)).FirstOrDefault();
                if (tmp != null) return false;
                _context.Adoptantes.Add(adoptante);
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on methog {nameof(SaveAdoptante)}");
                throw;
            }
        }

        public bool UpdateAdoptante(Adoptante adoptante, int adoptanteid)
        {
            try
            {
                Adoptante a = _context.Adoptantes.Where(e => e.Adoptanteid == adoptanteid).FirstOrDefault();
                if (adoptante == null || a == null) return false;
                a.Image = adoptante.Image;
                a.Mail = adoptante.Mail;
                a.Telephone = adoptante.Telephone;
                a.Nombre = adoptante.Nombre;
                a.Apellidos = adoptante.Apellidos;
                a.FechaDeNac = adoptante.FechaDeNac;
                a.Password = adoptante.Password;
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on method {nameof(SaveAdoptante)}", adoptanteid);
                throw;
            }
        }
    }
}
