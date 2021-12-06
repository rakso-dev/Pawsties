using System.Linq;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;

namespace PawstiesAPI.Controllers
{
    public class MascotaController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<MascotaController> _logger;

        public MascotaController(pawstiesContext context, ILogger<MascotaController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/mascotas/all")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Mascotum>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get()
        {
            _logger.LogInformation("Calling method GetAllMascotas");
            var mascota = _context.Mascota;
            return Ok (mascota);
        }

        [HttpGet ("pawstiesAPI/mascotas/{petID}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Mascotum))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetMascota(int petID)
        {
            _logger.LogInformation($"Calling method GetMascota with petID {petID}", null);
            var mascota = _context.Mascota.Where(e => e.Petid == petID).FirstOrDefault();
            return Ok(mascota);
        }
    }
}
