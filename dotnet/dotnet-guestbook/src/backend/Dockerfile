FROM mcr.microsoft.com/dotnet/core/aspnet:2.1 AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/core/sdk:2.1 AS build
WORKDIR /src
COPY . .
RUN dotnet restore backend.csproj
RUN dotnet build "./backend.csproj" -c Debug -o /app

FROM build AS publish
RUN dotnet publish backend.csproj -c Debug -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENV ASPNETCORE_URLS=http://*:8080
ENTRYPOINT ["dotnet", "backend.dll"]
