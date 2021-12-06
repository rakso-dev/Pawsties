using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Rescatistum
    {
        public Rescatistum()
        {
            Mascota = new HashSet<Mascotum>();
        }

        public string Image { get; set; }
        public string Mail { get; set; }
        public string Telephone { get; set; }
        public int Rescatistaid { get; set; }
        public string NombreEnt { get; set; }
        public string Rfc { get; set; }
        public decimal? Latitude { get; set; }
        public decimal? Longitude { get; set; }

        public virtual ICollection<Mascotum> Mascota { get; set; }
    }
}
