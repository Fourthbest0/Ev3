$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$envFile = Join-Path $root ".env"
if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        if ($_ -match '^\s*([^#][^=]*)=(.*)$') {
            $name = $matches[1].Trim(); $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($name, $value, "Process")
        }
    }
}
function Start-ServiceWindow($name,$folder,$command){$path=Join-Path $root $folder;Start-Process powershell -ArgumentList '-NoExit','-Command',"Set-Location '$path'; $command"}
Start-ServiceWindow 'Eureka' 'Eurekeando/eureka' '.\mvnw.cmd spring-boot:run'
Start-Sleep -Seconds 8
Start-ServiceWindow 'MS-A' 'MS-A' '.\mvnw.cmd spring-boot:run'
Start-ServiceWindow 'MS-B' 'MS-B' '.\mvnw.cmd spring-boot:run'
Start-ServiceWindow 'RATE' 'rate' '.\mvnw.cmd spring-boot:run'
Start-ServiceWindow 'Angular' 'frontend' 'npm install; npm start'
