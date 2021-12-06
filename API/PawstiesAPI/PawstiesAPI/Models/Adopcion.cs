using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Adopcion
    {
        public int? RAdoptante { get; set; }
        public int? RMascota { get; set; }
        public DateTime? Fecha { get; set; }

        public virtual Adoptante RAdoptanteNavigation { get; set; }
        public virtual Mascotum RMascotaNavigation { get; set; }
    }
}
