using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace frontend
{
    public interface IEnvironmentConfiguration
    {
        string BackendAddress { get; set; }
    }

    public class EnvironmentConfiguration : IEnvironmentConfiguration
    {
        public string BackendAddress { get; set; }
    }
}
