FROM mcr.microsoft.com/dotnet/aspnet:3.1 AS base
WORKDIR /src
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:3.1 AS build
WORKDIR /src
COPY . .
RUN dotnet restore backend.csproj
RUN dotnet build "./backend.csproj" -c Debug -o /out

FROM build AS publish
RUN dotnet publish backend.csproj -c Debug -o /out

# Building final image used in running container
FROM base AS final
WORKDIR /src
COPY --from=publish /out .
ENV ASPNETCORE_URLS=http://*:8080

# Installing vsdbg on the container to enable debugging of .NET Core
RUN apt-get update \
    && apt-get install -y unzip procps curl bash \
    && rm -rf /var/lib/apt/lists/* \
    && curl -sSL https://aka.ms/getvsdbgsh | bash /dev/stdin -v latest -l /vsdbg

ENTRYPOINT ["dotnet", "backend.dll"]
