# scoreboard
Agile Developer Workshop - Scoreboard


## Development

### Releasing

1. Increment version following semantic release rules using this command:
   `mvn versions:set -DnewVersion=<newVersion>`
1. In the root project execute `mvn clean install` 
1. Inside the scoreboard/console folder execute
   `mvn clean package assembly:single assembly:single`
1. Go to the [release page](https://github.com/jlink-workshop/scoreboard/releases)
   and create a new release
1. Open the release and upload the built binary from `console/scoreboard-app.zip`
