using System;
using System.Collections.Generic;
using System.Linq;
using PawstiesAPI.Models;
using PawstiesAPI.Singletons;

namespace PawstiesAPI.Business
{
    public class ColorSingleton: IColorSingleton
    {
        private readonly pawstiesContext _context;
        
        public ColorSingleton(pawstiesContext context)
        {
            _context = context;
        }

        public IEnumerable<Color> GetColors()
        {
            var colores = _context.Colors;
            return colores;
        }
    }
}
