# escape=`

ARG REPO=github.com/gliderlabs/registrator
ARG COMMIT_HASH=4322fe00304d6de661865721b073dc5c7e750bd2

FROM golang:1.18.0-nanoserver-ltsc2022 AS builder
SHELL ["cmd", "/S", "/C"]
COPY build.bat C:/build/build.bat
RUN C:\build\build.bat

FROM mcr.microsoft.com/windows/nanoserver:ltsc2022
RUN mkdir C:\Registrator
COPY --from=builder C:/build/registrator.exe C:/Registrator/registrator.exe
ENTRYPOINT ["C:\\Registrator\\registrator.exe"]


