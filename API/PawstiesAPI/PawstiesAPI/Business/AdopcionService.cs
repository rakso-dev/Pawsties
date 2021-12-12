using System;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;

namespace PawstiesAPI.Business
{
    public class AdopcionService
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<AdopcionService> _logger;
        public AdopcionService(pawstiesContext context, ILogger<AdopcionService> logger)
        {
            _context = context;
            _logger = logger;
        }
    }
}
