using System;
using System.Linq;
using PawstiesAPI.Helper;
using PawstiesAPI.Models;

namespace PawstiesAPI.Business
{
    public class UsuarioService
    {
        private readonly pawstiesContext _context;
        private readonly JwtSettings _jwtSettings;
        public UsuarioService(pawstiesContext context, JwtSettings jwtSettings)
        {
            _context = context;
            _jwtSettings = jwtSettings;
        }
        /*
        public Usuario Authenticate (string mail, string password)
        {
            var usuario = from u in _context.Usuarios
                          where u.Mail.Equals(mail) && u.Password.Equals(password)
                          select u;
            if (usuario == null) return usuario.;
        }*/
    }
}
