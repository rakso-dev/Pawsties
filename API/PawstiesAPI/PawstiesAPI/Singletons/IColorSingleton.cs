using System;
using System.Collections.Generic;
using PawstiesAPI.Models;

namespace PawstiesAPI.Singletons
{
    public interface IColorSingleton
    {
        IEnumerable<Color> GetColors();
    }
}
