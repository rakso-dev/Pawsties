using System;
using System.Collections;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;
using PawstiesAPI.Services;

namespace PawstiesAPI.Controllers
{
    public class GatoController: ControllerBase
    {
        private readonly IGatoService _service;
        private readonly ILogger<GatoController> _logger;

        public GatoController(IGatoService service, ILogger<GatoController> logger)
        {
            _service = service;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/gato/get/{distance}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAll([FromBody] JSONPoint point, int distance)
        {
            _logger.LogInformation("Calling GetAllGatos method");
            var gatos = _service.GetAll(point, distance);
            return Ok(gatos);
        }

        [HttpGet ("pawstiesAPI/gato/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Gato))]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get(int petid)
        {
            _logger.LogInformation($"Calling method GetGato with petid {petid}");
            Gato e = _service.GetGato(petid);
            if (e == null)
            {
                return BadRequest();
            }
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
                descripcion = e.Descripcion
            });
        }

        [HttpPost ("pawstiesAPI/gato")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult SaveGato([FromBody] Gato gato)
        {
            if (!_service.SaveGato(gato)) return BadRequest();
            return Ok();
        }

        [HttpPut ("pawstiesAPI/gato/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Update([FromBody] Gato gato, int petid)
        {
            if (!_service.UpdateGato(petid, gato)) return BadRequest();
            return Ok();
        }
    }
}
