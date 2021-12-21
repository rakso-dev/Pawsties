using System;
using System.Collections;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Services
{
    public interface IRescatistaService
    {
        //IEnumerable GetAll(JSONPoint point, int distance);
        Rescatistum GetRescatista(Rescatistum resc);//int rescatistaid);//string id);
        bool SaveRescatista(Rescatistum resc);
        bool Update(Rescatistum resc, int resatistaid);//string id);
    }
}
