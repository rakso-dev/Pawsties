using System;
using System.Collections.Generic;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class Color
    {
        public Color()
        {
            Mascota = new HashSet<Mascotum>();
        }

        public int IdColor { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Mascotum> Mascota { get; set; }
    }
}
