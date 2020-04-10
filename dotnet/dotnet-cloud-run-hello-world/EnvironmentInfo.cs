
namespace dotnet_cloud_run_hello_world
{
    public interface IEnvironmentInfo
    {
        string Revision { get; }
        string Service  { get; }
        string Project { get; }

        bool ProjectFound { get; }
    }

    public class EnvironmentInfo : IEnvironmentInfo
    {
        public EnvironmentInfo(string service, string project, string revision, bool projectFound)
        {
            Revision = revision;
            Service = service;
            Project = project;
            ProjectFound = projectFound;
        }

        public string Revision { get; private set; }
        public string Service { get; private set; }
        public string Project { get; private set; }
        public bool ProjectFound { get; private set; }
    }
}