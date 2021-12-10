using System;
using System.Collections;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IRescatistaService
    {
        //IEnumerable GetAll();
        Rescatistum GetRescatista(int rescatistaid);
        bool SaveRescatista(Rescatistum resc);
        bool Update(Rescatistum resc, int rescatistaid);
    }
}
