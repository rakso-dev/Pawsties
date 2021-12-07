using System;
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

        [HttpPost ("pawstiesAPI/perro")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        public IActionResult SavePerro(Perro perro)
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
