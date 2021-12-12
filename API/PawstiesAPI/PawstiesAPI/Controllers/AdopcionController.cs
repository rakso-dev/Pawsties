using System;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Controllers
{
    public class AdopcionController: ControllerBase
    {
        private readonly IAdopcionService _service;
        private readonly ILogger<AdopcionController> _logger;

        public AdopcionController(IAdopcionService service, ILogger<AdopcionController> logger)
        {
            _service = service;
            _logger = logger;
        }

        [HttpGet("pawstiesAPI/adopcion/{id}")]
        [ProducesResponseType(StatusCodes.Status200OK, Type = typeof(Adopcion))]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public IActionResult GetAdopciones(int id)//string id) 
        {
            try
            {
                var adopciones = _service.GetAdopciones(id);
                return Ok(adopciones);
            } catch (Exception ex)
            {
                throw;
            }
        }

        [HttpPost("pawstiesAPI/adopcion/")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public IActionResult SaveAdopciones([FromBody] Adopcion adoption)
        {
            try
            {
                if (!_service.SaveAdopcion(adoption, (int) adoption.RAdoptante, (int) adoption.RMascota)) return BadRequest();
                return Ok();
            } catch(Exception ex)
            {
                throw;
            }
        }
    }
}
