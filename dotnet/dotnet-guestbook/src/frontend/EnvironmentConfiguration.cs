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
