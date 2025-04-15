@echo off
start cmd /k "cd route_service && mvn spring-boot:run"
start cmd /k "cd vehicle_service && mvn spring-boot:run"
start cmd /k "cd schedule_service && mvn spring-boot:run"
start cmd /k "cd gateway_service && mvn spring-boot:run"