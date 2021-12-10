using System;
using PawstiesAPI.Models;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.Http;
using PawstiesAPI.Business;

namespace PawstiesAPI.Controllers
{
    public class RescatistaController: ControllerBase
    {
        private readonly RescatistaService _service;
        private readonly ILogger<RescatistaController> _logger;

        public RescatistaController(RescatistaService service, ILogger<RescatistaController> logger)
        {
            _service = service;
            _logger = logger;
        }

        /*
        [HttpGet ("pawstiesAPI/rescatista")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        public IActionResult Get()
        {
            var rescatista = _service.GetAll();
            return Ok(rescatista);
        }*/

        [HttpGet ("pawstiesAPI/rescatista/{rescatistaID}")]
        [ProducesResponseType (StatusCodes.Status200OK, Type = typeof(Rescatistum))]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult GetRescatista(int rescatistaID)
        {
            try
            {
                var rescatista = _service.GetRescatista(rescatistaID);
                _logger.LogInformation($"access to Rescatista on {rescatista.Ort.Coordinate}");
                return Ok( new {
                    image = rescatista.Image,
                    mail = rescatista.Mail,
                    telephone = rescatista.Telephone,
                    password = rescatista.Password,
                    rescatistaid = rescatista.Rescatistaid,
                    nombreEnt = rescatista.NombreEnt,
                    rfc = rescatista.Rfc,
                    latitude = rescatista.Ort.Coordinate.Y,
                    longitude = rescatista.Ort.Coordinate.X
                });
            } catch (Exception ex)
            {
                _logger.LogError(ex, "Error during query to Rescatista table");
                throw;
            }
        }

        [HttpPost ("pawstiesAPI/rescatista")]
        [ProducesResponseType (StatusCodes.Status200OK)]
        [ProducesResponseType (StatusCodes.Status400BadRequest)]
        [ProducesResponseType (StatusCodes.Status500InternalServerError)]
        public IActionResult Save([FromBody] Rescatistum rescatista)
        {
            if(_service.SaveRescatista(rescatista) == true)
            {
                return Ok();
            }
            return BadRequest();
        }

        [HttpPut ("pawstiesAPI/rescatista/{rescatistaid}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public IActionResult UpdateRescatista(Rescatistum r, int rescatistaid)
        {
            if(_service.Update(r, rescatistaid) == true)
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
