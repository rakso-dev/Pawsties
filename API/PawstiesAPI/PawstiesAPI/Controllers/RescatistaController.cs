using System;
using Microsoft.AspNetCore.Mvc;
using PawstiesAPI.Models;
using System.Linq;
using System.Collections.Generic;

namespace PawstiesAPI.Controllers
{
    public class RescatistaController : ControllerBase
    {
        private readonly pawstiesContext _context;
        public RescatistaController(pawstiesContext context)
        {
            _context = context;
        }

        [HttpGet ("pawstiesAPI/rescatista/all")]
        [ProducesResponseType (200, Type = typeof (IEnumerable<Rescatistum>))]
        [ProducesResponseType (500)]
        public IActionResult Get()
        {
            var rescatista = _context.Rescatista;
            return Ok (rescatista);
        }

        [HttpGet ("pawstiesAPI/rescatista/{rescatistaID}")]
        [ProducesResponseType(200, Type = typeof(Rescatistum))]
        [ProducesResponseType(500)]
        public IActionResult GetRescatista (int rescatistaID)
        {
            var rescatista = _context.Rescatista.Where(e => e.Rescatistaid == rescatistaID).FirstOrDefault();
            return Ok(rescatista);
        }
    }
}
