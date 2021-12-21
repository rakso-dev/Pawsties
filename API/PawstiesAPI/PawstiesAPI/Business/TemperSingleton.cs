using System;
using System.Collections.Generic;
using Microsoft.Extensions.Logging;
using PawstiesAPI.Models;
using PawstiesAPI.Singletons;

namespace PawstiesAPI.Business
{
    public class TemperSingleton: ITemperSingleton
    {
        private readonly pawstiesContext _context;
        private readonly ILogger<TemperSingleton> _logger;
        public TemperSingleton(pawstiesContext context, ILogger<TemperSingleton> logger)
        {
            _context = context;
            _logger = logger;
        }

        public IEnumerable<Temper> GetTempers()
        {
            try
            {
                var tempers = _context.Tempers;
                return tempers;
            } catch (Exception ex)
            {
                _logger.LogError(ex, $"Error while trying to retirve tempers");
                throw;
            }
        }
    }
}
