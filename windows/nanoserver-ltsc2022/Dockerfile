# escape=`

FROM golang:1.23.2-nanoserver-ltsc2022 AS builder
SHELL ["cmd", "/S", "/C"]
COPY build.bat C:/build/build.bat
RUN C:\build\build.bat

FROM mcr.microsoft.com/windows/nanoserver:ltsc2022-KB5011497
RUN mkdir C:\Registrator
COPY --from=builder C:/build/registrator.exe C:/Registrator/registrator.exe
ENTRYPOINT ["C:\\Registrator\\registrator.exe"]


