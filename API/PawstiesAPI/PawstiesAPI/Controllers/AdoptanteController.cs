using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
namespace PawstiesAPI.Controllers
{
    public class AdoptanteController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<AdoptanteController> _logger;

        public AdoptanteController(pawstiesContext context, ILogger<AdoptanteController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/adoptante")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Adoptante>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get()
        {
            _logger.LogInformation("Calling metho Get Usuarios");
            var adoptantes = _context.Adoptantes;
            return Ok(adoptantes);
        }

        [HttpGet ("pawstiesAPI/adoptante/{adoptanteid}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Usuario))]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetAdoptante(int adoptanteid)
        {
            try
            {
                Adoptante adoptante = _context.Adoptantes.Where(e => e.Adoptanteid == adoptanteid).FirstOrDefault();
                if (adoptante == null)
                {
                    return BadRequest("ID incorrecto");
                }
                return Ok(adoptante);
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error on adoptante {adoptanteid}");
                throw;
            }
        }

        [HttpPost ("pawstiesAPI/adoptante")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult SaveAdoptante([FromBody] Adoptante adoptante)
        {
            try
            {
                if (adoptante == null)
                {
                    return BadRequest("Error on data");
                }
                _context.Add(adoptante);
                _context.SaveChanges();
                return Ok();
            } catch (Exception ex)
            {
                _logger.LogError(ex, "Error on Adoptante insertion");
                throw;
            }
        }

        [HttpPut ("pawstiesAPI/adoptante/{adoptanteid}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Update([FromBody] Adoptante adoptante, int adoptanteid)
        {
            try
            {
                Adoptante adoptant = _context.Adoptantes.Where(e => e.Adoptanteid == adoptanteid).FirstOrDefault();
                if(adoptant == null)
                {
                    return BadRequest();
                }
                adoptant.Nombre = adoptante.Nombre;
                adoptant.Apellidos = adoptante.Apellidos;
                adoptant.FechaDeNac = adoptante.FechaDeNac;
                adoptant.Image = adoptante.Image;
                adoptant.Mail = adoptante.Mail;
                adoptant.Telephone = adoptante.Telephone;
                _context.SaveChanges();
                return Ok();
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error updating Adopstante wirh adoptanteid {adoptanteid}");
                throw;
            }
        }
    }
}
