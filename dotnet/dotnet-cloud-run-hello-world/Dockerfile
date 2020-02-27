FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-alpine AS base
WORKDIR /src

FROM mcr.microsoft.com/dotnet/core/sdk:3.1-alpine AS build
WORKDIR /src
COPY . .
RUN dotnet restore dotnet-cloud-run-hello-world.csproj
RUN dotnet build "./dotnet-cloud-run-hello-world.csproj" -c Debug -o /out

FROM build AS publish
RUN dotnet publish dotnet-cloud-run-hello-world.csproj -c Debug -o /out

# Building final image used in running container
FROM base AS final
WORKDIR /src
COPY --from=publish /out .
ENV ASPNETCORE_URLS=http://*:8080

ENTRYPOINT ["dotnet", "dotnet-cloud-run-hello-world.dll"]
