using System;
using System.Collections;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IAdopcionService
    {
        IEnumerable GetAdopciones(int rescatistaid);
        bool SaveAdopcion(Adopcion adopcion, int adoptanteid, int petid);
    }
}
