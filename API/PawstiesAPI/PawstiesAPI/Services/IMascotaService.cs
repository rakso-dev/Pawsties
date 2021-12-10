using System;
using System.Collections;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IMascotaService
    {
        IEnumerable GetAll(JSONPoint point, int distance);
        Mascotum GetMascota(int id);
        bool SaveMascota(Mascotum mascota);
        bool UpdateMascota(int id, Mascotum mascota);
    }
}
