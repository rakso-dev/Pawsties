using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Photo
    {
        public int? RMascota { get; set; }
        public string Route { get; set; }

        public virtual Mascotum RMascotaNavigation { get; set; }
    }
}
