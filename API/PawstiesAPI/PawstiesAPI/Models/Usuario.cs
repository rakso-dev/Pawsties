using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Npgsql;
using NetTopologySuite;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Usuario
    {
        public string Image { get; set; }
        public string Mail { get; set; }
        public string Telephone { get; set; }
        //public  Ort { get; set; }
        /*[Range(-90, 90)]
        public double Longitude { get; set; }
        [Range()]
        public double Latitude { get; set; }*/

    }
}
