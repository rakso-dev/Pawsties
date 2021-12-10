using System;
using System.Collections;
using System.Linq;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class RescatistaService : IRescatistaService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<RescatistaService> _logger;

        public RescatistaService(pawstiesContext context, ILogger<RescatistaService> logger)
        {
            _context = context;
            _logger = logger;
        }

        /*
        public IEnumerable GetAll()
        {
            return null;
        }*/

        public Rescatistum GetRescatista(int rescatistaid)
        {
            try
            {
                Rescatistum rescatista = _context.Rescatista.Where(e => e.Rescatistaid == rescatistaid).FirstOrDefault();
                return rescatista;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"An error ocurred on method {nameof (GetRescatista)}", new { rescatistaid });
                throw;
            }
        }

        public bool SaveRescatista(Rescatistum resc)
        {
            if (resc == null)
                return false;
            try
            {
                //hacer un mapper para insercion de punto espacial
                resc.Ort = new NetTopologySuite.Geometries.Point((double)resc.Longitude, (double)resc.Latitude);
                _context.Add(resc);
                _context.SaveChanges();

                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during {nameof(SaveRescatista)}", resc.Rescatistaid);
                throw;
            }
        }

        public bool Update(Rescatistum resc, int rescatistaid)
        {
            if(resc == null)
            {
                return false;
            }
            try
            {
                //hacer un mapper para insercion de punto espacial
                Rescatistum r = _context.Rescatista.Where(e => e.Rescatistaid == rescatistaid).FirstOrDefault();
                if(r == null)
                {
                    return false;
                }
                r.Image = resc.Image;
                r.Mail = resc.Mail;
                r.Telephone = resc.Telephone;
                r.Password = resc.Password;
                r.NombreEnt = resc.NombreEnt;
                r.Rfc = resc.Rfc;
                r.Ort = new NetTopologySuite.Geometries.Point((double)resc.Longitude, (double)resc.Latitude);
                _context.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during {nameof(Update)}", rescatistaid);
                throw;
            }
        }
    }
}
