// Copyright 2023 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


namespace dotnet_cloud_run_hello_world
{
    public interface IEnvironmentInfo
    {
        string Revision { get; }
        string Service  { get; }
    }
    public class EnvironmentInfo : IEnvironmentInfo
    {
        public EnvironmentInfo(string service, string revision)
        {
            Revision = revision;
            Service = service;
        }

        public string Revision { get; private set; }
        public string Service { get; private set; }
    }
}