using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
namespace PawstiesAPI.Controllers
{
    public class PerroController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<PerroController> _logger;

        public PerroController(pawstiesContext context, ILogger<PerroController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/perro/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Perro))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get (int petid)
        {
            _logger.LogInformation($"Calling Get method with petID {petid}");
            Perro perro = _context.Perros.Where(e => e.Petid == petid).FirstOrDefault();
            return Ok(perro);
        }

        [HttpGet ("pawstiesAPI/perro")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Perro>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAll ()
        {
            _logger.LogInformation("Calling GetAllPerros method");
            var perros = _context.Perros;
            return Ok(perros);
        }

        [HttpPost ("pawstiesAPI/perro")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        public IActionResult SavePerro([FromBody] Perro perro)
        {
            try
            {
                _context.Perros.Add(perro);
                _context.SaveChanges();
                return Ok();
            } catch(Exception ex)
            {
                _logger.LogError(ex, $"Error during insertion of Perro {perro.Petid}");
                throw;
            }
        }
    }
}
