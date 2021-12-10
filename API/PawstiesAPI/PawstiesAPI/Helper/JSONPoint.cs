using System;
using System.Collections;
namespace PawstiesAPI.Helper
{
    public class JSONPoint: IJSONPoint
    {
        public double Latitude { get; set; }
        public double Longitude { get; set; }
    }
}
