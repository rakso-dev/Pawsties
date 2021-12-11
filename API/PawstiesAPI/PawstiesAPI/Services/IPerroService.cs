using System;
using System.Collections;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IPerroService
    {
        IEnumerable GetAll(JSONPoint point, int distance);
        Perro GetPerro(int petid);
        bool SavePerro(Perro dog);
        bool UpdatePerro(int id, Perro perro);
    }
}
