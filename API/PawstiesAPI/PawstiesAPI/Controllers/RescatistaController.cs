using System;
using PawstiesAPI.Models;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.Http;

namespace PawstiesAPI.Controllers
{
    public class RescatistaController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<RescatistaController> _logger;

        public RescatistaController(pawstiesContext context, ILogger<RescatistaController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/rescatista/all")]
        
        public IActionResult Get()
        {
            var rescatista = _context.Rescatista;
            return Ok(rescatista);
        }

        [HttpGet ("pawstiesAPI/rescatista/{rescatistaID}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Rescatistum))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetRescatista(int rescatistaID)
        {
            Rescatistum rescatista = null;

            try
            {
                rescatista = _context.Rescatista.Where(e => e.Rescatistaid == rescatistaID).FirstOrDefault();
                return Ok(rescatista);
            } catch (Exception ex)
            {
                _logger.LogError(ex, "Error during query to Rescatista table");
                throw;
            }
        }
    }

    public interface DTO
    {
        public void Algo();
    }

    public class RescatistaDTO: DTO
    {
        public string Image { get; set; }
        public string Mail { get; set; }
        public string Telephone { get; set; }
        public int Rescatistaid { get; set; }
        public string NombreEnt { get; set; }
        public string Rfc { get; set; }
        public decimal Latitude { get; set; }
        public decimal Longitude { get; set; }

        public void Algo() {
            
        }

        
    }
}
