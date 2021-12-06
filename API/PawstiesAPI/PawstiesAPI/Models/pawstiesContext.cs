using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace PawstiesAPI.Models
{
    public partial class pawstiesContext : DbContext
    {
        public pawstiesContext()
        {
        }

        public pawstiesContext(DbContextOptions<pawstiesContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Adopcion> Adopcions { get; set; }
        public virtual DbSet<Adoptante> Adoptantes { get; set; }
        public virtual DbSet<Color> Colors { get; set; }
        public virtual DbSet<Gato> Gatos { get; set; }
        public virtual DbSet<Mascotum> Mascota { get; set; }
        public virtual DbSet<Perro> Perros { get; set; }
        public virtual DbSet<Photo> Photos { get; set; }
        public virtual DbSet<Rescatistum> Rescatista { get; set; }
        public virtual DbSet<Talla> Tallas { get; set; }
        public virtual DbSet<Temper> Tempers { get; set; }
        public virtual DbSet<Usuario> Usuarios { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseNpgsql("Host=localhost;Database=pawsties;Username=postgres;Password=sqlserver");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasPostgresExtension("postgis")
                .HasAnnotation("Relational:Collation", "en_US.UTF-8");

            modelBuilder.Entity<Adopcion>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("adopcion");

                entity.Property(e => e.Fecha)
                    .HasColumnType("date")
                    .HasColumnName("fecha");

                entity.Property(e => e.RAdoptante).HasColumnName("r_adoptante");

                entity.Property(e => e.RMascota).HasColumnName("r_mascota");

                entity.HasOne(d => d.RAdoptanteNavigation)
                    .WithMany()
                    .HasForeignKey(d => d.RAdoptante)
                    .HasConstraintName("adopcion_r_adoptante_fkey");

                entity.HasOne(d => d.RMascotaNavigation)
                    .WithMany()
                    .HasForeignKey(d => d.RMascota)
                    .HasConstraintName("adopcion_r_mascota_fkey");
            });

            modelBuilder.Entity<Adoptante>(entity =>
            {
                entity.ToTable("adoptante");

                entity.Property(e => e.Adoptanteid).HasColumnName("adoptanteid");

                entity.Property(e => e.Apellidos)
                    .HasMaxLength(50)
                    .HasColumnName("apellidos");

                entity.Property(e => e.FechaDeNac)
                    .HasColumnType("date")
                    .HasColumnName("fecha_de_nac");

                entity.Property(e => e.Image).HasColumnName("image");

                entity.Property(e => e.Mail)
                    .HasMaxLength(320)
                    .HasColumnName("mail");

                entity.Property(e => e.Nombre)
                    .HasMaxLength(50)
                    .HasColumnName("nombre");

                entity.Property(e => e.Sexo)
                    .HasMaxLength(1)
                    .HasColumnName("sexo");

                entity.Property(e => e.Telephone)
                    .HasMaxLength(13)
                    .HasColumnName("telephone")
                    .IsFixedLength(true);
            });

            modelBuilder.Entity<Color>(entity =>
            {
                entity.HasKey(e => e.IdColor)
                    .HasName("pk_color");

                entity.ToTable("color");

                entity.Property(e => e.IdColor).HasColumnName("id_color");

                entity.Property(e => e.Name)
                    .HasMaxLength(15)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Gato>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("gato");

                entity.Property(e => e.Discapacitado).HasColumnName("discapacitado");

                entity.Property(e => e.Edad)
                    .HasColumnType("date")
                    .HasColumnName("edad");

                entity.Property(e => e.Esterilizado).HasColumnName("esterilizado");

                entity.Property(e => e.Pelaje).HasColumnName("pelaje");

                entity.Property(e => e.Petid)
                    .HasColumnName("petid")
                    .HasDefaultValueSql("nextval('mascota_petid_seq'::regclass)");

                entity.Property(e => e.RColor).HasColumnName("r_color");

                entity.Property(e => e.RRescatista).HasColumnName("r_rescatista");

                entity.Property(e => e.RTemper).HasColumnName("r_temper");

                entity.Property(e => e.Sexo).HasColumnName("sexo");

                entity.Property(e => e.Vaxxed).HasColumnName("vaxxed");
            });

            modelBuilder.Entity<Mascotum>(entity =>
            {
                entity.HasKey(e => e.Petid)
                    .HasName("pk_mascota");

                entity.ToTable("mascota");

                entity.Property(e => e.Petid).HasColumnName("petid");

                entity.Property(e => e.Discapacitado).HasColumnName("discapacitado");

                entity.Property(e => e.Edad)
                    .HasColumnType("date")
                    .HasColumnName("edad");

                entity.Property(e => e.Esterilizado).HasColumnName("esterilizado");

                entity.Property(e => e.Pelaje).HasColumnName("pelaje");

                entity.Property(e => e.RColor).HasColumnName("r_color");

                entity.Property(e => e.RRescatista).HasColumnName("r_rescatista");

                entity.Property(e => e.RTemper).HasColumnName("r_temper");

                entity.Property(e => e.Sexo).HasColumnName("sexo");

                entity.Property(e => e.Vaxxed).HasColumnName("vaxxed");

                entity.HasOne(d => d.RColorNavigation)
                    .WithMany(p => p.Mascota)
                    .HasForeignKey(d => d.RColor)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("mascota_r_color_fkey");

                entity.HasOne(d => d.RRescatistaNavigation)
                    .WithMany(p => p.Mascota)
                    .HasForeignKey(d => d.RRescatista)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("rescatista_fk");

                entity.HasOne(d => d.RTemperNavigation)
                    .WithMany(p => p.Mascota)
                    .HasForeignKey(d => d.RTemper)
                    .HasConstraintName("mascota_r_temper_fkey");
            });

            modelBuilder.Entity<Perro>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("perro");

                entity.Property(e => e.Discapacitado).HasColumnName("discapacitado");

                entity.Property(e => e.Edad)
                    .HasColumnType("date")
                    .HasColumnName("edad");

                entity.Property(e => e.Esterilizado).HasColumnName("esterilizado");

                entity.Property(e => e.Pelaje).HasColumnName("pelaje");

                entity.Property(e => e.Petid)
                    .HasColumnName("petid")
                    .HasDefaultValueSql("nextval('mascota_petid_seq'::regclass)");

                entity.Property(e => e.RColor).HasColumnName("r_color");

                entity.Property(e => e.RRescatista).HasColumnName("r_rescatista");

                entity.Property(e => e.RTalla).HasColumnName("r_talla");

                entity.Property(e => e.RTemper).HasColumnName("r_temper");

                entity.Property(e => e.Sexo).HasColumnName("sexo");

                entity.Property(e => e.Vaxxed).HasColumnName("vaxxed");

                entity.HasOne(d => d.RTallaNavigation)
                    .WithMany()
                    .HasForeignKey(d => d.RTalla)
                    .HasConstraintName("perro_r_talla_fkey");
            });

            modelBuilder.Entity<Photo>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("photos");

                entity.Property(e => e.RMascota).HasColumnName("r_mascota");

                entity.Property(e => e.Route).HasColumnName("route");

                entity.HasOne(d => d.RMascotaNavigation)
                    .WithMany()
                    .HasForeignKey(d => d.RMascota)
                    .HasConstraintName("photos_r_mascota_fkey");
            });

            modelBuilder.Entity<Rescatistum>(entity =>
            {
                entity.HasKey(e => e.Rescatistaid)
                    .HasName("pk_rescatista");

                entity.ToTable("rescatista");

                entity.Property(e => e.Rescatistaid).HasColumnName("rescatistaid");

                entity.Property(e => e.Image).HasColumnName("image");

                entity.Property(e => e.Latitude).HasColumnName("latitude");

                entity.Property(e => e.Longitude).HasColumnName("longitude");

                entity.Property(e => e.Mail)
                    .HasMaxLength(320)
                    .HasColumnName("mail");

                entity.Property(e => e.NombreEnt)
                    .HasMaxLength(30)
                    .HasColumnName("nombre_ent");

                entity.Property(e => e.Rfc)
                    .HasMaxLength(10)
                    .HasColumnName("rfc");

                entity.Property(e => e.Telephone)
                    .HasMaxLength(13)
                    .HasColumnName("telephone")
                    .IsFixedLength(true);
            });

            modelBuilder.Entity<Talla>(entity =>
            {
                entity.HasKey(e => e.IdTalla)
                    .HasName("pk_talla");

                entity.ToTable("talla");

                entity.Property(e => e.IdTalla).HasColumnName("id_talla");

                entity.Property(e => e.Nombre)
                    .HasMaxLength(30)
                    .HasColumnName("nombre");
            });

            modelBuilder.Entity<Temper>(entity =>
            {
                entity.HasKey(e => e.IdTemper)
                    .HasName("pk_temper");

                entity.ToTable("temper");

                entity.Property(e => e.IdTemper).HasColumnName("id_temper");

                entity.Property(e => e.Name)
                    .HasMaxLength(30)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<Usuario>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("usuario");

                entity.Property(e => e.Image).HasColumnName("image");

                entity.Property(e => e.Mail)
                    .HasMaxLength(320)
                    .HasColumnName("mail");

                entity.Property(e => e.Telephone)
                    .HasMaxLength(13)
                    .HasColumnName("telephone")
                    .IsFixedLength(true);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
