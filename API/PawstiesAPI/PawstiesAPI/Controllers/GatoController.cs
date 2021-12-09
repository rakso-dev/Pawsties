using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;

namespace PawstiesAPI.Controllers
{
    public class GatoController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<GatoController> _logger;

        public GatoController(pawstiesContext context, ILogger<GatoController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/gato")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Gato>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAll()
        {
            _logger.LogInformation("Calling GetAllGatos method");
            var gatos = _context.Gatos;
            return Ok(gatos);
        }

        [HttpGet ("pawstiesAPI/gato/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Gato))]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get(int petid)
        {
            _logger.LogInformation($"Calling method GetGato with petid{petid}");
            Gato gato = _context.Gatos.Where(e => e.Petid == petid).FirstOrDefault();
            if (gato == null)
            {
                return BadRequest();
            }
            return Ok(gato);
        }

        [HttpPost ("pawstiesAPI/gato")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult SaveGato([FromBody] Gato gato)
        {
            try
            {
                _context.Add(gato);
                _context.SaveChanges();
                return Ok();
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Insertion error on Gato {gato.Petid}");
                throw;
            }
        }

        [HttpPut ("pawstiesAPI/gato/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Update([FromBody] Gato gato, int petid)
        {
            try
            {
                Gato cat = _context.Gatos.Where(e => e.Petid == petid).FirstOrDefault();
                if (cat == null)
                {
                    return BadRequest();
                }
                cat.Nombre = gato.Nombre;
                cat.Sexo = gato.Sexo;
                cat.Edad = gato.Edad;
                cat.RColor = gato.RColor;
                cat.Vaxxed = gato.Vaxxed;
                cat.RTemper = gato.RTemper;
                cat.Pelaje = gato.Pelaje;
                cat.Esterilizado = gato.Esterilizado;
                cat.Discapacitado = gato.Discapacitado;
                cat.RRescatista = gato.RRescatista;
                cat.Nombre = gato.Nombre;
                cat.Descripcion = gato.Descripcion;
                _context.SaveChanges();
                return Ok();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during Gato update with petid = {petid}");
                throw;
            }
        }
    }
}
