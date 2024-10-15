# escape=`

FROM golang:1.23.1-nanoserver-1809 AS builder
SHELL ["cmd", "/S", "/C"]
COPY build.bat C:/build/build.bat
RUN C:\build\build.bat

FROM mcr.microsoft.com/windows/nanoserver:1809-KB5011503
RUN mkdir C:\Registrator
COPY --from=builder C:/build/registrator.exe C:/Registrator/registrator.exe
ENTRYPOINT ["C:\\Registrator\\registrator.exe"]


