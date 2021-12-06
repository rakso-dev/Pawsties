using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Temper
    {
        public Temper()
        {
            Mascota = new HashSet<Mascotum>();
        }

        public int IdTemper { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Mascotum> Mascota { get; set; }
    }
}
