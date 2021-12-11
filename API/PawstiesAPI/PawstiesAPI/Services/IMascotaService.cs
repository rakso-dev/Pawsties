using System;
using System.Collections;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IMascotaService
    {
        IEnumerable GetAll(JSONPoint point, int distance);
        IEnumerable GetMascotaByRescatista(int rescatistaid);
        Mascotum GetMascota(int id);
        //bool SaveMascota(Mascotum pet, int petid);
        //bool UpdateMascota(int id, Mascotum mascota);
    }
}
