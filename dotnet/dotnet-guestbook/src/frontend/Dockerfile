FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /src
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY . .
RUN dotnet restore frontend.csproj
RUN dotnet build "./frontend.csproj" -c Debug -o /out

FROM build AS publish
RUN dotnet publish frontend.csproj -c Debug -o /out

# Building final image used in running container
FROM base AS final
WORKDIR /src
COPY --from=publish /out .
ENV ASPNETCORE_URLS=http://*:8080

# Installing procps on the container to enable debugging of .NET Core
RUN apt -o Acquire::https::No-Cache=True -o Acquire::http::No-Cache=True update \
    && apt --fix-broken install \
    && apt-get update \
    && apt-get upgrade -y \
    && apt-get install -y --no-install-recommends apt-utils unzip \
    && apt-get install -y procps
ENTRYPOINT ["dotnet", "frontend.dll"]
