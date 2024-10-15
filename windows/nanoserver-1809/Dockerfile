# escape=`

FROM golang:1.17.6-nanoserver-1809 AS builder
SHELL ["cmd", "/S", "/C"]
COPY build.bat C:/build/build.bat
RUN C:\build\build.bat

FROM mcr.microsoft.com/windows/nanoserver:2009-KB4579311
RUN mkdir C:\Registrator
COPY --from=builder C:/build/registrator.exe C:/Registrator/registrator.exe
ENTRYPOINT ["C:\\Registrator\\registrator.exe"]


