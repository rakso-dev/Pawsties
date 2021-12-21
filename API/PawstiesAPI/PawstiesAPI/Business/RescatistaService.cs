using System;
using System.Collections;
using System.Linq;
//using System.Security.Claims;
//using Microsoft.AspNetCore.DataProtection;
using Microsoft.Extensions.Logging;
//using Microsoft.IdentityModel.Tokens;
using NetTopologySuite.Geometries;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Business
{
    public class RescatistaService : IRescatistaService
    {
        private const string PURPOSE = "RescatistaProtection";
        private readonly pawstiesContext _context;
        private readonly ILogger<RescatistaService> _logger;
        //private readonly IDataProtector _protector;
       // private readonly JwtSettings _jwtSettings;

        public RescatistaService(pawstiesContext context, ILogger<RescatistaService> logger)//, IDataProtectionProvider provider, JwtSettings jwtSettings)
        {
            _context = context;
            _logger = logger;
            //_protector = provider.CreateProtector(PURPOSE);
            //_jwtSettings = jwtSettings;

        }

        /*
        public IEnumerable GetAll(Rescatistum res)
        {
            try
            {
                var rescatista = _context.Rescatista.Where(e => e.Mail.Equals(res.Mail) && e.Password.Equals(res.Password)).FirstOrDefault();
                return rescatista;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error retieving restatistas on {nameof(GetAll)} method");
                throw;
            }
        }*/

        public Rescatistum GetRescatista(Rescatistum resc)//int rescatistaid)//string id)
        {
            try
            {
                //var tmp = _protector.Unprotect(id);
                //var rescatistaid = int.Parse(tmp);
                //Rescatistum rescatista = _context.Rescatista.Where(e => e.Rescatistaid == rescatistaid).FirstOrDefault();
                Rescatistum rescatista = _context.Rescatista.Where(e => e.Password.Equals(resc.Password) && e.Mail.Equals(resc.Mail)).FirstOrDefault();
                rescatista.Password = "";
                return rescatista;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"An error ocurred on method {nameof (GetRescatista)}");
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
                resc.Ort = new Point((double)resc.Longitude, (double)resc.Latitude);
                _context.Rescatista.Add(resc);
                _context.SaveChanges();
                return true;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during {nameof(SaveRescatista)}", resc.Rescatistaid);
                throw;
            }
        }

        public bool Update(Rescatistum resc, int rescatistaid) //string id)
        {
            if(resc == null)
            {
                return false;
            }
            try
            {
                //hacer un mapper para insercion de punto espacial
                //var tmp = _protector.Unprotect(id);
                //var rescatistaid = int.Parse(tmp);
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
                r.Ort = new Point((double)resc.Longitude, (double)resc.Latitude);
                _context.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during {nameof(Update)}");
                throw;
            }
        }
        /*
        public Rescatistum Authenticate (string mail, string password)
        {
            var rescatista = _context.Rescatista.Where(e => e.Mail.Equals(mail) && e.Password.Equals(password)).FirstOrDefault();
            if (rescatista == null) return rescatista;

            var tokenHandler = new SecurityTokenDescriptor()
            {
                Subject = new ClaimsIdentity
            }
        }*/
    }
}
