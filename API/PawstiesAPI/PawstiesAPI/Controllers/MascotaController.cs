﻿using System.Linq;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using System;
using PawstiesAPI.Helper;
using PawstiesAPI.Services;

namespace PawstiesAPI.Controllers
{
    public class MascotaController: ControllerBase
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<MascotaController> _logger;
        private readonly IMascotaService _service;

        public MascotaController(pawstiesContext context,IMascotaService service, ILogger<MascotaController> logger)
        {
            _context = context;
            _service = service;
            _logger = logger;
        }

        [HttpGet ("pawstiesAPI/mascotas")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(IEnumerable<Mascotum>))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Get()
        {
            _logger.LogInformation("Calling method GetAllMascotas");
            var mascota = _context.Mascota;
            return Ok (mascota);
        }

        [HttpGet("pawstiesAPI/mascotas/get/{distance}")]
        [ProducesResponseType(StatusCodes.Status200OK, Type = typeof(IEnumerable<Mascotum>))]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public IActionResult Get([FromBody] JSONPoint point, int distance)
        {
            _logger.LogInformation("Calling method GetAllMascotas");
            var mascotas = _service.GetAll(point, distance);
            return Ok(mascotas);
        }

        [HttpGet ("pawstiesAPI/mascotas/{petID}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Mascotum))]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetMascota(int petID)
        {
            _logger.LogInformation($"Calling method GetMascota with petID {petID}", null);
            var mascota = _context.Mascota.Where(e => e.Petid == petID).FirstOrDefault();
            if (mascota == null)
            {
                return BadRequest("Unexistent petID");
            }
            return Ok(mascota);
        }

        [HttpPut ("pawstiesAPI/mascota/{petid}")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Update(int petid, Mascotum pet)
        {
            try
            {
                Mascotum mascota = _context.Mascota.Where(e => e.Petid == petid).FirstOrDefault();
                if (mascota == null)
                {
                    return BadRequest();
                }
                mascota.Nombre = pet.Nombre;
                mascota.Sexo = pet.Sexo;
                mascota.Edad = pet.Edad;
                mascota.RColor = pet.RColor;
                mascota.Vaxxed = pet.Vaxxed;
                mascota.RTemper = pet.RTemper;
                mascota.Pelaje = pet.Pelaje;
                mascota.Esterilizado = pet.Esterilizado;
                mascota.Discapacitado = pet.Discapacitado;
                mascota.RRescatista = pet.RRescatista;
                mascota.Nombre = pet.Nombre;
                mascota.Descripcion = pet.Descripcion;
                return Ok();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error during Mascota update with petid = {petid}");
                throw;
            }
        }
    }
}
