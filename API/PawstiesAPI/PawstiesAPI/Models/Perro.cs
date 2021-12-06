using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Perro
    {
        public int Petid { get; set; }
        public bool? Sexo { get; set; }
        public DateTime Edad { get; set; }
        public int RColor { get; set; }
        public bool? Vaxxed { get; set; }
        public int? RTemper { get; set; }
        public bool? Pelaje { get; set; }
        public bool? Esterilizado { get; set; }
        public bool? Discapacitado { get; set; }
        public int? RTalla { get; set; }

        public virtual Talla RTallaNavigation { get; set; }
    }
}
