using System;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IAdoptanteService
    {
        Adoptante GetAdoptante(int adoptanteid);
        bool SaveAdoptante(Adoptante adoptante);
        bool UpdateAdoptante(Adoptante adoptante, int adoptanteid);
    }
}
