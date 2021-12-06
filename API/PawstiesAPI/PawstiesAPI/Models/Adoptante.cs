using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Adoptante
    {
        public string Image { get; set; }
        public string Mail { get; set; }
        public string Telephone { get; set; }
        public int Adoptanteid { get; set; }
        public string Nombre { get; set; }
        public string Apellidos { get; set; }
        public char? Sexo { get; set; }
        public DateTime? FechaDeNac { get; set; }
    }
}
