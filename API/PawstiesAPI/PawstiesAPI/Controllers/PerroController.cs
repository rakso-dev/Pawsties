using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Controllers
{
    public class PerroController: ControllerBase
    {
        private readonly IPerroService _service;
        private readonly ILogger<PerroController> _logger;

        public PerroController(IPerroService service, ILogger<PerroController> logger)
        {
            _service = service;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/perro/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Perro))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get (int petid)
        {
            _logger.LogInformation($"Calling Get method with petID {petid}");
            Perro e = _service.GetPerro(petid);
            if (e == null) return BadRequest();
            return Ok(new
            {
                petid = e.Petid,
                nombre = e.Nombre,
                sexo = e.Sexo,
                rRescatista = e.RRescatista,
                edad = (DateTime.Today - e.Edad).Days,
                rColor = e.RColor,
                vaxxed = e.Vaxxed,
                rTemper = e.RTemper,
                pelaje = e.Pelaje,
                esterilizado = e.Esterilizado,
                discapacitado = e.Discapacitado,
                descripcion = e.Descripcion,
                talla = e.RTalla
            });
        }

        [HttpGet ("pawstiesAPI/perro/get/{distance}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Perro>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAll ([FromBody] JSONPoint point, int distance)
        {
            _logger.LogInformation("Calling GetAllPerros method");
            var perros = _service.GetAll(point, distance);
            return Ok(perros);
        }

        [HttpPost ("pawstiesAPI/perro")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult SavePerro([FromBody] Perro perro)
        {
            if(!_service.SavePerro(perro)) return BadRequest();
            return Ok();
        }

        [HttpPost ("pawstiesAPI/perro/{petid}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public IActionResult Update([FromBody] Perro perro, int petid)
        {
            if (_service.UpdatePerro(petid, perro))
                return Ok();
            return BadRequest();
        }
    }
}
