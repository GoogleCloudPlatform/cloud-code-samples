FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-alpine AS base
WORKDIR /src
EXPOSE 80

FROM mcr.microsoft.com/dotnet/core/sdk:3.1-alpine AS build
WORKDIR /src
COPY . .
RUN dotnet restore helloworld.csproj
RUN dotnet build "./helloworld.csproj" -c Debug -o /out

FROM build AS publish
RUN dotnet publish helloworld.csproj -c Debug -o /out

# Building final image used in running container
FROM base AS final
WORKDIR /src
COPY --from=publish /out .
ENV ASPNETCORE_URLS=http://*:8080

# Installing vsdbg on the container to enable debugging of .NET Core
RUN apk update \
    && apk add unzip \
    && apk add procps \
    && apk add curl bash \
    && rm -rf /var/lib/apt/lists/* \
    && curl -sSL https://aka.ms/getvsdbgsh | bash /dev/stdin -v latest -l /vsdbg

ENTRYPOINT ["dotnet", "helloworld.dll"]