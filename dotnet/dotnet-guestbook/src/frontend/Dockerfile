FROM mcr.microsoft.com/dotnet/aspnet:7.0-alpine AS base
WORKDIR /src
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:7.0-alpine AS build
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
RUN apk update \
    && apk add unzip procps
ENTRYPOINT ["dotnet", "frontend.dll"]
