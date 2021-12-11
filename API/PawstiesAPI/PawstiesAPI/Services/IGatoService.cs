using System;
using System.Collections;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IGatoService
    {
        IEnumerable GetAll(JSONPoint point, int distance);
        Gato GetGato(int id);
        bool SaveGato(Gato cat);
        bool UpdateGato(int id, Gato gato);
    }
}
