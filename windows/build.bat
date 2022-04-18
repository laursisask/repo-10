@echo off

setlocal
set BUILDDIR=C:\build
set REPO=github.com/gliderlabs/registrator
set COMMIT_HASH=4322fe00304d6de661865721b073dc5c7e750bd2
mkdir %BUILDDIR% >NUL 2>&1
cd %BUILDDIR%

echo [+] Downloading dependencies
go mod init local/build
go get -d -v github.com/gliderlabs/registrator@%COMMIT_HASH%
for /f "tokens=1" %%a in ('go list -m -f "{{ .Version }}" %REPO%') do set VERSION=%%a
echo [+] Building
go build -ldflags "-X main.Version=%VERSION%" -o %BUILDDIR%\registrator.exe github.com/gliderlabs/registrator || exit /b 1
