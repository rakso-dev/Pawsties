using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Controllers
{
    public class AdoptanteController: ControllerBase
    {
        private readonly IAdoptanteService _service;
        private readonly ILogger<AdoptanteController> _logger;

        public AdoptanteController(IAdoptanteService service, ILogger<AdoptanteController> logger)
        {
            _service = service;
            _logger = logger;
        }

        /*[HttpGet ("pawstiesAPI/adoptante")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Adoptante>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get()
        {
            _logger.LogInformation("Calling metho Get Usuarios");
            var adoptantes = _context.Adoptantes;
            return Ok(adoptantes);
        }*/

        [HttpGet ("pawstiesAPI/adoptante/{adoptanteid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Usuario))]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAdoptante(int adoptanteid)
        {
            Adoptante adoptante = _service.GetAdoptante(adoptanteid);
            if (adoptante == null) return BadRequest();
            return Ok(adoptante);
        }

        [HttpPost ("pawstiesAPI/adoptante")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult SaveAdoptante([FromBody] Adoptante adoptante)
        {
            if (!_service.SaveAdoptante(adoptante)) return BadRequest();
            _logger.LogInformation($"Adoptante {adoptante.Nombre} registered successfully");
            return Ok();
        }

        [HttpPut ("pawstiesAPI/adoptante/{adoptanteid}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Update([FromBody] Adoptante adoptante, int adoptanteid)
        {
            if (!_service.UpdateAdoptante(adoptante, adoptanteid)) return BadRequest();
            _logger.LogInformation($"Successfull update on {adoptante.Nombre}");
            return Ok();
        }
    }
}
