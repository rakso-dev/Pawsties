using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Singletons;

namespace PawstiesAPI.Business
{
    public class TallaSingleton: ITallaSingleton
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<TallaSingleton> _logger;

        public TallaSingleton(pawstiesContext context, ILogger<TallaSingleton> logger)
        {
            _context = context;
            _logger = logger;
        }

        public IEnumerable<Talla> GetTallas()
        {
            try
            {
                var tallas = _context.Tallas;
                return tallas;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, $"Error while trying to retrieve tallas");
                throw;
            }
        }
    }
}
